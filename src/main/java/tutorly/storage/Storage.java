package tutorly.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import tutorly.commons.exceptions.DataLoadingException;
import tutorly.model.ReadOnlyAddressBook;
import tutorly.model.ReadOnlyUserPrefs;
import tutorly.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends AddressBookStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataLoadingException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getAddressBookFilePath();

    @Override
    Optional<ReadOnlyAddressBook> readAddressBook() throws DataLoadingException;

    @Override
    void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException;

}
