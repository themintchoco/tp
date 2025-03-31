package tutorly.logic;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;
import java.util.Stack;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import tutorly.commons.core.GuiSettings;
import tutorly.commons.core.LogsCenter;
import tutorly.logic.commands.Command;
import tutorly.logic.commands.CommandResult;
import tutorly.logic.commands.exceptions.CommandException;
import tutorly.logic.parser.AddressBookParser;
import tutorly.logic.parser.exceptions.ParseException;
import tutorly.model.Model;
import tutorly.model.ReadOnlyAddressBook;
import tutorly.model.attendancerecord.AttendanceRecord;
import tutorly.model.person.Person;
import tutorly.model.session.Session;
import tutorly.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_FORMAT = "Could not save data due to the following error: %s";

    public static final String FILE_OPS_PERMISSION_ERROR_FORMAT =
            "Could not save data to file %s due to insufficient permissions to write to the file or the folder.";

    public static final String UNDO_STACK_EMPTY = "No command to undo.";

    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final AddressBookParser addressBookParser;

    private final Stack<Command> undoStack = new Stack<>();

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        addressBookParser = new AddressBookParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        Command command = addressBookParser.parse(commandText);
        CommandResult commandResult = command.execute(model);

        if (commandResult.hasReverseCommand()) {
            Command reverseCommand = commandResult.getReverseCommand();
            undoStack.push(reverseCommand);
        }

        if (commandResult.shouldReverseLast()) {
            if (undoStack.isEmpty()) {
                throw new CommandException(UNDO_STACK_EMPTY);
            }
            Command lastCommand = undoStack.pop();
            CommandResult undoCommandResult = lastCommand.execute(model);

            commandResult = new CommandResult.Builder(undoCommandResult)
                    .withFeedback(commandResult.getFeedbackToUser() + "\n" + undoCommandResult.getFeedbackToUser())
                    .build();
        }

        try {
            storage.saveAddressBook(model.getAddressBook());
        } catch (AccessDeniedException e) {
            throw new CommandException(String.format(FILE_OPS_PERMISSION_ERROR_FORMAT, e.getMessage()), e);
        } catch (IOException ioe) {
            throw new CommandException(String.format(FILE_OPS_ERROR_FORMAT, ioe.getMessage()), ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return model.getAddressBook();
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return model.getPersonList();
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    @Override
    public ObservableList<Session> getSessionList() {
        return model.getSessionList();
    }

    @Override
    public ObservableList<Session> getFilteredSessionList() {
        return model.getFilteredSessionList();
    }

    @Override
    public ObservableList<AttendanceRecord> getAttendanceRecordList() {
        return model.getAttendanceRecordList();
    }

    @Override
    public Path getAddressBookFilePath() {
        return model.getAddressBookFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }
}
