package derg.cbplus.interfaces

import net.minecraft.client.option.OptionFloat

interface IGameSettingsMixin {
    fun cbplus_getColorblindnessSeverity(): OptionFloat
    fun cbplus_getColorblindnessSkew(): OptionFloat
}
