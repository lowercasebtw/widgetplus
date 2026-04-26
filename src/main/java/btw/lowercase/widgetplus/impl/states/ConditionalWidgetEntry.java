package btw.lowercase.widgetplus.impl.states;

import btw.lowercase.widgetplus.impl.WidgetEntries;
import btw.lowercase.widgetplus.impl.properties.WidgetPropertyTest;
import btw.lowercase.widgetplus.impl.WidgetState;
import btw.lowercase.widgetplus.impl.properties.ConditionalWidgetProperties;
import btw.lowercase.widgetplus.impl.property.ConditionalWidgetProperty;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.gui.components.AbstractWidget;

public record ConditionalWidgetEntry(WidgetPropertyTest property, WidgetEntry onTrue,
                                     WidgetEntry onFalse) implements WidgetEntry {
    @Override
    public WidgetState resolve(final AbstractWidget widget) {
        return this.property.resolve(widget) ? this.onTrue.resolve(widget) : this.onFalse.resolve(widget);
    }

    public record Unbaked(ConditionalWidgetProperty property, WidgetEntry.Unbaked onTrue,
                          WidgetEntry.Unbaked onFalse) implements WidgetEntry.Unbaked {
        public static final MapCodec<Unbaked> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                ConditionalWidgetProperties.MAP_CODEC.forGetter(Unbaked::property),
                WidgetEntries.CODEC.fieldOf("on_true").forGetter(Unbaked::onTrue),
                WidgetEntries.CODEC.fieldOf("on_false").forGetter(Unbaked::onFalse)
        ).apply(instance, Unbaked::new));

        @Override
        public MapCodec<? extends Unbaked> type() {
            return MAP_CODEC;
        }

        @Override
        public WidgetEntry bake() {
            return new ConditionalWidgetEntry(this.adaptProperty(this.property), this.onTrue.bake(), this.onFalse.bake());
        }

        // TODO
        private WidgetPropertyTest adaptProperty(final ConditionalWidgetProperty property) {
            return (widget) -> true;
        }
    }
}
