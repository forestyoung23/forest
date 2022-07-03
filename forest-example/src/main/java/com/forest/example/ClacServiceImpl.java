package com.forest.example;

/**
 * @author Forest Dong
 * @date 2022年07月03日 17:05
 */
public class ClacServiceImpl implements ClacService {
    @Override
    public int add(int a, int b) {
        return a + b;
    }

    @Override
    public int minus(int a, int b) {
        return a - b;
    }
}
