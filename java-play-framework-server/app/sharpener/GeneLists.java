package sharpener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import apimodels.GeneInfo;
import apimodels.GeneList;
import scala.util.Random;

public class GeneLists {

	private static Map<String, GeneList> geneLists = new HashMap<String, GeneList>(); 
	
	public static GeneList createList(List<String> genes) {
		GeneList geneList = new GeneList();
		for (String symbol : genes)
			try {
				GeneInfo geneInfo = MyGene.Info.query(symbol);
				if (geneInfo != null) {
					geneList.addGenesItem(geneInfo);
				}
			}
			catch (Exception e) {
				geneList.addGenesItem(MyGene.Info.unknown(symbol));
			}
		save(geneList);
		return geneList;
	}
	
	private synchronized static void save(GeneList geneList) {
		String id = nextId();
		geneList.setGeneListId(id);
		geneLists.put(id, geneList);
	}
	
	private static Random rand = new Random();
	
	private static String randString(int len) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < len; i++) {
			sb.append(rand.scala$util$Random$$nextAlphaNum$1());
		}
		return sb.toString();
	}

	private synchronized static String nextId() {
		String id = randString(10);
		while (geneLists.containsKey(id)) {
			id = randString(10);
		}
		return id;
	}
	
}
