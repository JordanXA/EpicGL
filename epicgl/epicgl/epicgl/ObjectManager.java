package epicgl;

import java.util.List;
import java.util.ArrayList;


public class ObjectManager {
   
   private List<GameObject> objectList = new ArrayList<GameObject>();
   
   public void update() {
	   processCollisions();
	   for(GameObject object : objectList) {
		   object.tick();
	   }
	   
   }
   
   private void processCollisions() {
	   for(GameObject object : objectList) {
		   object.resetCollisions();
		   for(GameObject object2 : objectList) {
			   //makes sure not to check object for colliding against itself
			   if(object==object2) {continue;}
			   else {
				   if (Physics.CollisionTest(object, object2)) {
					   object.addCollidingObj(object2);
					   //TODO: make this not check twice per pair, because atm it does that
					   //TODO: after that is done, it will need to add object 1 to object 2's colliding list, presumably
				   }
			   }
			   //cnt:;
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
}