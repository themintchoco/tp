package tutorly.model.filter;

import java.util.List;
import java.util.function.Predicate;

import tutorly.model.ReadOnlyAddressBook;

/**
 * Represents a filter that can be used to filter a list of objects.
 */
@FunctionalInterface
public interface Filter<T> {

    /**
     * Returns a filter that represents the logical OR of the given filters.
     */
    public static <T> Filter<T> any(List<Filter<T>> filters) {
        return new AnyFilter<>(filters);
    }

    /**
     * Returns the predicate that represents the filter.
     *
     * @param addressBook The address book context.
     * @return A predicate that can be used for filtering.
     */
    public Predicate<T> toPredicate(ReadOnlyAddressBook addressBook);

}
