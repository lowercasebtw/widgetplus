package btw.lowercase.widgetplus.impl.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.Optional;

public record Bounds(boolean absolute, Optional<Integer> x, Optional<Integer> y, Optional<Integer> width,
                     Optional<Integer> height) {
    public static Codec<Bounds> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.BOOL.optionalFieldOf("absolute", false).forGetter(Bounds::absolute),
            Codec.INT.optionalFieldOf("x").forGetter(Bounds::x),
            Codec.INT.optionalFieldOf("y").forGetter(Bounds::y),
            Codec.INT.optionalFieldOf("width").forGetter(Bounds::width),
            Codec.INT.optionalFieldOf("height").forGetter(Bounds::height)
    ).apply(instance, Bounds::new));

    public Bounds(int x, int y, int width, int height) {
        this(true, x, y, width, height);
    }

    public int getX(final int x) {
        final int ox = this.x.orElse(0);
        return this.absolute ? ox : x + ox;
    }

    public int getY(final int y) {
        final int oy = this.y.orElse(0);
        return this.absolute ? oy : y + oy;
    }
}
