package dev.drf.pokedex.maven.plugin.model.processor;

import dev.drf.pokedex.maven.plugin.model.processor.model.ModelClass;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class ModelUtils {
    private ModelUtils() {
    }

    public static void recursiveModelClassesBuild(@Nonnull Map<String, Set<String>> hierarchyMap,
                                                  @Nonnull Map<String, ModelClass.Builder> modelMap,
                                                  @Nonnull String className,
                                                  @Nonnull List<ModelClass> result) {
        var model = modelMap.get(className)
                .build();
        var subclasses = hierarchyMap.get(className);

        if (subclasses != null && !subclasses.isEmpty()) {
            var fields = model.getFields();

            for (String subclass : subclasses) {
                var subclassModel = modelMap.get(subclass);
                fields.forEach(subclassModel::addField);

                recursiveModelClassesBuild(hierarchyMap, modelMap, subclass, result);
            }
        }

        if (!model.isAbstractClass()) {
            result.add(model);
        }
    }
}
