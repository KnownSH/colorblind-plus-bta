package derg.cbplus.options

import net.minecraft.client.gui.options.components.FloatOptionComponent
import net.minecraft.client.option.OptionFloat
import net.minecraft.client.option.enums.Colorblindness
import net.minecraft.core.lang.I18n

class CBSkewOptionComponent(optionFloat: OptionFloat) : FloatOptionComponent(optionFloat) {
    override fun onChanged() {
        val trans = I18n.getInstance()
        val cbType: Colorblindness = mc.gameSettings.colorblindnessFix.value
        val skew: Int = (this.option.value * 100).toInt()
        this.slider.displayString = when (cbType) {
            Colorblindness.NONE -> trans.translateKey("options.cb.skew.none")
            else -> trans.translateKeyAndFormat("options.cb.skew.${cbType.name.lowercase()}", skew, 100 - skew)
        }
    }

    fun invokeChange() {
        this.onChanged()
    }
}
