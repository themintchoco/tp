package tutorly.logic.commands;

import static java.util.Objects.requireNonNull;
import static tutorly.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static tutorly.logic.parser.CliSyntax.PREFIX_TIMESLOT;

import tutorly.commons.util.ToStringBuilder;
import tutorly.logic.Messages;
import tutorly.logic.commands.exceptions.CommandException;
import tutorly.model.Model;
import tutorly.model.session.Session;
import tutorly.ui.Tab;

/**
 * Creates a new tutoring session.
 */
public class AddSessionCommand extends SessionCommand {

    public static final String COMMAND_WORD = "add";
    public static final String COMMAND_STRING = SessionCommand.COMMAND_STRING + " " + COMMAND_WORD;

    public static final String MESSAGE_USAGE = COMMAND_STRING
            + ": Creates a tutoring session.\n"
            + "Parameters: "
            + PREFIX_TIMESLOT + "TIMESLOT "
            + PREFIX_SUBJECT + "SUBJECT\n"
            + "Example: " + COMMAND_STRING + " "
            + PREFIX_TIMESLOT + "30 Mar 2025 11:30-13:30 "
            + PREFIX_SUBJECT + "Mathematics";

    public static final String MESSAGE_SUCCESS = "New session created: %1$s";
    public static final String MESSAGE_DUPLICATE_SESSION = "This session already exists.";

    private final Session toCreate;

    /**
     * Creates a CreateSessionCommand to add the specified {@code Session}.
     *
     * @param session The session to be created.
     */
    public AddSessionCommand(Session session) {
        requireNonNull(session);
        toCreate = session;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasSession(toCreate)) {
            throw new CommandException(MESSAGE_DUPLICATE_SESSION);
        }

        model.addSession(toCreate);
        return new CommandResult.Builder(String.format(MESSAGE_SUCCESS, Messages.format(toCreate)))
                .withTab(Tab.SESSION)
                .build();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddSessionCommand otherCommand)) {
            return false;
        }

        return toCreate.equals(otherCommand.toCreate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toCreate", toCreate)
                .toString();
    }
}
