package kotlin;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.experimental.ExperimentalTypeInference;

@Retention(RetentionPolicy.CLASS)
@Target({ElementType.METHOD, ElementType.PARAMETER})
@Metadata
@kotlin.annotation.Retention
@kotlin.annotation.Target
@SinceKotlin
@ExperimentalTypeInference
public @interface BuilderInference {
}
