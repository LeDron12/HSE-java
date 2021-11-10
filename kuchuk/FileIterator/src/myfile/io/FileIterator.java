package myfile.io;

import java.io.*;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class FileIterator implements Iterator<String> {

    private FileReader file;
    private String path;

    private int nextItem;
    private int currentItem;

    private boolean hasNextElement;
    private boolean first; // Help variable.

    public FileIterator(String path) throws FileNotFoundException {
        this.hasNextElement = true;
        this.first = true;

        this.path = path;
        this.file = new FileReader(path);
    }

    @Override
    public boolean hasNext() {
        return hasNextElement;
    }

    @Override
    public String next() throws NoSuchElementException{

        try {
            // Checking if this is "first" reading.
            if(first) {
                currentItem = file.read();
                nextItem = file.read();
                first = false;
            } else {
                currentItem = nextItem;
                nextItem = file.read();
            }

            // Checking if EOF.
            if(nextItem == -1) {
                hasNextElement = false;
            }
            if(currentItem == -1) {
                System.err.println("Can't find next element");
                throw new NoSuchElementException();
            }

        } catch (IOException e) {
            System.out.print("Something bad with file");
        }

        return String.valueOf((char)currentItem);
    }

    // Closes file.
    public void finnish() {
        try {
            file.close();
        } catch (IOException exception) {
            System.out.println("cant close file: " + path);
        }
    }
}
