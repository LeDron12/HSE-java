package mapper.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

enum NullHandling {
    EXCLUDE,
    INCLUDE
}

enum UnknownPropertiesPolicy {
    IGNORE,
    FAIL
}


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Exported {
    NullHandling nullHandling() default NullHandling.EXCLUDE;

    UnknownPropertiesPolicy unknownPropertiesPolicy()
            default UnknownPropertiesPolicy.FAIL;
}
