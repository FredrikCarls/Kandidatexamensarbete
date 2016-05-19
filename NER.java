import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class NER {
	List<String> kvinnoOsakerLista;
	List<String> manOsakerLista;
	List<String> henOsakerLista;

	public NER(){
		kvinnoOsakerLista = new ArrayList<String>();
		manOsakerLista = new ArrayList<String>();
		henOsakerLista = new ArrayList<String>();
	}
	
	public void ner(List<String> versalOrdLista, List<String> punktOrdLista, Ordlista ordlista, Counter counter) {
		for (String kvinna : ordlista.kvinnoMatchList){
			if (punktOrdLista.contains(kvinna)){
				kvinnoOsakerLista.add(kvinna);
			}
			else
				continue;
		}
		for (String kvinna : kvinnoOsakerLista){
			ordlista.kvinnoMatchList.remove(kvinna);
		}
		for (String man : ordlista.manMatchList){
			for (String punktOrd : punktOrdLista){
				if (man.equals(punktOrd)){
					manOsakerLista.add(man);
				}
				else
					continue;
			}
		}
		for (String man : manOsakerLista){
			ordlista.manMatchList.remove(man);
		}
		for (String hen : ordlista.henMatchList){
			for (String punktOrd : punktOrdLista){
				if (hen.equals(punktOrd)){
					henOsakerLista.add(hen);
				}
				else
					continue;
			}
		}
		for (String hen : henOsakerLista){
			ordlista.henMatchList.remove(hen);
		}
		
		Set<String> unikPersoner = new HashSet<String>(ordlista.personLista.keySet());
		for (String person: unikPersoner){
			String [] slash = {"/"};
			if (Genustaggaren.stringContains(person, slash)){
				String fornamn = person.split("/")[0];
				if (kvinnoOsakerLista.contains(fornamn)){
					ordlista.kvinnoMatchList.add(fornamn);
					kvinnoOsakerLista.remove(fornamn);
				}else if (manOsakerLista.contains(fornamn)){
					ordlista.manMatchList.add(fornamn);
					manOsakerLista.remove(fornamn);
				}else if(henOsakerLista.contains(fornamn)){
					ordlista.henMatchList.add(fornamn);
					henOsakerLista.remove(fornamn);
				}
			}
		}
		for(String person : kvinnoOsakerLista){
			ordlista.personLista.remove(person);
		}
		for(String person : manOsakerLista){
			ordlista.personLista.remove(person);
		}
		for(String person : manOsakerLista){
			ordlista.personLista.remove(person);
		}
		
		String [] sep = {"/"};
		for(String personer: ordlista.personLista.keySet()){
			String ickegenetivOrd="";
			if (Genustaggaren.stringContains(personer, sep)){
				String efternamn = personer.split("/")[1];
				if (efternamn.substring(efternamn.length()-1).equals("s")){
					ickegenetivOrd = efternamn.substring(0, efternamn.length()-1);
				}
				for (String versalOrd : versalOrdLista){
					List<String> versalOrdsplit = new ArrayList<String>(Arrays.asList(versalOrd.split(" ")));
					if (versalOrdsplit.size()==1 && (versalOrd.equals(efternamn)|versalOrd.equals(ickegenetivOrd))){
						ordlista.personLista.put(personer, ordlista.personLista.get(personer)+1);
					}
				}
			}
		}
	}


}