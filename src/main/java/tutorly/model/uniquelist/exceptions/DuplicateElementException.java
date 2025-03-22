package tutorly.model.uniquelist.exceptions;

/**
 * Signals that the operation will result in duplicate elements
 */
public class DuplicateElementException extends RuntimeException {
    public DuplicateElementException() {
        super("Operation would result in duplicate elements");
    }
}
