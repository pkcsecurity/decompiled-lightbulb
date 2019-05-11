package kotlin.coroutines;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import kotlin.Metadata;
import kotlin.SinceKotlin;

@Retention(RetentionPolicy.CLASS)
@Target({ElementType.TYPE})
@Metadata
@kotlin.annotation.Retention
@kotlin.annotation.Target
@SinceKotlin
public @interface RestrictsSuspension {
}
