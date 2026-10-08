#version 410

out vec4 FragColor;
in vec2 TexCoords;

uniform sampler2D colortex0;
uniform float gamma;
uniform mat4 corrective;

vec3 adjustGamma(vec3 color, float value) {
    return pow(color, vec3(1.0 - value + 0.5));
}

void main() {
    vec4 texel = texture2D(colortex0, TexCoords);

    vec4 corrected = corrective * vec4(texel.rgb, 1.0);
    vec3 color = adjustGamma(corrected.rgb, gamma);

    FragColor = vec4(color, texel.a);
}
