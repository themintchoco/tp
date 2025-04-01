package tutorly.logic.commands;

import java.util.Optional;

import tutorly.logic.Messages;
import tutorly.logic.commands.exceptions.CommandException;
import tutorly.model.Model;
import tutorly.model.session.Session;
import tutorly.ui.Tab;

/**
 * Represents a command related to sessions.
 */
public class SessionCommand extends Command {

    public static final String COMMAND_WORD = "session";
    public static final String COMMAND_STRING = COMMAND_WORD;

    private final Optional<Integer> sessionId;

    public SessionCommand(int sessionId) {
        this.sessionId = Optional.of(sessionId);
    }

    public SessionCommand() {
        this.sessionId = Optional.empty();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (sessionId.isPresent()) {
            Session session = model.getSessionById(sessionId.get())
                    .orElseThrow(() -> new CommandException(Messages.MESSAGE_SESSION_NOT_FOUND));

            return new CommandResult.Builder(String.format(Messages.MESSAGE_SESSION_SHOWN, Messages.format(session)))
                    .withTab(Tab.session(session))
                    .build();
        }

        return new CommandResult.Builder(Messages.MESSAGE_SESSIONS_SHOWN).withTab(Tab.session()).build();
    }

}
