package hobbylist.logic.commands;

import static java.util.Objects.requireNonNull;

import hobbylist.commons.core.Messages;
import hobbylist.model.Model;
import hobbylist.model.activity.TagMatchesKeywordPredicate;

/**
 * Finds and lists all activities in HobbyList whose tag matches any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FilterTagCommand extends Command {

    public static final String COMMAND_WORD = "filterTag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all activities whose tags match"
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + "sport";

    private final TagMatchesKeywordPredicate predicate;

    public FilterTagCommand(TagMatchesKeywordPredicate predicate) {
        this.predicate = predicate;
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
                || (other instanceof FilterTagCommand // instanceof handles nulls
                && predicate.equals(((FilterTagCommand) other).predicate)); // state check
    }
}
