package tutorly.model.person;

import java.util.List;
import java.util.function.Predicate;

/**
 * Represents a filter that combines multiple predicates into a single predicate.
 * The combined predicate evaluates to {@code true} if any of the individual predicates evaluate to {@code true}.
 */
public class PredicateFilter {
    private final Predicate<Person> chainedPredicate;
    private final List<Predicate<Person>> predicates;

    /**
     * Constructs a {@code PredicateFilter} with the given list of predicates.
     * The predicates are combined using a logical OR operation.
     *
     * @param predicates The list of predicates to combine.
     */
    public PredicateFilter(List<Predicate<Person>> predicates) {
        this.predicates = predicates;
        this.chainedPredicate = predicates.stream().reduce(Predicate::or).orElse(p -> true);
    }

    public Predicate<Person> getPredicate() {
        return this.chainedPredicate;
    }

    @Override
    public String toString() {
        return this.predicates.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PredicateFilter)) {
            return false;
        }

        PredicateFilter otherPredicateFilter = (PredicateFilter) other;
        return predicates.equals(otherPredicateFilter.predicates);
    }
}
