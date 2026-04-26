package btw.lowercase.widgetplus.impl;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.resources.Identifier;

import java.util.Optional;

public record WidgetState(Identifier texture, Optional<RenderPipeline> pipeline) {
    public WidgetState(final Identifier texture) {
        this(texture, Optional.empty());
    }
}
