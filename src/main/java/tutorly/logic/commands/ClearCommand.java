package tutorly.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import tutorly.model.AddressBook;
import tutorly.model.Model;
import tutorly.model.ReadOnlyAddressBook;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String COMMAND_STRING = COMMAND_WORD;

    public static final String MESSAGE_CLEAR_SUCCESS = "Students, sessions and attendance records have been cleared!";
    public static final String MESSAGE_RESTORE_SUCCESS =
            "Students, sessions and attendance records have been restored!";

    private final Optional<ReadOnlyAddressBook> addressBook;

    /**
     * Creates a ClearCommand to clear the address book to the specified {@code addressBook}.
     */
    public ClearCommand(ReadOnlyAddressBook addressBook) {
        this.addressBook = Optional.ofNullable(addressBook);
    }

    /**
     * Creates a ClearCommand to clear the address book to an empty state.
     */
    public ClearCommand() {
        this(null);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        ReadOnlyAddressBook currentAddressBook = new AddressBook(model.getAddressBook());
        model.setAddressBook(addressBook.orElseGet(() -> new AddressBook()));

        return new CommandResult.Builder(addressBook.isPresent() ? MESSAGE_RESTORE_SUCCESS : MESSAGE_CLEAR_SUCCESS)
                .withReverseCommand(new ClearCommand(currentAddressBook))
                .build();
    }

}
