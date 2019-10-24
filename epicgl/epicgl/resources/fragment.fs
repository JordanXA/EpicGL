#version 330

in vec3 exColor;
out vec4 fragColor;

uniform bool DRAW_EDGES;

void main()
{
    if(DRAW_EDGES==true) {
    	fragColor=vec4(0.0,0.0,0.0,1.0);
    }
    else {
    	fragColor = vec4(exColor, 1.0);
    }
}