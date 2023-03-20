package dev.drf.pokedex.maven.plugin.model.processor.generator;

import com.squareup.javapoet.*;
import dev.drf.pokedex.maven.plugin.model.processor.model.ModelClass;
import dev.drf.pokedex.maven.plugin.model.processor.model.ModelField;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nonnull;
import javax.lang.model.element.Modifier;
import java.util.ArrayList;
import java.util.List;

public final class ClassGenerator {
    private static final String CLONE_METHOD_NAME = "clone";
    private static final ClassName ARRAY_LIST_TYPE_NAME = ClassName.get(ArrayList.class);

    private ClassGenerator() {
    }

    @Nonnull
    public static String generate(@Nonnull List<ModelClass> models,
                                  @Nonnull String packageName,
                                  @Nonnull String className) {
        var classBuilder = TypeSpec.classBuilder(className)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(buildPrivateConstructor());

        for (ModelClass model : models) {
            classBuilder.addMethod(buildCloneMethod(model, packageName));
        }

        var javaFile = JavaFile.builder(packageName, classBuilder.build())
                .build();
        return javaFile.toString();
    }

    /**
     * Тут генерируется private-конструктор без параметров
     */
    @Nonnull
    private static MethodSpec buildPrivateConstructor() {
        return MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PRIVATE)
                .build();
    }

    /**
     * Генерируем метод clone для конкретной модели
     */
    @Nonnull
    private static MethodSpec buildCloneMethod(@Nonnull ModelClass model,
                                               @Nonnull String packageName) {
        TypeName elementClassName = ClassName.get(model.getClassPackage(), model.getClassName());

        var methodCodeBuilder = CodeBlock.builder()
                // следующий блок сделает функцию устойчивой к null-значениям
                .beginControlFlow("if (value == null)")
                .addStatement("return null")
                .endControlFlow()
                // далее объект копия / объект клон
                .addStatement("$T result = new $T()", elementClassName, elementClassName);

        for (ModelField field : model.getFields()) {
            var filedSetter = buildFieldClone(field, packageName);
            methodCodeBuilder.add(filedSetter);
        }

        methodCodeBuilder.addStatement("return result");

        return MethodSpec.methodBuilder(CLONE_METHOD_NAME)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(elementClassName)
                .addParameter(ParameterSpec.builder(elementClassName, "value")
                        .build())
                .addCode(methodCodeBuilder.build())
                .build();
    }

    /**
     * Вызов конкретного setter-а для заданного поля
     * Блок кода строится из типа данных
     * </p>
     * Логика работы упрощенная, из-за простоты модели
     * Развилка в обработке только для полей типа <i>List</i>
     * Для них задан тип <i>generic</i>, и для них нужно добавить отдельный блок
     * с обработкой коллекции
     * </p>
     * Так же для типов связанных с моделью нужно вызвать соответствующий метод clone
     * В итоге у нас три варианта работы, определяем какой и вызываем нужную функцию
     */
    @Nonnull
    private static CodeBlock buildFieldClone(@Nonnull ModelField field,
                                             @Nonnull String packageName) {
        if (field.getGeneric() != null) {
            // Обработка коллекции
            return buildCollectionStatement(field);
        } else {
            // Не коллекция
            // Определяем, нужно ли вызывать clone
            var fieldType = field.getType();

            if (!fieldType.isPrimitive()
                    && fieldType.getTypePackage().startsWith(packageName)) {
                return buildCloneStatement(field);
            }
        }

        return buildSetterStatement(field);
    }

    /**
     * Копирование коллекции:
     * 1) если коллекция не null, создать ArrayList с такой же длиной
     * 2) копировать в цикле через clone
     */
    @Nonnull
    private static CodeBlock buildCollectionStatement(@Nonnull ModelField field) {
        var fieldName = field.getName();
        var getterStr = getter(fieldName);
        var setterStr = setter(fieldName);

        var checkNullStatement = String.format("if (value.%s != null)", getterStr);
        var initArrayStatement = String.format("result.%s(new $T<$T>(value.%s.size()))", setterStr, getterStr);
        var forLoopStatement = String.format("for ($T item : value.%s)", getterStr);
        var addItemStatement = String.format("result.%s.add(item)", getterStr);

        var generic = field.getGeneric();
        var genericType = ClassName.get(generic.getTypePackage(), generic.getTypeClass());

        return CodeBlock.builder()
                // проверка на null
                .beginControlFlow(checkNullStatement, genericType)
                // инициализация
                .addStatement(initArrayStatement, ARRAY_LIST_TYPE_NAME, genericType)
                // цикл с добавлением элементов
                .beginControlFlow(forLoopStatement, genericType)
                .addStatement(addItemStatement)
                .endControlFlow()
                .endControlFlow()
                .build();
    }

    /**
     * Передать в setter вызов функции clone()
     */
    @Nonnull
    private static CodeBlock buildCloneStatement(@Nonnull ModelField field) {
        var fieldName = field.getName();
        var statement = String.format("result.%s(clone(value.%s))", setter(fieldName), getter(fieldName));

        return CodeBlock.builder()
                .addStatement(statement)
                .build();
    }

    /**
     * Вызов setter-а
     */
    @Nonnull
    private static CodeBlock buildSetterStatement(@Nonnull ModelField field) {
        var fieldName = field.getName();
        var statement = String.format("result.%s(value.%s)", setter(fieldName), getter(fieldName));

        return CodeBlock.builder()
                .addStatement(statement)
                .build();
    }

    /**
     * value -> setValue
     */
    @Nonnull
    private static String setter(@Nonnull String fieldName) {
        return "set" + StringUtils.capitalize(fieldName);
    }

    /**
     * value -> getValue()
     */
    @Nonnull
    private static String getter(@Nonnull String fieldName) {
        return "get" +StringUtils.capitalize(fieldName) + "()";
    }
}
