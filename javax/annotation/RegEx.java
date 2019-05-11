package javax.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.annotation.Syntax;
import javax.annotation.meta.TypeQualifierNickname;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Syntax
@TypeQualifierNickname
public @interface RegEx {
}
