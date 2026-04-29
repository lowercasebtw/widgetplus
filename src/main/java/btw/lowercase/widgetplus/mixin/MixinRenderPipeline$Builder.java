package btw.lowercase.widgetplus.mixin;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.shaders.UniformType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

//? >=26.2 {
/*import com.mojang.blaze3d.pipeline.BindGroupLayout;
import net.minecraft.client.renderer.BindGroupLayouts;
*///? }

// TODO/NOTE: Hack to enable our custom uniform buffer
@Mixin(RenderPipeline.Builder.class)
public abstract class MixinRenderPipeline$Builder {
    //? >=26.2 {
    /*@Shadow
    public abstract RenderPipeline.Builder withBindGroupLayout(final BindGroupLayout bindGroupLayout);

    @Inject(method = "withBindGroupLayout", at = @At("HEAD"))
    private void widgetplus$setUniform(final BindGroupLayout bindGroupLayout, final CallbackInfoReturnable<RenderPipeline.Builder> cir) {
        if (BindGroupLayouts.DYNAMIC_TRANSFORMS.equals(bindGroupLayout)) {
            this.withBindGroupLayout(BindGroupLayout.builder().withUniform("WPData", UniformType.UNIFORM_BUFFER).build());
        }
    }
    *///? } else {
    @Shadow
    public abstract RenderPipeline.Builder withUniform(final String name, final UniformType type);

    @Inject(method = "withUniform(Ljava/lang/String;Lcom/mojang/blaze3d/shaders/UniformType;)Lcom/mojang/blaze3d/pipeline/RenderPipeline$Builder;", at = @At("HEAD"))
    private void widgetplus$setUniform(final String name, final UniformType type, final CallbackInfoReturnable<RenderPipeline.Builder> cir) {
        if ("DynamicTransforms".equals(name)) {
            this.withUniform("WPData", UniformType.UNIFORM_BUFFER);
        }
    }
    //? }
}