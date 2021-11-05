import java.io.IOException;

public class Main{
    public static void main(String[] args) throws IOException {
        String path = args[0];
        FIleClass file = null;

        try {
            file = new FIleClass(path);
        } catch (IOException exception) {
            System.out.println("Cant read file: " + path);
        }

        System.out.println("File contents:");
        while (file.hasNext()) {
            System.out.print(file.next());
        }
        System.out.println();

        file.reset();
//
//        System.out.println("Removing each 4th symbol: ");
//        while (file.hasNext()) {
//
//        }
    }
}
