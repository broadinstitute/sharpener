package sharpener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import apimodels.AggregationQuery;
import apimodels.Attribute;
import apimodels.GeneInfo;
import apimodels.GeneList;
import scala.util.Random;

/**
 * @author vdancik
 *
 */
public class GeneLists {

	private static TimeOrderedMap<String,GeneList> geneLists = new TimeOrderedMap<String,GeneList>(14 * 24 * 60 * 60 * 1000/* two weeks */);


	public static GeneList createList(GeneInfo[] genes) {
		GeneList geneList = new GeneList();
		for (GeneInfo gene : genes) {
			geneList.addGenesItem(gene);
		}
		save(geneList);
		return geneList;
	}


	/**
	 * Implements /create_gene_list API endpoint.
	 * 
	 * @param genes 
	 * @return
	 */
	public static GeneList createList(List<String> genes) {
		GeneList geneList = new GeneList();
		geneList.source("user input").attributes(new ArrayList<Attribute>());
		for (String symbol : genes)
			try {
				GeneInfo geneInfo = MyGene.Info.querySymbol(symbol);
				if (geneInfo != null) {
					geneInfo.addAttributesItem(new Attribute().name("source").value("user input").source("user input"));
					geneList.addGenesItem(geneInfo);
				}
			} catch (Exception e) {
				geneList.addGenesItem(MyGene.Info.unknown(symbol));
			}
		save(geneList);
		return geneList;
	}


	/**
	 * Implements /aggregate API endpoint.
	 * 
	 * @param query aggregate query
	 * @return Aggregated gene list
	 */
	public static GeneList aggregate(AggregationQuery query) {
		if (query.getOperation().equals("union")) {
			return union(query.getGeneListIds());
		} else if (query.getOperation().equals("intersection")) {
			return intersection(query.getGeneListIds());
		} else {
			return error("unknown aggregation operation", "/aggregate");
		}
	}


	/**
	 * Implements /gene_list endpoint
	 * 
	 * @param geneListId
	 * @return Gene list with a given id
	 */
	public static GeneList getGeneList(String geneListId) {
		GeneList geneList = findGeneList(geneListId);
		if (geneList != null) {
			return geneList;
		}
		return error("gene list " + geneListId + " not found", "/gene_list");
	}


	private static GeneList union(List<String> geneListIds) {
		if (geneListIds == null || geneListIds.size() == 0) {
			return error("empty gene-list collection", "Gene-list union");
		}
		HashMap<String,GeneInfo> genes = new HashMap<String,GeneInfo>();
		GeneList geneList = new GeneList().source("Gene-list union");
		for (String geneListId : geneListIds) {
			GeneList source = findGeneList(geneListId);
			if (source == null) {
				return error("gene list " + geneListId + " not found", "Gene-list union");
			}
			for (GeneInfo gene : source.getGenes()) {
				String geneId = gene.getGeneId();
				if (!genes.containsKey(geneId)) {
					GeneInfo unionGene = new GeneInfo().geneId(geneId);
					geneList.addGenesItem(unionGene);
					genes.put(geneId, unionGene);
				}
				mergeAttributes(genes.get(geneId), gene);
			}
		}
		save(geneList);
		return geneList;
	}


	private static GeneList intersection(List<String> geneListIds) {
		if (geneListIds == null || geneListIds.size() == 0) {
			return error("empty gene-list collection", "Gene-list intersection");
		}
		GeneList source = findGeneList(geneListIds.get(0));
		if (source == null) {
			return error("gene list " + geneListIds.get(0) + " not found", "Gene-list intersection");
		}
		HashMap<String,GeneInfo> intersection = new HashMap<String,GeneInfo>();
		for (GeneInfo gene : source.getGenes()) {
			intersection.put(gene.getGeneId(), new GeneInfo().geneId(gene.getGeneId()));
		}
		for (String geneListId : geneListIds) {
			source = findGeneList(geneListId);
			if (source == null) {
				return error("gene list " + geneListId + " not found", "Gene-list intersection");
			}
			HashMap<String,GeneInfo> newIntersection = new HashMap<String,GeneInfo>();
			for (GeneInfo gene : source.getGenes()) {
				String geneId = gene.getGeneId();
				if (intersection.containsKey(geneId)) {
					newIntersection.put(geneId, intersection.get(geneId));
					mergeAttributes(newIntersection.get(geneId), gene);
				}
			}
			intersection = newIntersection;
		}
		GeneList geneList = new GeneList().source("Gene-list intersection");
		for (GeneInfo gene : intersection.values()) {
			geneList.addGenesItem(gene);
		}
		save(geneList);
		return geneList;
	}


	private static void mergeAttributes(GeneInfo gene, GeneInfo src) {
		HashMap<String,HashMap<String,String>> attributes = new HashMap<String,HashMap<String,String>>();
		if (gene.getAttributes() != null && gene.getAttributes().size() > 0) {
			for (Attribute attribute : gene.getAttributes()) {
				if (!attributes.containsKey(attribute.getSource())) {
					attributes.put(attribute.getSource(), new HashMap<String,String>());
				}
				attributes.get(attribute.getSource()).put(attribute.getName(), attribute.getValue());
			}
		}
		for (Attribute attribute : src.getAttributes()) {
			if (!attributes.containsKey(attribute.getSource()) 
					|| !attributes.get(attribute.getSource()).containsKey(attribute.getName())) {
				gene.addAttributesItem(attribute);
			}
		}
	}


	private synchronized static void save(GeneList geneList) {
		String id = nextId();
		geneList.setGeneListId(id);
		geneLists.put(id, geneList);
	}


	synchronized static GeneList findGeneList(String geneListId) {
		return geneLists.get(geneListId);
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


	/**
	 * Generate error message instead of a GeneList.
	 * 
	 * @param message error message
	 * @return instance of GeneList containing no genes and the error message as an id
	 */
	static GeneList error(String message, String source) {
		GeneList errorList = new GeneList();
		errorList.geneListId("Error");
		errorList.source(source);
		errorList.addAttributesItem(new Attribute().name("error").source(source).value(message));
		return errorList;
	}
}
