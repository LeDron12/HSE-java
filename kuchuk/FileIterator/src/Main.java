import myfile.io.FileIterator;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.NoSuchElementException;

public class Main {

    public static void main(String[] args) {

        FileIterator fileIterator;
        String path;

        // Checking correct input.
        if(args.length < 1) {
            System.out.println("""
                    provide:
                    <path_to_file> as 1st argument
                    """);
            return;
        }
        path = args[0];

        // Creating file iterator instance.
        try {
            fileIterator = new FileIterator(path);
        } catch (FileNotFoundException exception) {
            System.out.println("Cant find file: " + path);
            return;
        }

        // Optional!
        // Reading whole file.
        while (fileIterator.hasNext()) {
            try {
                System.out.print(fileIterator.next());
            } catch (NoSuchElementException exception) {
                System.out.println(exception.getMessage());
            }
        }

        // Closing file.
        fileIterator.finnish();
    }

}
