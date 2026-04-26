package btw.lowercase.widgetplus;

import btw.lowercase.widgetplus.config.WidgetPlusConfig;
import net.fabricmc.api.ClientModInitializer;

public final class WidgetPlusClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        WidgetPlusConfig.load();
    }
}
