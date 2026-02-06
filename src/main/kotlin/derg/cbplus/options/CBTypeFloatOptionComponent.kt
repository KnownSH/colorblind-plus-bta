package derg.cbplus.options

import net.minecraft.client.gui.options.components.ToggleableOptionComponent
import net.minecraft.client.option.OptionToggleable

class CBTypeOptionComponent<E>(option: OptionToggleable<E>) : ToggleableOptionComponent<E>(option) {
    var skewComponent: CBSkewOptionComponent? = null

    override fun onChanged() {
        super.onChanged()
        skewComponent?.invokeChange()
    }
}
