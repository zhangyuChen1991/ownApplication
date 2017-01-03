package com.czy.algorithm;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testSolution() {
        QuestionSolution questionSolution = new QuestionSolution();
//        System.out.println(questionSolution.getLengthOfInteger(-1234));
//        System.out.println(questionSolution.getLengthOfInteger(-5));
//        System.out.println(questionSolution.getLengthOfInteger(-44123));
//        System.out.println(questionSolution.getLengthOfInteger(34));
//        System.out.println(questionSolution.getLengthOfInteger(65743));

//        System.out.println("1534236469   " + questionSolution.reverseInteger(1534236469));
//        System.out.println("-2147483648   " + questionSolution.reverseInteger(-2147483648));
//        System.out.println("-2147483648  length = "+questionSolution.getLengthOfInteger(-2147483648));
//        System.out.println("-5   " + questionSolution.reverseInteger(-5));
//        System.out.println("-44123   " + questionSolution.reverseInteger(-44123));
//        System.out.println("-34   " + questionSolution.reverseInteger(34));
//        System.out.println("65743   " + questionSolution.reverseInteger(65743));
//
//        System.out.println("max   "+Integer.MAX_VALUE);
//        System.out.println("min   "+Integer.MIN_VALUE);

        int[] g = {4, 5, 32, 6, 18, 51, 21, 8, 9};
        int[] s = {5, 99, 8, 7, 2, 1};

        QuestionSolutionTemp qst = new QuestionSolutionTemp();
        System.out.println("answer = " + qst.assignCookies(g, s));
    }
}