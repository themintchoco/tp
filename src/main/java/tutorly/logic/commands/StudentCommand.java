package tutorly.logic.commands;

import java.util.Optional;

import tutorly.logic.Messages;
import tutorly.logic.commands.exceptions.CommandException;
import tutorly.model.Model;
import tutorly.model.person.Identity;
import tutorly.model.person.Person;
import tutorly.ui.Tab;

/**
 * Represents a command related to students.
 */
public class StudentCommand extends Command {

    public static final String COMMAND_WORD = "student";
    public static final String COMMAND_STRING = COMMAND_WORD;

    private final Optional<Identity> identity;

    public StudentCommand(Identity identity) {
        this.identity = Optional.of(identity);
    }

    public StudentCommand() {
        this.identity = Optional.empty();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (identity.isPresent()) {
            Person student = model.getPersonByIdentity(identity.get())
                    .orElseThrow(() -> new CommandException(Messages.MESSAGE_PERSON_NOT_FOUND));

            return new CommandResult.Builder(String.format(Messages.MESSAGE_PERSON_SHOWN, Messages.format(student)))
                    .withTab(Tab.student(student))
                    .build();
        }

        return new CommandResult.Builder(Messages.MESSAGE_PERSONS_SHOWN).withTab(Tab.student()).build();
    }

}
