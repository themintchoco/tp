package tutorly.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tutorly.testutil.TypicalAddressBook.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import tutorly.model.Model;
import tutorly.model.ModelManager;
import tutorly.model.UserPrefs;

class IdentityTest {
    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    void constructor_withValidArgs_setsFields() {
        Identity identity1 = new Identity(10);
        Identity identity2 = new Identity(new Name("Alice"));
        assertEquals(10, identity1.getId());
        assertEquals("Alice", identity2.getName().fullName);
    }

    @Test
    void isIdPresent_idIsSet_returnsTrue() {
        Identity identity = new Identity(10);
        assertTrue(identity.isIdPresent());
    }

    @Test
    void isIdPresent_idIsNotSet_returnsFalse() {
        Identity identity = new Identity(new Name("Alice"));
        assertFalse(identity.isIdPresent());
    }

    @Test
    void isNamePresent_nameIsSet_returnsTrue() {
        Identity identity = new Identity(new Name("Carol"));
        assertTrue(identity.isNamePresent());
    }

    @Test
    void isNamePresent_nameIsNotSet_returnsFalse() {
        Identity identity = new Identity(10);
        assertFalse(identity.isNamePresent());
    }

    @Test
    void equals() {
        Identity identity = new Identity(10);

        // same values -> returns true
        assertEquals(identity, new Identity(10));

        // same object -> returns true
        assertEquals(identity, identity);

        // null -> returns false
        assertNotEquals(null, identity);

        // different types -> returns false
        assertFalse(identity.equals(5.0f));

        // different values -> returns false
        assertNotEquals(identity, new Identity(11));
    }

    @Test
    void toStringMethod() {
        String expected = Identity.class.getCanonicalName() + "{id=" + 42 + ", name=null}";
        assertEquals(expected, new Identity(42).toString());
    }
}
