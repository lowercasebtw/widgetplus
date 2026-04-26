package btw.lowercase.widgetplus.impl.property;

import com.mojang.serialization.MapCodec;

public interface RangeSelectWidgetProperty {
    // TODO: float get(...)

    MapCodec<? extends RangeSelectWidgetProperty> type();
}
