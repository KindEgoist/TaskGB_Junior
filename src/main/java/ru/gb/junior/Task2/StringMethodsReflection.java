package ru.gb.junior.Task2;

import java.lang.reflect.Method;

public class StringMethodsReflection {
    public static void main(String[] args) {
        Class<String> stringClass = String.class;

        Method[] methods = stringClass.getDeclaredMethods();

        for (Method method : methods) {
            System.out.println(method.getName());
        }
    }
}
