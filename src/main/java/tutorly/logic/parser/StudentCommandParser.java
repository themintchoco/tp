package tutorly.logic.parser;

import static tutorly.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import tutorly.logic.commands.AddStudentCommand;
import tutorly.logic.commands.Command;
import tutorly.logic.commands.DeleteStudentCommand;
import tutorly.logic.commands.EditStudentCommand;
import tutorly.logic.commands.ListStudentCommand;
import tutorly.logic.commands.RestoreStudentCommand;
import tutorly.logic.commands.SearchStudentCommand;
import tutorly.logic.commands.StudentCommand;
import tutorly.logic.parser.exceptions.ParseException;

/**
 * Subparser for the student command.
 */
public class StudentCommandParser extends AddressBookParser {

    @Override
    protected Command parseCommand(String command, String args) throws ParseException {
        switch (command) {
        case ListStudentCommand.COMMAND_WORD:
            return new ListStudentCommand();

        case AddStudentCommand.COMMAND_WORD:
            return new AddStudentCommandParser().parse(args);

        case EditStudentCommand.COMMAND_WORD:
            return new EditStudentCommandParser().parse(args);

        case DeleteStudentCommand.COMMAND_WORD:
            return new DeleteStudentCommandParser().parse(args);

        case RestoreStudentCommand.COMMAND_WORD:
            return new RestoreStudentCommandParser().parse(args);

        case SearchStudentCommand.COMMAND_WORD:
            return new SearchStudentCommandParser().parse(args);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    @Override
    protected Command defaultCommand() {
        return new StudentCommand();
    }

}
