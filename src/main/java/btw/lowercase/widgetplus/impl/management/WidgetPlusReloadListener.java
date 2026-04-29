package btw.lowercase.widgetplus.impl.management;

import btw.lowercase.widgetplus.WidgetPlus;
import btw.lowercase.widgetplus.impl.WidgetDefinition;
import btw.lowercase.widgetplus.impl.util.Utils;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.serialization.JsonOps;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.Resource;
import org.jspecify.annotations.NonNull;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;

public class WidgetPlusReloadListener implements PreparableReloadListener {
    public static final String WIDGETS_FOLDER = "widgets";

    private static boolean isJson(final Identifier identifier) {
        return identifier.getPath().endsWith(".json");
    }

    @Override
    public @NonNull CompletableFuture<Void> reload(final @NonNull SharedState sharedState, final @NonNull Executor taskExecutor, final PreparationBarrier preparationBarrier, final @NonNull Executor reloadExecutor) {
        return CompletableFuture.runAsync(() -> {
            final WidgetManager widgetManager = WidgetPlus.getWidgetManager();
            widgetManager.clear();
            final AtomicInteger successful = new AtomicInteger();
            final AtomicInteger error = new AtomicInteger();
            for (final Map.Entry<Identifier, Resource> entry : sharedState.resourceManager().listResources(WIDGETS_FOLDER, WidgetPlusReloadListener::isJson).entrySet()) {
                final Identifier id = entry.getKey();
                final Resource resource = entry.getValue();
                try (final InputStream stream = resource.open()) {
                    final JsonObject json = JsonParser.parseReader(new InputStreamReader(stream)).getAsJsonObject();
                    WidgetDefinition.CODEC.parse(JsonOps.INSTANCE, json).ifSuccess(widgetDefinition -> {
                        widgetManager.register(widgetDefinition.target().type(), id, widgetDefinition);
                        successful.getAndIncrement();
                    }).ifError(widgetDefinitionError -> {
                        WidgetPlus.getLogger().error(widgetDefinitionError.message());
                        error.getAndIncrement();
                    });
                } catch (final Throwable throwable) {
                    WidgetPlus.getLogger().error("Couldn't parse '{}'", id);
                    throwable.printStackTrace();
                    error.getAndIncrement();
                }
            }

            final int finalErrorCount = error.intValue();
            if (finalErrorCount != 0) {
                final String errorTitle = (finalErrorCount > 1) ? "widgetplus.info.errorPlural" : "widgetplus.info.errorSingular";
                SystemToast.addOrUpdate(Utils.getToastManager(), SystemToast.SystemToastId.PACK_LOAD_FAILURE, Component.translatable(errorTitle, error.intValue()), Component.translatable("widgetplus.info.checkLogs"));
            }
        }).thenCompose(preparationBarrier::wait);
    }
}
