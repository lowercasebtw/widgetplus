package btw.lowercase.widgetplus.impl.management;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;

public final class ItemBlockRenderer {
    public static void render(final WidgetRenderContext renderContext, final Item item) {
        // TODO
    }

    public record Item(Identifier model, boolean glint, int tint) {
        public static final Codec<Item> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Identifier.CODEC.fieldOf("model").forGetter(Item::model),
                Codec.BOOL.optionalFieldOf("glint", false).forGetter(Item::glint),
                Codec.INT.optionalFieldOf("tint", ARGB.white(1.0F)).forGetter(Item::tint)
        ).apply(instance, Item::new));
    }
}
