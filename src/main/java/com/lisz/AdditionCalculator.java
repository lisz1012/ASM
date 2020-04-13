package com.lisz;

public class AdditionCalculator {
    public static void main(String[] args) {
        int res = add(1, 2);
        System.out.println("result: " + res);
    }

    private static int add(int a, int b) {
        return a + b;
    }
}
