package dev.drf.pokedex.maven.plugin.model.processor.model;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class ModelField {
    private final ModelType type;
    private final String name;
    private final ModelType generic;

    private ModelField(@Nonnull ModelType type,
                       @Nonnull String name,
                       @Nullable ModelType generic) {
        this.type = type;
        this.name = name;
        this.generic = generic;
    }

    @Nonnull
    public static Builder builder() {
        return new Builder();
    }

    @Nonnull
    public ModelType getType() {
        return type;
    }

    @Nullable
    public ModelType getGeneric() {
        return generic;
    }

    @Nonnull
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "ModelField{" +
                "type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", generic='" + generic + '\'' +
                '}';
    }

    public static class Builder {
        private ModelType type;
        private String name;
        private ModelType generic;

        public Builder setType(@Nonnull ModelType type) {
            this.type = type;
            return this;
        }

        public Builder setName(@Nonnull String name) {
            this.name = name;
            return this;
        }

        public Builder setGeneric(@Nonnull ModelType generic) {
            this.generic = generic;
            return this;
        }

        @Nonnull
        public ModelField build() {
            return new ModelField(this.type, this.name, this.generic);
        }
    }
}
