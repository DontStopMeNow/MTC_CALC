package ru.nsu.shmakov;

import org.junit.Assert;
import org.junit.Test;

import java.io.StringBufferInputStream;

/**
 * Created by Иван on 17.02.2015.
 */
public class ParserTest {
    @Test(timeout = 10)
    public void testEmptyExpression() {
        CalcBuffer buffer = new CalcBuffer(new StringBufferInputStream(""));
        Lexer lexer = new Lexer(buffer);
        Parser parser = new Parser(lexer);

        try {
            parser.parseExpression();
            Assert.assertEquals(true, false);
        }
        catch (RuntimeException e) {
            if (!e.getMessage().equals("Invalid expression"))
                Assert.assertEquals(true, false);
        }
    }

    @Test(timeout = 10)
    public void testEmptyExpressionWithBrackets() {
        CalcBuffer buffer = new CalcBuffer(new StringBufferInputStream("1+()"));
        Lexer lexer = new Lexer(buffer);
        Parser parser = new Parser(lexer);

        try {
            System.out.println(parser.parseExpression());
            Assert.assertEquals(true, false);
        }
        catch (RuntimeException e) {
            if (!e.getMessage().equals("Invalid expression"))
                Assert.assertEquals(true, false);
        }
    }

    @Test(timeout = 10)
    public void testDoublePower() {
        CalcBuffer buffer = new CalcBuffer(new StringBufferInputStream("1^^2"));
        Lexer lexer = new Lexer(buffer);
        Parser parser = new Parser(lexer);

        try {
            parser.parseExpression();
            Assert.assertEquals(true, false);
        }
        catch (RuntimeException e) {
            if (!e.getMessage().equals("Invalid expression"))
                Assert.assertEquals(true, false);
        }
    }

    @Test(timeout = 10)
    public void testDoubleSigns() {
        CalcBuffer buffer = new CalcBuffer(new StringBufferInputStream("1*/2"));
        Lexer lexer = new Lexer(buffer);
        Parser parser = new Parser(lexer);

        try {
            parser.parseExpression();
            Assert.assertEquals(true, false);
        }
        catch (RuntimeException e) {
            if (!e.getMessage().equals("Invalid expression"))
                Assert.assertEquals(true, false);
        }
    }

    @Test(timeout = 10)
    public void testDoubleSigns2() {
        CalcBuffer buffer = new CalcBuffer(new StringBufferInputStream("1**2"));
        Lexer lexer = new Lexer(buffer);
        Parser parser = new Parser(lexer);

        try {
            parser.parseExpression();
            Assert.assertEquals(true, false);
        }
        catch (RuntimeException e) {
            if (!e.getMessage().equals("Invalid expression"))
                Assert.assertEquals(true, false);
        }
    }

    @Test(timeout = 10)
    public void testInvalidNumberOfBrackets() {
        CalcBuffer buffer = new CalcBuffer(new StringBufferInputStream("1+1*((2+1)"));
        Lexer lexer = new Lexer(buffer);
        Parser parser = new Parser(lexer);

        try {
            parser.parseExpression();
            Assert.assertEquals(true, false);
        }
        catch (Exception e) {
            ;
        }
    }

    @Test(timeout = 10)
    public void testInvalidNumberOfBrackets2() {
        CalcBuffer buffer = new CalcBuffer(new StringBufferInputStream("1+1*)(2+1)"));
        Lexer lexer = new Lexer(buffer);
        Parser parser = new Parser(lexer);

        try {
            parser.parseExpression();
            Assert.assertEquals(true, false);
        }
        catch (Exception e) {
            if (!e.getMessage().equals("Invalid expression"))
                Assert.assertEquals(true, false);
        }
    }

    @Test(timeout = 10)
    public void testInvalidNumberOfBrackets3() {
        CalcBuffer buffer = new CalcBuffer(new StringBufferInputStream("1+1*((2+1)))+2"));
        Lexer lexer = new Lexer(buffer);
        Parser parser = new Parser(lexer);

        try {
            parser.parseExpression();
            Assert.assertEquals(true, false);
        }
        catch (Exception e) {
            ;
        }
    }

    @Test(timeout = 10)
    public void testManyUnarOperations() {
        CalcBuffer buffer = new CalcBuffer(new StringBufferInputStream("1+-+-1"));
        Lexer lexer = new Lexer(buffer);
        Parser parser = new Parser(lexer);

        Assert.assertEquals(2, parser.parseExpression());
    }

    @Test(timeout = 10)
    public void testOneNumberExpression() {
        CalcBuffer buffer = new CalcBuffer(new StringBufferInputStream("1"));
        Lexer lexer = new Lexer(buffer);
        Parser parser = new Parser(lexer);

        Assert.assertEquals(1, parser.parseExpression());
    }

    @Test(timeout = 10)
    public void testSum() {
        CalcBuffer buffer = new CalcBuffer(new StringBufferInputStream("1+2"));
        Lexer lexer = new Lexer(buffer);
        Parser parser = new Parser(lexer);

        Assert.assertEquals(3, parser.parseExpression());
    }

    @Test(timeout = 10)
    public void testDifference() {
        CalcBuffer buffer = new CalcBuffer(new StringBufferInputStream("2+1-4"));
        Lexer lexer = new Lexer(buffer);
        Parser parser = new Parser(lexer);

        Assert.assertEquals(-1, parser.parseExpression());
    }

    @Test(timeout = 10)
    public void testMultiplication() {
        CalcBuffer buffer = new CalcBuffer(new StringBufferInputStream("1-2*2 "));
        Lexer lexer = new Lexer(buffer);
        Parser parser = new Parser(lexer);

        Assert.assertEquals(-3, parser.parseExpression());
    }

    @Test(timeout = 10)
     public void testDiv() {
        CalcBuffer buffer = new CalcBuffer(new StringBufferInputStream("1 - 2/2 + 3/2"));
        Lexer lexer = new Lexer(buffer);
        Parser parser = new Parser(lexer);

        Assert.assertEquals(1, parser.parseExpression());
    }

    @Test(timeout = 10)
    public void testPower() {
        CalcBuffer buffer = new CalcBuffer(new StringBufferInputStream("1 - 2^2^2"));
        Lexer lexer = new Lexer(buffer);
        Parser parser = new Parser(lexer);

        Assert.assertEquals(-15, parser.parseExpression());
    }

    @Test(timeout = 10)
    public void testBigExpression() {
        CalcBuffer buffer = new CalcBuffer(new StringBufferInputStream("(1 + 1) * ((2^3)/2 + 100) - 2 + (-2) +-+3"));
        Lexer lexer = new Lexer(buffer);                                //2*(8/2 + 100) - 2 - 2 - 3;
        Parser parser = new Parser(lexer);                              //2*104 - 7
                                                                        //201
                                                                        //(1 + 1) * ((2^3)/2 + 100) - 2 + (-2) +-+3
        Assert.assertEquals(201, parser.parseExpression());
    }
}
