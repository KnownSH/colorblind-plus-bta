package derg.cbplus

import net.fabricmc.api.ModInitializer
import net.minecraft.client.Minecraft
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import turniplabs.halplibe.util.GameStartEntrypoint

object CBPlus : ModInitializer, GameStartEntrypoint {
    const val MOD_ID: String = "cbplus"
    val LOGGER: Logger = LoggerFactory.getLogger(MOD_ID)

    override fun onInitialize() {}

    override fun beforeGameStart() {}

    override fun afterGameStart() {
        val mc = Minecraft.getMinecraft()
        mc.setRenderer(ShadersRendererCB(mc, "cbcorrection/"))
    }
}
