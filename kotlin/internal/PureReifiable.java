package kotlin.internal;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import kotlin.Metadata;

@Retention(RetentionPolicy.CLASS)
@Target({})
@Metadata
@kotlin.annotation.Retention
@kotlin.annotation.Target
public @interface PureReifiable {
}
