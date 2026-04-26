package btw.lowercase.widgetplus.impl.properties;

import btw.lowercase.widgetplus.impl.property.SelectWidgetProperty;
import com.mojang.serialization.Codec;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ExtraCodecs;

public class SelectWidgetProperties {
    private static final ExtraCodecs.LateBoundIdMapper<Identifier, SelectWidgetProperty.Type<?, ?>> ID_MAPPER = new ExtraCodecs.LateBoundIdMapper<>();
    public static final Codec<SelectWidgetProperty.Type<?, ?>> CODEC = ID_MAPPER.codec(Identifier.CODEC);

    public static void bootstrap() {
    }
}