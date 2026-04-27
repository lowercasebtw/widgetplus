package btw.lowercase.widgetplus.impl.management;

import btw.lowercase.widgetplus.WidgetPlus;
import btw.lowercase.widgetplus.impl.WidgetDefinition;
import btw.lowercase.widgetplus.impl.WidgetState;
import btw.lowercase.widgetplus.impl.entries.primitive.Fill;
import btw.lowercase.widgetplus.impl.entries.primitive.FillGradient;
import btw.lowercase.widgetplus.impl.entries.primitive.Outline;
import btw.lowercase.widgetplus.impl.util.Bounds;
import btw.lowercase.widgetplus.impl.util.UV;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.render.TextureSetup;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public final class WidgetRenderer {
    public static void renderDefinition(final WidgetDefinition.Type type, final AbstractWidget widget, final WidgetRenderContext renderContext, final Consumer<WidgetRenderContext> defaultRender) {
        renderState(WidgetPlus.getWidgetManager().getState(type, widget), renderContext, defaultRender);
    }

    public static void renderState(final WidgetState state, final WidgetRenderContext renderContext, final Consumer<WidgetRenderContext> defaultRender) {
        if (state instanceof WidgetState.Multiple(List<WidgetState> states, boolean inherit)) {
            final Bounds originalBounds = new Bounds(renderContext.x(), renderContext.y(), renderContext.width(), renderContext.height());
            states.forEach(it -> {
                if (!inherit) {
                    renderContext.setBounds(originalBounds);
                }

                renderState(it, renderContext, defaultRender);
            });
        } else if (state instanceof WidgetState.Sprite(Identifier sprite, Optional<RenderPipeline> pipeline)) {
            renderContext.guiGraphics().blitSprite(pipeline.orElse(renderContext.pipeline()), sprite, renderContext.x(), renderContext.y(), renderContext.width(), renderContext.height(), renderContext.color());
        } else if (state instanceof WidgetState.Texture texture) {
            renderTexture(texture, renderContext);
        } else if (state instanceof WidgetState.Primitive primitive) {
            renderPrimitive(primitive, renderContext);
        } else if (state instanceof WidgetState.Custom(WidgetState customState, Optional<Bounds> bounds)) {
            bounds.ifPresent(renderContext::setBounds);
            renderState(customState, renderContext, defaultRender);
        } else if (state instanceof WidgetState.Default(Optional<RenderPipeline> pipeline)) {
            pipeline.ifPresent(renderContext::setPipeline);
            defaultRender.accept(renderContext);
        }
    }

    public static void renderTexture(final WidgetState.Texture texture, final WidgetRenderContext renderContext) {
        final UV uv = texture.uv().orElse(new UV(0.0F, 0.0F, 1.0F, 1.0F));
        texture.bounds().ifPresent(renderContext::setBounds);
        renderContext.guiGraphics().innerBlit(
                texture.pipeline().orElse(renderContext.pipeline()),
                texture.texture(),
                renderContext.x(),
                renderContext.x1(),
                renderContext.y(),
                renderContext.y1(),
                uv.u0(),
                uv.u1(),
                uv.v0(),
                uv.v1(),
                ARGB.white(1.0F)
        );
    }

    public static void renderPrimitive(final WidgetState.Primitive primitive, final WidgetRenderContext renderContext) {
        primitive.pipeline().ifPresentOrElse(renderContext::setPipeline, () -> renderContext.setPipeline(RenderPipelines.GUI));
        primitive.bounds().ifPresent(renderContext::setBounds);
        switch (primitive.function()) {
            case Fill fill ->
                    renderContext.guiGraphics().fill(renderContext.pipeline(), renderContext.x(), renderContext.y(), renderContext.x() + renderContext.width(), renderContext.y() + renderContext.height(), fill.color());
            case FillGradient fillGradient ->
                    renderContext.guiGraphics().innerFill(renderContext.pipeline(), TextureSetup.noTexture(), renderContext.x(), renderContext.y(), renderContext.x() + renderContext.width(), renderContext.y() + renderContext.height(), fillGradient.startColor(), fillGradient.endColor());
            case Outline outline ->
                    outline(renderContext.guiGraphics(), renderContext.pipeline(), renderContext.x(), renderContext.y(), renderContext.width(), renderContext.height(), outline.color(), outline.lineWidth());
            case null, default ->
                    throw new RuntimeException("TODO: Implement primitive rendering for type: " + primitive.function());
        }
    }

    private static void outline(final GuiGraphicsExtractor guiGraphics, final RenderPipeline pipeline, final int x, final int y, final int width, final int height, final int color, final float lineWidth) {
        guiGraphics.fill(pipeline, x, y, x + width, (int) (y + lineWidth), color);
        guiGraphics.fill(pipeline, x, (int) (y + height - lineWidth), x + width, y + height, color);
        guiGraphics.fill(pipeline, x, (int) (y + lineWidth), (int) (x + lineWidth), (int) (y + height - lineWidth), color);
        guiGraphics.fill(pipeline, (int) (x + width - lineWidth), (int) (y + lineWidth), x + width, (int) (y + height - lineWidth), color);
    }
}
