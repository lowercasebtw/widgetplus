package btw.lowercase.widgetplus.impl.properties;

import btw.lowercase.widgetplus.impl.property.RangeSelectWidgetProperty;
import com.mojang.serialization.MapCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ExtraCodecs;

public class RangeSelectWidgetProperties {
    private static final ExtraCodecs.LateBoundIdMapper<Identifier, MapCodec<? extends RangeSelectWidgetProperty>> ID_MAPPER = new ExtraCodecs.LateBoundIdMapper<>();
    public static final MapCodec<RangeSelectWidgetProperty> MAP_CODEC = ID_MAPPER
            .codec(Identifier.CODEC)
            .dispatchMap("property", RangeSelectWidgetProperty::type, c -> c);

    public static void bootstrap() {
    }
}
