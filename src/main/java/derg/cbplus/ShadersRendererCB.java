package derg.cbplus;

import net.minecraft.client.Minecraft;
import net.minecraft.client.render.shader.ShaderProvider;
import net.minecraft.client.render.shader.ShaderProviderInternal;
import net.minecraft.client.render.shader.ShadersRenderer;

public class ShadersRendererCB extends ShadersRenderer {
	private final ShaderProvider cbProvider = new ShaderProviderInternal("/assets/cbplus/shaders/");

	public ShadersRendererCB(Minecraft minecraft) {
		super(minecraft);
	}

	@Override
	public void reload() {
		super.reload();
		finalShader.compile(cbProvider, "cbcorrection/base");
		if (!finalShader.isEnabled()) {
			CBPlus.LOGGER.error("cbcorrection/base failed to compile.");
			finalShader.compile(internal, "post/base");
		}
	}
}
