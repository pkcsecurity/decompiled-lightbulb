package kotlin.internal;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import kotlin.Metadata;

@Retention(RetentionPolicy.CLASS)
@Target({ElementType.METHOD})
@Metadata
@kotlin.annotation.Retention
@kotlin.annotation.Target
public @interface PlatformDependent {
}
