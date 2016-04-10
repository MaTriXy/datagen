package io.qala.datagen;

import org.junit.gen5.api.DisplayName;
import org.junit.gen5.api.Nested;
import org.junit.gen5.api.Test;
import org.junit.gen5.junit4.runner.JUnit5;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.List;
import java.util.Random;

import static io.qala.datagen.ContainsOneOfMatcher.containsOneOf;
import static io.qala.datagen.RandomValue.*;
import static io.qala.datagen.StringModifier.Impls.oneOf;
import static io.qala.datagen.StringModifier.Impls.spaces;
import static io.qala.datagen.StringModifier.Impls.specialSymbol;
import static io.qala.datagen.Vocabulary.specialSymbols;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.gen5.api.Assertions.expectThrows;

@RunWith(JUnit5.class)
@SuppressWarnings("ThrowableResultOfMethodCallIgnored")
public class RandomValueTest {
    @Nested @DisplayName("Integer Generator") class Integers {
        @Test void returnsPositiveIntegers() {
            assertThat(upTo(Integer.MAX_VALUE).integer(), greaterThan(0));
            assertThat(positiveInteger(), greaterThan(0));
        }
        @Test void returnsNegativeIntegers() {
            assertThat(between(Integer.MIN_VALUE, -1).integer(), lessThan(0));
        }
        @Test void returnsSameNumberIfBoundariesEqual() {
            int boundary = new Random().nextInt();
            assertEquals(boundary, between(boundary, boundary).integer());
        }
        @Test void returnsAnyInteger() {
            assertNotNull(anyInteger());
        }

        @Test void throwsIfMaxBoundaryGreaterThanInteger() {
            expectThrows(NumberOutOfBoundaryException.class, () -> upTo(GREATER_THAN_MAX_INT).integer());
        }
        @Test void throwsIfMinBoundaryLessThanInteger() {
            expectThrows(NumberOutOfBoundaryException.class, () -> between(LESS_THAN_INT_MIN, LESS_THAN_INT_MIN).integer());
        }
    }

    @Nested @DisplayName("String Generator") class Strings {
        @Test void returnsStringUpToMaxBoundary() {
            assertThat(upTo(100).alphanumeric().length(), lessThanOrEqualTo(100));
        }
        @Test void returnsStringBetweenBoundaries() {
            assertThat(between(10, 100).alphanumeric().length(), lessThanOrEqualTo(100));
            assertThat(between(10, 100).alphanumeric().length(), greaterThanOrEqualTo(10));
        }
        @Test void returnsStringWithExactLength() {
            assertThat(length(100).alphanumeric().length(), equalTo(100));
        }
        @Test void returnsEmptyStringIfLengthIsSetTo0() {
            assertThat(length(0).alphanumeric(), emptyString());
        }
        @Test void returnsNumbersIfNumeric() {
            assertThat(length(1000).numeric(), containsString("1"));
        }
        @Test void doesNotReturnsNumbersIfEnglishRequested() {
            assertThat(length(100).english(), not(containsString("1")));
        }

        @Test void createsStringWithSpecialSymbols() {
            assertThat(length(1000).specialSymbols(), containsString(","));
        }
        @Test void addsSpecialSymbolsViaStringModifiers() {
            assertThat(length(100).with(specialSymbol()).english(), containsOneOf(specialSymbols()));
        }
        @Test void doesNotModifyLengthIfAddsSpecialSymbol() {
            assertThat(length(100).with(specialSymbol()).english().length(), equalTo(100));
        }
        @Test void addsOneOfPassedSymbols() {
            assertThat(length(100).with(oneOf(",")).english(), containsString(","));
        }
        @Test void addsSpacesViaStringModifier() {
            assertThat(length(100).with(spaces()).numeric(), containsString(" "));
        }

        @Test void createsMultipleStringsInBatchMode() {
            List<String> alphanumerics = upTo(10).alphanumerics(5);
            assertThat(alphanumerics.size(), equalTo(5));
        }
        @Test void createsUpTo100StringsInBatchMode() {
            List<String> alphanumerics = upTo(10).alphanumerics();
            assertThat(alphanumerics.size(), greaterThanOrEqualTo(1));
            assertThat(alphanumerics.size(), lessThanOrEqualTo(100));
        }

        @Test void throwsIfMinBoundaryIsNegative() {
            expectThrows(NumberOutOfBoundaryException.class, () -> between(-1, 10).alphanumeric());
        }
    }
    @Nested @DisplayName("Date Generator") class Dates {
        @Test void returnsDateUpToMaxBoundary() {
            assertThat(upTo(new Date()).date(), lessThanOrEqualTo(new Date()));
        }
        @Test void startsDatesFrom1970_ifUpToUsed() {
            assertThat(upTo(new Date()).date(), greaterThanOrEqualTo(new Date(0)));
        }
        @Test void createsDatesBetweenBoundaries() {
            Date to = new Date();
            Date from = new Date();
            assertThat(between(from, to).date(), greaterThanOrEqualTo(from));
            assertThat(between(from, to).date(), lessThanOrEqualTo(to));
        }
    }

    private static final long
            LESS_THAN_INT_MIN = ((long) Integer.MIN_VALUE) - 1,
            GREATER_THAN_MAX_INT = ((long) Integer.MAX_VALUE) + 1;
}