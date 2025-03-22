package tutorly.model.person;

import java.util.List;
import java.util.function.Predicate;

import tutorly.model.Model;

/**
 * Represents a filter that combines multiple predicates into a single predicate.
 * The combined predicate evaluates to {@code true} if any of the individual predicates evaluate to {@code true}.
 */
public class PredicateFilter {
    private final List<Predicate<Person>> predicates;

    /**
     * Constructs a {@code PredicateFilter} with the given list of predicates.
     * The predicates are combined using a logical OR operation.
     *
     * @param predicates The list of predicates to combine.
     */
    public PredicateFilter(List<Predicate<Person>> predicates) {
        this.predicates = predicates;
    }

    public Predicate<Person> getPredicate(Model model) {
        predicates.stream()
                .filter(AttendSessionPredicate::isAttendSessionPredicate)
                .map(predicate -> (AttendSessionPredicate) predicate)
                .forEach(inSessionPredicate ->
                        inSessionPredicate.initFilteredAttendanceRecords(model.getAddressBook().getAttendanceRecordsList()));

        return predicates.stream().reduce(Predicate::or).orElse(p -> true);
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
