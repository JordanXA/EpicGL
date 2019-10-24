package epicgl;
import java.util.ArrayList;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;
import java.io.InputStream;
import java.util.Scanner;


import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL40.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;


public class Mesh {
	
	private final boolean PRINT_VERTS = false;

   protected Vector3[] vertices;
   protected int[] indices; //needs to be built into vao?
   protected Vector3[] colors;
   protected int vao = 0; //pass from object to renderer
   protected int vbVertices = 0;
   protected int vbIndices = 0; //needs to be built into vao??
   protected int vbColors = 0;

   public Mesh() {
      vertices = new Vector3[] {new Vector3(0f, 0f), new Vector3(0f, 0f), new Vector3(0f, 0f)};
      
      int[] newIndices = new int[(vertices.length - 2)*3];
      
      for (int i = 0; i<(newIndices.length/3); i++) {
          newIndices[i*3] = i;
          newIndices[(i*3)+1] = i+1;
          newIndices[(i*3)+2] = i+2;
      }
      
      indices=newIndices;
      
      colors = new Vector3[vertices.length];
      for (int i=0; i<vertices.length; i++) {colors[i] = new Vector3(0f,0.75f,0.75f);}
   }

   
   public Mesh(Vector3[] newVertices) {
      vertices = newVertices;
      
      int[] newIndices = new int[(newVertices.length - 2)*3];
      
      for (int i = 0; i<(newIndices.length/3); i++) {
          newIndices[i*3] = i;
          newIndices[(i*3)+1] = i+1;
          newIndices[(i*3)+2] = i+2;
      }
      
      indices=newIndices;
      
      colors = new Vector3[newVertices.length];
      for (int i=0; i<newVertices.length; i++) {colors[i] = new Vector3(0f,0.75f,0.75f);}
   }
   
   public Mesh(Vector3[] newVertices, Vector3 fillColor) {
      vertices = newVertices;
      
      int[] newIndices = new int[(newVertices.length - 2)*3];
      
      for (int i = 0; i<(newIndices.length/3); i++) {
          newIndices[i*3] = i;
          newIndices[(i*3)+1] = i+1;
          newIndices[(i*3)+2] = i+2;
      }
      
      indices=newIndices;
      
      colors = new Vector3[newVertices.length];
      for (int i=0; i<newVertices.length; i++) {colors[i] = fillColor;}
   }

   public Mesh(Vector3[] newVertices, int[] newIndices) {
      vertices = newVertices;
      indices = newIndices;
      colors = new Vector3[newVertices.length];
      for (int i=0; i<newVertices.length; i++) {colors[i] = new Vector3(0f,0.75f,0.75f);}
   }
   
   public Mesh(Vector3[] newVertices, int[] newIndices, Vector3 fillColor) {
      vertices = newVertices;
      indices = newIndices;
      colors = new Vector3[newVertices.length];
      for (int i=0; i<newVertices.length; i++) {colors[i] = fillColor;}
   }
   
   public Mesh(Vector3[] newVertices, int[] newIndices, Vector3[] newColors) {
      vertices = newVertices;
      indices = newIndices;
      colors = newColors;
   }
   
   public void fillColor(Vector3 fillColor) {
	   boolean changed = false;
	   Vector3[] newColors = new Vector3[indices.length];
	   for (int i=0; i<indices.length; i++) {
		   newColors[i] = fillColor;
		   
		   //checks for changes made to the color array
		   if(newColors.length!=colors.length) {
			   changed = true;
			   //records if the new color array was a different length than the old color array, which would mean it has changed
		   }
		   else {
			   if(newColors[i].x!=colors[i].x || newColors[i].y!=colors[i].y || newColors[i].y!=colors[i].y) {
				   changed = true;
				   //records if any color in the mesh is changed.
			   }
		   }
		   
	   }
	   //only update the buffers if a color in the mesh actually changed.
	   if (changed) {
		   colors=newColors;
		   updateBuffers();
	   }
   }
   
   /*public void change(Vector3[] newVertices, int[] newIndices) {
      
      vertices = newVertices;
      indices = newIndices;
      //setVertices(newVertices);
      updateBuffers();
      
   }*/
   
   protected float[] getFloats(Vector3[] vectors) {
      float[] output = new float[vectors.length*3];
      
      for (int i=0; i<vectors.length; i++) {
          for (int k=0; k<(vectors[i].toFloats()).length; k++) {
              output[i*3+k] = vectors[i].toFloats()[k];
          }
      }
      return output;
   }
   
      
   protected void updateBuffers() {

	   if(PRINT_VERTS) {
		      for (int i=0; i<getFloats(vertices).length; i++) {
		          System.out.printf(" %f",getFloats(vertices)[i]);
		       }
		       System.out.println();
	   }

      //sets up temporary buffers to hold the data to put into the VBOs
      FloatBuffer verticesBuffer = MemoryUtil.memAllocFloat(getFloats(vertices).length);
      verticesBuffer.put(getFloats(vertices)).flip();
      
      IntBuffer indicesBuffer = MemoryUtil.memAllocInt(indices.length);
      indicesBuffer.put(indices).flip();
      
      FloatBuffer colorBuffer = MemoryUtil.memAllocFloat(getFloats(colors).length);
      colorBuffer.put(getFloats(colors)).flip();
      
      if(vao==0) {
          //make vertex array object to store the buffers, if needed
          vao = glGenVertexArrays();
      }

      glBindVertexArray(vao);
      
      //make vertex buffer object to store the vertices,
      //but also, only create new buffer ids if needed
      if (vbVertices==0) {vbVertices = glGenBuffers();}
      if (vbIndices==0) {vbIndices= glGenBuffers();}
      if (vbColors==0) {vbColors = glGenBuffers();}

      //starts setting up the vertices vbo
      glBindBuffer(GL_ARRAY_BUFFER, vbVertices);
      glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);
      //sets up the data of the vertices vbo
      glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
      glBindBuffer(GL_ARRAY_BUFFER, 0);
      //sets up the indices vbo
      glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vbIndices);
      glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);
      //glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0); //unbind the indices vbo
      //sets up the colors vbo
      glBindBuffer(GL_ARRAY_BUFFER, vbColors);
      glBufferData(GL_ARRAY_BUFFER, colorBuffer, GL_STATIC_DRAW);
      //sets up the data of the color vbo and unbinds
      glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);
      //glBindBuffer(GL_ARRAY_BUFFER, 0);
      //unbind the vao
      glEnableVertexAttribArray(0);
      glEnableVertexAttribArray(1);
      glBindVertexArray(0); 
      
      
      
      if (verticesBuffer!=null) {
         MemoryUtil.memFree(verticesBuffer);
         verticesBuffer=null;
      }
      if (indicesBuffer!=null) {
         MemoryUtil.memFree(indicesBuffer);
         indicesBuffer=null;
      }
      if (colorBuffer!=null) {
         MemoryUtil.memFree(colorBuffer);
         colorBuffer=null;
      }
      
      
      if (vao == 0) {
         System.out.println("frIK");
      }
      
   }
   
   public void cleanup() {
	   glDeleteBuffers(vbVertices);
	   glDeleteBuffers(vbIndices);
	   glDeleteBuffers(vbColors);
	   glDeleteBuffers(vao);
	   
   }
   
   public int getVao() {
      if (vao == 0) {
         updateBuffers();
      }
      return vao;
   }

   //DEPRECACTED BY THE CREATION OF THE RENDERER CLASS WHICH WILL HOLD ALL OF THIS CODE
   public void draw() {
      
      if (vao == 0) {
         updateBuffers();
      }
   
      IntBuffer numIndices = MemoryUtil.memAllocInt(1);
      numIndices.position(0);
      
      //Bind to the VAO
      glBindVertexArray(vao);
      //sets the VAO to use the first array
      //glEnableVertexAttribArray(0);
      //glEnableVertexAttribArray(1);
      
      //glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vbIndices);
      
      glGetBufferParameteriv(GL_ELEMENT_ARRAY_BUFFER, GL_BUFFER_SIZE, numIndices);
      glDrawElements(GL_TRIANGLES, numIndices.get(0), GL_UNSIGNED_INT, NULL);
      if(glGetError()!=0) {System.out.println(glGetError());}
      //glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
      
      //glDisableVertexAttribArray(0);
      //glDisableVertexAttribArray(1);
      glBindVertexArray(0);
      
      if (numIndices!=null) {
         MemoryUtil.memFree(numIndices);
      }
   
   }
   
   //Do i need a destructor method that frees up the vao and stuff?
      
}