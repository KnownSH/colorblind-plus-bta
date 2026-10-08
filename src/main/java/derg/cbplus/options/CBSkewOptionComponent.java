package derg.cbplus.options;

import net.minecraft.client.gui.options.components.FloatOptionComponent;
import net.minecraft.client.option.GameSettings;
import net.minecraft.client.option.OptionFloat;
import net.minecraft.client.option.enums.Colorblindness;
import net.minecraft.core.lang.I18n;

import java.util.Locale;

public class CBSkewOptionComponent extends FloatOptionComponent {
	public CBSkewOptionComponent(OptionFloat option) {
		super(option);
	}

	@Override
	protected void onChanged() {
		I18n trans = I18n.getInstance();
		Colorblindness cbType = GameSettings.COLORBLINDNESS_FIX.value;
		int skew = (int) (this.option.value * 100);

		this.slider.displayString = cbType == Colorblindness.NONE
			? trans.translateKey("options.cb.skew.none")
			: trans.translateKeyAndFormat("options.cb.skew." + cbType.name().toLowerCase(Locale.ROOT), skew, 100 - skew);
	}

	public void invokeChange() {
		this.onChanged();
	}
}
