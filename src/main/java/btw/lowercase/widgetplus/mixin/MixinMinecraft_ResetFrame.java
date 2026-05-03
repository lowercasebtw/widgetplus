package btw.lowercase.widgetplus.mixin;

import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.client.Minecraft;

//? >=26.2 {
/*import btw.lowercase.widgetplus.WidgetPlus;
import btw.lowercase.widgetplus.config.WidgetPlusConfig;
import btw.lowercase.widgetplus.impl.management.WidgetPlusDynamicUniforms;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
*///? }

@Mixin(Minecraft.class)
public abstract class MixinMinecraft_ResetFrame {
    //? >=26.2 {
    /*@Inject(method = "renderFrame", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/DynamicUniforms;reset()V", shift = At.Shift.AFTER))
    private static void widgetplus$resetUniforms(final boolean advanceGameTime, final CallbackInfo ci) {
        final WidgetPlusDynamicUniforms dynamicUniforms = WidgetPlus.dynamicUniforms();
        if (WidgetPlusConfig.instance().enabled && dynamicUniforms != null) {
            dynamicUniforms.reset();
        }
    }
    *///? }
}
