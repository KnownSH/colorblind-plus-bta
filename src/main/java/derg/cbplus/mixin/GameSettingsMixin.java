package derg.cbplus.mixin;

import derg.cbplus.interfaces.IGameSettingsMixin;
import net.minecraft.client.option.GameSettings;
import net.minecraft.client.option.OptionFloat;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(value = GameSettings.class, remap = false)
public class GameSettingsMixin implements IGameSettingsMixin {
	@Unique
	public OptionFloat colorblindnessSeverity = new OptionFloat((GameSettings) (Object) this, "colorblindnessSeverity", 1.0f);

	@Unique
	public OptionFloat colorblindnessSkew = new OptionFloat((GameSettings) (Object) this, "colorblindnessSkew", 0.5f);

	@Override
	public @NotNull OptionFloat cbplus_getColorblindnessSeverity() {
		return colorblindnessSeverity;
	}

	@Override
	public @NotNull OptionFloat cbplus_getColorblindnessSkew() {
		return colorblindnessSkew;
	}
}
