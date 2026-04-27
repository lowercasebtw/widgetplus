package btw.lowercase.widgetplus.impl.properties.conditional;

import com.mojang.serialization.MapCodec;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.entity.player.Player;
import org.jspecify.annotations.Nullable;

public record Hovered() implements ConditionalWidgetProperty {
    public static final MapCodec<Hovered> MAP_CODEC = MapCodec.unit(new Hovered());

    @Override
    public boolean get(final AbstractWidget widget, @Nullable final Screen screen, @Nullable final Player player) {
        return widget.isHovered();
    }

    @Override
    public MapCodec<? extends ConditionalWidgetProperty> type() {
        return MAP_CODEC;
    }
}