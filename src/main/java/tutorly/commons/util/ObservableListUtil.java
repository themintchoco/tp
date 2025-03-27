package tutorly.commons.util;

import java.util.List;
import java.util.function.Predicate;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

/**
 * Utility class for observable lists.
 */
public class ObservableListUtil {

    /**
     * Creates an observable list backed by an {@code ArrayList}.
     */
    public static <T> ObservableList<T> arrayList() {
        return FXCollections.observableArrayList();
    }

    /**
     * Returns an unmodifiable view of the specified observable list.
     */
    public static <T> ObservableList<T> unmodifiableList(ObservableList<T> list) {
        return FXCollections.unmodifiableObservableList(list);
    }

    /**
     * Creates a filtered list that is updated whenever the list or the dependencies change.
     */
    public static <T> FilteredList<T> filteredList(ObservableList<T> list, Predicate<T> predicate,
            List<ObservableList<?>> dependencies) {
        FilteredList<T> filteredList = new FilteredList<>(list, predicate);

        for (ObservableList<?> dependency : dependencies) {
            dependency.addListener((ListChangeListener<? super Object>) change -> {
                // Reset the predicate to refilter the list
                filteredList.setPredicate(t -> false);
                filteredList.setPredicate(predicate);
            });
        }

        return filteredList;
    }

}
