package btw.lowercase.widgetplus.impl.properties.select;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.world.entity.player.Player;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public record Message() implements SelectWidgetProperty<Component> {
    public static final SelectWidgetProperty.Type<Message, Component> TYPE = SelectWidgetProperty.Type.create(
            MapCodec.unit(new Message()), ComponentSerialization.CODEC
    );

    @Override
    public @NonNull Component get(final AbstractWidget widget, @Nullable final Screen screen, @Nullable final Player player) {
        return widget.getMessage();
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