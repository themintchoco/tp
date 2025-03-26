package tutorly.testutil;

import static tutorly.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static tutorly.logic.parser.CliSyntax.PREFIX_EMAIL;
import static tutorly.logic.parser.CliSyntax.PREFIX_MEMO;
import static tutorly.logic.parser.CliSyntax.PREFIX_NAME;
import static tutorly.logic.parser.CliSyntax.PREFIX_PHONE;
import static tutorly.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import tutorly.logic.commands.AddStudentCommand;
import tutorly.logic.commands.EditStudentCommand.EditPersonDescriptor;
import tutorly.model.person.Person;
import tutorly.model.tag.Tag;

/**
 * A utility class for Person.
 */
public class PersonUtil {

    /**
     * Returns an add command string for adding the {@code person}.
     */
    public static String getAddCommand(Person person) {
        return AddStudentCommand.COMMAND_STRING + " " + getPersonDetails(person);
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getPersonDetails(Person person) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME).append(person.getName().fullName).append(" ");

        if (!person.getPhone().value.isEmpty()) {
            sb.append(PREFIX_PHONE).append(person.getPhone().value).append(" ");
        }

        if (!person.getEmail().value.isEmpty()) {
            sb.append(PREFIX_EMAIL).append(person.getEmail().value).append(" ");
        }

        if (!person.getAddress().value.isEmpty()) {
            sb.append(PREFIX_ADDRESS).append(person.getAddress().value).append(" ");
        }

        person.getTags().forEach(
                s -> sb.append(PREFIX_TAG).append(s.tagName).append(" ")
        );

        if (!person.getMemo().value.isEmpty()) {
            sb.append(person.getMemo().value);
        }

        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditPersonDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));

        descriptor.getPhone().ifPresent(phone -> {
            if (!phone.value.isEmpty()) {
                sb.append(PREFIX_PHONE).append(phone.value).append(" ");
            }
        });

        descriptor.getEmail().ifPresent(email -> {
            if (!email.value.isEmpty()) {
                sb.append(PREFIX_EMAIL).append(email.value).append(" ");
            }
        });

        descriptor.getAddress().ifPresent(address -> {
            if (!address.value.isEmpty()) {
                sb.append(PREFIX_ADDRESS).append(address.value).append(" ");
            }
        });

        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }

        descriptor.getMemo().ifPresent(memo -> {
            if (!memo.value.isEmpty()) {
                sb.append(PREFIX_MEMO).append(memo.value).append(" ");
            }
        });

        return sb.toString();
    }
}
