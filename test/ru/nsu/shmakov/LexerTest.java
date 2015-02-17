package ru.nsu.shmakov;

import org.junit.Assert;
import org.junit.Test;

import java.io.StringBufferInputStream;

/**
 * Created by Иван on 17.02.2015.
 */
public class LexerTest {
    @Test(timeout = 10)
    public void globalTest() {
        CalcBuffer buffer = new CalcBuffer(new StringBufferInputStream("232)1+(-/*^"));
        Lexer lexer = new Lexer(buffer);

        StringBuilder sb = new StringBuilder();

        while (lexer.available()) {
            Lexeme lex = lexer.pop();
            sb.append(lex.getValue());
        }
        Assert.assertEquals(sb.toString(), "232)1+(-/*^;"); // There we have ; that means end of expression like '\0'

        //----------------------------------------------------------------------
        // Test for correct end of expression

        buffer = new CalcBuffer(new StringBufferInputStream("232)1;+(-/*^"));
        lexer = new Lexer(buffer);

        sb = new StringBuilder();

        while (lexer.available()) {
            Lexeme lex = lexer.pop();
            sb.append(lex.getValue());
        }
        Assert.assertEquals(sb.toString(), "232)1;");

        //----------------------------------------------------------------------
        // Test for unknown lexeme exception
        buffer = new CalcBuffer(new StringBufferInputStream("232)1s+(-/*^"));
        try {
            lexer = new Lexer(buffer);
            Assert.assertEquals(true, false);
        }
        catch (RuntimeException e) {
            if (!e.getMessage().equals("Unknown lexeme."))
                Assert.assertEquals(true, false);
        }

    }

    //e.t.c...
}
