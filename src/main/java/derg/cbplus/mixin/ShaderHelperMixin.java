package derg.cbplus.mixin;

import derg.cbplus.ColorblindMatrices;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.shader.Shader;
import net.minecraft.client.render.shader.ShaderHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ShaderHelper.class)
public class ShaderHelperMixin {
	@Inject(method = "setUniforms", at = @At("TAIL"))
	private static void applyCBSeverity$cbplus(Minecraft minecraft, Shader shader, float partialTicks, CallbackInfo ci) {
		ColorblindMatrices.applyUniforms(shader);
	}
}
