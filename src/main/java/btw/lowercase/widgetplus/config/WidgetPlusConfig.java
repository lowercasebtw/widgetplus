package btw.lowercase.widgetplus.config;

import btw.lowercase.widgetplus.WidgetPlus;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import dev.isxander.yacl3.platform.YACLPlatform;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

public final class WidgetPlusConfig {
    private static final ConfigClassHandler<WidgetPlusConfig> CONFIG = ConfigClassHandler.createBuilder(WidgetPlusConfig.class)
            .serializer((config) -> GsonConfigSerializerBuilder.create(config)
                    .setPath(YACLPlatform.getConfigDir().resolve("widgetplus.json"))
                    .build()
            ).build();

    @SerialEntry
    public boolean enabled = true;

    @SerialEntry
    public boolean uncapGuiFramerate = true;

    public static Screen getConfigScreen(@Nullable Screen parent) {
        return YetAnotherConfigLib.create(CONFIG, (defaults, config, builder) -> {
            builder.title(Component.translatable(WidgetPlus.MOD_ID + ".title"));
            return builder;
        }).generateScreen(parent);
    }

    public static void load() {
        CONFIG.load();
    }

    public static WidgetPlusConfig instance() {
        return CONFIG.instance();
    }
}
