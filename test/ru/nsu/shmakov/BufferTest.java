package ru.nsu.shmakov;

import org.junit.Assert;
import org.junit.Test;

import java.io.StringBufferInputStream;

/**
 * Created by Иван on 17.02.2015.
 */
public class BufferTest {

    @Test(timeout = 10)
    public void testPop() {
        CalcBuffer buffer = new CalcBuffer(new StringBufferInputStream("2)"));
        Character c = buffer.pop();
        Assert.assertEquals((char) c, '2');
        c = buffer.pop();
        Assert.assertEquals((char) c, ')');
        c = buffer.pop();
        Assert.assertEquals(c, null);
    }

    @Test(timeout = 10)
    public void testConstructor() {
        CalcBuffer buffer = new CalcBuffer(new StringBufferInputStream(""));
        Character c = buffer.pop();
        Assert.assertEquals(c, null);
        try {
            buffer = new CalcBuffer("asdasd");
            Assert.assertEquals(false, true);
        } catch (RuntimeException e) {
            if (!e.getMessage().equals("Cannot open file."))
                Assert.assertEquals(true, false);
        }
    }

    @Test(timeout = 10)
    public void testGoBack() {
        CalcBuffer buffer = new CalcBuffer(new StringBufferInputStream("2)"));
        Character c = buffer.pop();
        Assert.assertEquals((char) c, '2');

        c = buffer.pop();
        Assert.assertEquals((char) c, ')');

        buffer.goBack();
        buffer.goBack();
        c = buffer.pop();
        Assert.assertEquals((char) c, '2');
    }

    //e.t.c... more tests;
}