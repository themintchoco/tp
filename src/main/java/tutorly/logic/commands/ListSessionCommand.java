package tutorly.logic.commands;

import static java.util.Objects.requireNonNull;
import static tutorly.model.Model.FILTER_SHOW_ALL_SESSIONS;

import tutorly.model.Model;
import tutorly.ui.Tab;

/**
 * Lists all sessions in the address book to the user.
 */
public class ListSessionCommand extends SessionCommand {

    public static final String COMMAND_WORD = "list";
    public static final String COMMAND_STRING = SessionCommand.COMMAND_STRING + " " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Listed all sessions";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredSessionList(FILTER_SHOW_ALL_SESSIONS);
        return new CommandResult.Builder(MESSAGE_SUCCESS).withTab(Tab.SESSION).build();
    }
}
