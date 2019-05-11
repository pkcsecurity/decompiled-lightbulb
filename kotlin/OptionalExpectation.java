package kotlin;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import kotlin.ExperimentalMultiplatform;
import kotlin.Metadata;

@Retention(RetentionPolicy.CLASS)
@Target({ElementType.ANNOTATION_TYPE})
@Metadata
@kotlin.annotation.Retention
@kotlin.annotation.Target
@ExperimentalMultiplatform
public @interface OptionalExpectation {
}
