//this is a utility class to read jason file - PurchaseOrder.jason

package abhatestlabs.data;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/*
public class DataReader {
	
	//creating a method to get jason data
	public void getJasonDataToMap() throws IOException {
		//1. READING JSON FILE TO A STRING
		//FileUtils has a method readFileToString() that reads json file to string
		String jsonData = FileUtils.readFileToString(new File(System.getProperty("user.dir") + "//src//test//java//abhatestlabs//data//PurchaseOrder.jason"));
		
		//2. CONVERTING JSON STRING OUTPUT TO HASH MAP
		//we can achieve this using Jackson DataBind dependency (from maven repository)
		/*
		 -- ObjectMapper class will help us to convert the string output to hashmap
		 -- ObjectMapper class has readvalue() method called  which can read the string and convert that to hash map
		 -- readValue() method takes two arguments - the input stream (string here) that we would like it to read (here jsonData)
		                                           - in the second argument we should tell hit the format it should use to produce the output 
		 
		 -- here we are clearly telling the readValue() method to go ahead and create two hash maps & put them in one list.
		 -- So when it creates two hash maps, it creates a list and puts those two hash maps in it and returns it.
		 
		 -- So we are asking to create hash maps based upon number of indexes we have in the JSON file and return a List of has maps.
		 */
	/*
		ObjectMapper mapper = new ObjectMapper();
		
		//the output data is a List with two arguments, first argument has one hash map and second has another
		//List<HashMap<String, String>> data = mapper.readValue(jsonData, new TypeReference<List<HashMap<String, String>>);
		List<HashMap<String, String>> data = mapper.readValue(jsonData, TypeReference<List<HashMap<String, String>>>(data-> {
		}));
		
			return data;
			
		}
	}
}
*/
