package tutorly.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import tutorly.commons.util.ToStringBuilder;
import tutorly.ui.Tab;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /** Tab that the user should be switched to. */
    private final Tab tab;

    /** Help information should be shown to the user. */
    private final boolean showHelp;

    /** The application should exit. */
    private final boolean exit;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit, Tab tab) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.tab = tab;
    }

    private CommandResult(Builder builder) {
        this.feedbackToUser = builder.feedbackToUser;
        this.showHelp = builder.showHelp;
        this.exit = builder.exit;
        this.tab = builder.tab;
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public Tab getTab() {
        return tab;
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    public boolean isExit() {
        return exit;
    }

    public boolean isSwitchTab() {
        return tab != null;
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
                && showHelp == otherCommandResult.showHelp
                && exit == otherCommandResult.exit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("feedbackToUser", feedbackToUser)
                .add("showHelp", showHelp)
                .add("exit", exit)
                .toString();
    }

    /**
     * Builder for {@code CommandResult}.
     */
    public static class Builder {
        private String feedbackToUser;
        private Tab tab;
        private boolean showHelp;
        private boolean exit;

        /**
         * Constructs a {@code CommandResult.Builder} with the specified feedback.
         */
        public Builder(String feedbackToUser) {
            this.feedbackToUser = feedbackToUser;
        }

        /**
         * Sets the tab that the user should be switched to.
         */
        public Builder withTab(Tab tab) {
            this.tab = tab;
            return this;
        }

        /**
         * Sets that help information should be shown to the user.
         */
        public Builder showHelp() {
            this.showHelp = true;
            return this;
        }

        /**
         * Sets that the application should exit.
         */
        public Builder exit() {
            this.exit = true;
            return this;
        }

        public CommandResult build() {
            return new CommandResult(this);
        }
    }

}
