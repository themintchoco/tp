package tutorly.testutil;

import static tutorly.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static tutorly.logic.parser.CliSyntax.PREFIX_TIMESLOT;

import tutorly.logic.commands.AddSessionCommand;
import tutorly.logic.commands.EditSessionCommand.EditSessionDescriptor;
import tutorly.logic.parser.ParserUtil;
import tutorly.model.session.Session;

/**
 * A utility class for Session.
 */
public class SessionUtil {

    /**
     * Returns an add command string for adding the {@code session}.
     */
    public static String getAddCommand(Session session) {
        return AddSessionCommand.COMMAND_STRING + " " + getSessionDetails(session);
    }

    /**
     * Returns the part of command string for the given {@code session}'s details.
     */
    public static String getSessionDetails(Session session) {
        StringBuilder sb = new StringBuilder();

        sb.append(PREFIX_TIMESLOT)
                .append(session.getTimeslot().getStartTime().format(ParserUtil.DATE_FORMATTER))
                .append(" ")
                .append(session.getTimeslot().getStartTime().format(ParserUtil.TIME_FORMATTER))
                .append("-")
                .append(session.getTimeslot().getEndTime().format(ParserUtil.DATE_FORMATTER))
                .append(" ")
                .append(session.getTimeslot().getEndTime().format(ParserUtil.TIME_FORMATTER))
                .append(" ");

        sb.append(PREFIX_SUBJECT)
                .append(session.getSubject().subjectName)
                .append(" ");

        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditSessionDescriptor}'s details.
     */
    public static String getEditSessionDescriptorDetails(EditSessionDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();

        descriptor.getTimeslot().ifPresent(timeslot -> sb.append(PREFIX_TIMESLOT)
                .append(timeslot.getStartTime().format(ParserUtil.DATE_FORMATTER))
                .append(" ")
                .append(timeslot.getStartTime().format(ParserUtil.TIME_FORMATTER))
                .append("-")
                .append(timeslot.getEndTime().format(ParserUtil.DATE_FORMATTER))
                .append(" ")
                .append(timeslot.getEndTime().format(ParserUtil.TIME_FORMATTER))
                .append(" "));

        descriptor.getSubject().ifPresent(subject -> sb.append(PREFIX_SUBJECT)
                .append(subject.subjectName)
                .append(" "));

        return sb.toString();
    }
}
