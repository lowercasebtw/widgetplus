package btw.lowercase.widgetplus.impl.states;

import btw.lowercase.widgetplus.impl.WidgetState;
import btw.lowercase.widgetplus.impl.states.primitive.PrimitiveType;
import btw.lowercase.widgetplus.impl.states.primitive.PrimitiveTypes;
import btw.lowercase.widgetplus.impl.util.Bounds;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.gui.components.AbstractWidget;

import java.util.Optional;

public record PrimitiveWidgetEntry(PrimitiveType function, Optional<Bounds> bounds) implements WidgetEntry {
    @Override
    public WidgetState resolve(final AbstractWidget widget) {
        return new WidgetState.Primitive(this.function, this.bounds);
    }

    public record Unbaked(PrimitiveType function, Optional<Bounds> bounds) implements WidgetEntry.Unbaked {
        public static final MapCodec<Unbaked> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                PrimitiveTypes.CODEC.fieldOf("function").forGetter(Unbaked::function),
                Bounds.CODEC.optionalFieldOf("bounds").forGetter(Unbaked::bounds)
        ).apply(instance, Unbaked::new));

        @Override
        public MapCodec<? extends Unbaked> type() {
            return MAP_CODEC;
        }

        @Override
        public WidgetEntry bake() {
            return new PrimitiveWidgetEntry(this.function, this.bounds);
        }
    }
}