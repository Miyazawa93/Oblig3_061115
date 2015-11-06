package Assignment2;
import static org.junit.Assert.*;
import java.util.HashSet;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;

public class MyHashMapTest<K, V> {
	
	MyHashMap< String, Integer > classMap;

	@Before
	public void setUpClass(){
		
		classMap = new MyHashMap<>();
		
		classMap.put("Ingeborg", 23); 
		classMap.put("Kristine", 20); 
		classMap.put("Thomas", 44); 
		classMap.put("Jim", 23); 
		classMap.put("Adrian", 22); 
		classMap.put("Magnus", 15); 
		classMap.put("Vemund", 22); 
		classMap.put("Mathias", 22); 
		classMap.put("Mathias", 50); 
		classMap.put("Mats", 23); 
		classMap.put("Kristin", 22); 
	}
	
	@Test
	public void isEmpty_assertFalse(){
		assertFalse(classMap.isEmpty()); 
	}
	
	@Test
	public void get_getClassMates() {
		assertEquals("44", classMap.get("Thomas").toString()); 
		assertEquals("22", classMap.get("Vemund").toString()); 
		assertEquals("22", classMap.get("Adrian").toString()); 

	}
	
	@Test
	public void put_getAdded(){
		classMap.put("Knut", 24); 
		assertEquals("24", classMap.get("Knut").toString());
	}
	@Test
	public void getAll_twoPersonsWithSameName(){
		Set<Integer> set = new HashSet<>();
		set.add(50); 
		set.add(22); 
		assertEquals(set, classMap.getAll("Mathias")); 	
	}
	@Test
	public void getAll_removeOneDuplicateName(){
		Set<Integer> set = new HashSet<>(); 
		set.add(50); 
		classMap.remove("Mathias");
		assertEquals(set, classMap.getAll("Mathias")); 
	}
	@Test
	public void getAll_addMultipleAtSameValue(){
		classMap.put("Kari", 1); 
		classMap.put("Kari", 12); 
		classMap.put("Kari", 14); 
		classMap.put("Kari", 15); 
		assertEquals("[1, 12, 14, 15]", classMap.getAll("Kari").toString());  
	}
}