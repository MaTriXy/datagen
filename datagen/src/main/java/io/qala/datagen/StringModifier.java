package io.qala.datagen;

import io.qala.datagen.adaptors.CommonsLang3RandomStringUtils;

import java.util.Arrays;

import static io.qala.datagen.RandomValue.between;
import static io.qala.datagen.RandomValue.upTo;

public interface StringModifier {
    String modify(String original);

    class Impls {
        static StringModifier spaces() {
            return multipleOf(' ');
        }

        static StringModifier spaceLeft() {
            return prefix(" ");
        }

        static StringModifier spacesLeft(int n) {
            char[] prefix = new char[n];
            Arrays.fill(prefix, ' ');
            return prefix(String.valueOf(prefix));
        }

        static StringModifier spaceRight() {
            return suffix(" ");
        }

        static StringModifier spacesRight(int n) {
            char[] suffix = new char[n];
            Arrays.fill(suffix, ' ');
            return suffix(String.valueOf(suffix));
        }

        static StringModifier specialSymbol() {
            return oneOf(Vocabulary.specialSymbols());
        }

        static StringModifier oneOf(String chars) {
            return oneOf(chars.toCharArray());
        }

        static StringModifier oneOf(final char... chars) {
            return new StringModifier() {
                @Override public String modify(String original) {
                    int index = upTo(original.length()).integer();
                    String symbol = CommonsLang3RandomStringUtils.random(1, chars);
                    return new StringBuilder(original).replace(index, index + 1, symbol).toString();
                }
            };
        }

        static StringModifier multipleOf(String chars) {
            return multipleOf(chars.toCharArray());
        }

        static StringModifier multipleOf(final char... chars) {
            return new StringModifier() {
                @Override public String modify(String original) {
                    int nOfSymbols = between(1, original.length()).integer();
                    StringBuilder stringBuilder = new StringBuilder(original);
                    for (int i = 0; i < nOfSymbols; i++) {
                        int index = upTo(original.length()).integer();
                        String symbol = CommonsLang3RandomStringUtils.random(1, chars);
                        stringBuilder.replace(index, index + 1, symbol);
                    }
                    return stringBuilder.toString();
                }
            };
        }

        static StringModifier prefix(final String prefix) {
            return new StringModifier() {
                @Override public String modify(String original) {
                    if (original.length() < prefix.length())
                        throw new IllegalArgumentException("Prefix cannot be longer than the main string");

                    StringBuilder stringBuilder = new StringBuilder(original);
                    stringBuilder.replace(0, prefix.length(), prefix);
                    return stringBuilder.toString();
                }
            };
        }
        static StringModifier suffix(final String suffix) {
            return new StringModifier() {
                @Override public String modify(String original) {
                    if(original.length() < suffix.length())
                        throw new IllegalArgumentException("Prefix cannot be longer than the main string");
                    StringBuilder stringBuilder = new StringBuilder(original);
                    stringBuilder.replace(original.length() - suffix.length(), original.length(), suffix);
                    return stringBuilder.toString();
                }
            };
        }
    }
}