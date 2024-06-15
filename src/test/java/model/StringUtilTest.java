package model;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.github.creme332.utils.StringUtil;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StringUtilTest {

    @ParameterizedTest
    @MethodSource("levenshteinDistanceDataProvider")
    void testLevenshteinDistance(String str1, String str2, int expected) {
        assertEquals(expected, StringUtil.levenshteinDistance(str1, str2));
    }

    static Stream<Arguments> levenshteinDistanceDataProvider() {
        return Stream.of(
            Arguments.of("Almond", "Coconut", 5),
            Arguments.of("Almond", "Almond", 0),
            Arguments.of("Almond", "Almon", 1),
            Arguments.of("Almond", "Almondd", 1),
            Arguments.of("Almond", "Almend", 1),
            Arguments.of("Almond", "", 6),
            Arguments.of("", "", 0)
        );
    }

    @ParameterizedTest
    @MethodSource("isSimilarDataProvider")
    void testIsSimilar(String str1, String str2, boolean expected) {
        assertEquals(expected, StringUtil.isSimilar(str1, str2));
    }

    static Stream<Arguments> isSimilarDataProvider() {
        return Stream.of(
            Arguments.of("Espresso", "Espresso", true),
            Arguments.of("Espreso", "Espresso", true),
            Arguments.of("Espressso", "Espresso", true),
            Arguments.of("", "Espresso", false),
            Arguments.of("123", "Espresso", false)
        );
    }
}
