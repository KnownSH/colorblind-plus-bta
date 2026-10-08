package derg.cbplus;

import net.minecraft.client.option.GameSettings;
import net.minecraft.client.option.OptionFloat;

public final class CBPlusOptions {
	public static final OptionFloat SEVERITY = GameSettings.register(new OptionFloat("colorblindnessSeverity", 1.0f));
	public static final OptionFloat SKEW =  GameSettings.register(new OptionFloat("colorblindnessSkew", 0.5f));

	private CBPlusOptions() {}
}
