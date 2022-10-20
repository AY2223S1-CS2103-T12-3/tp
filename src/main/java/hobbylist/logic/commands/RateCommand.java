package hobbylist.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import hobbylist.commons.core.Messages;
import hobbylist.commons.core.index.Index;
import hobbylist.commons.util.CollectionUtil;
import hobbylist.logic.commands.exceptions.CommandException;
import hobbylist.logic.parser.CliSyntax;
import hobbylist.model.Model;
import hobbylist.model.activity.Activity;
import hobbylist.model.activity.Description;
import hobbylist.model.activity.Name;
import hobbylist.model.date.Date;
import hobbylist.model.tag.Tag;

/**
 * Add or modify the rating of an activity.
 */
public class RateCommand extends Command {

    public static final String COMMAND_WORD = "rate";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds or modifies the rating of the activity identified "
            + "by the index number used in the displayed activity list. "
            + "Existing value will be overwritten by the input value.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + CliSyntax.PREFIX_RATING + "RATING (1-5)]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + CliSyntax.PREFIX_RATING + " 4";

    public static final String MESSAGE_EDIT_ACTIVITY_SUCCESS = "Rated Activity: %1$s";
    public static final String MESSAGE_DUPLICATE_ACTIVITY = "This activity already exists in the HobbyList";

    private final Index index;
    private final int rating;

    /**
     * @param index of the activity in the filtered activity list to edit
     * @param rating rating of the activity (1 to 5)
     */
    public RateCommand(Index index, int rating) {
        requireNonNull(index);

        this.index = index;
        this.rating = rating;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Activity> lastShownList = model.getFilteredActivityList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ACTIVITY_DISPLAYED_INDEX);
        }

        Activity activityToRate= lastShownList.get(index.getZeroBased());
        Activity activityWithRating = createActivityWithRating(activityToRate, rating);

        if (!activityToRate.isSameActivity(activityWithRating) && model.hasActivity(activityWithRating)) {
            throw new CommandException(MESSAGE_DUPLICATE_ACTIVITY);
        }

        model.setActivity(activityToRate, activityWithRating);
        model.updateFilteredActivityList(Model.PREDICATE_SHOW_ALL_ACTIVITIES);
        return new CommandResult(String.format(MESSAGE_EDIT_ACTIVITY_SUCCESS, activityWithRating));
    }

    /**
     * Creates and returns an {@code Activity} with the details of {@code activityToRate}
     * with {@code rating}.
     */
    private static Activity createActivityWithRating(Activity activityToRate,
                                                 int rating) {
        assert activityToRate != null;
        return new Activity(activityToRate.getName(), activityToRate.getDescription(), activityToRate.getTags(), activityToRate.getDate(), rating);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RateCommand)) {
            return false;
        }

        // state check
        RateCommand r = (RateCommand) other;
        return index.equals(r.index)
                && rating == r.rating;
    }
}
