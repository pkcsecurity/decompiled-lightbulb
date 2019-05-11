package kotlin.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import kotlin.Metadata;
import kotlin.annotation.MustBeDocumented;

@Documented
@java.lang.annotation.Retention(RetentionPolicy.RUNTIME)
@java.lang.annotation.Target({ElementType.ANNOTATION_TYPE})
@Metadata
@MustBeDocumented
@Target
public @interface Target {
}
