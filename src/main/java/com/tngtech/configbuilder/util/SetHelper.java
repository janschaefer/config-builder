package com.tngtech.configbuilder.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Provides newHashSet known from Guava
 */
public class SetHelper {
    public static <T> Set<T> newHashSet(T... values) {
        return new HashSet<T>(Arrays.asList(values));
    }
}
