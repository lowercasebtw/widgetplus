package btw.lowercase.widgetplus.mixin;

import btw.lowercase.widgetplus.config.WidgetPlusConfig;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(AbstractWidget.class)
public class MixinWidget_HashDev {

    @Inject(method = "extractRenderState", at = @At("TAIL"))
    private void widgetplus$renderHashValue(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a, CallbackInfo ci) {
        if (!WidgetPlusConfig.instance().showHashCode) return;

        AbstractWidget widget = (AbstractWidget) (Object) this;
        if (widget.isHovered()) {
            String widgetClass = widget.getClass().getName();
            String hashValue = String.valueOf(widget.hashCode());
            String formattedString = "%s\n%s".formatted(widgetClass, hashValue);
            widget.setTooltip(Tooltip.create(Component.literal(formattedString)));
        }
    }
}
