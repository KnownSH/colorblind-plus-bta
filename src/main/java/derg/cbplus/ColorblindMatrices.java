package derg.cbplus;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.option.GameSettings;
import net.minecraft.client.option.enums.Colorblindness;
import net.minecraft.client.render.shader.Shader;
import org.joml.Matrix4f;

public final class ColorblindMatrices {
	private static final Matrix4f uniformMatrix = new Matrix4f();

	private static final float[] IDENTITY = {
		1f, 0f, 0f, 0f,
		0f, 1f, 0f, 0f,
		0f, 0f, 1f, 0f,
		0f, 0f, 0f, 1f
	};

	private static final float[] PROTAN_CORR = {
		0.00000000f, 0.58344492f, 0.63836163f, 0.00000000f,
		0.00000000f, -0.58344491f, -0.63836163f, 0.00000000f,
		0.00000000f, -0.00000001f, -0.00000001f, 0.00000000f,
		0.00000000f, 0.00000000f, 0.00000000f, 0.00000000f
	};

	private static final float[] DEUTAN_CORR = {
		0.29350236f, 0.00000000f, -0.24853154f, 0.00000000f,
		-0.29350236f, 0.00000000f, 0.24853154f, 0.00000000f,
		-0.00000000f, 0.00000000f, 0.00000000f, 0.00000000f,
		0.00000000f, 0.00000000f, 0.00000000f, 0.00000000f
	};

	private static final float[] TRITAN_CORR = {
		-0.00000000f, -0.00000000f, 0.00000000f, 0.00000000f,
		-0.67697457f, -0.70050992f, 0.00000000f, 0.00000000f,
		0.67697456f, 0.70050992f, 0.00000000f, 0.00000000f,
		0.00000000f, 0.00000000f, 0.00000000f, 0.00000000f
	};

	private static final float[] GRAY_CORR = {
		-0.701f, 0.299f, 0.299f, 0.0f,
		0.587f, -0.413f, 0.587f, 0.0f,
		0.114f, 0.114f, -0.886f, 0.0f,
		0.0f, 0.0f, 0.0f, 0.0f
	};

	private ColorblindMatrices() {}

	@Environment(EnvType.CLIENT)
	public static void applyUniforms(Shader shader) {
		if (!shader.isEnabled()) return;

		float severity = CBPlusOptions.SEVERITY.value;
		float skew = CBPlusOptions.SKEW.value;
		Colorblindness mode = GameSettings.COLORBLINDNESS_FIX.value;

		float[] corr = switch (mode) {
			case PROTANOPIA -> PROTAN_CORR;
			case DEUTERANOPIA -> DEUTAN_CORR;
			case TRITANOPIA -> TRITAN_CORR;
			case GRAYSCALE -> GRAY_CORR;
			default -> null;
		};

		float[] finalMatrix = IDENTITY.clone();
		if (corr != null) {
			switch (mode) {
				case PROTANOPIA -> {
					float gWeight = skew * 2.0f;
					float bWeight = (1.0f - skew) * 2.0f;
					finalMatrix[1] = IDENTITY[1] + severity * corr[1] * gWeight;
					finalMatrix[5] = IDENTITY[5] + severity * corr[5] * gWeight;
					finalMatrix[2] = IDENTITY[2] + severity * corr[2] * bWeight;
					finalMatrix[6] = IDENTITY[6] + severity * corr[6] * bWeight;
				}
				case DEUTERANOPIA -> {
					float rWeight = skew * 2.0f;
					float bWeight = (1.0f - skew) * 2.0f;
					finalMatrix[0] = IDENTITY[0] + severity * corr[0] * rWeight;
					finalMatrix[4] = IDENTITY[4] + severity * corr[4] * rWeight;
					finalMatrix[2] = IDENTITY[2] + severity * corr[2] * bWeight;
					finalMatrix[6] = IDENTITY[6] + severity * corr[6] * bWeight;
				}
				case TRITANOPIA -> {
					float rWeight = skew * 2.0f;
					float gWeight = (1.0f - skew) * 2.0f;
					finalMatrix[4] = IDENTITY[4] + severity * corr[4] * rWeight;
					finalMatrix[8] = IDENTITY[8] + severity * corr[8] * rWeight;
					finalMatrix[5] = IDENTITY[5] + severity * corr[5] * gWeight;
					finalMatrix[9] = IDENTITY[9] + severity * corr[9] * gWeight;
				}
				default -> {
					for (int i = 0; i < 16; i++) finalMatrix[i] = IDENTITY[i] + (severity * corr[i]);
				}
			}
		}

		uniformMatrix.set(finalMatrix);
		shader.uniformMat4f("corrective", uniformMatrix);
	}
}
