import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;

public class Ordlista {
	List<String> kvinnoList;
	List<String> manList;
	List<String> henList;
	List<String> personList;
	Hashtable<String, Integer> personLista;
	List<String> kvinnoMatchList;
	List<String> manMatchList;
	List<String> henMatchList;
	public Ordlista(String listType) {
		kvinnoList = new ArrayList<String>();
		manList = new ArrayList<String>();
		henList = new ArrayList<String>();
		personList = new ArrayList<String>();
		personLista = new Hashtable<String, Integer>();
		kvinnoMatchList = new ArrayList<String>();
		manMatchList = new ArrayList<String>();
		henMatchList = new ArrayList<String>();
		File kvinnofil = new File("C:/Users/Fredrik/Dropbox/Egna mappen/KTH/KEX/Ubunto kex folder/KEX/Listor/Kvinnor"+listType+".txt");
		File manfil = new File("C:/Users/Fredrik/Dropbox/Egna mappen/KTH/KEX/Ubunto kex folder/KEX/Listor/Man"+listType+".txt");
		Scanner scan;
		try {
			scan = new Scanner(kvinnofil);
			while (scan.hasNextLine()){
				kvinnoList.add(scan.nextLine());
			}
			scan.close();
			scan = new Scanner(manfil);
			while (scan.hasNextLine()){
				manList.add(scan.nextLine());
			}
			scan.close();
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		for (String man: manList){
			for (String kvinna : kvinnoList){
				if (man.equals(kvinna)){
					henList.add(man);
				}
			}
		}
		for (String hen : henList){
			manList.remove(hen);
			kvinnoList.remove(hen);
			personList.add(hen);
		}
		for (String kvinna : kvinnoList){
			personList.add(kvinna);
		}
		for (String man : manList){
			personList.add(man);
		}
		System.out.println(henList);
	}
	
	public void ordlista(List<String> versalOrdLista){
		for (String versalOrd : versalOrdLista){
			List<String> versalOrdsplit = new ArrayList<String>(Arrays.asList(versalOrd.split(" ")));
			String ickegenetivOrd = null;
			if (versalOrdsplit.size()==3){
				String fornamn = versalOrdsplit.get(0)+" "+versalOrdsplit.get(1);
				String efternamn = versalOrdsplit.get(2);
				if (fornamn.substring(fornamn.length()-1).equals("s")){
					ickegenetivOrd = fornamn.substring(0, fornamn.length()-1);
				}
				if (personList.contains(fornamn)){
					if (personLista.containsKey(fornamn+"/"+efternamn)){
						personLista.put(fornamn+"/"+efternamn, personLista.get(fornamn+"/"+efternamn)+1);
					}
					else{
						personLista.put(fornamn+"/"+efternamn, 1);
					}
					continue;
				}
				else if (personList.contains(ickegenetivOrd)){
					if (personLista.containsKey(ickegenetivOrd+"/"+efternamn)){
						personLista.put(ickegenetivOrd+"/"+efternamn, personLista.get(ickegenetivOrd+"/"+efternamn)+1);
					}else{
						personLista.put(ickegenetivOrd+"/"+efternamn, 1);
					}
					continue;
				}
				fornamn = versalOrdsplit.get(0);
				if (fornamn.substring(fornamn.length()-1).equals("s")){
					ickegenetivOrd = fornamn.substring(0, fornamn.length()-1);
				}
				if(personList.contains(fornamn)){
					if (personLista.containsKey(fornamn+"/"+efternamn)){
						personLista.put(fornamn+"/"+efternamn, personLista.get(fornamn+"/"+efternamn)+1);
					}else{
						personLista.put(fornamn+"/"+efternamn, 1);
					}
					continue;
				}
				else if (personList.contains(ickegenetivOrd)){
					if (personLista.containsKey(ickegenetivOrd+"/"+efternamn)){
						personLista.put(ickegenetivOrd+"/"+efternamn, personLista.get(ickegenetivOrd+"/"+efternamn)+1);
					}else{
						personLista.put(ickegenetivOrd+"/"+efternamn, 1);
					}
					continue;
				}
				fornamn = versalOrdsplit.get(1);
				if (fornamn.substring(fornamn.length()-1).equals("s")){
					ickegenetivOrd = fornamn.substring(0, fornamn.length()-1);
				}
				if(personList.contains(fornamn)){
					if (personLista.containsKey(fornamn+"/"+efternamn)){
						personLista.put(fornamn+"/"+efternamn, personLista.get(fornamn+"/"+efternamn)+1);
					}else{
						personLista.put(fornamn+"/"+efternamn, 1);
					}
					continue;
				}
				else if (personList.contains(ickegenetivOrd)){
					if (personLista.containsKey(ickegenetivOrd+"/"+efternamn)){
						personLista.put(ickegenetivOrd+"/"+efternamn, personLista.get(ickegenetivOrd+"/"+efternamn)+1);
					}else{
						personLista.put(ickegenetivOrd+"/"+efternamn, 1);
					}
					continue;
				}
			}
			else if (versalOrdsplit.size()==2){
				String fornamn = versalOrdsplit.get(0)+" "+versalOrdsplit.get(1);
				if (fornamn.substring(fornamn.length()-1).equals("s")){
					ickegenetivOrd = fornamn.substring(0, fornamn.length()-1);
				}
				if (personList.contains(fornamn)){
					if (personLista.containsKey(fornamn)){
						personLista.put(fornamn, personLista.get(fornamn)+1);
					}else{
						personLista.put(fornamn, 1);
					}
					continue;
				}
				else if (personList.contains(ickegenetivOrd)){
					if (personLista.containsKey(ickegenetivOrd)){
						personLista.put(ickegenetivOrd, personLista.get(ickegenetivOrd)+1);
					}else{
						personLista.put(ickegenetivOrd, 1);
					}
					continue;
				}
				fornamn = versalOrdsplit.get(0);
				String efternamn = versalOrdsplit.get(1);
				if (fornamn.substring(fornamn.length()-1).equals("s")){
						ickegenetivOrd = fornamn.substring(0, fornamn.length()-1);
				}
				if(personList.contains(fornamn)){
					if (personLista.containsKey(fornamn+"/"+efternamn)){
						personLista.put(fornamn+"/"+efternamn, personLista.get(fornamn+"/"+efternamn)+1);
					}
					else{
						personLista.put(fornamn+"/"+efternamn, 1);
					}
					continue;
				}
				else if (personList.contains(ickegenetivOrd)){
					if (personLista.containsKey(ickegenetivOrd+"/"+efternamn)){
						personLista.put(ickegenetivOrd+"/"+efternamn, personLista.get(ickegenetivOrd+"/"+efternamn)+1);
					}else{
						personLista.put(ickegenetivOrd+"/"+efternamn, 1);
					}
					continue;
				}
				fornamn = versalOrdsplit.get(1);
				if (fornamn.substring(fornamn.length()-1).equals("s")){
					ickegenetivOrd = fornamn.substring(0, fornamn.length()-1);
				}
				if(personList.contains(fornamn)){
					if (personLista.containsKey(fornamn)){
						personLista.put(fornamn, personLista.get(fornamn)+1);
					}
					else{
						personLista.put(fornamn, 1);
					}
					continue;
				}
				else if (personList.contains(ickegenetivOrd)){
					if (personLista.containsKey(ickegenetivOrd)){
						personLista.put(ickegenetivOrd, personLista.get(ickegenetivOrd)+1);
					}else{
						personLista.put(ickegenetivOrd, 1);
					}
					continue;
				}
			}else{
				String fornamn = versalOrdsplit.get(0);
				if (fornamn.substring(fornamn.length()-1).equals("s")){
					ickegenetivOrd = fornamn.substring(0, fornamn.length()-1);
				}
				if(personList.contains(fornamn)){
					if (personLista.containsKey(fornamn)){
						personLista.put(fornamn, personLista.get(fornamn)+1);
					}else{
						personLista.put(fornamn, 1);
					}
				}
				else if (personList.contains(ickegenetivOrd)){
					if (personLista.containsKey(ickegenetivOrd)){
						personLista.put(ickegenetivOrd, personLista.get(ickegenetivOrd)+1);
					}else{
						personLista.put(ickegenetivOrd, 1);
					}
				}
				else{
					continue;
				}
			}
		}
		
		
		for (String person : personLista.keySet()){
			person=person.split("/")[0];
			if (kvinnoList.contains(person)&&!kvinnoMatchList.contains(person)){
				kvinnoMatchList.add(person);
				}
			else if (manList.contains(person)&&!manMatchList.contains(person)){
				manMatchList.add(person);
				}
			else if (henList.contains(person)&&!henMatchList.contains(person)){
				henMatchList.add(person);
				}
			else
				;
			}
		
		}
	}