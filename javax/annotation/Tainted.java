package javax.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.annotation.Untainted;
import javax.annotation.meta.TypeQualifierNickname;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Untainted
@TypeQualifierNickname
public @interface Tainted {
}
