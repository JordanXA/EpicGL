package epicgl;

import java.util.List;

import org.joml.Vector2f;

import java.util.ArrayList;


public class ObjectManager {
   
   private List<GameObject> objectList = new ArrayList<GameObject>();
   
   public void update() {
	   processCollisions();
	   for(GameObject object : objectList) {
		   Vector2f gravity = new Vector2f(Physics.gravity);
		   //if(! object.isOutsideScreen())
		   object.addForce(gravity.mul(object.getMass()));
		   object.tick();
		   
		   //fun color code
		   /*
		   if (object.getCollisions().size()>0) {
			   object.fillObject(new Vector3(0.95f,0f,0f));
		   }
		   else {
			   object.fillObject(new Vector3(0.5f,0.5f,0.5f));
		   }
		   */
		   
		   
		   //fun color code
		   object.resetCollisions();
	   }
	   
   }
   
   private void processCollisions() {
	   for(int i = 0; i <objectList.size(); i++) {
		   //objectList.get(i).resetCollisions(); was breaking code
		   for(int k = 0; k<objectList.size(); k++) {
			   //makes sure not to check object for colliding against itself or any object already checked
			   if(k<=i) {continue;}
			   GameObject object1 = objectList.get(i);
			   GameObject object2 = objectList.get(k);
			   
			   if (Physics.collisionTest(object1, object2)) {
				   object1.addCollidingObj(object2);
				   object2.addCollidingObj(object1);
				   Physics.collisionResolve(object1, object2);
			   }
		   }
		   
	   }
	   
   }
   
   public GameObject[] getObjectsArray() {
      GameObject[] objects = objectList.toArray(new GameObject[objectList.size()]);
      return objects;
   }
   
   public List<GameObject> getObjects() {
      return objectList;
   }
   
   /**
    * Adds to object to the list of objects the Object Manager will update automatically.
    * Any object added via this method will be drawn to the screen, for example.
    * @param newObject
    */
   public void addObject(GameObject newObject) {
      objectList.add(newObject);
   }
   
   public static void printNameOfObjectList(List<GameObject> list) {
	   for (GameObject object : list) {
		   System.out.print(object.getName()+", ");
	   }
	   System.out.println();
   }
   
   public void setExitBehaviors(GameObject.ExitBehavior behavior) {
	   for (GameObject object : objectList) {
		   object.setExitBehavior(behavior);
	   }
   }
}