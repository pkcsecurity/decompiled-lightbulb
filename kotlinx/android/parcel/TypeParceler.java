package kotlinx.android.parcel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import kotlin.Metadata;
import kotlin.annotation.Repeatable;
import kotlinx.android.parcel.Parceler;

@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.TYPE})
@Metadata
@Repeatable
@kotlin.annotation.Retention
@kotlin.annotation.Target
public @interface TypeParceler<T extends Object, P extends Object & Parceler<? super T>> {
}
