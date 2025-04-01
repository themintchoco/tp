package tutorly.model.filter;

import java.util.List;
import java.util.function.Predicate;

import tutorly.commons.util.StringUtil;
import tutorly.commons.util.ToStringBuilder;
import tutorly.model.ReadOnlyAddressBook;
import tutorly.model.session.Session;

/**
 * Represents a filter for a {@code Session} whose {@code Subject} matches any of the keywords given.
 */
public class SubjectContainsKeywordsFilter implements Filter<Session> {
    private final List<String> keywords;

    public SubjectContainsKeywordsFilter(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public Predicate<Session> toPredicate(ReadOnlyAddressBook addressBook) {
        return session -> keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(session.getSubject().subjectName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SubjectContainsKeywordsFilter otherSubjectContainsKeywordsFilter)) {
            return false;
        }

        return keywords.equals(otherSubjectContainsKeywordsFilter.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
