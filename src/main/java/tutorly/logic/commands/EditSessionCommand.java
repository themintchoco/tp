package tutorly.logic.commands;

import static java.util.Objects.requireNonNull;
import static tutorly.logic.parser.CliSyntax.PREFIX_DATE;
import static tutorly.logic.parser.CliSyntax.PREFIX_SESSION;
import static tutorly.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static tutorly.model.Model.FILTER_SHOW_ALL_SESSIONS;

import java.time.LocalDate;
import java.util.Optional;

import tutorly.commons.util.CollectionUtil;
import tutorly.commons.util.ToStringBuilder;
import tutorly.logic.Messages;
import tutorly.logic.commands.exceptions.CommandException;
import tutorly.model.Model;
import tutorly.model.session.Session;
import tutorly.ui.Tab;

/**
 * Edits the details of an existing session in the address book.
 */
public class EditSessionCommand extends SessionCommand {
    public static final String COMMAND_WORD = "edit";
    public static final String COMMAND_STRING = SessionCommand.COMMAND_STRING + " " + COMMAND_WORD;

    public static final String MESSAGE_USAGE = COMMAND_STRING
            + ": Edits the details of the session identified by a SESSION_ID. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: SESSION_ID "
            + PREFIX_SESSION + "SESSION_ID\n"
            + "[" + PREFIX_DATE + "DATE] "
            + "[" + PREFIX_SUBJECT + "SUBJECT] "
            + "Example: " + COMMAND_STRING + " 1 "
            + PREFIX_DATE + "2023-10-01 "
            + PREFIX_SUBJECT + "Maths";

    public static final String MESSAGE_EDIT_SESSION_SUCCESS = "Edited session: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

    private final int sessionId;
    private final EditSessionDescriptor editSessionDescriptor;

    /**
     * @param id             containing the ID or name of the session to edit
     * @param editSessionDescriptor details to edit the session with
     */
    public EditSessionCommand(int id, EditSessionDescriptor editSessionDescriptor) {
        requireNonNull(editSessionDescriptor);

        this.sessionId = id;
        this.editSessionDescriptor = new EditSessionDescriptor(editSessionDescriptor);
    }

    /**
     * Creates and returns a {@code Session} with the details of {@code sessionToEdit}
     * edited with {@code editSessionDescriptor}. The ID of the session cannot be edited.
     */
    private static Session createEditedSession(Session sessionToEdit, EditSessionDescriptor editSessionDescriptor) {
        assert sessionToEdit != null;

        LocalDate updatedDate = editSessionDescriptor.getDate().orElse(sessionToEdit.getDate());
        String updatedSubject = editSessionDescriptor.getSubject().orElse(sessionToEdit.getSubject());

        return new Session(sessionToEdit.getId(), updatedDate, updatedSubject);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Optional<Session> sessionToEdit = model.getSessionById(sessionId);
        if (sessionToEdit.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_SESSION_NOT_FOUND);
        }

        Session editedSession = createEditedSession(sessionToEdit.get(), editSessionDescriptor);

        model.setSession(sessionToEdit.get(), editedSession);
        model.updateFilteredSessionList(FILTER_SHOW_ALL_SESSIONS);
        return new CommandResult.Builder(String.format(MESSAGE_EDIT_SESSION_SUCCESS, editedSession))
                .withTab(Tab.SESSION)
                .build();
    }

    /**
     * Stores the details to edit the session with. Each non-empty field value will replace the
     * corresponding field value of the session.
     */
    public static class EditSessionDescriptor {
        private LocalDate date;
        private String subject;

        public EditSessionDescriptor() {
        }

        /**
         * Copy constructor.
         */
        public EditSessionDescriptor(EditSessionDescriptor toCopy) {
            setDate(toCopy.date);
            setSubject(toCopy.subject);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(date, subject);
        }

        Optional<LocalDate> getDate() {
            return Optional.ofNullable(date);
        }

        public void setDate(LocalDate date) {
            this.date = date;
        }

        Optional<String> getSubject() {
            return Optional.ofNullable(subject);
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            if (!(other instanceof EditSessionDescriptor)) {
                return false;
            }

            EditSessionDescriptor e = (EditSessionDescriptor) other;

            return getDate().equals(e.getDate())
                    && getSubject().equals(e.getSubject());
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("date", date)
                    .add("subject", subject)
                    .toString();
        }
    }
}
