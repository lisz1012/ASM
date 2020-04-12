package com.lisz;

public class HelloProxy {
    public static void before() {
        System.out.println("Start: ");
    }

    public static void after() {
        System.out.println("I'm kidding, hahaha!");
    }
}
