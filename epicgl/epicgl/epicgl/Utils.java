package epicgl;

import static org.lwjgl.opengl.GL11.glFrustum;

import java.io.InputStream;
import java.nio.FloatBuffer;
import java.util.Scanner;

import org.joml.Matrix4f;
import org.joml.Vector4f;

public class Utils {

   public static String loadResource(String fileName) throws Exception {
      String result;
      try (InputStream in = Class.forName(Utils.class.getName()).getResourceAsStream(fileName);
                Scanner scanner = new Scanner(in, "UTF-8")) {
         result = scanner.useDelimiter("\\A").next();
      }
      return result;
   }
   
   
   public static void printFloatBuffer(FloatBuffer fb) {
	for(int i = 0; i<fb.limit(); i++) {
		System.out.print(fb.get(i)+" ");
	}
	System.out.println();
}


public static void gluPerspective(float fovy, float aspect, float near, float far) {
      float bottom = -near * (float) Math.tan(fovy / 2);
      float top = -bottom;
      float left = aspect * bottom;
      float right = -left;
      glFrustum(left, right, bottom, top, near, far);
   }
   
   public static Matrix4f createPerspectiveProjection(float fov, float aspect, float zNear, float zFar){
    Matrix4f mat = new Matrix4f();

    float yScale = (float) (1 / (Math.tan(Math.toRadians(fov / 2))));
    float xScale = yScale / aspect;
    float frustrumLength = zFar - zNear;

    mat.m00( xScale);
    mat.m11( yScale);
    mat.m22( -((zFar + zNear) / frustrumLength));
    mat.m23( -1);
    mat.m32(-((2 * zFar * zNear) / frustrumLength));
    mat.m33(0);

    return mat;
   }
   
   public static Matrix4f createOrthoProjection(float left, float right, float bottom, float top, float near, float far) {
      Matrix4f mat = new Matrix4f();
      
      //https://en.wikipedia.org/wiki/Orthographic_projection
      
      mat.m00(2/(right-left));
      mat.m11(2/(top-bottom));
      mat.m22(-2/(far-near));
      mat.m30(-(right+left)/(right-left));
      mat.m31(-(top+bottom)/(top-bottom));
      mat.m32(-(far+near)/(far-near));
      mat.m33(1);
      
      return mat;
   }
   
   public static Matrix4f identityMatrix() {
      Matrix4f mat = new Matrix4f();
      
      mat.m00(1);
      mat.m11(1);
      mat.m22(1);
      mat.m33(1);
      
      return mat;
   }
   
   public static void printFloatArray(float[] arr) {
	   for (int i = 0; i<arr.length; i++) {
		   System.out.print(arr[i]+" ");
	   }
   }
   
   public static void printMatrix4f(Matrix4f mat) {
      for (int i = 0; i<4; i++) {
         Vector4f vec = new Vector4f();
         mat.getColumn(i, vec);
         System.out.println(" "+vec.x+" "+vec.y+" "+vec.z+" "+vec.w);
      }
   }

}