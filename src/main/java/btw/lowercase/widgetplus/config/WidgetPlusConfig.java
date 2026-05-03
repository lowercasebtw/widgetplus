package btw.lowercase.widgetplus.config;

import btw.lowercase.widgetplus.WidgetPlus;
import dev.isxander.yacl3.api.*;
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
    public boolean uncapGuiFramerate = false;

    @SerialEntry
    public boolean showScreenName = false;

    @SerialEntry
    public boolean showHashCode = false;

    /// Returns the translatable component with the mod id prepended.
    private static Component transComponent(String key) {
        return Component.translatable(WidgetPlus.MOD_ID + "." + key);
    }
    public static Screen getConfigScreen(@Nullable Screen parent) {
        return YetAnotherConfigLib.create(CONFIG, (defaults, config, builder) -> {
            builder.title(transComponent("title"));

            {
                final ConfigCategory.Builder mainCategory = ConfigCategory.createBuilder();
                final OptionGroup.Builder packDevGroup = OptionGroup.createBuilder();
                mainCategory.name(transComponent("category.main"));
                packDevGroup.description(OptionDescription.of(transComponent("group.packDev.description")));

                mainCategory.option(Option.<Boolean>createBuilder()
                        .name(transComponent("enabled"))
                        .description(OptionDescription.of(transComponent("enabled.description")))
                        .binding(defaults.enabled, () -> config.enabled, enabled -> config.enabled = enabled)
                        .controller(TickBoxControllerBuilderImpl::new)
                        .build());
                mainCategory.option(Option.<Boolean>createBuilder()
                        .name(transComponent("uncapGuiFramerate"))
                        .description(OptionDescription.of(transComponent("uncapGuiFramerate.description")))
                        .binding(defaults.uncapGuiFramerate, () -> config.uncapGuiFramerate, uncapGuiFramerate -> config.uncapGuiFramerate = uncapGuiFramerate)
                        .controller(TickBoxControllerBuilderImpl::new)
                        .build());

                packDevGroup.name(transComponent("group.packDev"));
                packDevGroup.option(Option.<Boolean>createBuilder()
                        .name(transComponent("showScreenName"))
                        .description(OptionDescription.of(transComponent("showScreenName.description")))
                        .binding(defaults.showScreenName, () -> config.showScreenName, showScreenName -> config.showScreenName = showScreenName)
                        .controller(TickBoxControllerBuilderImpl::new)
                        .build());
                packDevGroup.option(Option.<Boolean>createBuilder()
                        .name(transComponent("showHashCode"))
                        .description(OptionDescription.of(transComponent("showHashCode.description")))
                        .binding(defaults.showHashCode, () -> config.showHashCode, showHashCode -> config.showHashCode = showHashCode)
                        .controller(TickBoxControllerBuilderImpl::new)
                        .build());

                mainCategory.group(packDevGroup.build());
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
