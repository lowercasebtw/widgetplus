package btw.lowercase.widgetplus.impl;

import net.minecraft.resources.Identifier;

import java.util.Optional;

public record WidgetState(Identifier texture, Optional<GuiPipelineOverrides> pipelineOverrides) {
    public WidgetState(final Identifier texture) {
        this(texture, Optional.empty());
    }
}
