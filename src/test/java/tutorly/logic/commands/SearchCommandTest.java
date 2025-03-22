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
import tutorly.model.person.AttendSessionPredicate;
import tutorly.model.person.NameContainsKeywordsPredicate;
import tutorly.model.person.Person;
import tutorly.model.person.PhoneContainsKeywordsPredicate;
import tutorly.model.person.PredicateFilter;

/**
 * Contains integration tests (interaction with the Model) for {@code SearchCommand}.
 */
public class SearchCommandTest {
    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        PredicateFilter firstFilter =
                new PredicateFilter(Collections.singletonList(
                        new NameContainsKeywordsPredicate(Collections.singletonList("first"))));
        PredicateFilter secondFilter =
                new PredicateFilter(Collections.singletonList(
                        new NameContainsKeywordsPredicate(Collections.singletonList("second"))));

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
        PredicateFilter thirdFilter =
                new PredicateFilter(Collections.singletonList(
                        new PhoneContainsKeywordsPredicate(Collections.singletonList("first"))));
        SearchCommand searchThirdCommand = new SearchCommand(thirdFilter);
        assertFalse(searchFirstCommand.equals(searchThirdCommand));
    }

    @Test
    public void execute_zeroPredicates_allPersonsFound() {
        List<Person> expectedResult = Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE);
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, expectedResult.size());

        SearchCommand command = new SearchCommand(new PredicateFilter(Collections.emptyList()));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(expectedResult, model.getFilteredPersonList());
    }

    @Test
    public void execute_multiplePredicates_multiplePersonsFound() {
        List<Person> expectedResult = Arrays.asList(ALICE, CARL, DANIEL, ELLE, FIONA, GEORGE);
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, expectedResult.size());

        AttendSessionPredicate sessionPredicate = new AttendSessionPredicate(ENGLISH_SESSION.getId());
        NameContainsKeywordsPredicate namePredicate = prepareNamePredicate("Kurz Elle Kunz");
        PhoneContainsKeywordsPredicate phonePredicate = preparePhonePredicate("948");
        PredicateFilter filter = new PredicateFilter(Arrays.asList(sessionPredicate, namePredicate, phonePredicate));
        SearchCommand command = new SearchCommand(filter);

        expectedModel.updateFilteredPersonList(filter.getPredicate(expectedModel));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(expectedResult, model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        AttendSessionPredicate sessionPredicate = new AttendSessionPredicate(1);
        NameContainsKeywordsPredicate namePredicate =
                new NameContainsKeywordsPredicate(Arrays.asList("keyword1", "keyword2"));
        PhoneContainsKeywordsPredicate phonePredicate =
                new PhoneContainsKeywordsPredicate(Arrays.asList("keyword3", "keyword4"));
        PredicateFilter filter = new PredicateFilter(Arrays.asList(sessionPredicate, namePredicate, phonePredicate));
        SearchCommand searchCommand = new SearchCommand(filter);

        String expected = SearchCommand.class.getCanonicalName()
                + "{predicates=[" + sessionPredicate + ", " + namePredicate + ", " + phonePredicate + "]}";
        assertEquals(expected, searchCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameContainsKeywordsPredicate prepareNamePredicate(String userInput) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Parses {@code userInput} into a {@code PhoneContainsKeywordsPredicate}.
     */
    private PhoneContainsKeywordsPredicate preparePhonePredicate(String userInput) {
        return new PhoneContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
