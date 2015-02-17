package ru.nsu.shmakov;

/**
 * Created by Иван on 16.02.2015.
 */
public class Paarser {

    public Paarser(Lexer lexer) {
        this.lexer = lexer;
    }

    public int parseExpression(){
        int result = 0;
        boolean working = true;
        result = parseTerm();

        while(working) {
            Lexeme lex = lexer.pop();
            if (LexemeType.END == lex.getLexemeType() || LexemeType.FUNCTION == lex.getLexemeType() && lex.getValue().equals(")")) {
                working = false;
            }
            else if(LexemeType.FUNCTION == lex.getLexemeType() && lex.getValue().equals("-")) {
                result -= parseTerm();
            }
            else if(LexemeType.FUNCTION == lex.getLexemeType() && lex.getValue().equals("+")) {
                result += parseTerm();
            }
            else {
                System.out.println("Cannot parse lexeme.");
                throw new RuntimeException("Invalid lexeme.");
            }
        }
        return result;
    }

    private int parseTerm(){
        int result = 0;
        result = parseFactor();
        boolean working = true;

        while(working) {
            Lexeme lex = lexer.pop();
            if(LexemeType.FUNCTION == lex.getLexemeType() && lex.getValue().equals("*")) {
                result *= parseFactor();
            }
            else if(LexemeType.FUNCTION == lex.getLexemeType() && lex.getValue().equals("/")) {
                result /= parseFactor();
            }
            else {
                working = false;
                lexer.goBack();
            }
        }
        return result;
    }

    private int parseFactor(){
        int result = 0;
        result = parsePower();
        Lexeme lex = lexer.pop();

        if(LexemeType.FUNCTION == lex.getLexemeType() && lex.getValue().equals("^")) {
            result = (int) Math.pow(result, parseFactor());
        }
        else {
            lexer.goBack();
        }
        return result;
    }

    private int parsePower(){
        int result = 0;
        Lexeme lex = lexer.pop();
        if (LexemeType.FUNCTION == lex.getLexemeType() && lex.getValue().equals("-")) {
            result = -parsePower();
        }
        else if (LexemeType.FUNCTION == lex.getLexemeType() && lex.getValue().equals("+")) {
            result = parsePower();
        }
        else {
            lexer.goBack();
            result = parseAtom();
        }
        return result;
    }

    private int parseAtom(){
        int result = 0;
        Lexeme lex = lexer.pop();
        if (LexemeType.NUMBER == lex.getLexemeType()) {
            result = Integer.parseInt(lex.getValue());
        }
        else if (LexemeType.FUNCTION == lex.getLexemeType() && lex.getValue().equals("(")) {
            result = parseExpression();
            lex = lexer.pop();
            /*if (LexemeType.FUNCTION != lex.getLexemeType() || lex.getValue().equals(")")) {
                throw new RuntimeException("Invalid expression");
            }*/
        }
        else {
            throw new RuntimeException("Invalid expression");
        }

        return result;
    }

    private Lexer lexer;
}
