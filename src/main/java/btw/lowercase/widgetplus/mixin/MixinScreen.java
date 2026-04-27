package btw.lowercase.widgetplus.mixin;

import btw.lowercase.widgetplus.impl.util.ScreenTime;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Screen.class)
public abstract class MixinScreen implements ScreenTime {
    @Unique
    private long widgetplus$openTimestamp = 0;

    @Inject(method = "init(II)V", at = @At("HEAD"))
    private void widgetplus$updateTimestamp(final int width, final int height, final CallbackInfo ci) {
        this.widgetplus$openTimestamp = System.currentTimeMillis();
    }

    @Override
    public int widgetplus$getElapsedOpenTime() {
        return Math.toIntExact(System.currentTimeMillis() - this.widgetplus$openTimestamp);
    }
}