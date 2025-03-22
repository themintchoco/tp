package tutorly.logic.commands;

import static java.util.Objects.requireNonNull;
import static tutorly.logic.parser.CliSyntax.PREFIX_NAME;
import static tutorly.logic.parser.CliSyntax.PREFIX_PHONE;
import static tutorly.logic.parser.CliSyntax.PREFIX_SESSION;

import java.util.List;

import tutorly.commons.util.ToStringBuilder;
import tutorly.logic.Messages;
import tutorly.model.Model;
import tutorly.model.filter.Filter;
import tutorly.model.person.Person;

/**
 * Finds and lists all persons in address book whose fields contains any of the argument keywords, or attends the
 * session with the session id.
 * Keyword matching is case-insensitive.
 */
public class SearchCommand extends Command {

    public static final String COMMAND_WORD = "search";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Searches for all students who attended a session or "
            + "whose fields contain any of the specified keywords (case-insensitive) and displays them as a list.\n"
            + "Parameters: "
            + "[" + PREFIX_SESSION + "SESSION_ID] "
            + "[" + PREFIX_NAME + "NAME_KEYWORDS] "
            + "[" + PREFIX_PHONE + "PHONE_KEYWORDS]\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_SESSION + "1 " + PREFIX_NAME + "ali bob charli "
            + PREFIX_PHONE + "9124 86192";

    private final Filter<Person> filter;

    public SearchCommand(Filter<Person> filter) {
        this.filter = filter;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(filter);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SearchCommand)) {
            return false;
        }

        SearchCommand otherSearchCommand = (SearchCommand) other;
        return filter.equals(otherSearchCommand.filter);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("filter", filter)
                .toString();
    }
}
