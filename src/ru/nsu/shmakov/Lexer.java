package ru.nsu.shmakov;

import java.util.ArrayList;

/**
 * Created by Иван on 16.02.2015.
 */
public class Lexer {

    public Lexer(CalcBuffer calcBuffer) {
        this.calcBuffer = calcBuffer;
        StringBuilder sb = new StringBuilder();
        boolean isNumber = false;
        boolean isEnded = false;
        while(calcBuffer.available() && !isEnded) {
            Character c = calcBuffer.pop();

            switch (c) {
                case '+': case '-': case '(': case ')': case '*': case '/': case '^':
                    if(isNumber) {

                        lexemes.add(new Lexeme(LexemeType.NUMBER, sb.toString()));
                        sb.setLength(0);
                        isNumber = false;
                    }
                    lexemes.add(new Lexeme(LexemeType.FUNCTION, c.toString()));
                    break;


                case'0': case'1': case'2': case'3': case'4': case'5': case'6': case'7': case'8': case'9':
                    isNumber = true;
                    sb.append(c);
                    break;

                case ' ':
                    break;


                case '\0':
                    if(isNumber) {
                        lexemes.add(new Lexeme(LexemeType.NUMBER, sb.toString()));
                        sb.setLength(0);
                        isNumber = false;
                    }
                    lexemes.add(new Lexeme(LexemeType.END, ";"));
                    isEnded = true;
                    break;


                default:
                    System.out.println("Unknown lexeme");
                    throw new RuntimeException("Unknown lexeme");
            }
        }
        if(!isEnded) {
            if(isNumber) {
                lexemes.add(new Lexeme(LexemeType.NUMBER, sb.toString()));
                sb.setLength(0);
                isNumber = false;
            }
            lexemes.add(new Lexeme(LexemeType.END, ";"));
            isEnded = true;
        }
        size = lexemes.size();
    }

    public void push(Lexeme l) {
        lexemes.add(l);
    }

    public Lexeme peak() {
        if (position >= size)
            return null;
        return lexemes.get(position);
    }

    public Lexeme pop() {
        if (position >= size)
            return null;

        ++position;
        return lexemes.get(position - 1);
    }

    public void goBack() {
        if (position >= 0)
            --position;
    }

    public void skip(){
        if (position < size)
            ++position;
    }

    public void toStart() {
        position = 0;
    }

    public boolean available() {
        return(size > position);
    }

    private CalcBuffer calcBuffer;
    private ArrayList<Lexeme> lexemes = new ArrayList<Lexeme>();
    private int size = 0;
    private int position = 0;
}
