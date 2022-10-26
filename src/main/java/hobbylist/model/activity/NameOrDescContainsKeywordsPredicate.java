package hobbylist.model.activity;

import java.util.List;
import java.util.function.Predicate;

import com.sun.jdi.IntegerValue;
import hobbylist.commons.util.StringUtil;

/**
 * Tests that an {@code Activity}'s {@code Name} matches any of the keywords given.
 */
public class NameOrDescContainsKeywordsPredicate implements Predicate<Activity> {
    private final List<String> keywords;

    public NameOrDescContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Activity activity) {
        for(int i =0;i<keywords.size();i++){
            if (keywords.get(i).split("rate/").length==2){
                String[] t = keywords.get(i).split("rate/");
                int index = Integer.valueOf(t[1]);
                if(activity.getRating()==index){
                    return true;
                }
            }
        }
        return keywords.stream().anyMatch(keyword -> StringUtil.containsWordIgnoreCase(
                activity.getName().fullName, keyword)
            || StringUtil.containsWordIgnoreCase(activity.getDescription().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameOrDescContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((NameOrDescContainsKeywordsPredicate) other).keywords)); // state check
    }

}
