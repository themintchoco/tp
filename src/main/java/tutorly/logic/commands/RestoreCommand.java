package tutorly.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import tutorly.commons.core.index.Index;
import tutorly.commons.util.ToStringBuilder;
import tutorly.logic.Messages;
import tutorly.logic.commands.exceptions.CommandException;
import tutorly.model.Model;
import tutorly.model.person.Person;

/**
 * Restores a person identified using it's displayed ID from the address book.
 */
public class RestoreCommand extends Command {

    public static final String COMMAND_WORD = "restore";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Restores a previously archived person identified by the ID number "
            + "used in the displayed person list.\n"
            + "Parameters: ID (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_RESTORE_PERSON_SUCCESS = "Restored Person: %1$s";

    private final Index targetId;

    public RestoreCommand(Index targetId) {
        this.targetId = targetId;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getArchivedPersonList();

        // get person from archive list by persons ID attribute
        Person personToRestore = null;
        for (Person person : lastShownList) {
            if (person.getId() == targetId.getOneBased()) {
                personToRestore = person;
            }
        }

        if (personToRestore == null) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        model.restorePerson(personToRestore);
        return new CommandResult(String.format(MESSAGE_RESTORE_PERSON_SUCCESS, Messages.format(personToRestore)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RestoreCommand)) {
            return false;
        }

        RestoreCommand otherRestoreCommand = (RestoreCommand) other;
        return targetId.equals(otherRestoreCommand.targetId);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetId", targetId)
                .toString();
    }
}
