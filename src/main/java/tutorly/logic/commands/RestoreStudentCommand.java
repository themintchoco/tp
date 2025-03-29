package tutorly.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import tutorly.commons.util.ToStringBuilder;
import tutorly.logic.Messages;
import tutorly.logic.commands.exceptions.CommandException;
import tutorly.model.Model;
import tutorly.model.person.Identity;
import tutorly.model.person.Person;
import tutorly.ui.Tab;

/**
 * Restores a person identified by their name or ID from the archived list in the address book.
 */
public class RestoreStudentCommand extends StudentCommand {

    public static final String COMMAND_WORD = "restore";
    public static final String COMMAND_STRING = StudentCommand.COMMAND_STRING + " " + COMMAND_WORD;

    public static final String MESSAGE_USAGE = COMMAND_STRING
            + ": Restores a recently deleted student identified by a STUDENT_IDENTIFIER (ID or full name).\n"
            + "Parameters: STUDENT_IDENTIFIER\n"
            + "Example: " + COMMAND_STRING + " 1";

    public static final String MESSAGE_RESTORE_PERSON_SUCCESS = "Restored student: %1$s";

    private final Identity identity;

    public RestoreStudentCommand(Identity identity) {
        this.identity = identity;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Optional<Person> toRestore = identity.getPerson(model, true);
        if (toRestore.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_PERSON_NOT_FOUND);
        }

        if (model.hasPerson(toRestore.get())) {
            throw new CommandException(Messages.MESSAGE_DUPLICATE_PERSON);
        }

        model.restorePerson(toRestore.get());
        return new CommandResult.Builder(
                String.format(MESSAGE_RESTORE_PERSON_SUCCESS, Messages.format(toRestore.get())))
                .withTab(Tab.STUDENT)
                .build();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RestoreStudentCommand otherRestoreCommand)) {
            return false;
        }

        return identity.equals(otherRestoreCommand.identity);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("identity", identity)
                .toString();
    }
}
