package kotlin;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import kotlin.SinceKotlin;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Metadata
@kotlin.annotation.Retention
@kotlin.annotation.Target
@SinceKotlin
public @interface Metadata {
}
