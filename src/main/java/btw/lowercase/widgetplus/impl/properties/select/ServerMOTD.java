package btw.lowercase.widgetplus.impl.properties.select;

import btw.lowercase.widgetplus.impl.util.Utils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.world.entity.player.Player;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public record ServerMOTD() implements SelectWidgetProperty<Component> {
    public static final SelectWidgetProperty.Type<ServerMOTD, Component> TYPE = SelectWidgetProperty.Type.create(
            MapCodec.unit(new ServerMOTD()), ComponentSerialization.CODEC
    );

    @Override
    public @NonNull Component get(final AbstractWidget widget, @Nullable final Screen screen, @Nullable final Player player) {
        final ServerData serverData = Minecraft.getInstance().getCurrentServer();
        if (!Utils.isSingleplayer() && serverData != null) {
            return serverData.motd;
        } else {
            return Component.empty();
        }
    }

    @Override
    public Codec<Component> valueCodec() {
        return ComponentSerialization.CODEC;
    }

    @Override
    public Type<? extends SelectWidgetProperty<Component>, Component> type() {
        return TYPE;
    }
}
