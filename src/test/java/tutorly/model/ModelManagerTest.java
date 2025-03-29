package tutorly.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tutorly.model.Model.FILTER_SHOW_ALL_PERSONS;
import static tutorly.testutil.Assert.assertThrows;
import static tutorly.testutil.TypicalAddressBook.ALICE;
import static tutorly.testutil.TypicalAddressBook.ALICE_ATTEND_ENGLISH;
import static tutorly.testutil.TypicalAddressBook.BENSON;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import tutorly.commons.core.GuiSettings;
import tutorly.model.filter.NameContainsKeywordsFilter;
import tutorly.model.person.Identity;
import tutorly.testutil.AddressBookBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new AddressBook(), new AddressBook(modelManager.getAddressBook()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setAddressBookFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setAddressBookFilePath(null));
    }

    @Test
    public void setAddressBookFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setAddressBookFilePath(path);
        assertEquals(path, modelManager.getAddressBookFilePath());
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        modelManager.addPerson(ALICE);
        assertTrue(modelManager.hasPerson(ALICE));
    }

    @Test
    public void getPersonByIdentity_personInAddressBook_returnsPerson() {
        modelManager.addPerson(ALICE);
        assertEquals(ALICE, modelManager.getPersonByIdentity(new Identity(ALICE.getId()), false).get());
    }

    @Test
    public void getPersonByIdentity_personNotInAddressBook_returnsEmptyPerson() {
        assertTrue(modelManager.getPersonByIdentity(new Identity(ALICE.getId()), false).isEmpty());
    }

    @Test
    public void getPersonByIdentity_personInArchivedList_returnsPerson() {
        modelManager.addPerson(ALICE);
        modelManager.deletePerson(ALICE);
        assertEquals(ALICE, modelManager.getPersonByIdentity(new Identity(ALICE.getId()), true).get());
    }

    @Test
    public void getPersonByIdentity_personNotInArchivedList_returnsNull() {
        modelManager.addPerson(ALICE);
        assertTrue(modelManager.getPersonByIdentity(new Identity(ALICE.getId()), true).isEmpty());
    }


    @Test
    public void hasAttendanceRecord_recordInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasAttendanceRecord(ALICE_ATTEND_ENGLISH));
    }

    @Test
    public void hasAttendanceRecord_recordInAddressBook_returnsTrue() {
        modelManager.addAttendanceRecord(ALICE_ATTEND_ENGLISH);
        assertTrue(modelManager.hasAttendanceRecord(ALICE_ATTEND_ENGLISH));
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredPersonList().remove(0));
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        AddressBook differentAddressBook = new AddressBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(addressBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(addressBook, userPrefs);
        assertEquals(modelManager, modelManagerCopy);

        // same object -> returns true
        assertEquals(modelManager, modelManager);

        // null -> returns false
        assertNotEquals(null, modelManager);

        // different types -> returns false
        assertNotEquals(5, modelManager);

        // different addressBook -> returns false
        assertNotEquals(modelManager, new ModelManager(differentAddressBook, userPrefs));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPersonList(new NameContainsKeywordsFilter(Arrays.asList(keywords)));
        assertNotEquals(modelManager, new ModelManager(addressBook, userPrefs));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(FILTER_SHOW_ALL_PERSONS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertNotEquals(modelManager, new ModelManager(addressBook, differentUserPrefs));
    }
}
