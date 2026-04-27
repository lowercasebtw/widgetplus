package btw.lowercase.widgetplus.impl.states;

import btw.lowercase.widgetplus.impl.WidgetEntries;
import btw.lowercase.widgetplus.impl.WidgetState;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.gui.components.AbstractWidget;

public record CustomWidgetEntry(WidgetEntry entry) implements WidgetEntry {
    @Override
    public WidgetState resolve(final AbstractWidget widget) {
        return this.entry.resolve(widget);
    }

    public record Unbaked(WidgetEntry.Unbaked widget) implements WidgetEntry.Unbaked {
        public static final MapCodec<Unbaked> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                WidgetEntries.CODEC.fieldOf("widget").forGetter(Unbaked::widget)
        ).apply(instance, Unbaked::new));

        @Override
        public MapCodec<? extends Unbaked> type() {
            return MAP_CODEC;
        }

        @Override
        public WidgetEntry bake() {
            return new CustomWidgetEntry(this.widget.bake());
        }
    }
}
