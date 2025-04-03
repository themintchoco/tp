package tutorly.logic.commands;

import static java.util.Objects.requireNonNull;
import static tutorly.commons.util.CollectionUtil.requireAllNonNull;
import static tutorly.logic.parser.CliSyntax.PREFIX_SESSION;
import static tutorly.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static tutorly.logic.parser.CliSyntax.PREFIX_TIMESLOT;
import static tutorly.model.Model.FILTER_SHOW_ALL_SESSIONS;

import java.util.Optional;

import tutorly.commons.util.CollectionUtil;
import tutorly.commons.util.ToStringBuilder;
import tutorly.logic.Messages;
import tutorly.logic.commands.exceptions.CommandException;
import tutorly.model.Model;
import tutorly.model.session.Session;
import tutorly.model.session.Subject;
import tutorly.model.session.Timeslot;
import tutorly.ui.Tab;

/**
 * Edits the details of an existing session in the address book.
 */
public class EditSessionCommand extends SessionCommand {
    public static final String COMMAND_WORD = "edit";
    public static final String COMMAND_STRING = SessionCommand.COMMAND_STRING + " " + COMMAND_WORD;

    public static final String MESSAGE_USAGE = COMMAND_STRING
            + ": Edits the details of the session identified by a SESSION_ID. "
            + "Existing values will be overwritten by the input values."
            + "\nParameters: SESSION_ID "
            + "[" + PREFIX_TIMESLOT + "TIMESLOT] "
            + "[" + PREFIX_SUBJECT + "SUBJECT] "
            + "\nExample: " + COMMAND_STRING + " 1 "
            + PREFIX_TIMESLOT + "30 Mar 2025 11:30-13:30 "
            + PREFIX_SUBJECT + "Mathematics";

    public static final String MESSAGE_EDIT_SESSION_SUCCESS = "Edited session: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

    private final int sessionId;
    private final EditSessionDescriptor editSessionDescriptor;

    /**
     * @param id containing the ID or name of the session to edit
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
        requireAllNonNull(sessionToEdit, editSessionDescriptor);

        Timeslot updatedTimeslot = editSessionDescriptor.getTimeslot().orElse(sessionToEdit.getTimeslot());
        Subject updatedSubject = editSessionDescriptor.getSubject().orElse(sessionToEdit.getSubject());

        Session newSession = new Session(updatedTimeslot , updatedSubject);
        newSession.setId(sessionToEdit.getId());

        return newSession;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Optional<Session> sessionToEdit = model.getSessionById(sessionId);
        if (sessionToEdit.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_SESSION_NOT_FOUND);
        }

        Session editedSession = createEditedSession(sessionToEdit.get(), editSessionDescriptor);

        if (model.hasOverlappingSession(editedSession)) {
            throw new CommandException(Messages.MESSAGE_SESSION_OVERLAP);
        }

        model.setSession(sessionToEdit.get(), editedSession);
        model.updateFilteredSessionList(FILTER_SHOW_ALL_SESSIONS);

        return new CommandResult.Builder(String.format(MESSAGE_EDIT_SESSION_SUCCESS, Messages.format(editedSession)))
                .withTab(Tab.session(editedSession))
                .withReverseCommand(new EditSessionCommand(
                        sessionId, EditSessionDescriptor.fromSession(sessionToEdit.get())))
                .build();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof EditSessionCommand)) {
            return false;
        }

        EditSessionCommand e = (EditSessionCommand) other;
        return sessionId == e.sessionId
                && editSessionDescriptor.equals(e.editSessionDescriptor);
    }

    /**
     * Stores the details to edit the session with. Each non-empty field value will replace the
     * corresponding field value of the session.
     */
    public static class EditSessionDescriptor {
        private Timeslot timeslot;
        private Subject subject;

        public EditSessionDescriptor() {
        }

        /**
         * Copy constructor.
         */
        public EditSessionDescriptor(EditSessionDescriptor toCopy) {
            setTimeslot(toCopy.timeslot);
            setSubject(toCopy.subject);
        }

        /**
        * Returns a {@code EditSessionDescriptor} with the same values as {@code session}.
        */
        public static EditSessionDescriptor fromSession(Session session) {
            EditSessionDescriptor descriptor = new EditSessionDescriptor();
            descriptor.setTimeslot(session.getTimeslot());
            descriptor.setSubject(session.getSubject());
            return descriptor;
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(timeslot, subject);
        }

        public Optional<Timeslot> getTimeslot() {
            return Optional.ofNullable(timeslot);
        }

        public void setTimeslot(Timeslot date) {
            this.timeslot = date;
        }

        public Optional<Subject> getSubject() {
            return Optional.ofNullable(subject);
        }

        public void setSubject(Subject subject) {
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

            return getTimeslot().equals(e.getTimeslot())
                    && getSubject().equals(e.getSubject());
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("timeslot", timeslot)
                    .add("subject", subject)
                    .toString();
        }
    }
}
