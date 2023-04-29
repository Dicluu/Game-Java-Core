package Main.Utils.Annotations;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Repeatable(value = ImprovementContainer.class)
@Retention(value = RetentionPolicy.SOURCE)
public @interface NeedImprovement {
    String comment();
}
