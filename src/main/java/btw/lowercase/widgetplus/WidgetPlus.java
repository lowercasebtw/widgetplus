package btw.lowercase.widgetplus;

import btw.lowercase.widgetplus.impl.management.WidgetManager;
import btw.lowercase.widgetplus.impl.management.WidgetPlusDynamicUniforms;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class WidgetPlus {
    public static final String MOD_ID = "@MODID@";
    public static final String VERSION = "@VERSION@";
    public static final String COMMIT_HASH = "@COMMIT_HASH@";

    public static Identifier id(final String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    private static final WidgetManager widgetManager = new WidgetManager();
    private static WidgetPlusDynamicUniforms uniform;

    public static Logger getLogger() {
        return LOGGER;
    }

    public static WidgetManager getWidgetManager() {
        return widgetManager;
    }

    public static @Nullable WidgetPlusDynamicUniforms dynamicUniforms() {
        return uniform;
    }

    public static void initUniform() {
        uniform = new WidgetPlusDynamicUniforms();
    }
}
