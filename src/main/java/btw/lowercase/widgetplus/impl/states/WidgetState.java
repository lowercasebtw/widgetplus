package btw.lowercase.widgetplus.impl.states;

import com.mojang.serialization.MapCodec;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.resources.model.ResolvableModel;

public interface WidgetState {
    void update(AbstractWidget widget);

    interface Unbaked extends ResolvableModel {
        MapCodec<? extends WidgetState.Unbaked> type();
    }
}