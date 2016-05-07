package io.qala.datagen;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static io.qala.datagen.RandomElements.from;
import static io.qala.datagen.RandomValue.*;

/**
 * If you need a more flexible way of generating the values including different string modifiers like prefixes and
 * suffixes, use {@link RandomValue}.
 */
public class RandomShortApi {
    private RandomShortApi() {
    }
    public static int integer(int max) {
        return upTo(max).integer();
    }
    public static int integer(int min, int max) {
        return between(min, max).integer();
    }
    public static int integer() {
        return between(Integer.MIN_VALUE, Integer.MAX_VALUE).integer();
    }
    public static int positiveInteger() {
        return upTo(Integer.MAX_VALUE).integer();
    }

    public static String alphanumeric(int exactLength) {
        return length(exactLength).alphanumeric();
    }
    public static String alphanumeric(int min, int max) {
        return between(min, max).alphanumeric();
    }

    public static String numeric(int exactLength) {
        return length(exactLength).numeric();
    }
    public static String numeric(int min, int max) {
        return between(min, max).numeric();
    }

    public static String english(int exactLength) {
        return length(exactLength).english();
    }
    public static String english(int min, int max) {
        return between(min, max).english();
    }

    /**
     * Generates unicode string of variable length that includes characters from different languages, weird symbols
     * and <a href="https://docs.oracle.com/javase/tutorial/i18n/text/supplementaryChars.html">Supplementary Characters</a>
     * that are comprised of multiple chars.
     *
     * @param exactLength length of the string to be returned
     * @return unicode characters including different languages and weird symbols
     */
    public static String unicode(int exactLength) {
        return length(exactLength).unicode();
    }

    /**
     * Generates unicode string of variable length that includes characters from different languages, weird symbols
     * and <a href="https://docs.oracle.com/javase/tutorial/i18n/text/supplementaryChars.html">Supplementary Characters</a>
     * that are comprised of multiple chars.
     *
     * @param min min boundary of the string length
     * @param max max boundary of the string length
     * @return unicode characters including different languages and weird symbols
     */
    public static String unicode(int min, int max) {
        return between(min, max).unicode();
    }

    public static String specialSymbols(int exactLength) {
        return length(exactLength).specialSymbols();
    }
    public static String specialSymbols(int min, int max) {
        return between(min, max).specialSymbols();
    }

    /**
     * Returns an array of random booleans (true/false).
     *
     * @param n size of the resulting array
     * @return an array of random booleans
     */
    public static boolean[] bools(int n) {
        boolean[] result = new boolean[n];
        for (int i = 0; i < n; i++) {
            result[i] = bool();
        }
        return result;
    }

    public static boolean bool() {
        return RandomValue.RANDOM.nextBoolean();
    }

    /**
     * Returns true with the specified probability.
     *
     * @param probabilityOfTrue the probability that true is to be returned, must be between 0 and 1
     * @return true with the specified probability. Always returns true if 1 is passed and always false if 0 is passed.
     */
    @SuppressWarnings("SimplifiableIfStatement")
    public static boolean weighedTrue(double probabilityOfTrue) {
        if (probabilityOfTrue == 0.0) return false;
        if (probabilityOfTrue == 1.0) return true;
        return probabilityOfTrue >= RANDOM.nextDouble();
    }
    /**
     * Besides returning TRUE or FALSE it can also return {@code null}.
     *
     * @return TRUE, FALSE or {@code null}
     */
    public static Boolean nullableBool() {
        return from(Boolean.TRUE, Boolean.FALSE, null).sample();
    }

    /**
     * Returns random element from the collection.
     *
     * @return a random element from the collection
     * @see RandomElements
     */
    public static <T> T sample(Collection<T> toSampleFrom) {
        return from(toSampleFrom).sample();
    }

    /**
     * Returns random element from the collection.
     *
     * @param toSampleFrom the population of the elements you'd like to get a random value from
     * @return a random element from the collection
     * @see RandomElements
     */
    @SafeVarargs public static <T> T sample(T... toSampleFrom) {
        return from(toSampleFrom).sample();
    }

    /**
     * Returns a random element from the collection. Is used in case you have a collection and then couple of other
     * elements you want to sample from too, but you don't want to create a collection that includes all of them
     * combined.
     *
     * @param elements the main collection to sample from
     * @param others   other elements you'd like to include into population to sample from
     * @return a random element from all the listed elements/other elements
     * @see RandomElements
     */
    @SafeVarargs public static <T> T sample(Collection<T> elements, T... others) {
        return from(elements, others).sample();
    }

    /**
     * Returns multiple random elements from the specified collection.
     *
     * @param toSampleFrom the population of the elements you'd like to get a random value from
     * @return a random element from the collection
     */
    public static <T> List<T> sampleMultiple(Collection<T> toSampleFrom) {
        return sampleMultiple(integer(toSampleFrom.size()), toSampleFrom);
    }

    /**
     * Returns multiple random elements from the specified collection.
     *
     * @param toSampleFrom the population of the elements you'd like to get a random value from
     * @return a random element from the collection
     */
    public static <T> Set<T> sampleMultiple(Set<T> toSampleFrom) {
        return new HashSet<T>(sampleMultiple((Collection<T>) toSampleFrom));
    }

    /**
     * Returns multiple random elements from the specified collection.
     *
     * @param toSampleFrom the population of the elements you'd like to get a random value from
     * @param nToReturn    number of elements to be returned, must be smaller than the collection size
     * @return a random element from the collection
     */
    public static <T> List<T> sampleMultiple(int nToReturn, Collection<T> toSampleFrom) {
        return from(toSampleFrom).sample(nToReturn);
    }

    /**
     * Returns random element from the collection.
     *
     * @param toSampleFrom the population of the elements you'd like to get a random value from
     * @param nToReturn    number of elements to be returned, must be smaller than the collection size
     * @return a random element from the collection
     */
    @SafeVarargs public static <T> List<T> sampleMultiple(int nToReturn, T... toSampleFrom) {
        return from(toSampleFrom).sample(nToReturn);
    }
}
