package hobbylist.logic.commands;

import static java.util.Objects.requireNonNull;

import hobbylist.commons.core.Messages;
import hobbylist.model.Model;
import hobbylist.model.activity.NameOrDescContainsKeywordsPredicate;

/**
 * Finds and lists all activities in HobbyList whose name or description contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {



    private static String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all activities whose names or descriptions"

            + "contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers "
            + "or find all activities on a certain date/in a certain month/year.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...or yyyy-mm-dd/yyyy-mm/yyyy\n"
            + "Example: " + COMMAND_WORD + " sleep exercise code\n"
            + "Example: " + COMMAND_WORD + " 1974-02-02\n";


    private final NameOrDescContainsKeywordsPredicate predicate;

    public FindCommand(NameOrDescContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    /**
     * Sets the command word for the command.
     * @param word Word to set command to.
     */
    public static void setCommandWord(String word) {
        COMMAND_WORD = word;
    }

    /**
     * Gets the command word for the command.
     * @return Command word.
     */
    public static String getCommandWord() {
        return COMMAND_WORD;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredActivityList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_ACTIVITIES_LISTED_OVERVIEW, model.getFilteredActivityList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
