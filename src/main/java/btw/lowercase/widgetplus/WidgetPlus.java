package btw.lowercase.widgetplus;

import btw.lowercase.widgetplus.impl.WidgetManager;
import net.minecraft.resources.Identifier;

public final class WidgetPlus {
    public static final String MOD_ID = "widgetplus";
    public static final String VERSION = "1.0";

    public static Identifier id(final String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }

    private static final WidgetManager widgetManager = new WidgetManager();

    public static WidgetManager getWidgetManager() {
        return widgetManager;
    }
}
