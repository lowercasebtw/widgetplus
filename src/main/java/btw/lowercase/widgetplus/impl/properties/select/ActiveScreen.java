package btw.lowercase.widgetplus.impl.properties.select;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.entity.player.Player;
import org.jspecify.annotations.Nullable;

public record ActiveScreen() implements SelectWidgetProperty<String> {
    public static final SelectWidgetProperty.Type<ActiveScreen, String> TYPE = SelectWidgetProperty.Type.create(
            MapCodec.unit(new ActiveScreen()), Codec.STRING
    );

    @Override
    public @Nullable String get(final AbstractWidget widget, final @Nullable Screen screen, final @Nullable Player player) {
        return screen != null ? screen.getClass().getName() : null;
    }

    @Override
    public Codec<String> valueCodec() {
        return Codec.STRING;
    }

    @Override
    public Type<? extends SelectWidgetProperty<String>, String> type() {
        return TYPE;
    }
}
