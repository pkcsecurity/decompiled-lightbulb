package javax.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.annotation.Nonnull;
import javax.annotation.meta.TypeQualifierNickname;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Nonnull
@TypeQualifierNickname
public @interface CheckForNull {
}
