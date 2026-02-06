#version 120

uniform sampler2D colortex0;
uniform float gamma;
uniform mat4 corrective;

varying vec2 texcoord;

vec3 adjustGamma(vec3 color, float value) {
    return pow(color, vec3(1.0 - value + 0.5));
}

void main() {
    vec4 texel = texture2D(colortex0, texcoord);

    vec4 corrected = corrective * vec4(texel.rgb, 1.0);
    vec3 color = adjustGamma(corrected.rgb, gamma);

    gl_FragColor = vec4(color, texel.a);
}
