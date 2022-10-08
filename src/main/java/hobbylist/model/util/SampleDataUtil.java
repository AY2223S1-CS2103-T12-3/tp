package hobbylist.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import hobbylist.model.HobbyList;
import hobbylist.model.ReadOnlyHobbyList;
import hobbylist.model.activity.Activity;
import hobbylist.model.activity.Description;
import hobbylist.model.activity.Name;
import hobbylist.model.activity.Remark;
import hobbylist.model.tag.Tag;

/**
 * Contains utility methods for populating {@code HobbyList} with sample data.
 */
public class SampleDataUtil {
    public static Activity[] getSampleActivities() {
        return new Activity[] {
            new Activity(new Name("And Then There Were None"),
                new Description("Mystery novel by Agatha Christie"),
                    new Remark(""),
                getTagSet("book")),
            new Activity(new Name("Battlefield 4"),
                new Description("First person shooter by EA"),
                    new Remark(""),
                getTagSet("video_game")),
            new Activity(new Name("Charlotte"),
                new Description("Comedy drama anime"),
                    new Remark(""),
                getTagSet("anime", "tv_series")),
            new Activity(new Name("Despicable Me 3"),
                new Description("Comedy film directed by Pierre Coffin and Kyle Balda"),
                    new Remark(""),
                getTagSet("movie")),
            new Activity(new Name("42km run"),
                new Description("Exercise on 1 Jan 1970"),
                    new Remark(""),
                getTagSet("exercise")),
            new Activity(new Name("Sleep for 48 hours"),
                new Description("Sleep from 1 Oct 2022 to 3 Oct 2022"),
                    new Remark(""),
                getTagSet("sleep"))
        };
    }

    public static ReadOnlyHobbyList getSampleHobbyList() {
        HobbyList sampleAb = new HobbyList();
        for (Activity sampleActivity : getSampleActivities()) {
            sampleAb.addActivity(sampleActivity);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
