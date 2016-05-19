import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;


public class Basfall {
	private static List<String> kvinnoList;
	private static List<String> manList;
	private static ArrayList<String> henList;
	public Basfall() {
		kvinnoList = new ArrayList<String>(Arrays.asList("hon", "henne", "hennes"));
		manList = new ArrayList<String>(Arrays.asList("han", "honom", "hans"));
		henList = new ArrayList<String>(Arrays.asList("hen", "hens"));
	}
	public Map<String, Integer> basfall(String text){
		
		Map<String, Integer> benamningar = new HashMap<String, Integer>();
		benamningar.put("kvinnor", 0);
		benamningar.put("man", 0);
		benamningar.put("hen", 0);
		for (String ord:text.replaceAll("(\\r|\\n)", " ").split(" "))
			if(ord.equals("")){
				continue;
			}else{
				ord = ord.replaceAll("(\\r|\\n)", " ");
				ord = ord.replaceAll("[\\W&&[^Â‰ˆ≈ƒ÷-]]", "").toLowerCase();
				if (kvinnoList.contains(ord))
					benamningar.put("kvinnor", benamningar.get("kvinnor")+1);
				else if (manList.contains(ord))
					benamningar.put("man", benamningar.get("man")+1);
				else if (henList.contains(ord)){
					benamningar.put("hen", benamningar.get("hen")+1);
				}
				else
					;
				}
		return benamningar;
		}

	}