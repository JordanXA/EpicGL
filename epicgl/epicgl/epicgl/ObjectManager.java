package epicgl;

import java.util.List;
import java.util.ArrayList;


public class ObjectManager {
   
   private List<Object> objectList = new ArrayList<Object>();
   
   public void update() {
	   processCollisions();
	   for(Object object : objectList) {
		   object.update();
		   if (object.getCollisions().size()>0) {
			   object.fillObject(new Vector3(0.95f,0f,0f));
		   }
		   else {
			   object.fillObject(new Vector3(0.5f,0.5f,0.5f));
		   }
		   object.resetCollisions();
	   }
	   
   }
   
   private void processCollisions() {
	   for(Object object : objectList) {
		   for(Object object2 : objectList) {
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
   
   public Object[] getObjectsArray() {
      Object[] objects = objectList.toArray(new Object[objectList.size()]);
      return objects;
   }
   
   public List<Object> getObjects() {
      return objectList;
   }
   
   /**
    * Adds to object to the list of objects the Object Manager will update automatically.
    * Any object added via this method will be drawn to the screen, for example.
    * @param newObject
    */
   public void addObject(Object newObject) {
      objectList.add(newObject);
   }
}