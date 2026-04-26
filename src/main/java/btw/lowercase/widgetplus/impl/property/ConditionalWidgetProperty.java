package btw.lowercase.widgetplus.impl.property;

import com.mojang.serialization.MapCodec;

public interface ConditionalWidgetProperty {
    MapCodec<? extends ConditionalWidgetProperty> type();
}