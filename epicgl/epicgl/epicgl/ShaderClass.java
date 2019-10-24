package epicgl;

import static org.lwjgl.opengl.GL20.*;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.system.MemoryStack;

//thanks internet
public class ShaderClass {
 
 private final int programId;
 private int vertexShaderId;
 private int fragmentShaderId;
 
 private final Map<String, Integer> uniforms;
 
 public ShaderClass() throws Exception {
    programId = glCreateProgram();
    uniforms = new HashMap<>();
    
    if (programId == 0) {
       throw new Exception("Could not create shader");
    }
    
 }
 
 public void createVertexShader(String shaderCode) throws Exception {
    vertexShaderId = createShader(shaderCode, GL_VERTEX_SHADER);
 }
 
 public void createFragmentShader(String shaderCode) throws Exception {
    fragmentShaderId = createShader(shaderCode, GL_FRAGMENT_SHADER);
 }
 
 protected int createShader(String shaderCode, int shaderType) throws Exception {
    int shaderId = glCreateShader(shaderType);
    if (shaderId == 0) {
       throw new Exception("Error creating shader of type: "+shaderType);
    }
    
    glShaderSource(shaderId, shaderCode);
    glCompileShader(shaderId);
    
    if (glGetShaderi(shaderId, GL_COMPILE_STATUS) == 0) {
       throw new Exception("Error compiling shader Code: "+glGetShaderInfoLog(shaderId,1024));
    }
    
    glAttachShader(programId,shaderId);
    
    return shaderId;
    
 }
 
 public void link() throws Exception {
    
    glLinkProgram(programId);
    
    if(glGetProgrami(programId, GL_LINK_STATUS) == 0) {
       throw new Exception("Error linking shader Code: "+glGetShaderInfoLog(programId,1024));
    }
    
    if (vertexShaderId !=0) {
       glDetachShader(programId, vertexShaderId);
    }
    
    if (fragmentShaderId !=0) {
       glDetachShader(programId, fragmentShaderId);
    }
    
    glValidateProgram(programId);
    if(glGetProgrami(programId, GL_VALIDATE_STATUS)==0) {
       System.err.println("Warning validating Shader code: "+glGetProgramInfoLog(programId,1024));
    }
 }
 
 void setProjectionMatrix(Matrix4f mat) throws Exception {
    int uniformLocation = glGetUniformLocation(programId, "projectionMatrix");
    if (uniformLocation < 0) {
       throw new Exception("Could not find projectionMatrix");
    }
    try (MemoryStack stack = MemoryStack.stackPush()) {
       FloatBuffer fb = stack.mallocFloat(16);
       mat.get(fb);
       fb.position(0);
       glUniformMatrix4fv(uniformLocation, false, fb);
    }
 }
 
 void setUniform(String uniformName, Matrix4f mat) throws Exception { 
    int uniformLocation = glGetUniformLocation(programId, uniformName);
    if (uniformLocation < 0) {
       throw new Exception("Could not find "+uniformName);
    }
    try (MemoryStack stack = MemoryStack.stackPush()) {
       FloatBuffer fb = BufferUtils.createFloatBuffer(16);
       float[] matarr = new float[16];
       mat.get(matarr);
       fb.put(matarr);
       fb.flip();
       
       //Utils.printFloatBuffer(fb);
       glUniformMatrix4fv(uniformLocation, false, fb);
    }
 }
    
    void setUniform(String uniformName, boolean bool) throws Exception { 
        int uniformLocation = glGetUniformLocation(programId, uniformName);
        if (uniformLocation < 0) {
           throw new Exception("Could not find "+uniformName);
        }
        try (MemoryStack stack = MemoryStack.stackPush()) {
        	int intBool = bool? 1 : 0;
           //Utils.printFloatBuffer(fb);
           glUniform1i(uniformLocation, intBool);
        }
 }
 
 void resetWorldMatrix() {
      int uniformLocation = glGetUniformLocation(programId, "worldMatrix");
      try (MemoryStack stack = MemoryStack.stackPush()) {
    	  FloatBuffer fb = stack.mallocFloat(16);
          Utils.identityMatrix().get(fb);
          fb.position(0);
          glUniformMatrix4fv(uniformLocation, false, fb);
      }
 }
 
 public void bind() {
    glUseProgram(programId);
 }
 
 public void unbind() {
    glUseProgram(0);
 }
 
 public void cleanup() {
    unbind();
    if (programId != 0) {
       glDeleteProgram(programId);
    }
 }
 
}