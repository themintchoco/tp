package tutorly.logic.commands;

import static java.util.Objects.requireNonNull;
import static tutorly.logic.parser.CliSyntax.PREFIX_DATE;
import static tutorly.logic.parser.CliSyntax.PREFIX_SUBJECT;

import tutorly.commons.util.ToStringBuilder;
import tutorly.logic.Messages;
import tutorly.model.Model;
import tutorly.model.filter.Filter;
import tutorly.model.session.Session;
import tutorly.ui.Tab;

/**
 * Finds and lists all sessions on a particular date or whose subject contains any of the keywords.
 * Keyword matching is case-insensitive.
 */
public class SearchSessionCommand extends SessionCommand {
    public static final String COMMAND_WORD = "search";

    public static final String COMMAND_STRING = SessionCommand.COMMAND_STRING + " " + COMMAND_WORD;

    public static final String MESSAGE_USAGE = COMMAND_STRING
            + ": Searches for all sessions on a particular date, or "
            + "whose subject contain any of the specified keywords (case-insensitive) and displays them as a list."
            + "\n\nParameters: "
            + "[" + PREFIX_DATE + "DATE] "
            + "[" + PREFIX_SUBJECT + "SUBJECT_KEYWORDS]"
            + "\n\nExample: " + COMMAND_STRING + " " + PREFIX_DATE + "18 Mar 2025 " + PREFIX_SUBJECT + "Math Eng";

    private final Filter<Session> filter;

    public SearchSessionCommand(Filter<Session> filter) {
        this.filter = filter;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredSessionList(filter);
        return new CommandResult.Builder(
                String.format(Messages.MESSAGE_SESSIONS_LISTED_OVERVIEW, model.getFilteredSessionList().size()))
                .withTab(Tab.session())
                .build();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SearchSessionCommand otherSearchCommand)) {
            return false;
        }

        return filter.equals(otherSearchCommand.filter);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("filter", filter)
                .toString();
    }
}
