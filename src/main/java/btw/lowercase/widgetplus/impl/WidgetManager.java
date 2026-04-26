package btw.lowercase.widgetplus.impl;

import btw.lowercase.widgetplus.WidgetPlus;
import btw.lowercase.widgetplus.impl.states.WidgetEntry;
import net.minecraft.resources.Identifier;

import java.util.HashMap;

// TODO
public class WidgetManager {
    private final HashMap<Identifier, WidgetEntry> entries = new HashMap<>();

    public WidgetEntry getWidgetEntry(final Identifier id) {
        return entries.getOrDefault(id, widget -> null);
    }

    // TODO/NOTE: For widgets that extend other widgets and aren't defined manually (hack)
    // Looks for file named by hashCode of the widget first before looking for a legit id file/parent file
    public WidgetEntry getWidgetByHashOrId(final int hashCode, final Identifier id) {
        return entries.getOrDefault(WidgetPlus.id("" + hashCode), entries.getOrDefault(id, widget -> null));
    }
}
