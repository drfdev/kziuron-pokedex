package dev.drf.pokedex.maven.plugin.model.processor;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import dev.drf.pokedex.maven.plugin.model.processor.model.ModelClass;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import dev.drf.pokedex.maven.plugin.model.processor.generator.ClassGenerator;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static dev.drf.pokedex.maven.plugin.model.processor.ModelUtils.recursiveModelClassesBuild;
import static dev.drf.pokedex.maven.plugin.model.processor.PluginUtils.*;

@Mojo(name = "process-model", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
public class ModelProcessorMojo extends AbstractMojo {
    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    private MavenProject project;

    @Parameter(property = "process.model.package", required = true, readonly = true)
    private String modelPackage;

    @Parameter(property = "process.model.interface", required = true, readonly = true)
    private String mainInterface;

    @Parameter(property = "result.clas.name", required = true, readonly = true)
    private String cloneName;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("### --------------------------- ###");
        getLog().info("Pokedex Model Processor plugin");
        getLog().info("### --------------------------- ###");
        getLog().info("Package: " + modelPackage);
        getLog().info("Main Interface Class Name: " + mainInterface);
        getLog().info("Output Util-Class Name: " + cloneName);

        var build = project.getBuild();

        if (build == null) {
            throw new MojoExecutionException("Build is null, something goes wrong");
        }

        final var sourceDirectory = build.getSourceDirectory() + packageToFolder(modelPackage);
        final var outputDirectory = build.getDirectory() + OUTPUT_DIRECTORY;

        getLog().info("Source directory: " + sourceDirectory);
        getLog().info("Generated source target directory: " + outputDirectory);

        var files = loadAllFileFromSource(sourceDirectory);
        var parsedUnits = parseSourceClasses(files);
        var modelClasses = filterModelClasses(parsedUnits, mainInterface);

        var classGenerated = ClassGenerator.generate(modelClasses, modelPackage, cloneName);

        var classDirectory = outputDirectory + packageToFolder(modelPackage);
        try {
            Files.createDirectories(Paths.get(classDirectory));

            var classFilePath = Paths.get(classDirectory, cloneName + ".java");
            Files.writeString(classFilePath, classGenerated, StandardOpenOption.CREATE);

            getLog().info("Write file: " + classFilePath);
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new MojoFailureException("Failed to write class into: " + classDirectory, ex);
        }

        getLog().info("### --------------------------- ###");
        getLog().info("End of generation");
        getLog().info("### --------------------------- ###");
    }

    /**
     * Загрузим из папки исходников все java-классы
     * В результате рекурсивного обхода папки получим список Path
     * со всеми файлами, которые могут быть классами
     */
    @Nonnull
    private List<Path> loadAllFileFromSource(@Nonnull String sourceDirectory) throws MojoExecutionException {
        try {
            try (Stream<Path> walkStream = Files.walk(Paths.get(sourceDirectory))) {
                return walkStream
                        .filter(path -> !Files.isDirectory(path))
                        .filter(PluginUtils::isJavaFile)
                        .collect(Collectors.toList());
            }
        } catch (Exception ex) {
            throw new MojoExecutionException("Load source directory error", ex);
        }
    }

    /**
     * Загрузим каждый класс из списка paths
     */
    @Nonnull
    private List<CompilationUnit> parseSourceClasses(@Nonnull List<Path> files) throws MojoExecutionException {
        var parser = new JavaParser();
        var parsedUnits = new ArrayList<CompilationUnit>(files.size());

        for (Path file : files) {
            try {
                var result = parser.parse(file);
                if (!result.isSuccessful()) {
                    throw new MojoExecutionException("Unsuccessful parse, class: " + file);
                }
                var parseResult = result.getResult();
                if (parseResult.isEmpty()) {
                    throw new MojoExecutionException("Unsuccessful parse with empty result, class: " + file);
                }
                parsedUnits.add(parseResult.get());
            } catch (Exception ex) {
                throw new MojoExecutionException(ex);
            }
        }

        return parsedUnits;
    }

    /**
     * По загруженным классам построим дерево их наследования
     * Затем по заданному интерфейсу отрежем все лишнее
     * Так же исключим все абстрактные классы
     * <p>
     * В результате должен получиться список классов со всеми полями
     */
    @Nonnull
    private List<ModelClass> filterModelClasses(@Nonnull List<CompilationUnit> parsedUnits,
                                                @Nonnull String mainInterface) throws MojoExecutionException {
        var hierarchyMap = new HashMap<String, Set<String>>();
        var modelMap = new HashMap<String, ModelClass.Builder>();

        for (CompilationUnit unit : parsedUnits) {
            var parentClass = getParentClass(unit);
            var unitClass = getUnitClass(unit);
            var modelBuilder = convertToCompilationUnit(unit);

            modelMap.put(unitClass, modelBuilder);

            var valueSet = hierarchyMap.computeIfAbsent(parentClass, it -> new HashSet<>());
            valueSet.add(unitClass);
        }

        // Требуется добавить поля родительских классов в наследников
        // Так же нужно отфильтровать все абстрактные классы
        var result = new ArrayList<ModelClass>();

        // Поиск и отсечение лишних классов нужно сделать по mainInterface
        recursiveModelClassesBuild(hierarchyMap, modelMap, mainInterface, result);
        return result;
    }
}
