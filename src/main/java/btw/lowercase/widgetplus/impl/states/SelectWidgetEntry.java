package btw.lowercase.widgetplus.impl.states;

import btw.lowercase.widgetplus.impl.WidgetEntries;
import btw.lowercase.widgetplus.impl.WidgetState;
import btw.lowercase.widgetplus.impl.properties.SelectWidgetProperties;
import btw.lowercase.widgetplus.impl.property.SelectWidgetProperty;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.gui.components.AbstractWidget;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Optional;

// TODO
public record SelectWidgetEntry<T>(SelectWidgetProperty<T> property) implements WidgetEntry {
    @Override
    public @Nullable WidgetState resolve(final AbstractWidget widget) {
        return null;
    }

    public interface EntrySelector<T> {
        WidgetEntry get(@Nullable final T value);
    }

    public record SwitchCase<T>(List<T> values, WidgetEntry.Unbaked model) {
        public static <T> Codec<SwitchCase<T>> codec(final Codec<T> valueCodec) {
            return null; // TODO
        }
    }

    public record Unbaked(UnbakedSwitch<?, ?> unbakedSwitch,
                          Optional<WidgetEntry.Unbaked> fallback) implements WidgetEntry.Unbaked {
        public static final MapCodec<Unbaked> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                UnbakedSwitch.MAP_CODEC.forGetter(Unbaked::unbakedSwitch),
                WidgetEntries.CODEC.optionalFieldOf("fallback").forGetter(Unbaked::fallback)
        ).apply(instance, Unbaked::new));

        @Override
        public MapCodec<? extends Unbaked> type() {
            return MAP_CODEC;
        }

        @Override
        public WidgetEntry bake() {
            return new SelectWidgetEntry<>(null);
        }
    }

    public record UnbakedSwitch<P extends SelectWidgetProperty<T>, T>(P property, List<SwitchCase<T>> cases) {
        public static final MapCodec<UnbakedSwitch<?, ?>> MAP_CODEC = SelectWidgetProperties.CODEC.dispatchMap(
                "property",
                unbaked -> unbaked.property().type(),
                SelectWidgetProperty.Type::switchCodec
        );

        public WidgetEntry bake() {
            return null; // TODO
        }
    }
}