package derg.cbplus.mixin;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import derg.cbplus.CBPlusOptions;
import derg.cbplus.options.CBSkewOptionComponent;
import derg.cbplus.options.CBTypeOptionComponent;
import net.minecraft.client.gui.options.components.FloatOptionComponent;
import net.minecraft.client.gui.options.components.OptionsCategory;
import net.minecraft.client.gui.options.components.ToggleableOptionComponent;
import net.minecraft.client.gui.options.data.OptionsPage;
import net.minecraft.client.gui.options.data.OptionsPages;
import net.minecraft.client.option.GameSettings;
import net.minecraft.client.option.enums.Colorblindness;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = OptionsPages.class, remap = false)
public class OptionsPagesMixin {
	@Shadow
	public static OptionsPage ACCESSIBILITY;

	@Inject(method = "init", at = @At("TAIL"))
	private static void addCBSeverityOption(CallbackInfo ci) {
		CBTypeOptionComponent<Colorblindness> cbTypeComponent = new CBTypeOptionComponent<>(GameSettings.COLORBLINDNESS_FIX);
		CBSkewOptionComponent skewComponent = new CBSkewOptionComponent(CBPlusOptions.SKEW);
		cbTypeComponent.setSkewComponent(skewComponent);

		ACCESSIBILITY.withComponent(new OptionsCategory("gui.options.page.accessibility.category.colorblindness")
			.withComponent(cbTypeComponent)
			.withComponent(new FloatOptionComponent(CBPlusOptions.SEVERITY))
			.withComponent(skewComponent)
		);
	}

	@Definition(id = "ToggleableOptionComponent", type = ToggleableOptionComponent.class)
	@Definition(id = "COLORBLINDNESS_FIX", field = "Lnet/minecraft/client/option/GameSettings;COLORBLINDNESS_FIX:Lnet/minecraft/client/option/OptionEnum;")
	@Expression("new ToggleableOptionComponent(COLORBLINDNESS_FIX)")
	@ModifyExpressionValue(method = "init", at = @At(value = "MIXINEXTRAS:EXPRESSION", ordinal = 1))
	private static <E> ToggleableOptionComponent<E> removeOldCBOption(ToggleableOptionComponent<E> original) {
		return null;
	}
}
