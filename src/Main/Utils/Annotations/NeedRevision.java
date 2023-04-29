package Main.Utils.Annotations;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Repeatable(value = RevisionsContainer.class)
@Retention(value = RetentionPolicy.SOURCE)
public @interface NeedRevision {
    String comment();
}
