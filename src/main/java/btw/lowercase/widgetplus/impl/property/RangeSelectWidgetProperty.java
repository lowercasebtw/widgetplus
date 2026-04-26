package btw.lowercase.widgetplus.impl.property;

import com.mojang.serialization.MapCodec;

public interface RangeSelectWidgetProperty {
    float get();

    MapCodec<? extends RangeSelectWidgetProperty> type();
}
