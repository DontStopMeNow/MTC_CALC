package ru.nsu.shmakov;

/**
 * Created by Иван on 16.02.2015.
 */
public class Lexeme {

    private LexemeType lexemeType;
    private String value;

    public Lexeme(LexemeType lexemeType, String value) {
        this.lexemeType = lexemeType;
        this.value = value;
    }

    public LexemeType getLexemeType() {

        return lexemeType;
    }

    public void setLexemeType(LexemeType lexemeType) {
        this.lexemeType = lexemeType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
