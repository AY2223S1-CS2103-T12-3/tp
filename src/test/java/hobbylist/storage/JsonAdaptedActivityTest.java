package hobbylist.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static hobbylist.storage.JsonAdaptedActivity.MISSING_FIELD_MESSAGE_FORMAT;
import static hobbylist.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import hobbylist.testutil.Assert;
import hobbylist.testutil.TypicalActivities;
import hobbylist.commons.exceptions.IllegalValueException;
import hobbylist.model.activity.Description;
import hobbylist.model.activity.Name;

public class JsonAdaptedActivityTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_DESCRIPTION = " ";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = TypicalActivities.ACTIVITY_B.getName().toString();
    private static final String VALID_ADDRESS = TypicalActivities.ACTIVITY_B.getDescription().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = TypicalActivities.ACTIVITY_B.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validActivityDetails_returnsActivity() throws Exception {
        JsonAdaptedActivity activity = new JsonAdaptedActivity(TypicalActivities.ACTIVITY_B);
        assertEquals(TypicalActivities.ACTIVITY_B, activity.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedActivity activity =
                new JsonAdaptedActivity(INVALID_NAME, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, activity::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedActivity activity = new JsonAdaptedActivity(null, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, activity::toModelType);
    }

    @Test
    public void toModelType_invalidDescription_throwsIllegalValueException() {
        JsonAdaptedActivity activity =
                new JsonAdaptedActivity(VALID_NAME, INVALID_DESCRIPTION, VALID_TAGS);
        String expectedMessage = Description.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, activity::toModelType);
    }

    @Test
    public void toModelType_nullDescription_throwsIllegalValueException() {
        JsonAdaptedActivity activity = new JsonAdaptedActivity(VALID_NAME, null, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, activity::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedActivity activity =
                new JsonAdaptedActivity(VALID_NAME, VALID_ADDRESS, invalidTags);
        Assert.assertThrows(IllegalValueException.class, activity::toModelType);
    }

}
