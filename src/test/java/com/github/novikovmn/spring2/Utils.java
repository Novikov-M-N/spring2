package com.github.novikovmn.spring2;

public class Utils {
    public static <T> int sizeOf(Iterable<T> iterable) {
        int result = 0;
        for(T t : iterable) {
            result++;
        }
        return result;
    }
}
