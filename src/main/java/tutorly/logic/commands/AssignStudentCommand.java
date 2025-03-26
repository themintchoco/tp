package tutorly.logic.commands;

import static java.util.Objects.requireNonNull;
import static tutorly.logic.parser.CliSyntax.PREFIX_NAME;
import static tutorly.logic.parser.CliSyntax.PREFIX_SESSION;

import tutorly.commons.util.ToStringBuilder;
import tutorly.logic.Messages;
import tutorly.logic.commands.exceptions.CommandException;
import tutorly.model.Model;
import tutorly.model.attendancerecord.AttendanceRecord;
import tutorly.model.person.Person;
import tutorly.model.session.Session;

/**
 * Creates a new AttendanceRecord for a student to a session.
 */
public class AssignStudentCommand extends StudentCommand {

    public static final String COMMAND_WORD = "assign";
    public static final String COMMAND_STRING = StudentCommand.COMMAND_STRING + " " + COMMAND_WORD;

    public static final String MESSAGE_USAGE = COMMAND_STRING + ": Assigns a student to a session. "
            + "Parameters: "
            + PREFIX_SESSION + "SESSION_ID "
            + PREFIX_NAME + "NAME\n"
            + "Example: " + COMMAND_STRING + " "
            + PREFIX_SESSION + "1 "
            + PREFIX_NAME + "Alice";

    public static final String MESSAGE_SUCCESS = "%1$s assigned to %2$s";
    public static final String MESSAGE_DUPLICATE_ASSIGNMENT = "This student is already already assigned to the session";
    public static final boolean DEFAULT_PRESENCE = false;

    private final Person person;
    private final Session session;

    /**
     * Creates an AssignStudentCommand for the given {@code Person} to the given {@code Session}
     */
    public AssignStudentCommand(Person person, Session session) {
        requireNonNull(person);
        requireNonNull(session);
        this.person = person;
        this.session = session;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        AttendanceRecord record = new AttendanceRecord(person.getId(), session.getId(), DEFAULT_PRESENCE);
        if (model.hasAttendanceRecord(record)) {
            throw new CommandException(MESSAGE_DUPLICATE_ASSIGNMENT);
        }

        model.addAttendanceRecord(record);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(person), Messages.format(session)));
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

        return person.equals(otherAssignStudentCommand.person)
                && session.equals(otherAssignStudentCommand.session);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("person", person)
                .add("session", session)
                .toString();
    }
}
