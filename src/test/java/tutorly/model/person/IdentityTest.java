package tutorly.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tutorly.testutil.TypicalAddressBook.ALICE;
import static tutorly.testutil.TypicalAddressBook.BOB;
import static tutorly.testutil.TypicalAddressBook.getTypicalAddressBook;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import tutorly.model.Model;
import tutorly.model.ModelManager;
import tutorly.model.UserPrefs;

class IdentityTest {
    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    void constructor_withValidArgs_setsFields() {
        Identity identity = new Identity(10, new Name("Alice"));
        assertEquals(10, identity.getId());
        assertEquals("Alice", identity.getName().fullName);
    }

    @Test
    void setId_withValidArgs_updatesId() {
        Identity identity = new Identity();
        identity.setId(5);
        assertEquals(5, identity.getId());
    }

    @Test
    void setId_withInvalidArgs_throwsIllegalException() {
        Identity identity = new Identity();
        assertThrows(IllegalArgumentException.class, () -> identity.setId(0));
        assertThrows(IllegalArgumentException.class, () -> identity.setId(-1));
    }

    @Test
    void isIdPresent_idIsSet_returnsTrue() {
        Identity identity = new Identity(10, null);
        assertTrue(identity.isIdPresent());
    }

    @Test
    void isIdPresent_idIsNotSet_returnsFalse() {
        Identity identity = new Identity();
        assertFalse(identity.isIdPresent());
    }

    @Test
    void setName_updatesName() {
        Identity identity = new Identity();
        Name name = new Name("Bob");
        identity.setName(name);
        assertEquals(name, identity.getName());
    }

    @Test
    void isNamePresent_nameIsSet_returnsTrue() {
        Identity identity = new Identity(1, new Name("Carol"));
        assertTrue(identity.isNamePresent());
    }

    @Test
    void isNamePresent_nameIsNotSet_returnsFalse() {
        Identity identity = new Identity();
        assertFalse(identity.isNamePresent());
    }

    @Test
    void getPerson_withNoIdAndNoName_returnsEmptyPerson() {
        Identity identity = new Identity();
        Optional<Person> result = identity.getPerson(model);

        assertTrue(result.isEmpty());
    }

    @Test
    void getPerson_withIdInModel_returnsPerson() {
        Identity identity = new Identity();
        identity.setId(1);
        Optional<Person> result = identity.getPerson(model);

        assertTrue(result.isPresent());
        assertEquals(ALICE, result.get());
    }

    @Test
    void getPerson_withIdNotInModel_returnsEmptuPerson() {
        Identity identity = new Identity();
        identity.setId(100);
        Optional<Person> result = identity.getPerson(model);

        assertTrue(result.isEmpty());
    }

    @Test
    void getPerson_withNameInModel_returnsPerson() {
        Identity identity = new Identity();
        identity.setName(ALICE.getName());
        Optional<Person> result = identity.getPerson(model);

        assertTrue(result.isPresent());
        assertEquals(ALICE, result.get());
    }

    @Test
    void getPerson_withNameNotInModel_returnsEmptyPerson() {
        Identity identity = new Identity();
        identity.setName(new Name("Invalid Name"));
        Optional<Person> result = identity.getPerson(model);

        assertTrue(result.isEmpty());
    }

    @Test
    void getPerson_withIdAndNameInModel_returnsPerson() {
        Identity identity = new Identity(ALICE.getId(), BOB.getName());
        Optional<Person> result = identity.getPerson(model);

        assertTrue(result.isPresent());
        assertEquals(ALICE, result.get());
    }

    @Test
    void getPerson_withIdAndNameNotInModel_returnsEmptyPerson() {
        Identity identity = new Identity(100, BOB.getName());
        Optional<Person> result = identity.getPerson(model);

        assertTrue(result.isEmpty());
    }

    @Test
    void equals() {
        Identity identity = new Identity(10, new Name("Alice"));

        // same values -> returns true
        assertEquals(identity, new Identity(10, new Name("Alice")));

        // same object -> returns true
        assertEquals(identity, identity);

        // null -> returns false
        assertNotEquals(null, identity);

        // different types -> returns false
        assertFalse(identity.equals(5.0f));

        // different values -> returns false
        assertNotEquals(identity, new Identity(10, new Name("Bob")));
    }

    @Test
    void toStringMethod() {
        String expected = Identity.class.getCanonicalName() + "{id=" + 42 + ", name=Alice}";
        assertEquals(expected, new Identity(42, new Name("Alice")).toString());
    }
}
