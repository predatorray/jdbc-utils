package me.predatorray.jdbc;

import java.util.Map;

public final class Check {

    private Check() {}

    public static void argumentIsNotNull(Object arg, String msg)
            throws IllegalArgumentException {
        if (arg == null) {
            throw new IllegalArgumentException(msg);
        }
    }

    public static void argumentIsValid(boolean validExpr, String msg) {
        if (!validExpr) {
            throw new IllegalArgumentException(msg);
        }
    }

    public static void argumentIsPositive(int number, String msg) {
        if (number <= 0) {
            throw new IllegalArgumentException(msg);
        }
    }

    public static <K, V> void mapIsNotNullOrEmpty(Map<K, V> map, String msg) {
        if (map == null || map.isEmpty()) {
            throw new IllegalArgumentException(msg);
        }
    }
}
