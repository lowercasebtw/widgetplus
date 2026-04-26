package btw.lowercase.widgetplus.config;

import btw.lowercase.widgetplus.WidgetPlus;
import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import dev.isxander.yacl3.impl.controller.TickBoxControllerBuilderImpl;
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

            {
                final ConfigCategory.Builder mainCategory = ConfigCategory.createBuilder();
                mainCategory.name(Component.translatable(WidgetPlus.MOD_ID + ".category.main"));
                mainCategory.option(Option.<Boolean>createBuilder()
                        .name(Component.translatable(WidgetPlus.MOD_ID + ".enabled"))
                        .binding(defaults.enabled, () -> config.enabled, enabled -> config.enabled = enabled)
                        .controller(TickBoxControllerBuilderImpl::new)
                        .build());
                mainCategory.option(Option.<Boolean>createBuilder()
                        .name(Component.translatable(WidgetPlus.MOD_ID + ".uncapGuiFramerate"))
                        .binding(defaults.uncapGuiFramerate, () -> config.uncapGuiFramerate, uncapGuiFramerate -> config.uncapGuiFramerate = uncapGuiFramerate)
                        .controller(TickBoxControllerBuilderImpl::new)
                        .build());
                builder.category(mainCategory.build());
            }

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
