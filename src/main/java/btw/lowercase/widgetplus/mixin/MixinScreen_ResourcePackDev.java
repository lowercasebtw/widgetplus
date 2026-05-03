package btw.lowercase.widgetplus.mixin;

import btw.lowercase.widgetplus.config.WidgetPlusConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Screen.class)
public abstract class MixinScreen_ResourcePackDev {
    @Inject(method = "extractRenderState", at = @At("TAIL"))
    private void renderScreenName(final GuiGraphicsExtractor graphics, final int mouseX, final int mouseY, final float a, CallbackInfo Ci) {
        if (!WidgetPlusConfig.instance().showScreenName) return;
        String text = (this).getClass().getName();
        graphics.fill(0, 0, graphics.guiWidth(), 16, 0xFF000000);
        graphics.text(Minecraft.getInstance().font, text, 0, 0, 0xFFFFFFFF);
    }
}
