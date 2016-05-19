import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class Genustaggaren {
	public static String text="";
	public static void main(String[] args) {
		//ArrayList<String> kallor = new ArrayList<String>(Arrays.asList("Aftonbladet", "DI", "DN", "Expressen", "GP", "Metro", "SVD"));
		ArrayList<String> kallor = new ArrayList<String>(Arrays.asList("Aftonbladet", "DIweb", "DN", "Expressenweb", "GP", "GPweb", "Metroweb", "SVD", "SVDweb"));
		String listType = "50";
		for(String kalla : kallor){
			System.out.println(kalla+"\n");
		//for(int i=1;i<11;i++){
		//	System.out.println(kalla+i+":\n");
		List<String> punktOrdLista = new ArrayList<String>();
		List<String> versalOrdLista = new ArrayList<String>();
		Map<String, List<String>> kontext = new HashMap<String, List<String>>();
		
		Counter counter = new Counter();
		Basfall basfall = new Basfall();
		Ordlista ordlista = new Ordlista(listType);
		NER ner = new NER();
		Scanner scanner = null;
		String gammalt_ord = "";
		try {
			//scanner = new Scanner(new File("/home/f/r/frcarls/KEX/Gold-Standard/"+kalla+"/"+kalla+i+"x.txt"));
			//scanner = new Scanner(new File("C:/Users/Fredrik/Dropbox/Egna mappen/KTH/KEX/Ubunto kex folder/KEX/Gold-Standard/"+kalla+"/"+kalla+i+"x.txt"));
			scanner = new Scanner(new File("C:/Users/Fredrik/Dropbox/Egna mappen/KTH/KEX/Ubunto kex folder/KEX/Artiklar/04.13/"+kalla+".txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		scanner.useDelimiter("==============================================================================\n\n");
		List<String> versalKombo= new ArrayList<String>();
		while (scanner.hasNext()){
			text=format(scanner.next());
			List<String> textLista= new ArrayList<String>(Arrays.asList(text.replaceAll("(\\r|\\n)", " ").split(" ")));
			for (String ord: textLista){
				ord = ord.replaceAll("[\"|()]", "");
				if (ord.equals("") | ord.equals("-")|ord.equals(" ")|ord.equals("■")){
					continue;
				}
				String[] sc1 = {".","!","?",":", ",", "/", ";"};
				if (stringContains(gammalt_ord, sc1) && isVersalOrd(ord) && !punktOrdLista.contains(ord)){
					punktOrdLista.add(ord.replaceAll("[.?!:/,;]", ""));
				}
				
				if (isVersalOrd(ord)&&!stringContains(gammalt_ord, sc1)){
					versalKombo.add(ord.replaceAll("[.?!:/,;]", ""));
				}
				else{
					String multipelVersaler="";
					for (int j=0;j<versalKombo.size();j++){
						if (j==versalKombo.size()-1){
							multipelVersaler+=versalKombo.get(j);
						}
						else{
							multipelVersaler+=versalKombo.get(j)+ " ";
						}
					}
					if (!multipelVersaler.equals("")){
						versalOrdLista.add(multipelVersaler);
						versalKombo.clear();
					}
					if (isVersalOrd(ord)){
						versalKombo.add(ord.replaceAll("[.?!:/,;]", ""));
					}
				}
				
				gammalt_ord=ord;
			}
			String multipelVersaler="";
			for (int j=0;j<versalKombo.size();j++){
				if (j==versalKombo.size()-1){
					multipelVersaler+=versalKombo.get(j);
				}
				else{
					multipelVersaler+=versalKombo.get(j)+ " ";
				}
			}
			if (!multipelVersaler.equals("")){
				versalOrdLista.add(multipelVersaler);
				versalKombo.clear();
			}
			
			Map<String, Integer> benamningar = basfall.basfall(text);
			ordlista.ordlista(versalOrdLista);
			ner.ner(versalOrdLista, punktOrdLista, ordlista, counter);
			kontext(ordlista, basfall);
			
			
			//Counters
			for(String person : ordlista.personLista.keySet()){
				String [] slash = {"/"};
				if (stringContains(person, slash)){
					String fornamn=person.split("/")[0];
					String efternamn=person.split("/")[1];
					if (ordlista.kvinnoMatchList.contains(fornamn)){
						counter.antal_kvinnor+= ordlista.personLista.get(fornamn+"/"+efternamn);
					}
					else if (ordlista.manMatchList.contains(fornamn)){
						counter.antal_man+= ordlista.personLista.get(fornamn+"/"+efternamn);
					}
					else if (ordlista.henMatchList.contains(fornamn)){
						counter.antal_hen+= ordlista.personLista.get(fornamn+"/"+efternamn);
					}
				}
				else{
					if (ordlista.kvinnoMatchList.contains(person)){
						counter.antal_kvinnor+= ordlista.personLista.get(person);
					}
					else if (ordlista.manMatchList.contains(person)){
						counter.antal_man+= ordlista.personLista.get(person);
					}
					else if (ordlista.henMatchList.contains(person)){
						counter.antal_hen+= ordlista.personLista.get(person);
					}
				}
			}
			counter.antal_kvinnor+= benamningar.get("kvinnor");
			counter.antal_man+= benamningar.get("man");
			counter.antal_hen+= benamningar.get("hen");
			
			//Clear lists
			ordlista.kvinnoMatchList.clear();
			ordlista.manMatchList.clear();
			ordlista.henMatchList.clear();
			ordlista.personLista.clear();
			versalOrdLista.clear();
			punktOrdLista.clear();
			ner.kvinnoOsakerLista.clear();
			ner.manOsakerLista.clear();
			ner.henOsakerLista.clear();
		} 
		scanner.close();
		
		//Prints
		System.out.println("Antalet kvinnliga könsspecifika pronomen:\t" + counter.getKvinna());
		System.out.println("Antalet manliga könsspecifika pronomen: \t" + counter.getMan());
		System.out.println("Antalet neutrala könsspecifika pronomen: \t" + counter.getHen());
		float sum = counter.getKvinna()+counter.getMan()+counter.getHen();
		DecimalFormat df = new DecimalFormat("#.#");
		System.out.println("Procent kvinnliga könsspecifika pronomen:\t" + df.format(100*counter.getKvinna()/sum)+" %");
		System.out.println("Procent manliga könsspecifika pronomen: \t" + df.format(100*counter.getMan()/sum)+" %");
		System.out.println("Procent neutrala könsspecifika pronomen: \t" + df.format(100*counter.getHen()/sum)+" %");
		System.out.println("\n");
		}
	}
	//}
	public static boolean isVersalOrd(String givenString){
        if(givenString == null | givenString.isEmpty()){
        	return false;
        }
        else{
        	return (Character.isUpperCase(givenString.codePointAt(0)));
        }
    }
	public static boolean stringContains(String inputString, String[] items){
	    for(int i =0; i < items.length; i++){
	        if(inputString.contains(items[i])){
	            return true;
	        }
	    }
	    return false;
	}
	public static String format(String artikel) {
		String regex = "Publicerat i print. \n\n|Publicerat på webb. \n\n";
		String text = artikel.split("------------------------------------------------------------------------------\n")[1];
		text= text.split(regex)[1];
		text = text.split("©")[0];
		return text;
	}
	public static void kontext(Ordlista ordlista, Basfall basfall){
		
		List<String> tempHen = new ArrayList<String>();
		List<String> meningar = new ArrayList<String>(Arrays.asList(text.split("\\.")));
		boolean forsta = true;
		for(String hen : ordlista.henMatchList){
			for (int i=0; i<meningar.size();i++){
				String mening = meningar.get(i);
				if (mening.contains(hen)){
					kolla_pronom(mening, meningar, i, hen, ordlista, basfall, tempHen, forsta);
				}
			}
		}
		for (String hen: tempHen){
			ordlista.henMatchList.remove(hen);
		}
	}
	public static void kolla_pronom(String mening, List<String> meningar, int i, String hen, Ordlista ordlista, Basfall basfall, List<String> tempHen, boolean forsta){
		int counter=0;
		for (String person: ordlista.personList){
			if (mening.contains(person)){
				counter+=1;
			}
		}
		if (counter>0 && forsta==false | (counter >1)){
			return;
		}else{
			forsta=false;
			Map<String, Integer> pronomen = basfall.basfall(mening);
			if (pronomen.get("kvinnor")>0){
				ordlista.kvinnoMatchList.add(hen);
				tempHen.add(hen);
			}else if (pronomen.get("man")>0){
				ordlista.manMatchList.add(hen);
				tempHen.add(hen);
			}else if (pronomen.get("hen")>0){
				return;
			}else{
				try{
					mening = meningar.get(i+1);
					kolla_pronom(mening, meningar, i+1, hen, ordlista, basfall, tempHen, forsta);
				}catch (IndexOutOfBoundsException e){
					return;
				}
			}
			
		}
	}
}
