package epicgl;


public class Vector3 {
   public float x = 0.0f;
   public float y = 0.0f;
   public float z = 0.0f;
   
   public Vector3() {
      x = 0.0f;
      y = 0.0f;
      z = 0.0f;
   }
   
   public Vector3(float ix, float iy) {
      x = ix;
      y = iy;
      z = 0.0f;
   }
   
   public Vector3(float ix, float iy, float iz) {
      x = ix;
      y = iy;
      z = iz;
   }
   
   public float[] toFloats() {
      float[] floatArr = {x,y,z};
      return floatArr; 
   }
   
}
