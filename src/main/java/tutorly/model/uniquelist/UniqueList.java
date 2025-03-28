package tutorly.model.uniquelist;

import static java.util.Objects.requireNonNull;
import static tutorly.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.ObservableList;
import tutorly.commons.util.ObservableListUtil;
import tutorly.model.uniquelist.exceptions.DuplicateElementException;
import tutorly.model.uniquelist.exceptions.ElementNotFoundException;

/**
 * A list that enforces uniqueness between its elements and does not allow nulls.
 * An element is considered unique by comparing using {@code UniqueList<T>#isDistinct(T, T)}. As such, adding and
 * updating of elements uses {@code UniqueList<T>#isDistinct(T, T)} for equality so as to ensure that the element
 * being added or updated is unique in terms of identity in the UniqueList. However, the removal of an element uses
 * {@code T#equals(Object)} so as to ensure that the exact element will be removed.
 * <p>
 * Supports a minimal set of list operations.
 */
public class UniqueList<T> implements Iterable<T> {

    protected final ObservableList<T> internalList = ObservableListUtil.arrayList();
    protected final ObservableList<T> internalUnmodifiableList = ObservableListUtil.unmodifiableList(internalList);

    /**
     * Returns true if the list contains an equivalent element as the given argument.
     */
    public boolean contains(T toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(element -> !isDistinct(element, toCheck));
    }

    /**
     * Adds an element to the list.
     * The element must not already exist in the list.
     */
    public void add(T toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateElementException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the element {@code target} in the list with {@code edited}.
     * {@code target} must exist in the list.
     * The edited element must not be the same as another existing element in the list.
     */
    public void set(T target, T edited) {
        requireAllNonNull(target, edited);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ElementNotFoundException();
        }

        if (isDistinct(target, edited) && contains(edited)) {
            throw new DuplicateElementException();
        }

        internalList.set(index, edited);
    }

    /**
     * Removes the equivalent element from the list.
     * The element must exist in the list.
     */
    public void remove(T toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new ElementNotFoundException();
        }
    }

    public void setAll(UniqueList<T> replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code replacement}.
     * {@code replacement} must not contain duplicate elements.
     */
    public void setAll(List<T> replacement) {
        requireAllNonNull(replacement);
        if (!elementsAreUnique(replacement)) {
            throw new DuplicateElementException();
        }

        internalList.setAll(replacement);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<T> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    public int size() {
        return internalList.size();
    }

    public void clear() {
        internalList.clear();
    }

    @Override
    public Iterator<T> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UniqueList otherUniqueList)) {
            return false;
        }

        return internalList.equals(otherUniqueList.internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }

    /**
     * Returns true if the list contains only unique elements.
     */
    protected boolean elementsAreUnique(List<T> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = i + 1; j < list.size(); j++) {
                if (!isDistinct(list.get(i), list.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Returns true if two elements are distinct, and false otherwise.
     */
    protected boolean isDistinct(T element1, T element2) {
        return element1.equals(element2);
    }
}
