package btw.lowercase.widgetplus.impl.properties.select;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.GraphicsPreset;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.entity.player.Player;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public record VanillaGraphicsPreset() implements SelectWidgetProperty<GraphicsPreset> {
    public static final SelectWidgetProperty.Type<VanillaGraphicsPreset, GraphicsPreset> TYPE = SelectWidgetProperty.Type.create(
            MapCodec.unit(new VanillaGraphicsPreset()), GraphicsPreset.CODEC
    );

    @Override
    public @NonNull GraphicsPreset get(final AbstractWidget widget, @Nullable final Screen screen, @Nullable final Player player) {
        return Minecraft.getInstance().options.graphicsPreset().get();
    }

    @Override
    public Codec<GraphicsPreset> valueCodec() {
        return GraphicsPreset.CODEC;
    }

    @Override
    public Type<? extends SelectWidgetProperty<GraphicsPreset>, GraphicsPreset> type() {
        return TYPE;
    }
}