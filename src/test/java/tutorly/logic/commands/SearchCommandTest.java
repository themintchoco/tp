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
import static tutorly.testutil.TypicalAddressBook.FIONA;
import static tutorly.testutil.TypicalAddressBook.GEORGE;
import static tutorly.testutil.TypicalAddressBook.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import tutorly.model.Model;
import tutorly.model.ModelManager;
import tutorly.model.UserPrefs;
import tutorly.model.person.NameContainsKeywordsPredicate;
import tutorly.model.person.PhoneContainsKeywordsPredicate;
import tutorly.model.person.PredicateFilter;

/**
 * Contains integration tests (interaction with the Model) for {@code SearchCommand}.
 */
public class SearchCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

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
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 7);
        SearchCommand command = new SearchCommand(new PredicateFilter(Collections.emptyList()));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 4);
        NameContainsKeywordsPredicate namePredicate = prepareNamePredicate("Kurz Elle Kunz");
        PhoneContainsKeywordsPredicate phonePredicate = preparePhonePredicate("948");
        PredicateFilter filter = new PredicateFilter(Arrays.asList(namePredicate, phonePredicate));
        SearchCommand command = new SearchCommand(filter);
        expectedModel.updateFilteredPersonList(filter.getPredicate());
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA, GEORGE), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        NameContainsKeywordsPredicate namePredicate =
                new NameContainsKeywordsPredicate(Arrays.asList("keyword1", "keyword2"));
        PhoneContainsKeywordsPredicate phonePredicate =
                new PhoneContainsKeywordsPredicate(Arrays.asList("keyword3", "keyword4"));
        PredicateFilter filter = new PredicateFilter(Arrays.asList(namePredicate, phonePredicate));
        SearchCommand searchCommand = new SearchCommand(filter);
        String expected = SearchCommand.class.getCanonicalName()
                + "{predicates=[" + namePredicate + ", " + phonePredicate + "]}";
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
