package dev.drf.pokedex.maven.plugin.model.processor.model;

import javax.annotation.Nonnull;

public final class ModelType {
    public static final String PRIMITIVE_STUB = "__primitive__";

    private final String typePackage;
    private final String typeClass;

    private ModelType(@Nonnull String typePackage,
                      @Nonnull String typeClass) {
        this.typePackage = typePackage;
        this.typeClass = typeClass;
    }

    @Nonnull
    public static ModelType of(@Nonnull String typePackage,
                               @Nonnull String typeClass) {
        return new ModelType(typePackage, typeClass);
    }

    @Nonnull
    public static ModelType primitive(@Nonnull String typeClass) {
        return new ModelType(PRIMITIVE_STUB, typeClass);
    }

    @Nonnull
    public String getTypePackage() {
        return typePackage;
    }

    @Nonnull
    public String getTypeClass() {
        return typeClass;
    }

    public boolean isPrimitive() {
        return PRIMITIVE_STUB.equals(this.typePackage);
    }

    @Override
    public String toString() {
        return "ModelType{" +
                "typePackage='" + typePackage + '\'' +
                ", typeClass='" + typeClass + '\'' +
                '}';
    }
}
