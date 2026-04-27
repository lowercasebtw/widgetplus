package btw.lowercase.widgetplus.impl.properties.conditional;

import com.mojang.serialization.MapCodec;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.entity.player.Player;
import org.jspecify.annotations.Nullable;

public record HoveredOrFocused() implements ConditionalWidgetProperty {
    public static final MapCodec<HoveredOrFocused> MAP_CODEC = MapCodec.unit(new HoveredOrFocused());

    @Override
    public boolean get(final AbstractWidget widget, @Nullable final Screen screen, @Nullable final Player player) {
        return widget.isHoveredOrFocused();
    }

    @Override
    public MapCodec<? extends ConditionalWidgetProperty> type() {
        return MAP_CODEC;
    }
}
