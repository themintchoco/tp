package tutorly.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tutorly.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import tutorly.commons.exceptions.IllegalValueException;
import tutorly.commons.util.JsonUtil;
import tutorly.model.AddressBook;
import tutorly.testutil.TypicalAddressBook;

public class JsonSerializableAddressBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableAddressBookTest");
    private static final Path TYPICAL_ADDRESS_BOOK_FILE = TEST_DATA_FOLDER.resolve("typicalAddressBook.json");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidPersonAddressBook.json");
    private static final Path INVALID_NEXT_PERSON_ID_FILE =
            TEST_DATA_FOLDER.resolve("invalidnextPersonIdAddressBook.json");
    private static final Path INVALID_NEXT_SESSION_ID_FILE =
            TEST_DATA_FOLDER.resolve("invalidnextSessionIdAddressBook.json");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicatePersonAddressBook.json");
    private static final Path DUPLICATE_SESSION_FILE = TEST_DATA_FOLDER.resolve("duplicateSessionAddressBook.json");

    @Test
    public void toModelType_typicalAddressBookFile_success() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_ADDRESS_BOOK_FILE,
                JsonSerializableAddressBook.class).get();
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        AddressBook typicalAddressBook = TypicalAddressBook.getTypicalAddressBook();
        assertEquals(addressBookFromFile, typicalAddressBook);
    }

    @Test
    public void toModelType_invalidNextPersonIdFile_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(INVALID_NEXT_PERSON_ID_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableAddressBook.MESSAGE_ILLEGAL_NEXT_PERSON_ID,
                dataFromFile::toModelType);
    }

    @Test
    public void toModelType_invalidNextSessionIdFile_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(INVALID_NEXT_SESSION_ID_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableAddressBook.MESSAGE_ILLEGAL_NEXT_SESSION_ID,
                dataFromFile::toModelType);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(INVALID_PERSON_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PERSON_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableAddressBook.MESSAGE_DUPLICATE_PERSON,
                dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateSessions_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_SESSION_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableAddressBook.MESSAGE_DUPLICATE_SESSION,
                dataFromFile::toModelType);
    }

}
