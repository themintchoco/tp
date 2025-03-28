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
 * Creates a new AttendanceRecord for a student to a session.
 */
public class AssignStudentCommand extends StudentCommand {

    public static final String COMMAND_WORD = "assign";
    public static final String COMMAND_STRING = StudentCommand.COMMAND_STRING + " " + COMMAND_WORD;

    public static final String MESSAGE_USAGE =
            COMMAND_STRING + ": Assigns a student identified by their ID or name to a session. "
                    + "Parameters: ID/NAME "
                    + PREFIX_SESSION + "SESSION_ID\n"
                    + "Example: " + COMMAND_STRING + " 1 "
                    + PREFIX_SESSION + "2 ";

    public static final String MESSAGE_SUCCESS = "%1$s assigned to Session: %2$s";
    public static final String MESSAGE_DUPLICATE_ASSIGNMENT = "This student is already assigned to the session";
    public static final boolean DEFAULT_PRESENCE = false;

    private final Identity identity;
    private final int sessionId;

    /**
     * Creates an AssignStudentCommand for the given {@code Person} to the given {@code Session}
     */
    public AssignStudentCommand(Identity identity, int sessionId) {
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

        AttendanceRecord record = new AttendanceRecord(person.get().getId(), sessionId, DEFAULT_PRESENCE);
        if (model.hasAttendanceRecord(record)) {
            throw new CommandException(MESSAGE_DUPLICATE_ASSIGNMENT);
        }

        model.addAttendanceRecord(record);
        return new CommandResult.Builder(
                String.format(MESSAGE_SUCCESS, person.get().getName().fullName, Messages.format(session.get())))
                .withTab(Tab.STUDENT)
                .build();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AssignStudentCommand otherAssignStudentCommand)) {
            return false;
        }

        return identity.equals(otherAssignStudentCommand.identity)
                && sessionId == otherAssignStudentCommand.sessionId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("identity", identity)
                .add("sessionId", sessionId)
                .toString();
    }
}
