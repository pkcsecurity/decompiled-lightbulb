package kotlinx.android.parcel;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import kotlin.Metadata;
import kotlinx.android.parcel.Parceler;

@Retention(RetentionPolicy.SOURCE)
@Target({})
@Metadata
@kotlin.annotation.Retention
@kotlin.annotation.Target
public @interface WriteWith<P extends Object & Parceler<?>> {
}
