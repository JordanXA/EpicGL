package epicgl;

import java.lang.Math;

public class CircleMesh extends Mesh {

   float radius;
   float centerX;
   float centerY;

   CircleMesh(float radius, int vertCount, float centerX, float centerY) {
      this.radius = radius;
      this.centerX = centerX;
      this.centerY = centerY;
      
      genVertices(vertCount);
      genIndices();
      fillColor(new Vector3(0.75f,0.0f,0f));
   }
   
   CircleMesh(float radius) {
	   this.radius = radius;
	   centerX=0;
	   centerY=0;
	   
	   genVertices((int)Math.ceil(radius));
	   //genVertices(30);
	   genIndices();
	   fillColor(new Vector3(0.75f,0.0f,0f));
   }

   
   private void genVertices(int vertCount) {
      
      vertices = new Vector3[vertCount + 1];
      
      vertices[0] = new Vector3(centerX, centerY, -1f);
      
      float angle = (float)Math.PI * 2f / (float)vertCount;
      
      for (int i = 0; i<vertCount; i++) {
         float vertX = radius * (float)Math.sin(angle*i);
         float vertY = -radius * (float)Math.cos(angle*i);
         vertices[i+1] = new Vector3(centerX + vertX, centerY + vertY, -1f);
      }
   }
   
   private void genIndices() {
      int[] newIndices = new int[((vertices.length - 2)*3)+3];
      
      for (int i = 0; i<((newIndices.length/3)-1); i++) {
          newIndices[i*3] = 0;
          newIndices[(i*3)+1] = i+1;
          newIndices[(i*3)+2] = i+2;
      }
      
      newIndices[newIndices.length-3] = 0;
      newIndices[newIndices.length-2] = 1;
      newIndices[newIndices.length-1] = vertices.length-1;
      
      
      indices=newIndices;
   }
   
}