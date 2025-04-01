package tutorly.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;
import java.util.Optional;

import tutorly.commons.util.ToStringBuilder;
import tutorly.ui.Tab;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    /** Feedback displayed to the user */
    private final String feedbackToUser;

    /** Help information should be shown to the user. */
    private final boolean shouldShowHelp;

    /** The application should exit. */
    private final boolean shouldExit;

    /** The last reversible command should be reversed. */
    private final boolean shouldReverseLast;

    /** Tab that the user should be switched to. */
    private final Optional<Tab> tab;

    /** The command that undoes the effects of this command. */
    private final Optional<Command> reverseCommand;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean shouldShowHelp, boolean shouldExit, boolean shouldReverseLast,
            Tab tab, Command reverseCommand) {
        requireNonNull(feedbackToUser);

        this.feedbackToUser = feedbackToUser;
        this.shouldShowHelp = shouldShowHelp;
        this.shouldExit = shouldExit;
        this.shouldReverseLast = shouldReverseLast;
        this.tab = Optional.ofNullable(tab);
        this.reverseCommand = Optional.ofNullable(reverseCommand);
    }

    /**
     * Constructs a {@code CommandResult} with {@code feedbackToUser},
     * and default values for the other fields.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false, false, null, null);
    }

    /**
     * Constructs a {@code CommandResult} with {@code feedbackToUser}, {@code shouldShowHelp}, {@code shouldExit},
     * and default values for the other fields.
     */
    public CommandResult(String feedbackToUser, boolean shouldShowHelp, boolean shouldExit) {
        this(feedbackToUser, shouldShowHelp, shouldExit, false, null, null);
    }

    private CommandResult(Builder builder) {
        this.feedbackToUser = builder.feedbackToUser;
        this.shouldShowHelp = builder.shouldShowHelp;
        this.shouldExit = builder.shouldExit;
        this.shouldReverseLast = builder.shouldReverseLast;
        this.tab = builder.tab;
        this.reverseCommand = builder.reverseCommand;
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public boolean shouldShowHelp() {
        return shouldShowHelp;
    }

    public boolean shouldExit() {
        return shouldExit;
    }

    public boolean shouldReverseLast() {
        return shouldReverseLast;
    }

    public Tab getTab() {
        return tab.get();
    }

    public Command getReverseCommand() {
        return reverseCommand.get();
    }

    public boolean shouldSwitchTab() {
        return tab.isPresent();
    }

    public boolean hasReverseCommand() {
        return reverseCommand.isPresent();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommandResult)) {
            return false;
        }

        CommandResult otherCommandResult = (CommandResult) other;
        return feedbackToUser.equals(otherCommandResult.feedbackToUser)
                && shouldShowHelp == otherCommandResult.shouldShowHelp
                && shouldExit == otherCommandResult.shouldExit
                && shouldReverseLast == otherCommandResult.shouldReverseLast
                && tab.equals(otherCommandResult.tab)
                && reverseCommand.equals(otherCommandResult.reverseCommand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, shouldShowHelp, shouldExit, shouldReverseLast, tab, reverseCommand);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("feedbackToUser", feedbackToUser)
                .add("shouldShowHelp", shouldShowHelp)
                .add("shouldExit", shouldExit)
                .add("shouldReverseLast", shouldReverseLast)
                .add("tab", tab.orElse(null))
                .add("reverseCommand", reverseCommand.orElse(null))
                .toString();
    }

    /**
     * Builder for {@code CommandResult}.
     */
    public static class Builder {
        private String feedbackToUser;
        private boolean shouldShowHelp;
        private boolean shouldExit;
        private boolean shouldReverseLast;
        private Optional<Tab> tab;
        private Optional<Command> reverseCommand;

        /**
         * Constructs a {@code CommandResult.Builder} with the specified feedback.
         */
        public Builder(String feedbackToUser) {
            this.feedbackToUser = feedbackToUser;
            this.tab = Optional.empty();
            this.reverseCommand = Optional.empty();
        }

        /**
         * Constructs a {@code CommandResult.Builder} from an existing {@code CommandResult}.
         */
        public Builder(CommandResult commandResult) {
            this.feedbackToUser = commandResult.getFeedbackToUser();
            this.shouldShowHelp = commandResult.shouldShowHelp();
            this.shouldExit = commandResult.shouldExit();
            this.shouldReverseLast = commandResult.shouldReverseLast();
            this.tab = commandResult.tab;
            this.reverseCommand = commandResult.reverseCommand;
        }

        /**
         * Sets the feedback to the user.
         */
        public Builder withFeedback(String feedbackToUser) {
            this.feedbackToUser = feedbackToUser;
            return this;
        }

        /**
         * Sets whether help information should be shown to the user.
         */
        public Builder withShowHelp(boolean shouldShowHelp) {
            this.shouldShowHelp = shouldShowHelp;
            return this;
        }

        /**
         * Sets whether the application should exit.
         */
        public Builder withExit(boolean shouldExit) {
            this.shouldExit = shouldExit;
            return this;
        }
        /**
         * Sets whether the last reversible command should be reversed.
         */
        public Builder withReverseLast(boolean shouldReverseLast) {
            this.shouldReverseLast = shouldReverseLast;
            return this;
        }

        /**
         * Sets the tab that the user should be switched to.
         */
        public Builder withTab(Tab tab) {
            this.tab = Optional.of(tab);
            return this;
        }

        /**
         * Sets the reverse command.
         */
        public Builder withReverseCommand(Command reverseCommand) {
            this.reverseCommand = Optional.of(reverseCommand);
            return this;
        }

        /**
         * Sets that help information should be shown to the user.
         */
        public Builder showHelp() {
            return withShowHelp(true);
        }

        /**
         * Sets that the application should exit.
         */
        public Builder exit() {
            return withExit(true);
        }

        /**
         * Sets that the last reversible command should be reversed.
         */
        public Builder reverseLast() {
            return withReverseLast(true);
        }

        public CommandResult build() {
            return new CommandResult(this);
        }
    }

}
