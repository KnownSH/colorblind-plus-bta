package derg.cbplus;

import net.fabricmc.api.ModInitializer;
import net.minecraft.client.Minecraft;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.HalpLibe;
import turniplabs.halplibe.event.defs.ClientEvents;
import turniplabs.halplibe.util.dependency.Key;

public class CBPlus implements ModInitializer {
	public static final String MOD_ID = HalpLibe.registerMod("cbplus", false);
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ClientEvents.AFTER_CLIENT_START.listen(Key.of(MOD_ID), CBPlus::afterClientStart);
	}

	private static void afterClientStart() {
		Minecraft mc = Minecraft.getMinecraft();
		mc.setRenderer(new ShadersRendererCB(mc));
	}
}
