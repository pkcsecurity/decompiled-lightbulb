package kotlin;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import kotlin.Metadata;
import kotlin.annotation.MustBeDocumented;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({})
@Metadata
@MustBeDocumented
@kotlin.annotation.Target
public @interface ExtensionFunctionType {
}
