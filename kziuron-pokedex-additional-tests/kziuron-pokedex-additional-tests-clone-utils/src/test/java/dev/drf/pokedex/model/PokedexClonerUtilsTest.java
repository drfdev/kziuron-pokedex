package dev.drf.pokedex.model;

import org.junit.jupiter.api.Test;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PokedexClonerUtilsTest {

    @Test
    void shouldCorrectClone_whenPokemonObjectRandomlyGenerated() throws Exception {
        // arrange
        var random = RandomBeanModelUtils.buildEnhancedRandom();
        var value = random.nextObject(Pokemon.class);

        // act
        var result = PokedexClonerUtils.clone(value);

        // assert
        assertNotNull(result);
        recursiveAssert(value, result);
    }

    private <T extends IdentifiableEntity> void recursiveAssert(@Nonnull T expected,
                                                                @Nonnull T tested) throws Exception {
        var type = expected.getClass();
        var fields = loadFields(type);

        for (Field field : fields) {
            field.setAccessible(true);

            var expectedFieldValue = field.get(expected);
            var testedFieldValue = field.get(tested);

            if (expectedFieldValue == null) {
                // если ожидаемое значение null, то и тестируемое тоже null
                assertNull(testedFieldValue);
            } else if (expectedFieldValue instanceof IdentifiableEntity) {
                // если часть модели - нужно зайти вглубь
                var expectedRecursive = (IdentifiableEntity) expectedFieldValue;
                var testedRecursive = (IdentifiableEntity) testedFieldValue;

                recursiveAssert(expectedRecursive, testedRecursive);
            } else if (expectedFieldValue instanceof List<?>) {
                // Если список - нужно пройтись по каждому элементу
                // А затем зайти вглубь
                var expectedList = (List<? extends IdentifiableEntity>) expectedFieldValue;
                var testedList = (List<? extends IdentifiableEntity>) testedFieldValue;

                assertNotNull(expectedList);
                assertNotNull(testedList);

                assertEquals(expectedList.size(), testedList.size());

                var limit = expectedList.size();
                for (int index = 0; index < limit; index++) {
                    var expectedByIndex = expectedList.get(index);
                    var testedByIndex = testedList.get(index);

                    recursiveAssert(expectedByIndex, testedByIndex);
                }
            } else {
                // для остальных значений - просто сравнение
                assertEquals(expectedFieldValue, testedFieldValue);
            }
        }
    }

    @Nonnull
    private List<Field> loadFields(@Nonnull Class<?> type) {
        var result = new ArrayList<Field>();

        var indexedType = type;
        while (indexedType != Object.class) {
            var fields = indexedType.getDeclaredFields();
            result.addAll(Arrays.asList(fields));

            indexedType = indexedType.getSuperclass();
        }

        return result;
    }
}
