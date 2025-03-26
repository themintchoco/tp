package tutorly.logic.commands;

import static java.util.Objects.requireNonNull;
import static tutorly.model.Model.FILTER_SHOW_ALL_PERSONS;

import tutorly.model.Model;
import tutorly.ui.Tab;

/**
 * Lists all persons in the address book to the user.
 */
public class ListStudentCommand extends StudentCommand {

    public static final String COMMAND_WORD = "list";
    public static final String COMMAND_STRING = StudentCommand.COMMAND_STRING + " " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Listed all persons";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(FILTER_SHOW_ALL_PERSONS);
        return new CommandResult.Builder(MESSAGE_SUCCESS).withTab(Tab.STUDENT).build();
    }
}
