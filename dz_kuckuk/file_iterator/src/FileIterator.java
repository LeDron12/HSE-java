public interface FileIterator {

    // Tells if there is a value.
    boolean hasNext();

    // Returns next value.
    char next();

    // Removes last element returned by iterator.
    void remove();

    // Resets iterator.
    void reset();
}
