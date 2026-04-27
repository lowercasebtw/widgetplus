package btw.lowercase.widgetplus.impl.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.Optional;

public record Bounds(boolean relative, Optional<Integer> x, Optional<Integer> y, Optional<Integer> width,
                     Optional<Integer> height) {
    public static Codec<Bounds> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.BOOL.optionalFieldOf("relative", false).forGetter(Bounds::relative),
            Codec.INT.optionalFieldOf("x").forGetter(Bounds::x),
            Codec.INT.optionalFieldOf("y").forGetter(Bounds::y),
            Codec.INT.optionalFieldOf("width").forGetter(Bounds::width),
            Codec.INT.optionalFieldOf("height").forGetter(Bounds::height)
    ).apply(instance, Bounds::new));

    public int getX(final int x) {
        return this.relative ? x + this.x.orElse(0) : x;
    }

    public int getY(final int y) {
        return this.relative ? y + this.y.orElse(0) : y;
    }
}
