package tutorly.model.filter;

import java.util.List;
import java.util.function.Predicate;

import tutorly.commons.util.ToStringBuilder;
import tutorly.model.ReadOnlyAddressBook;

/**
 * Represents a filter that represents the logical OR of the given filters.
 */
public class AnyFilter<T> implements Filter<T> {

    private final List<Filter<T>> filters;

    protected AnyFilter(List<Filter<T>> filters) {
        this.filters = filters;
    }

    @Override
    public Predicate<T> toPredicate(ReadOnlyAddressBook addressBook) {
        return filters.stream().map(f -> f.toPredicate(addressBook)).reduce(Predicate::or).orElse(t -> true);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AnyFilter otherAnyFilter)) {
            return false;
        }

        return filters.equals(otherAnyFilter.filters);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("filters", filters).toString();
    }

}
