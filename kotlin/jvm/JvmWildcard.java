package kotlin.jvm;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import kotlin.Metadata;
import kotlin.annotation.MustBeDocumented;

@Documented
@Retention(RetentionPolicy.CLASS)
@Target({})
@Metadata
@MustBeDocumented
@kotlin.annotation.Retention
@kotlin.annotation.Target
public @interface JvmWildcard {
}
