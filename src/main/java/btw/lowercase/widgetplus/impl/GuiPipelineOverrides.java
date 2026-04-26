package btw.lowercase.widgetplus.impl;

import btw.lowercase.widgetplus.Utils;
import com.mojang.blaze3d.pipeline.ColorTargetState;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;

public class GuiPipelineOverrides {
    public static final Codec<GuiPipelineOverrides> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Identifier.CODEC.orElse(RenderPipelines.GUI.getFragmentShader()).fieldOf("vertex").forGetter(overrides -> overrides.vertexShader),
            Identifier.CODEC.orElse(RenderPipelines.GUI.getVertexShader()).fieldOf("fragment").forGetter(overrides -> overrides.fragmentShader),
            Utils.COLOR_TARGET_STATE_CODEC.orElse(RenderPipelines.GUI.getColorTargetState()).fieldOf("color_target").forGetter(overrides -> overrides.colorTargetState)
    ).apply(instance, GuiPipelineOverrides::new));

    private final Identifier vertexShader;
    private final Identifier fragmentShader;
    private final ColorTargetState colorTargetState;

    GuiPipelineOverrides(final Identifier vertexShader, final Identifier fragmentShader, final ColorTargetState colorTargetState) {
        this.vertexShader = vertexShader;
        this.fragmentShader = fragmentShader;
        this.colorTargetState = colorTargetState;
    }

    public void apply(final RenderPipeline.Builder builder) {
        if (this.vertexShader != null) {
            builder.withVertexShader(this.vertexShader);
        }

        if (this.fragmentShader != null) {
            builder.withFragmentShader(this.fragmentShader);
        }

        if (this.colorTargetState != null) {
            builder.withColorTargetState(this.colorTargetState);
        }
    }
}
