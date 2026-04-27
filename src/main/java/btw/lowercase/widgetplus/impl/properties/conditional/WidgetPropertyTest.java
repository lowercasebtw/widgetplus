package btw.lowercase.widgetplus.impl.properties.conditional;

import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.entity.player.Player;
import org.jspecify.annotations.Nullable;

public interface WidgetPropertyTest {
    boolean get(final AbstractWidget widget, @Nullable final Screen screen, @Nullable final Player player);
}