package tutorly.logic.commands;

import static java.util.Objects.requireNonNull;
import static tutorly.logic.parser.CliSyntax.PREFIX_NAME;
import static tutorly.logic.parser.CliSyntax.PREFIX_PHONE;

import tutorly.commons.util.ToStringBuilder;
import tutorly.logic.Messages;
import tutorly.model.Model;
import tutorly.model.person.PredicateFilter;

/**
 * Finds and lists all persons in address book whose fields contains any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public class SearchCommand extends Command {

    public static final String COMMAND_WORD = "search";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Searches for all students whose fields contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list.\n"
            + "Parameters: "
            + "[" + PREFIX_NAME + "NAME_KEYWORDS] "
            + "[" + PREFIX_PHONE + "PHONE_KEYWORDS]\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_NAME + "ali bob charli " + PREFIX_PHONE + "9124 86192";

    private final PredicateFilter filter;

    public SearchCommand(PredicateFilter filter) {
        this.filter = filter;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(filter.getPredicate());
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
                .add("predicates", filter)
                .toString();
    }
}
