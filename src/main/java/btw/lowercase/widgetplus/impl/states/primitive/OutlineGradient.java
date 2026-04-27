package btw.lowercase.widgetplus.impl.states.primitive;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record OutlineGradient(int startColor, int endColor) implements PrimitiveType {
    public static final MapCodec<OutlineGradient> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.INT.fieldOf("start_color").forGetter(OutlineGradient::startColor),
            Codec.INT.fieldOf("end_color").forGetter(OutlineGradient::endColor)
    ).apply(instance, OutlineGradient::new));

    @Override
    public MapCodec<? extends PrimitiveType> type() {
        return MAP_CODEC;
    }
}