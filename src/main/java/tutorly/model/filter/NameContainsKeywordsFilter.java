package tutorly.model.filter;

import java.util.List;
import java.util.function.Predicate;

import tutorly.commons.util.StringUtil;
import tutorly.commons.util.ToStringBuilder;
import tutorly.model.ReadOnlyAddressBook;
import tutorly.model.person.Person;

/**
 * Represents a filter for a {@code Person} whose {@code Name} matches any of the keywords given.
 */
public class NameContainsKeywordsFilter implements Filter<Person> {
    private final List<String> keywords;

    public NameContainsKeywordsFilter(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public Predicate<Person> toPredicate(ReadOnlyAddressBook addressBook) {
        return person -> keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NameContainsKeywordsFilter otherNameContainsKeywordsFilter)) {
            return false;
        }

        return keywords.equals(otherNameContainsKeywordsFilter.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }

}
