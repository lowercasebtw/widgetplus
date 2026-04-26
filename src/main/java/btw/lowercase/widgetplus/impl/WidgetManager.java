package btw.lowercase.widgetplus.impl;

import btw.lowercase.widgetplus.WidgetPlus;
import btw.lowercase.widgetplus.impl.states.EmptyWidgetEntry;
import btw.lowercase.widgetplus.impl.states.TextureWidgetEntry;
import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.ColorTargetState;
import net.minecraft.resources.Identifier;

import java.util.HashMap;
import java.util.Optional;

// TODO
public class WidgetManager {
    private final HashMap<Identifier, WidgetDefinition> entries = new HashMap<>();

    public WidgetDefinition getWidgetDefinition(final Identifier id) {
        return entries.getOrDefault(id, null);
    }

    // TODO/NOTE: For widgets that extend other widgets and aren't defined manually (hack)
    // Looks for file named by hashCode of the widget first before looking for a legit id file/parent file
    public WidgetDefinition getWidgetByHashOrId(final int hashCode, final Identifier id) {
        if (WidgetLocations.BUTTON.equals(id)) {
            return new WidgetDefinition(
                    new WidgetDefinition.Target(WidgetDefinition.Type.BUTTON, Optional.empty()),
                    new TextureWidgetEntry.Unbaked(
                            Identifier.withDefaultNamespace("widget/button"),
                            Optional.of(new GuiPipelineOverrides(
                                    Optional.empty(),
                                    Optional.empty(),
                                    Optional.of(new ColorTargetState(BlendFunction.TRANSLUCENT_PREMULTIPLIED_ALPHA))
                            ))
                    )
            );
        }

        return entries.getOrDefault(WidgetPlus.id("" + hashCode), entries.getOrDefault(id, new WidgetDefinition(new WidgetDefinition.Target(WidgetDefinition.Type.BUTTON, Optional.empty()), new EmptyWidgetEntry.Unbaked())));
    }
}
