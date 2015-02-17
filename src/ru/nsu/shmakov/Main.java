package ru.nsu.shmakov;

/**
 * Created by Иван on 16.02.2015.
 */
public class Main {
    public static void main(String[] args) {
        CalcBuffer cb = new CalcBuffer("./data/sample.txt");
        Lexer l = new Lexer(cb);
        while(l.available()) {
            System.out.print(l.pop().getValue());
        }
        l.toStart();
        System.out.println();
        Parser p = new Parser(l);
        System.out.println(p.parseExpression());
    }
}
