package btw.lowercase.widgetplus.impl;

import btw.lowercase.widgetplus.impl.states.WidgetEntry;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.Optional;

public record WidgetDefinition(Target target, WidgetEntry.Unbaked widget) {
    public static final Codec<WidgetDefinition> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Target.MAP_CODEC.forGetter(WidgetDefinition::target),
            WidgetEntries.CODEC.fieldOf("widget").forGetter(WidgetDefinition::widget)
    ).apply(instance, WidgetDefinition::new));

    public record Target(Optional<Integer> hash) {
        public static final MapCodec<Target> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Codec.INT.optionalFieldOf("hash_code").forGetter(Target::hash)
        ).apply(instance, Target::new));
    }
}
