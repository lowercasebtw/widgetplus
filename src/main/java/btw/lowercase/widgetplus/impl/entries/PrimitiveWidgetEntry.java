package btw.lowercase.widgetplus.impl.entries;

import btw.lowercase.widgetplus.impl.WidgetState;
import btw.lowercase.widgetplus.impl.entries.primitive.*;
import btw.lowercase.widgetplus.impl.util.Bounds;
import btw.lowercase.widgetplus.impl.util.GuiPipelineOverrides;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.world.entity.player.Player;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

public record PrimitiveWidgetEntry(PrimitiveFunction function, Optional<RenderPipeline> pipeline,
                                   Optional<Bounds> bounds) implements WidgetEntry {
    @Override
    public WidgetState resolve(final AbstractWidget widget, final @Nullable Screen screen, final @Nullable Player player) {
        return new WidgetState.Primitive(this.function, this.pipeline, this.bounds);
    }

    public record Unbaked(PrimitiveFunction function, Optional<Bounds> bounds) implements WidgetEntry.Unbaked {
        public static final MapCodec<Unbaked> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                PrimitiveTypes.MAP_CODEC.forGetter(Unbaked::function),
                Bounds.CODEC.optionalFieldOf("bounds").forGetter(Unbaked::bounds)
        ).apply(instance, Unbaked::new));

        @Override
        public MapCodec<? extends Unbaked> type() {
            return MAP_CODEC;
        }

        @Override
        public WidgetEntry bake() {
            final Optional<GuiPipelineOverrides> pipelineOverrides = extractPipelineOverrides(this.function);

            RenderPipeline pipeline = null;
            if (pipelineOverrides.isPresent()) {
                final RenderPipeline.Builder builder = RenderPipeline.builder(RenderPipelines.GUI_SNIPPET);
                builder.withLocation("pipeline/dynamic_widget_" + this.function.hashCode());
                pipelineOverrides.ifPresent(overrides -> overrides.apply(builder));
                pipeline = builder.build();
            }

            return new PrimitiveWidgetEntry(this.function, Optional.ofNullable(pipeline), this.bounds);
        }

        private Optional<GuiPipelineOverrides> extractPipelineOverrides(final PrimitiveFunction function) {
            Optional<GuiPipelineOverrides> pipelineOverrides = Optional.empty();
            if (function instanceof Fill fill) {
                pipelineOverrides = fill.pipelineOverrides();
            } else if (function instanceof FillGradient fillGradient) {
                pipelineOverrides = fillGradient.pipelineOverrides();
            } else if (function instanceof Outline outline) {
                pipelineOverrides = outline.pipelineOverrides();
            }

            return pipelineOverrides;
        }
    }
}