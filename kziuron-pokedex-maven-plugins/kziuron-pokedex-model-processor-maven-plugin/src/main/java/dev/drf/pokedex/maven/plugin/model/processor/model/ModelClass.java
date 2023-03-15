package dev.drf.pokedex.maven.plugin.model.processor.model;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public final class ModelClass {
    private final String className;
    private final String classPackage;
    private final List<ModelField> fields;
    private final boolean abstractClass;

    private ModelClass(@Nonnull String className,
                      @Nonnull String classPackage,
                      @Nonnull List<ModelField> fields,
                      boolean abstractClass) {
        this.className = className;
        this.classPackage = classPackage;
        this.fields = fields;
        this.abstractClass = abstractClass;
    }

    @Nonnull
    public static Builder builder(@Nonnull String className,
                                  @Nonnull String classPackage) {
        return new Builder(className, classPackage);
    }

    @Nonnull
    public String getClassName() {
        return className;
    }

    @Nonnull
    public String getClassPackage() {
        return classPackage;
    }

    @Nonnull
    public List<ModelField> getFields() {
        return fields;
    }

    public boolean isAbstractClass() {
        return abstractClass;
    }

    @Override
    public String toString() {
        return "ModelClass{" +
                "className='" + className + '\'' +
                ", fields=" + fields +
                ", abstractClass=" + abstractClass +
                '}';
    }

    public static class Builder {
        private final String className;
        private final String classPackage;
        private final List<ModelField> fields = new ArrayList<>();
        private boolean abstractClass;

        public Builder(String className, String classPackage) {
            this.className = className;
            this.classPackage = classPackage;
        }

        public Builder addField(@Nonnull ModelField modelField) {
            this.fields.add(modelField);
            return this;
        }

        public Builder setAbstractClass(boolean abstractClass) {
            this.abstractClass = abstractClass;
            return this;
        }

        @Nonnull
        public ModelClass build() {
            return new ModelClass(this.className, this.classPackage,
                    this.fields, this.abstractClass);
        }
    }
}
