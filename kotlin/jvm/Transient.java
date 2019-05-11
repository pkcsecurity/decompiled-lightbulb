package kotlin.jvm;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import kotlin.Metadata;
import kotlin.annotation.MustBeDocumented;

@Documented
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.FIELD})
@Metadata
@MustBeDocumented
@kotlin.annotation.Retention
@kotlin.annotation.Target
public @interface Transient {
}
