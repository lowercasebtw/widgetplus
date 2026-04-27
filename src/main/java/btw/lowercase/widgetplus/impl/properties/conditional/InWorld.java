package btw.lowercase.widgetplus.impl.properties.conditional;

import com.mojang.serialization.MapCodec;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.entity.player.Player;
import org.jspecify.annotations.Nullable;

public record InWorld() implements ConditionalWidgetProperty {
    public static final MapCodec<InWorld> MAP_CODEC = MapCodec.unit(new InWorld());

    @Override
    public boolean get(final AbstractWidget widget, @Nullable final Screen screen, @Nullable final Player player) {
        return player != null;
    }

    @Override
    public MapCodec<? extends ConditionalWidgetProperty> type() {
        return MAP_CODEC;
    }
}