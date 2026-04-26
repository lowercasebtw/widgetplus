package btw.lowercase.widgetplus;

import btw.lowercase.widgetplus.config.WidgetPlusConfig;
import btw.lowercase.widgetplus.impl.properties.ConditionalWidgetProperties;
import btw.lowercase.widgetplus.impl.properties.RangeSelectWidgetProperties;
import btw.lowercase.widgetplus.impl.properties.SelectWidgetProperties;
import net.fabricmc.api.ClientModInitializer;

public final class WidgetPlusClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        WidgetPlusConfig.load();

        ConditionalWidgetProperties.bootstrap();
        RangeSelectWidgetProperties.bootstrap();
        SelectWidgetProperties.bootstrap();
    }
}
