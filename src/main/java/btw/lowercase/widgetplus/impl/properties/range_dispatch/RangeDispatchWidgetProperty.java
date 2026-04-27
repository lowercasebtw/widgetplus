package btw.lowercase.widgetplus.impl.properties.range_dispatch;

import com.mojang.serialization.MapCodec;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.entity.player.Player;
import org.jspecify.annotations.Nullable;

public interface RangeDispatchWidgetProperty {
    float get(final AbstractWidget widget, final @Nullable Screen screen, final @Nullable Player player);

    MapCodec<? extends RangeDispatchWidgetProperty> type();
}
