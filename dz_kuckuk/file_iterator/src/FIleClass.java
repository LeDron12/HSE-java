import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FIleClass implements FileIterator{
    List<String> content;
    String strContent;
    String path;
    int size;

    int iterator;

    public FIleClass(String path) throws IOException {
        this.iterator = -1;
        this.path = path;
        this.strContent = "";

        content = Files.readAllLines(Path.of(path));
        for (int i = 0; i < content.size(); i++) {
            size += content.get(i).length() + 1;
            strContent += content.get(i) + "\n";
        }
    }

    @Override
    public boolean hasNext() {
        return iterator != size - 1;
    }

    @Override
    public char next() {
        iterator++;
        return strContent.charAt(iterator);
    }

    @Override
    public void remove() {
//        content.remove(iterator);
//        iterator--;
    }

    @Override
    public void reset() {
        iterator = 0;
    }

      // Saves changes to current file.
//    public saveChanges() {
//
//    }
}
