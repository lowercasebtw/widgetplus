package btw.lowercase.widgetplus.mixin;

import btw.lowercase.widgetplus.config.WidgetPlusConfig;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.platform.FramerateLimitTracker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FramerateLimitTracker.class)
public abstract class MixinFramerateLimitTracker_UncapUIFPS {
    @WrapOperation(method = "getFramerateLimit", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/FramerateLimitTracker;getThrottleReason()Lcom/mojang/blaze3d/platform/FramerateLimitTracker$FramerateThrottleReason;"))
    private FramerateLimitTracker.FramerateThrottleReason widgetplus$uncapGuiFramerate(final FramerateLimitTracker instance, final Operation<FramerateLimitTracker.FramerateThrottleReason> original) {
        final FramerateLimitTracker.FramerateThrottleReason reason = original.call(instance);
        if (WidgetPlusConfig.instance().enabled && WidgetPlusConfig.instance().uncapGuiFramerate) {
            return reason == FramerateLimitTracker.FramerateThrottleReason.OUT_OF_LEVEL_MENU ? FramerateLimitTracker.FramerateThrottleReason.NONE : reason;
        } else {
            return reason;
        }
    }
}
