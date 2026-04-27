package btw.lowercase.widgetplus.impl.entries;

import btw.lowercase.widgetplus.impl.WidgetState;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.entity.player.Player;
import org.jspecify.annotations.Nullable;

public interface WidgetEntry {
    WidgetState resolve(final AbstractWidget widget, @Nullable final Screen screen, @Nullable final Player player);

    interface Unbaked {
        MapCodec<? extends WidgetEntry.Unbaked> type();

        WidgetEntry bake();
    }
}