package tutorly.logic.commands;

import static java.util.Objects.requireNonNull;

import tutorly.model.AddressBook;
import tutorly.model.Model;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String COMMAND_STRING = COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Students, sessions and attendance records have been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setAddressBook(new AddressBook());
        return new CommandResult.Builder(MESSAGE_SUCCESS).build();
    }
}
