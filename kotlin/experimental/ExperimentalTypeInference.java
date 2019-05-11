package kotlin.experimental;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import kotlin.Experimental;
import kotlin.Metadata;
import kotlin.SinceKotlin;

@Retention(RetentionPolicy.CLASS)
@Target({ElementType.ANNOTATION_TYPE})
@Metadata
@kotlin.annotation.Retention
@kotlin.annotation.Target
@Experimental
@SinceKotlin
public @interface ExperimentalTypeInference {
}
