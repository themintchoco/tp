package tutorly.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import tutorly.commons.core.GuiSettings;
import tutorly.model.Model;
import tutorly.model.ReadOnlyAddressBook;
import tutorly.model.ReadOnlyUserPrefs;
import tutorly.model.session.Session;
import tutorly.testutil.PersonBuilder;

public class PredicateFilterTest {
    private final NameContainsKeywordsPredicate firstPredicate =
            new NameContainsKeywordsPredicate(Collections.singletonList("Alice"));
    private final PhoneContainsKeywordsPredicate secondPredicate =
            new PhoneContainsKeywordsPredicate(Arrays.asList("9123", "876"));
    private final ModelStub modelStub = new ModelStub();

    @Test
    public void equals() {
        PredicateFilter firstFilter = new PredicateFilter(Collections.singletonList(firstPredicate));
        PredicateFilter secondFilter = new PredicateFilter(Collections.singletonList(secondPredicate));

        // same object -> returns true
        assertTrue(firstFilter.equals(firstFilter));

        // same predicates -> returns true
        PredicateFilter firstFilterCopy = new PredicateFilter(Collections.singletonList(firstPredicate));
        assertTrue(firstFilter.equals(firstFilterCopy));

        // different types -> returns false
        assertFalse(firstFilter.equals(1));

        // null -> returns false
        assertFalse(firstFilter.equals(null));

        // different predicates -> returns false
        assertFalse(firstFilter.equals(secondFilter));
    }

    @Test
    public void toStringMethod() {
        PredicateFilter filter = new PredicateFilter(Arrays.asList(firstPredicate, secondPredicate));
        String expectedString = Arrays.asList(firstPredicate, secondPredicate).toString();

        assertEquals(expectedString, filter.toString());
    }

    @Test
    public void getPredicate_multiplePredicatesPresent_returnsCombinedPredicate() {
        PredicateFilter filter = new PredicateFilter(Arrays.asList(firstPredicate, secondPredicate));
        Predicate<Person> combinedPredicate = filter.getPredicate(modelStub);

        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withName("Bob").withPhone("91234567").build();
        Person charlie = new PersonBuilder().withName("Charlie").build();

        // Combined predicate should return true for Alice or Bob
        assertTrue(combinedPredicate.test(alice));
        assertTrue(combinedPredicate.test(bob));

        // Combined predicate should return false for Charlie
        assertFalse(combinedPredicate.test(charlie));
    }

    @Test
    public void getPredicate_emptyPredicateList_returnsAlwaysTruePredicate() {
        PredicateFilter filter = new PredicateFilter(Collections.emptyList());
        Predicate<Person> combinedPredicate = filter.getPredicate(modelStub);

        Person person = new PersonBuilder().build();

        // Combined predicate should always return true for an empty predicate list
        assertTrue(combinedPredicate.test(person));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void restorePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getArchivedPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasSession(Session toCreate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addSession(Session toCreate) {
            throw new AssertionError("This method should not be called.");
        }
    }
}
