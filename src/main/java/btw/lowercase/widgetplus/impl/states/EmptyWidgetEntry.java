package btw.lowercase.widgetplus.impl.states;

import com.mojang.serialization.MapCodec;
import net.minecraft.client.gui.components.AbstractWidget;

public class EmptyWidgetEntry implements WidgetEntry {
    @Override
    public void update(final AbstractWidget widget) {
    }

    public record Unbaked() implements WidgetEntry.Unbaked {
        public static final MapCodec<Unbaked> MAP_CODEC = MapCodec.unit(Unbaked::new);

        @Override
        public MapCodec<? extends Unbaked> type() {
            return MAP_CODEC;
        }

        @Override
        public void resolveDependencies(final Resolver resolver) {
        }
    }
}