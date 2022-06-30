package com.forest.common;

import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.*;

public class ReflectionUtilsTest {

    @Test
    public void newInstance() {
        TestClass instance = ReflectionUtils.newInstance(TestClass.class);
        assertNotNull(instance);
    }

    @Test
    public void getPublicMethids() {
        Method[] publicMethods = ReflectionUtils.getPublicMethods(TestClass.class);
        assertEquals(1, publicMethods.length);
        String name = publicMethods[0].getName();
        assertEquals("c", name);
    }

    @Test
    public void invoke() {
        Method[] publicMethods = ReflectionUtils.getPublicMethods(TestClass.class);
        Method method = publicMethods[0];
        TestClass aClass = new TestClass();
        Object invoke = ReflectionUtils.invoke(aClass, method);
        assertEquals("C", invoke);
    }
}