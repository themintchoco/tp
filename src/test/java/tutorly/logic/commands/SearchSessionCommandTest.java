package tutorly.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tutorly.logic.Messages.MESSAGE_SESSIONS_LISTED_OVERVIEW;
import static tutorly.logic.commands.CommandTestUtil.assertCommandSuccess;
import static tutorly.testutil.TypicalAddressBook.MATH_SESSION;
import static tutorly.testutil.TypicalAddressBook.MATH_TIMESLOT;
import static tutorly.testutil.TypicalAddressBook.getTypicalAddressBook;
import static tutorly.testutil.TypicalAddressBook.getTypicalSessions;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import tutorly.model.Model;
import tutorly.model.ModelManager;
import tutorly.model.UserPrefs;
import tutorly.model.filter.AnyFilter;
import tutorly.model.filter.DateSessionFilter;
import tutorly.model.filter.Filter;
import tutorly.model.filter.SubjectContainsKeywordsFilter;
import tutorly.model.session.Session;

/**
 * Contains integration tests (interaction with the Model) for {@code SearchSessionCommand}.
 */
public class SearchSessionCommandTest {
    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final LocalDate validDate = LocalDate.of(2025, 1, 1);

    @Test
    public void equals() {
        Filter<Session> firstFilter = new DateSessionFilter(validDate);
        Filter<Session> secondFilter = new SubjectContainsKeywordsFilter(Collections.singletonList("keyword"));

        SearchSessionCommand searchFirstCommand = new SearchSessionCommand(firstFilter);
        SearchSessionCommand searchSecondCommand = new SearchSessionCommand(secondFilter);

        // same object -> returns true
        assertTrue(searchFirstCommand.equals(searchFirstCommand));

        // same values -> returns true
        SearchSessionCommand searchFirstCommandCopy = new SearchSessionCommand(firstFilter);
        assertTrue(searchFirstCommand.equals(searchFirstCommandCopy));

        // different types -> returns false
        assertFalse(searchFirstCommand.equals(1));

        // null -> returns false
        assertFalse(searchFirstCommand.equals(null));

        // different filter -> returns false
        assertFalse(searchFirstCommand.equals(searchSecondCommand));
    }

    @Test
    public void execute_noFilters_allSessionsFound() {
        List<Session> expectedResult = getTypicalSessions();
        String expectedMessage = String.format(MESSAGE_SESSIONS_LISTED_OVERVIEW, expectedResult.size());

        SearchSessionCommand command = new SearchSessionCommand(Filter.any(List.of()));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(expectedResult, model.getFilteredSessionList());
    }

    @Test
    public void execute_multipleFilters_sessionFound() {
        List<Session> expectedResult = Collections.singletonList(MATH_SESSION);
        String expectedMessage = String.format(MESSAGE_SESSIONS_LISTED_OVERVIEW, expectedResult.size());

        DateSessionFilter dateFilter = new DateSessionFilter(MATH_TIMESLOT.getStartTime().toLocalDate());
        SubjectContainsKeywordsFilter subjectFilter = prepareSubjectFilter("mat");

        Filter<Session> filter = Filter.any(Arrays.asList(dateFilter, subjectFilter));
        SearchSessionCommand command = new SearchSessionCommand(filter);

        expectedModel.updateFilteredSessionList(filter);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(expectedResult, model.getFilteredSessionList());
    }

    @Test
    public void toStringMethod() {
        DateSessionFilter dateFilter = new DateSessionFilter(validDate);
        SubjectContainsKeywordsFilter subjectFilter = prepareSubjectFilter("keyword1 keyword2");
        Filter<Session> filter = Filter.any(Arrays.asList(dateFilter, subjectFilter));
        SearchSessionCommand searchCommand = new SearchSessionCommand(filter);

        String expected = SearchSessionCommand.class.getCanonicalName()
                + "{filter=" + AnyFilter.class.getCanonicalName()
                + "{filters=[" + dateFilter + ", " + subjectFilter + "]}}";
        assertEquals(expected, searchCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code SubjectContainsKeywordsPredicate}.
     */
    private SubjectContainsKeywordsFilter prepareSubjectFilter(String userInput) {
        return new SubjectContainsKeywordsFilter(Arrays.asList(userInput.split("\\s+")));
    }
}
