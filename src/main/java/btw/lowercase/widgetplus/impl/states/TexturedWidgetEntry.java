package btw.lowercase.widgetplus.impl.states;

import btw.lowercase.widgetplus.impl.GuiPipelineOverrides;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.resources.Identifier;

import java.util.Optional;

public class TexturedWidgetEntry implements WidgetEntry {
    @Override
    public void update(final AbstractWidget widget) {
        // TODO
    }

    public record Unbaked(Identifier texture,
                          Optional<GuiPipelineOverrides> pipelineOverrides) implements WidgetEntry.Unbaked {
        public static final MapCodec<Unbaked> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Identifier.CODEC.fieldOf("texture").forGetter(Unbaked::texture),
                GuiPipelineOverrides.CODEC.optionalFieldOf("pipeline_overrides").forGetter(Unbaked::pipelineOverrides)
        ).apply(instance, Unbaked::new));

        @Override
        public MapCodec<? extends Unbaked> type() {
            return MAP_CODEC;
        }

        @Override
        public void resolveDependencies(final Resolver resolver) {
            resolver.markDependency(this.texture);
        }
    }
}

