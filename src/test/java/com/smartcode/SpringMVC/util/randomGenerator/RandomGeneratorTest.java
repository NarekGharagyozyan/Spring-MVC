package com.smartcode.SpringMVC.util.randomGenerator;

import com.smartcode.SpringMVC.util.codeGenerator.RandomGenerator;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class RandomGeneratorTest {

    @Test //can't be any letter
    public void test1() {
        String s = RandomGenerator.generateNumericString(6);
        assertNotEquals("1284s1", s);
    }

    @Test //can't be less than 6
    public void test2() {
        String s = RandomGenerator.generateNumericString(6);
        assertNotEquals("1284", s);
    }

    @Test //can't be more than 6
    public void test3() {
        String s = RandomGenerator.generateNumericString(6);
        assertNotEquals("17454564", s);
    }

    @Test //can't be empty
    public void test4() {
        String s = RandomGenerator.generateNumericString(6);
        assertNotEquals("", s);
    }

    @Test //can't be letters
    public void test5() {
        String s = RandomGenerator.generateNumericString(6);
        assertNotEquals("afbgjs", s);
    }

}
