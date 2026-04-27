package btw.lowercase.widgetplus.impl.entries;

import btw.lowercase.widgetplus.impl.WidgetState;
import btw.lowercase.widgetplus.impl.util.GuiPipelineOverrides;
import btw.lowercase.widgetplus.impl.util.UV;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

public record TextureWidgetEntry(Identifier texture,
                                 Optional<RenderPipeline> pipeline, Optional<UV> uv) implements WidgetEntry {
    @Override
    public WidgetState resolve(final AbstractWidget widget, final @Nullable Screen screen, final @Nullable Player player) {
        return new WidgetState.Texture(this.texture, this.pipeline, this.uv);
    }

    public record Unbaked(Identifier texture,
                          Optional<GuiPipelineOverrides> pipelineOverrides,
                          Optional<UV> uv) implements WidgetEntry.Unbaked {
        public static final MapCodec<Unbaked> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Identifier.CODEC.fieldOf("texture").forGetter(Unbaked::texture),
                GuiPipelineOverrides.CODEC.optionalFieldOf("pipeline_overrides").forGetter(Unbaked::pipelineOverrides),
                UV.CODEC.optionalFieldOf("uv").forGetter(Unbaked::uv)
        ).apply(instance, Unbaked::new));

        @Override
        public MapCodec<? extends Unbaked> type() {
            return MAP_CODEC;
        }

        @Override
        public WidgetEntry bake() {
            final RenderPipeline.Builder builder = RenderPipeline.builder(RenderPipelines.GUI_TEXTURED_SNIPPET);
            builder.withLocation("pipeline/dynamic_widget_" + this.texture.hashCode());
            this.pipelineOverrides.ifPresent(overrides -> overrides.apply(builder));
            return new TextureWidgetEntry(this.texture, Optional.of(builder.build()), this.uv);
        }
    }
}