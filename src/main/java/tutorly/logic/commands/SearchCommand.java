package tutorly.logic.commands;

import static java.util.Objects.requireNonNull;
import static tutorly.logic.parser.CliSyntax.PREFIX_NAME;

import tutorly.commons.util.ToStringBuilder;
import tutorly.logic.Messages;
import tutorly.model.Model;
import tutorly.model.person.NameContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public class SearchCommand extends Command {

    public static final String COMMAND_WORD = "search";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Searches for all students whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: " + PREFIX_NAME + "KEYWORD [KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_NAME + "alice bob charlie";

    private final NameContainsKeywordsPredicate predicate;

    public SearchCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
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
        return predicate.equals(otherSearchCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
