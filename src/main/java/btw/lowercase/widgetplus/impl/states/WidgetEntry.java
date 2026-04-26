package btw.lowercase.widgetplus.impl.states;

import com.mojang.serialization.MapCodec;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.resources.model.ResolvableModel;

public interface WidgetEntry {
    void update(final AbstractWidget widget);

    interface Unbaked extends ResolvableModel {
        MapCodec<? extends WidgetEntry.Unbaked> type();
    }
}