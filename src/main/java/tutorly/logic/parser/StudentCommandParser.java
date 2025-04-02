package tutorly.logic.parser;

import tutorly.logic.commands.AddStudentCommand;
import tutorly.logic.commands.Command;
import tutorly.logic.commands.DeleteStudentCommand;
import tutorly.logic.commands.EditStudentCommand;
import tutorly.logic.commands.ListStudentCommand;
import tutorly.logic.commands.SearchStudentCommand;
import tutorly.logic.commands.StudentCommand;
import tutorly.logic.parser.exceptions.ParseException;

/**
 * Subparser for the student command.
 */
public class StudentCommandParser extends AddressBookParser {

    @Override
    protected Command parseCommand(String command, String args) throws ParseException {
        command = command.toLowerCase();

        switch (command) {
        case ListStudentCommand.COMMAND_WORD:
            return new ListStudentCommand();

        case AddStudentCommand.COMMAND_WORD:
            return new AddStudentCommandParser().parse(args);

        case EditStudentCommand.COMMAND_WORD:
            return new EditStudentCommandParser().parse(args);

        case DeleteStudentCommand.COMMAND_WORD:
            return new DeleteStudentCommandParser().parse(args);

        case SearchStudentCommand.COMMAND_WORD:
            return new SearchStudentCommandParser().parse(args);

        default:
            return defaultCommand(command);
        }
    }

    @Override
    protected Command defaultCommand() {
        return new StudentCommand();
    }

    private Command defaultCommand(String args) throws ParseException {
        return new StudentCommand(ParserUtil.parseIdentity(args));
    }

}
