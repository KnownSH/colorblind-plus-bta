package derg.cbplus

import derg.cbplus.interfaces.IGameSettingsMixin
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.Minecraft
import net.minecraft.client.option.enums.Colorblindness
import net.minecraft.client.render.shader.Shader
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL20
import java.nio.FloatBuffer

object ColorblindMatrices {
    private val matrixBuffer: FloatBuffer = BufferUtils.createFloatBuffer(16)

    private val IDENTITY = floatArrayOf(
        1f, 0f, 0f, 0f,
        0f, 1f, 0f, 0f,
        0f, 0f, 1f, 0f,
        0f, 0f, 0f, 1f
    )

    private val PROTAN_CORR = floatArrayOf(
        0.00000000f, 0.58344492f, 0.63836163f, 0.00000000f,
        0.00000000f, -0.58344491f, -0.63836163f, 0.00000000f,
        0.00000000f, -0.00000001f, -0.00000001f, 0.00000000f,
        0.00000000f, 0.00000000f, 0.00000000f, 0.00000000f
    )

    private val DEUTAN_CORR = floatArrayOf(
        0.29350236f, 0.00000000f, -0.24853154f, 0.00000000f,
        -0.29350236f, 0.00000000f, 0.24853154f, 0.00000000f,
        -0.00000000f, 0.00000000f, 0.00000000f, 0.00000000f,
        0.00000000f, 0.00000000f, 0.00000000f, 0.00000000f
    )

    private val TRITAN_CORR = floatArrayOf(
        -0.00000000f, -0.00000000f, 0.00000000f, 0.00000000f,
        -0.67697457f, -0.70050992f, 0.00000000f, 0.00000000f,
        0.67697456f, 0.70050992f, 0.00000000f, 0.00000000f,
        0.00000000f, 0.00000000f, 0.00000000f, 0.00000000f
    )

    private val GRAY_CORR = floatArrayOf(
        -0.701f,  0.299f,  0.299f,  0.0f,
        0.587f, -0.413f,  0.587f,  0.0f,
        0.114f,  0.114f, -0.886f,  0.0f,
        0.0f,    0.0f,    0.0f,    0.0f
    )

    @Environment(EnvType.CLIENT)
    fun applyUniforms(shader: Shader, mc: Minecraft) {
        val severity: Float = (mc.gameSettings as IGameSettingsMixin).cbplus_getColorblindnessSeverity().value
        val skew: Float = (mc.gameSettings as IGameSettingsMixin).cbplus_getColorblindnessSkew().value
        val mode: Colorblindness = mc.gameSettings.colorblindnessFix.value

        val corr = when (mode) {
            Colorblindness.PROTANOPIA -> PROTAN_CORR
            Colorblindness.DEUTERANOPIA -> DEUTAN_CORR
            Colorblindness.TRITANOPIA -> TRITAN_CORR
            Colorblindness.GRAYSCALE -> GRAY_CORR
            else -> null
        }

        val finalMatrix = FloatArray(16)
        if (corr != null) {
            System.arraycopy(IDENTITY, 0, finalMatrix, 0, 16)
            when (mode) {
                Colorblindness.PROTANOPIA -> {
                    val gWeight = skew * 2.0f
                    val bWeight = (1.0f - skew) * 2.0f
                    finalMatrix[1] = IDENTITY[1] + severity * corr[1] * gWeight
                    finalMatrix[5] = IDENTITY[5] + severity * corr[5] * gWeight
                    finalMatrix[2] = IDENTITY[2] + severity * corr[2] * bWeight
                    finalMatrix[6] = IDENTITY[6] + severity * corr[6] * bWeight
                }
                Colorblindness.DEUTERANOPIA -> {
                    val rWeight = skew * 2.0f
                    val bWeight = (1.0f - skew) * 2.0f
                    finalMatrix[0] = IDENTITY[0] + severity * corr[0] * rWeight
                    finalMatrix[4] = IDENTITY[4] + severity * corr[4] * rWeight
                    finalMatrix[2] = IDENTITY[2] + severity * corr[2] * bWeight
                    finalMatrix[6] = IDENTITY[6] + severity * corr[6] * bWeight
                }
                Colorblindness.TRITANOPIA -> {
                    val rWeight = skew * 2.0f
                    val gWeight = (1.0f - skew) * 2.0f
                    finalMatrix[4] = IDENTITY[4] + severity * corr[4] * rWeight
                    finalMatrix[8] = IDENTITY[8] + severity * corr[8] * rWeight
                    finalMatrix[5] = IDENTITY[5] + severity * corr[5] * gWeight
                    finalMatrix[9] = IDENTITY[9] + severity * corr[9] * gWeight
                }
                else -> {
                    for (i in 0..15) finalMatrix[i] = IDENTITY[i] + (severity * corr[i])
                }
            }
        } else {
            System.arraycopy(IDENTITY, 0, finalMatrix, 0, 16)
        }

        matrixBuffer.clear()
        matrixBuffer.put(finalMatrix)
        matrixBuffer.flip()

        GL20.glUniformMatrix4fv(shader.getUniform("corrective"), false, matrixBuffer)
    }
}


