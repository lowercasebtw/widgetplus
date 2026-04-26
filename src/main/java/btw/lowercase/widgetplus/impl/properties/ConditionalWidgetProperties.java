package btw.lowercase.widgetplus.impl.properties;

import btw.lowercase.widgetplus.impl.property.ConditionalWidgetProperty;
import com.mojang.serialization.MapCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ExtraCodecs;

public class ConditionalWidgetProperties {
    public static final ExtraCodecs.LateBoundIdMapper<Identifier, MapCodec<? extends ConditionalWidgetProperty>> ID_MAPPER = new ExtraCodecs.LateBoundIdMapper<>();
    public static final MapCodec<ConditionalWidgetProperty> MAP_CODEC = ID_MAPPER
            .codec(Identifier.CODEC)
            .dispatchMap("property", ConditionalWidgetProperty::type, c -> c);

    public static void bootstrap() {
    }
}