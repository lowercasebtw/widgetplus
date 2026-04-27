package btw.lowercase.widgetplus.impl.entries.primitive;

import btw.lowercase.widgetplus.impl.util.GuiPipelineOverrides;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ExtraCodecs;

import java.util.Optional;

public record OutlineGradient(int startColor, int endColor, float lineWidth,
                              Optional<GuiPipelineOverrides> pipelineOverrides) implements PrimitiveFunction {
    public static final MapCodec<OutlineGradient> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            ExtraCodecs.STRING_ARGB_COLOR.fieldOf("start_color").forGetter(OutlineGradient::startColor),
            ExtraCodecs.STRING_ARGB_COLOR.fieldOf("end_color").forGetter(OutlineGradient::endColor),
            Codec.FLOAT.optionalFieldOf("width", 1.0F).forGetter(OutlineGradient::lineWidth),
            GuiPipelineOverrides.CODEC.optionalFieldOf("pipeline_overrides").forGetter(OutlineGradient::pipelineOverrides)
    ).apply(instance, OutlineGradient::new));

    @Override
    public MapCodec<? extends PrimitiveFunction> type() {
        return MAP_CODEC;
    }
}