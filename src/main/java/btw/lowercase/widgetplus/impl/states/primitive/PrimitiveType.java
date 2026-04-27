package btw.lowercase.widgetplus.impl.states.primitive;

import com.mojang.serialization.MapCodec;

public interface PrimitiveType {
    MapCodec<? extends PrimitiveType> type();
}