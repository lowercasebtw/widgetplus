package btw.lowercase.widgetplus.impl.properties.select;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.EditBox;
import org.jspecify.annotations.Nullable;

public record EditBoxContents() implements SelectWidgetProperty<String> {
    public static final SelectWidgetProperty.Type<EditBoxContents, String> TYPE = SelectWidgetProperty.Type.create(
            MapCodec.unit(new EditBoxContents()), Codec.STRING
    );

    @Override
    public @Nullable String get(final AbstractWidget widget) {
        return widget instanceof EditBox editBox ? editBox.getValue() : null;
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
