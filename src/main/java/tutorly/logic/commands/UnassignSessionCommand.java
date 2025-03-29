package tutorly.logic.commands;

import static java.util.Objects.requireNonNull;
import static tutorly.logic.parser.CliSyntax.PREFIX_SESSION;

import java.util.Optional;

import tutorly.commons.util.ToStringBuilder;
import tutorly.logic.Messages;
import tutorly.logic.commands.exceptions.CommandException;
import tutorly.model.Model;
import tutorly.model.attendancerecord.AttendanceRecord;
import tutorly.model.person.Identity;
import tutorly.model.person.Person;
import tutorly.model.session.Session;
import tutorly.ui.Tab;

/**
 * Removes an AttendanceRecord for a student to a session.
 */
public class UnassignSessionCommand extends SessionCommand {
    public static final String COMMAND_WORD = "unassign";
    public static final String COMMAND_STRING = SessionCommand.COMMAND_STRING + " " + COMMAND_WORD;

    public static final String MESSAGE_USAGE =
            COMMAND_STRING + ": Removes a student identified by their ID or name from a session. "
                    + "Parameters: ID/NAME "
                    + PREFIX_SESSION + "SESSION_ID\n"
                    + "Example: " + COMMAND_STRING + " 1 "
                    + PREFIX_SESSION + "2 ";

    public static final String MESSAGE_SUCCESS = "%1$s has been removed from Session: %2$s";
    public static final String MESSAGE_MISSING_ASSIGNMENT = "This student is not assigned to the session";

    private final Identity identity;
    private final int sessionId;

    /**
     * Creates an UnassignSessionCommand for the given {@code Person} to the given {@code Session}
     */
    public UnassignSessionCommand(Identity identity, int sessionId) {
        requireNonNull(identity);
        this.identity = identity;
        this.sessionId = sessionId;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Optional<Person> person = identity.getPerson(model);
        if (person.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_PERSON_NOT_FOUND);
        }

        Optional<Session> session = model.getSessionById(sessionId);
        if (session.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_INVALID_SESSION_ID);
        }

        // value of isPresent is not used when checking if a record is in AddressBook, set to false as a placeholder
        AttendanceRecord record = new AttendanceRecord(person.get().getId(), sessionId, false);
        if (!model.hasAttendanceRecord(record)) {
            throw new CommandException(MESSAGE_MISSING_ASSIGNMENT);
        }

        model.removeAttendanceRecord(record);
        return new CommandResult.Builder(
                String.format(MESSAGE_SUCCESS, person.get().getName().fullName, Messages.format(session.get())))
                .withTab(Tab.SESSION)
                .build();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UnassignSessionCommand otherUnassignSessionCommand)) {
            return false;
        }

        return identity.equals(otherUnassignSessionCommand.identity)
                && sessionId == otherUnassignSessionCommand.sessionId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("identity", identity)
                .add("sessionId", sessionId)
                .toString();
    }
}
