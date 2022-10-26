package hobbylist.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import hobbylist.commons.core.GuiSettings;
import hobbylist.commons.core.LogsCenter;
import hobbylist.logic.commands.Command;
import hobbylist.logic.commands.CommandResult;
import hobbylist.logic.commands.exceptions.CommandException;
import hobbylist.logic.parser.HobbyListParser;
import hobbylist.logic.parser.exceptions.ParseException;
import hobbylist.model.Model;
import hobbylist.model.ReadOnlyHobbyList;
import hobbylist.model.activity.Activity;
import hobbylist.storage.Storage;
import javafx.collections.ObservableList;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final HobbyListParser hobbyListParser;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        hobbyListParser = new HobbyListParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = hobbyListParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveHobbyList(model.getHobbyList());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyHobbyList getHobbyList() {
        return model.getHobbyList();
    }

    @Override
    public ObservableList<Activity> getFilteredActivityList() {
        return model.getFilteredActivityList();
    }

    @Override
    public ObservableList<Activity> getSelectedActivity() {
        return model.getSelectedActivity();
    }

    @Override
    public Path getHobbyListFilePath() {
        return model.getHobbyListFilePath();
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
