package derg.cbplus.mixin;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import derg.cbplus.interfaces.IGameSettingsMixin;
import derg.cbplus.options.CBTypeOptionComponent;
import derg.cbplus.options.CBSkewOptionComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.options.components.FloatOptionComponent;
import net.minecraft.client.gui.options.components.OptionsCategory;
import net.minecraft.client.gui.options.components.ToggleableOptionComponent;
import net.minecraft.client.gui.options.data.OptionsPage;
import net.minecraft.client.gui.options.data.OptionsPages;
import net.minecraft.client.option.enums.Colorblindness;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = OptionsPages.class, remap = false)
public class OptionsPagesMixin {
	@Shadow
	public static OptionsPage ACCESSIBILITY;

	@Shadow
	@Final
	private static Minecraft mc;

	@Inject(method = "init", at = @At("TAIL"))
	private static void addCBSeverityOption(CallbackInfo ci) {
		CBTypeOptionComponent<Colorblindness> cbTypeComponent = new CBTypeOptionComponent<>(mc.gameSettings.colorblindnessFix);
		CBSkewOptionComponent skewComponent = new CBSkewOptionComponent(((IGameSettingsMixin) mc.gameSettings).cbplus_getColorblindnessSkew());
		cbTypeComponent.setSkewComponent(skewComponent);

		ACCESSIBILITY.withComponent(new OptionsCategory("gui.options.page.accessibility.category.colorblindness")
			.withComponent(cbTypeComponent)
			.withComponent(new FloatOptionComponent(((IGameSettingsMixin) mc.gameSettings).cbplus_getColorblindnessSeverity()))
			.withComponent(skewComponent)
		);
	}

	@Definition(id = "ToggleableOptionComponent", type = ToggleableOptionComponent.class)
	@Definition(id = "gameSettings", field = "Lnet/minecraft/client/Minecraft;gameSettings:Lnet/minecraft/client/option/GameSettings;")
	@Definition(id = "colorblindnessFix", field = "Lnet/minecraft/client/option/GameSettings;colorblindnessFix:Lnet/minecraft/client/option/OptionEnum;")
	@Definition(id = "mc", field = "Lnet/minecraft/client/gui/options/data/OptionsPages;mc:Lnet/minecraft/client/Minecraft;")
	@Expression("new ToggleableOptionComponent(mc.gameSettings.colorblindnessFix)")
	@ModifyExpressionValue(method = "init", at = @At(value = "MIXINEXTRAS:EXPRESSION", ordinal = 1))
	private static <E> ToggleableOptionComponent<E> removeOldCBOption(ToggleableOptionComponent<E> original) {
		return null;
	}
}
