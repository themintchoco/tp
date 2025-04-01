package tutorly.testutil;

import static tutorly.logic.parser.ParserUtil.parseSubject;
import static tutorly.logic.parser.ParserUtil.parseTimeslot;

import tutorly.logic.commands.EditSessionCommand.EditSessionDescriptor;
import tutorly.logic.parser.exceptions.ParseException;
import tutorly.model.session.Session;
import tutorly.model.session.Subject;

/**
 * A utility class to help with building EditSessionDescriptor objects.
 */
public class EditSessionDescriptorBuilder {

    private final EditSessionDescriptor descriptor;

    public EditSessionDescriptorBuilder() {
        descriptor = new EditSessionDescriptor();
    }

    public EditSessionDescriptorBuilder(EditSessionDescriptor descriptor) {
        this.descriptor = new EditSessionDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditSessionDescriptor} with fields containing {@code session}'s details
     */
    public EditSessionDescriptorBuilder(Session session) {
        descriptor = new EditSessionDescriptor();

        if (!session.getTimeslot().toString().isEmpty()) {
            descriptor.setTimeslot(session.getTimeslot());
        }
        if (!session.getSubject().toString().isEmpty()) {
            descriptor.setSubject(session.getSubject());
        }
    }

    /**
     * Sets the {@code Timeslot} of the {@code EditSessionDescriptor} that we are building.
     */
    public EditSessionDescriptorBuilder withTimeslot(String timeslot) throws ParseException {
        descriptor.setTimeslot(parseTimeslot(timeslot));
        return this;
    }

    /**
     * Sets the {@code Subject} of the {@code EditSessionDescriptor} that we are building.
     */
    public EditSessionDescriptorBuilder withSubject(String subject) throws ParseException {
        descriptor.setSubject(parseSubject(subject));
        return this;
    }

    public EditSessionDescriptor build() {
        return descriptor;
    }
}
