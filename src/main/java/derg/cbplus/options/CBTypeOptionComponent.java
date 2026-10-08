package derg.cbplus.options;

import net.minecraft.client.gui.options.components.ToggleableOptionComponent;
import net.minecraft.client.option.OptionToggleable;

public class CBTypeOptionComponent<E> extends ToggleableOptionComponent<E> {
	private CBSkewOptionComponent skewComponent = null;

	public CBTypeOptionComponent(OptionToggleable<E> option) {
		super(option);
	}

	public void setSkewComponent(CBSkewOptionComponent skewComponent) {
		this.skewComponent = skewComponent;
	}

	@Override
	protected void onChanged() {
		super.onChanged();
		if (skewComponent != null) skewComponent.invokeChange();
	}
}
