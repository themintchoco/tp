package tutorly.model.filter;

import java.time.LocalDate;
import java.util.function.Predicate;

import tutorly.commons.util.ToStringBuilder;
import tutorly.model.ReadOnlyAddressBook;
import tutorly.model.session.Session;

/**
 * Represents a filter for a {@code Session} whose {@code Date} matches the given date.
 */
public class DateSessionFilter implements Filter<Session> {
    private final LocalDate date;

    public DateSessionFilter(LocalDate date) {
        this.date = date;
    }

    @Override
    public Predicate<Session> toPredicate(ReadOnlyAddressBook addressBook) {
        return session -> session.getDate().equals(date);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DateSessionFilter otherDateSessionFilter)) {
            return false;
        }

        return date.equals(otherDateSessionFilter.date);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("date", date).toString();
    }
}
