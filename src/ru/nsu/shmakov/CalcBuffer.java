package ru.nsu.shmakov;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by Иван on 16.02.2015.
 */
public class CalcBuffer {
    public CalcBuffer(InputStream is) {
        iStream = is;
        try {
            int c = 0;
            while (-1 != (c = iStream.read())) {
                buff.add((char) c);
            }
        } catch (IOException e) {
            System.out.println("Cannot read file");
            throw new RuntimeException("Invalid file.");
        }
        size = buff.size();
    }

    public CalcBuffer(String filename) {
        try {
            iStream = new FileInputStream(new File(filename));
        } catch (FileNotFoundException e) {
            System.out.println("Cannot open file");
            throw new RuntimeException("Cannot open file.");
        }

        try {
            int c = 0;
            while (-1 != (c = iStream.read())) {
                buff.add((char) c);
            }
        } catch (IOException e) {
            System.out.println("Cannot read file");
            throw new RuntimeException("Invalid file.");
        }
        size = buff.size();

    }

    public void push(char c) {
        buff.add(c);
    }

    public Character peak() {
        if (position >= size)
            return null;
        return buff.get(position);
    }

    public Character pop() {
        if (position >= size)
            return null;

        ++position;
        return buff.get(position - 1);
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

    private int size = 0;
    private InputStream iStream;
    private ArrayList<Character> buff = new ArrayList<Character>();
    private int position = 0;
}
