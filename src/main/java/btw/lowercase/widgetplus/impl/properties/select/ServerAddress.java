package btw.lowercase.widgetplus.impl.properties.select;

import btw.lowercase.widgetplus.impl.util.Utils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.world.entity.player.Player;
import org.jspecify.annotations.Nullable;

public record ServerAddress() implements SelectWidgetProperty<String> {
    public static final SelectWidgetProperty.Type<ServerAddress, String> TYPE = SelectWidgetProperty.Type.create(
            MapCodec.unit(new ServerAddress()), Codec.STRING
    );

    @Override
    public @Nullable String get(final AbstractWidget widget, @Nullable final Screen screen, @Nullable final Player player) {
        if (Utils.isSingleplayer()) {
            return "singleplayer";
        } else {
            final ServerData serverData = Minecraft.getInstance().getCurrentServer();
            return serverData != null ? serverData.ip : null;
        }
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