package btw.lowercase.widgetplus.impl.management;

import btw.lowercase.widgetplus.WidgetPlus;
import btw.lowercase.widgetplus.impl.WidgetDefinition;
import btw.lowercase.widgetplus.impl.WidgetState;
import btw.lowercase.widgetplus.impl.states.primitive.*;
import btw.lowercase.widgetplus.impl.util.Bounds;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public final class WidgetRenderer {
    public static void render(final WidgetDefinition.Type type, final AbstractWidget widget, final BlitRenderContext renderContext, final Consumer<BlitRenderContext> defaultRender) {
        render(WidgetPlus.getWidgetManager().getState(type, widget), renderContext, defaultRender);
    }

    public static void render(final WidgetState state, final BlitRenderContext renderContext, final Consumer<BlitRenderContext> defaultRender) {
        if (state instanceof WidgetState.Multiple(List<WidgetState> states)) {
            for (final WidgetState innerState : states) {
                render(innerState, renderContext, defaultRender);
            }
        } else if (state instanceof WidgetState.Textured(Identifier texture, Optional<RenderPipeline> pipeline)) {
            renderContext.guiGraphicsExtractor.blitSprite(pipeline.orElse(renderContext.pipeline), texture, renderContext.x, renderContext.y, renderContext.width, renderContext.height, renderContext.color);
        } else if (state instanceof WidgetState.Primitive(PrimitiveType function, Optional<Bounds> bounds)) {
            final BlitRenderContext blitRenderContext = bounds.map(renderContext::withBounds).orElse(renderContext);
            if (function instanceof Fill(int color)) {
                renderContext.guiGraphicsExtractor.fill(blitRenderContext.x, blitRenderContext.y, blitRenderContext.x + blitRenderContext.width, blitRenderContext.y + blitRenderContext.height, color);
            } else if (function instanceof FillGradient(int startColor, int endColor)) {
                blitRenderContext.guiGraphicsExtractor.fillGradient(blitRenderContext.x, blitRenderContext.y, blitRenderContext.x + blitRenderContext.width, blitRenderContext.y + blitRenderContext.height, startColor, endColor);
            } else if (function instanceof Outline(int color)) {
                blitRenderContext.guiGraphicsExtractor.outline(blitRenderContext.x, blitRenderContext.y, blitRenderContext.width, blitRenderContext.height, color);
            } else if (function instanceof OutlineGradient(int startColor, int endColor)) {
                outlineGradient(blitRenderContext.guiGraphicsExtractor, blitRenderContext.x, blitRenderContext.y, blitRenderContext.width, blitRenderContext.height, startColor, endColor);
            }
        } else if (state instanceof WidgetState.Custom(WidgetState customState, Optional<Bounds> bounds)) {
            render(customState, bounds.map(renderContext::withBounds).orElse(renderContext), defaultRender);
        } else if (state instanceof WidgetState.Default(Optional<RenderPipeline> pipeline)) {
            defaultRender.accept(pipeline.map(renderContext::withPipeline).orElse(renderContext));
        }
    }

    // TODO: Check if accurate
    private static void outlineGradient(final GuiGraphicsExtractor guiGraphicsExtractor, final int x, final int y, final int width, final int height, final int startColor, final int endColor) {
        guiGraphicsExtractor.fillGradient(x, y, x + width, y + 1, startColor, endColor);
        guiGraphicsExtractor.fillGradient(x, y + height - 1, x + width, y + height, startColor, endColor);
        guiGraphicsExtractor.fillGradient(x, y + 1, x + 1, y + height - 1, startColor, endColor);
        guiGraphicsExtractor.fillGradient(x + width - 1, y + 1, x + width, y + height - 1, startColor, endColor);
    }

    public record BlitRenderContext(GuiGraphicsExtractor guiGraphicsExtractor,
                                    RenderPipeline pipeline,
                                    Identifier location,
                                    int x, int y,
                                    int width, int height,
                                    int color) {
        public BlitRenderContext(
                final GuiGraphicsExtractor guiGraphicsExtractor,
                final RenderPipeline pipeline,
                final Identifier location,
                final int x,
                final int y,
                final int width,
                final int height
        ) {
            this(guiGraphicsExtractor, pipeline, location, x, y, width, height, ARGB.white(1.0F));
        }

        public BlitRenderContext withPipeline(final RenderPipeline pipeline) {
            return new BlitRenderContext(this.guiGraphicsExtractor, pipeline, this.location, this.x, this.y, this.width, this.height);
        }

        public BlitRenderContext withBounds(final Bounds bounds) {
            return new BlitRenderContext(
                    this.guiGraphicsExtractor,
                    this.pipeline,
                    this.location,
                    bounds.x().orElse(this.x),
                    bounds.y().orElse(this.y),
                    bounds.width().orElse(this.width),
                    bounds.height().orElse(this.height)
            );
        }
    }
}
