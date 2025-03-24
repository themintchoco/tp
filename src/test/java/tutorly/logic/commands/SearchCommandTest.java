package tutorly.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tutorly.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static tutorly.logic.commands.CommandTestUtil.assertCommandSuccess;
import static tutorly.testutil.TypicalAddressBook.ALICE;
import static tutorly.testutil.TypicalAddressBook.BENSON;
import static tutorly.testutil.TypicalAddressBook.CARL;
import static tutorly.testutil.TypicalAddressBook.DANIEL;
import static tutorly.testutil.TypicalAddressBook.ELLE;
import static tutorly.testutil.TypicalAddressBook.ENGLISH_SESSION;
import static tutorly.testutil.TypicalAddressBook.FIONA;
import static tutorly.testutil.TypicalAddressBook.GEORGE;
import static tutorly.testutil.TypicalAddressBook.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import tutorly.model.Model;
import tutorly.model.ModelManager;
import tutorly.model.UserPrefs;
import tutorly.model.filter.AnyFilter;
import tutorly.model.filter.AttendSessionFilter;
import tutorly.model.filter.Filter;
import tutorly.model.filter.NameContainsKeywordsFilter;
import tutorly.model.filter.PhoneContainsKeywordsFilter;
import tutorly.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) for {@code SearchCommand}.
 */
public class SearchCommandTest {
    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        Filter<Person> firstFilter = new NameContainsKeywordsFilter(Collections.singletonList("first"));
        Filter<Person> secondFilter = new NameContainsKeywordsFilter(Collections.singletonList("second"));

        SearchCommand searchFirstCommand = new SearchCommand(firstFilter);
        SearchCommand searchSecondCommand = new SearchCommand(secondFilter);

        // same object -> returns true
        assertTrue(searchFirstCommand.equals(searchFirstCommand));

        // same values -> returns true
        SearchCommand searchFirstCommandCopy = new SearchCommand(firstFilter);
        assertTrue(searchFirstCommand.equals(searchFirstCommandCopy));

        // different types -> returns false
        assertFalse(searchFirstCommand.equals(1));

        // null -> returns false
        assertFalse(searchFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(searchFirstCommand.equals(searchSecondCommand));

        // different predicate type -> returns false
        Filter<Person> thirdFilter = new PhoneContainsKeywordsFilter(Collections.singletonList("first"));
        SearchCommand searchThirdCommand = new SearchCommand(thirdFilter);
        assertFalse(searchFirstCommand.equals(searchThirdCommand));
    }

    @Test
    public void execute_noFilters_allPersonsFound() {
        List<Person> expectedResult = Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE);
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, expectedResult.size());

        SearchCommand command = new SearchCommand(Filter.any(List.of()));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(expectedResult, model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleFilters_multiplePersonsFound() {
        List<Person> expectedResult = Arrays.asList(ALICE, CARL, DANIEL, ELLE, FIONA, GEORGE);
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, expectedResult.size());

        AttendSessionFilter sessionFilter = new AttendSessionFilter(ENGLISH_SESSION.getId());
        NameContainsKeywordsFilter nameFilter = prepareNameFilter("Kurz Elle Kunz");
        PhoneContainsKeywordsFilter phoneFilter = preparePhoneFilter("948");
        Filter<Person> filter = Filter.any(Arrays.asList(sessionFilter, nameFilter, phoneFilter));
        SearchCommand command = new SearchCommand(filter);

        expectedModel.updateFilteredPersonList(filter);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(expectedResult, model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        AttendSessionFilter sessionFilter = new AttendSessionFilter(1);
        NameContainsKeywordsFilter nameFilter =
                new NameContainsKeywordsFilter(Arrays.asList("keyword1", "keyword2"));
        PhoneContainsKeywordsFilter phoneFilter =
                new PhoneContainsKeywordsFilter(Arrays.asList("keyword3", "keyword4"));
        Filter<Person> filter = Filter.any(Arrays.asList(sessionFilter, nameFilter, phoneFilter));
        SearchCommand searchCommand = new SearchCommand(filter);

        String expected = SearchCommand.class.getCanonicalName()
                + "{filter=" + AnyFilter.class.getCanonicalName()
                + "{filters=[" + sessionFilter + ", " + nameFilter + ", " + phoneFilter + "]}}";
        assertEquals(expected, searchCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameContainsKeywordsFilter prepareNameFilter(String userInput) {
        return new NameContainsKeywordsFilter(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Parses {@code userInput} into a {@code PhoneContainsKeywordsPredicate}.
     */
    private PhoneContainsKeywordsFilter preparePhoneFilter(String userInput) {
        return new PhoneContainsKeywordsFilter(Arrays.asList(userInput.split("\\s+")));
    }
}
