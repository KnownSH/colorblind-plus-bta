package derg.cbplus

import net.minecraft.client.Minecraft
import net.minecraft.client.render.shader.ShaderProvider
import net.minecraft.client.render.shader.ShaderProviderInternal
import net.minecraft.client.render.shader.ShadersRenderer

class ShadersRendererCB(minecraft: Minecraft, val shaderDir: String) : ShadersRenderer(minecraft) {
    override fun getShader(): ShaderProvider {
        return ShaderProviderInternal("/assets/cbplus/shaders/$shaderDir")
    }
}
