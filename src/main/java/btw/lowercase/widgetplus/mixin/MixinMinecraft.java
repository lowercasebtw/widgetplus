package btw.lowercase.widgetplus.mixin;

import btw.lowercase.widgetplus.impl.util.ElapsedTimes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//? >=26.2 {
/*@Mixin(net.minecraft.client.gui.Gui.class)
*///? } else {
@Mixin(Minecraft.class)
 //? }
public abstract class MixinMinecraft implements ElapsedTimes {
    //? >=26.2 {
    /*@Shadow
    @org.spongepowered.asm.mixin.Final
    private Minecraft minecraft;
    *///? } else {
    @Shadow
    public abstract boolean isPaused();
    //? }

    @Unique
    private long widgetplus$pauseTimestamp = 0;

    @Unique
    private long widgetplus$screenOpenTimestamp = 0;

    @Inject(method = "tick", at = @At("TAIL"))
    private void widgetplus$updatePauseTimestamp(final CallbackInfo ci) {
        final boolean isPaused =
                //? >=26.2 {
                /*this.minecraft.isPaused();
                *///? } else {
                this.isPaused();
                //? }
        if (this.widgetplus$pauseTimestamp != 0 && !isPaused) {
            this.widgetplus$screenOpenTimestamp = 0;
        } else if (this.widgetplus$pauseTimestamp == 0 && isPaused) {
            this.widgetplus$pauseTimestamp = System.currentTimeMillis();
        }
    }

    @Inject(method = "setScreen", at = @At("HEAD"))
    private void widgetplus$updateScreenTimestamp(final Screen screen, final CallbackInfo ci) {
        this.widgetplus$screenOpenTimestamp = screen != null ? System.currentTimeMillis() : 0;
    }

    @Override
    public int widgetplus$getElapsedPauseTime() {
        return this.widgetplus$pauseTimestamp != 0 ? Math.toIntExact(System.currentTimeMillis() - this.widgetplus$pauseTimestamp) : 0;
    }

    @Override
    public int widgetplus$getElapsedScreenOpenTime() {
        return this.widgetplus$screenOpenTimestamp != 0 ? Math.toIntExact(System.currentTimeMillis() - this.widgetplus$screenOpenTimestamp) : 0;
    }
}