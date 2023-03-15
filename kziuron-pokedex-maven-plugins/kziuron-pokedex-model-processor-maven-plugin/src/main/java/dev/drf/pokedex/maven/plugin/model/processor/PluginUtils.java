package dev.drf.pokedex.maven.plugin.model.processor;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.Type;
import dev.drf.pokedex.maven.plugin.model.processor.model.ModelClass;
import dev.drf.pokedex.maven.plugin.model.processor.model.ModelField;
import dev.drf.pokedex.maven.plugin.model.processor.model.ModelType;
import org.apache.maven.plugin.MojoExecutionException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.nio.file.Path;
import java.util.Set;

public final class PluginUtils {
    public static final String OUTPUT_DIRECTORY = "/generated-sources/model-processor";

    private static final String PACKAGE_DELIMITER = ".";
    private static final String DIRECTORY_DELIMITER = "/";

    private static final String JAVA_EXTENSION = ".java";

    private static final Set<String> JAVA_LANG_MODEL_TYPES = Set.of("String", "Integer", "Long", "Float",
            "Double", "Boolean", "Byte", "Character");
    private static final Set<String> JAVA_PRIMAL_TYPES = Set.of("byte", "short", "int", "long", "float",
            "double", "boolean", "char");
    private static final String JAVA_LANG_PACKAGE = "java.lang";


    private PluginUtils() {
    }

    @Nonnull
    public static String packageToFolder(@Nonnull String packageName) {
        return DIRECTORY_DELIMITER + packageName.replace(PACKAGE_DELIMITER, DIRECTORY_DELIMITER);
    }

    public static boolean isJavaFile(@Nonnull Path path) {
        var stringValue = path.toString().toLowerCase();
        return stringValue.endsWith(JAVA_EXTENSION);
    }

    @Nonnull
    public static ModelClass.Builder convertToCompilationUnit(@Nonnull CompilationUnit unit) throws MojoExecutionException {
        var primaryType = getPrimaryType(unit);
        var className = primaryType.getNameAsString();
        var classPackage = unit.getPackageDeclaration()
                .orElseThrow(() -> new MojoExecutionException("Class should contain package: " + unit))
                .getNameAsString();

        var imports = unit.getImports();
        var packageName = getPackageName(unit);

        var builder = ModelClass.builder(className, classPackage);

        if (primaryType instanceof ClassOrInterfaceDeclaration) {
            var classOrInterface = primaryType.asClassOrInterfaceDeclaration();

            builder.setAbstractClass(classOrInterface.isInterface() || classOrInterface.isAbstract());
        }

        var fields = primaryType.getFields();
        for (FieldDeclaration field : fields) {
            var filedBuilder = ModelField.builder();

            for (VariableDeclarator variable : field.getVariables()) {
                var fieldType = variable.getType();
                var fieldName = variable.getName();
                String typeName;

                if (fieldType instanceof ClassOrInterfaceType) {
                    var classOrInterface = fieldType.asClassOrInterfaceType();
                    typeName = classOrInterface.getName().asString();

                    if (classOrInterface.getTypeArguments().isPresent()) {
                        var types = classOrInterface.getTypeArguments().get();
                        for (Type type : types) {
                            var generic = buildNameWithPackage(type.asString(), packageName, imports);
                            filedBuilder.setGeneric(generic);
                        }
                    }

                } else {
                    typeName = variable.getType().asString();
                }
                filedBuilder.setName(fieldName.asString());
                filedBuilder.setType(buildNameWithPackage(typeName, packageName, imports));
            }

            builder.addField(filedBuilder.build());
        }

        return builder;
    }

    @Nullable
    public static String getParentClass(@Nonnull CompilationUnit unit) throws MojoExecutionException {
        var primaryType = getPrimaryType(unit);
        var packageName = getPackageName(unit);

        var imports = unit.getImports();

        if (primaryType instanceof ClassOrInterfaceDeclaration) {
            var classOrInterface = primaryType.asClassOrInterfaceDeclaration();

            var classExtends = classOrInterface.getExtendedTypes();
            if (classExtends != null) {
                for (ClassOrInterfaceType classExtend : classExtends) {
                    var className = classExtend.getNameAsString();
                    var type = buildNameWithPackage(className, packageName, imports);
                    return toStringType(type);
                }
            }

            var classInterfaces = classOrInterface.getImplementedTypes();
            if (classInterfaces != null) {
                // Выбираем первый попавшийся интерфейс
                for (ClassOrInterfaceType classInterface : classInterfaces) {
                    var className = classInterface.getNameAsString();
                    var type = buildNameWithPackage(className, packageName, imports);
                    return toStringType(type);
                }
            }
        }

        return null;
    }

    @Nonnull
    public static String getUnitClass(@Nonnull CompilationUnit unit) throws MojoExecutionException {
        var primaryType = getPrimaryType(unit);
        var packageName = getPackageName(unit);

        var className = primaryType.getNameAsString();

        return packageName + PACKAGE_DELIMITER + className;
    }

    @Nonnull
    private static TypeDeclaration<?> getPrimaryType(@Nonnull CompilationUnit unit) throws MojoExecutionException {
        return unit.getPrimaryType()
                .orElseThrow(() -> new MojoExecutionException("No primary type for parsed class: " + unit));
    }

    @Nonnull
    private static String getPackageName(@Nonnull CompilationUnit unit) throws MojoExecutionException {
        var packageInfo = unit.getPackageDeclaration()
                .orElseThrow(() -> new MojoExecutionException("Class should be in package: " + unit));

        return packageInfo.getName().asString();
    }

    @Nonnull
    private static ModelType buildNameWithPackage(@Nonnull String className,
                                                  @Nonnull String packageName,
                                                  @Nullable NodeList<ImportDeclaration> imports) {
        if (JAVA_PRIMAL_TYPES.contains(className)) {
            return ModelType.primitive(className);
        }

        if (JAVA_LANG_MODEL_TYPES.contains(className)) {
            return ModelType.of(JAVA_LANG_PACKAGE, className);
        }

        if (imports != null) {
            for (ImportDeclaration imp : imports) {
                var importName = imp.getNameAsString();
                if (importName.endsWith(className)) {
                    return modelTypeFrom(importName);
                }
            }
        }

        return ModelType.of(packageName, className);
    }

    @Nonnull
    private static ModelType modelTypeFrom(@Nonnull String importName) {
        var separatorIndex = importName.lastIndexOf(PACKAGE_DELIMITER);
        return ModelType.of(
                importName.substring(0, separatorIndex),
                importName.substring(separatorIndex + 1)
        );
    }

    @Nonnull
    private static String toStringType(@Nonnull ModelType type) {
        if (type.isPrimitive()) {
            return type.getTypeClass();
        }

        return type.getTypePackage() + PACKAGE_DELIMITER + type.getTypeClass();
    }
}
