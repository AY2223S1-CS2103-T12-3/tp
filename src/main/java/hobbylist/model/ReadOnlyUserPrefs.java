package hobbylist.model;

import java.nio.file.Path;

import hobbylist.commons.core.AliasSettings;
import hobbylist.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    AliasSettings getAliasSettings();

    Path getHobbyListFilePath();

}
