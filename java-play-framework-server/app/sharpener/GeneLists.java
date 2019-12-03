package sharpener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import apimodels.AggregationQuery;
import apimodels.Attribute;
import apimodels.GeneInfo;
import apimodels.GeneList;


public class GeneLists {

	private static final ArrayList<Attribute> NO_ATTRIBUTES = new ArrayList<Attribute>();

	private static TimeOrderedMap<String,GeneList> geneLists = new TimeOrderedMap<String,GeneList>(14 * 24 * 60 * 60 * 1000/* two weeks */);

	private static IdGenerator idGenerator = new IdGenerator(10, geneLists);

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
		geneList.source("user input").attributes(NO_ATTRIBUTES);
		for (String symbol : genes)
			try {
				GeneInfo geneInfo = MyGene.Info.querySymbol(symbol);
				if (geneInfo != null) {
					geneInfo.setSource("user input");
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
		List<String> geneListIds = query.getGeneListIds();
		String operation = query.getOperation();
		if (geneListIds == null || geneListIds.size() == 0) {
			return error("empty gene-list collection", "Gene-list "+operation);
		}
		for (String geneListId : geneListIds) {
			if (findGeneList(geneListId) == null) {
				return error("gene list " + geneListId + " not found", "Gene-list "+operation);
			}
		}		
		
		if (operation.equals("union") || operation.equals("Gene-list union")) {
			return union(geneListIds);
		} else if (operation.equals("intersection") || operation.equals("Gene-list intersection")) {
			return intersection(geneListIds);
		} else if (operation.equals("difference") || operation.equals("Gene-list difference")) {
			return difference(geneListIds);
		} else if (operation.equals("symmetric difference") || operation.equals("Gene-list symmetric difference (XOR)")) {
			return symDifference(geneListIds);
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
		HashMap<String,GeneInfo> genes = new HashMap<String,GeneInfo>();
		GeneList geneList = new GeneList().source("Gene-list union").attributes(NO_ATTRIBUTES);
		for (String geneListId : geneListIds) {
			GeneList source = findGeneList(geneListId);
			for (GeneInfo gene : source.getGenes()) {
				String geneId = gene.getGeneId();
				if (!genes.containsKey(geneId)) {
					GeneInfo unionGene = new GeneInfo().geneId(geneId);
					unionGene.setIdentifiers(gene.getIdentifiers());
					unionGene.setSource(source.getSource());
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
		GeneList source = findGeneList(geneListIds.get(0));
		HashMap<String,GeneInfo> intersection = new HashMap<String,GeneInfo>();
		for (GeneInfo gene : source.getGenes()) {
			GeneInfo geneInfo = new GeneInfo();
			geneInfo.geneId(gene.getGeneId()).identifiers(gene.getIdentifiers()).source(source.getSource());
			intersection.put(gene.getGeneId(), geneInfo);
		}
		for (String geneListId : geneListIds) {
			source = findGeneList(geneListId);
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
		GeneList geneList = new GeneList().source("Gene-list intersection").attributes(NO_ATTRIBUTES);
		for (GeneInfo gene : intersection.values()) {
			geneList.addGenesItem(gene);
		}
		save(geneList);
		return geneList;
	}


	private static GeneList difference(List<String> geneListIds) {
		HashSet<String> removeGenes = new HashSet<String>();
		String first = null;
		for (String geneListId : geneListIds) {
			GeneList source = findGeneList(geneListId);
			if (first == null) {
				first = geneListId;
			} 
			else {
				for (GeneInfo gene : source.getGenes()) {
					removeGenes.add(gene.getGeneId());
				}
			}
		}
		GeneList geneList = new GeneList().source("Gene-list difference").attributes(NO_ATTRIBUTES);
		for (GeneInfo gene : findGeneList(first).getGenes()) {
			if (!removeGenes.contains(gene.getGeneId())) {
				geneList.addGenesItem(gene);
			}
		}
		save(geneList);
		return geneList;
	}
	

	private static GeneList symDifference(List<String> geneListIds) {
		HashSet<String> intersection = new HashSet<String>();
		for (GeneInfo gene : intersection(geneListIds).getGenes()) {
			intersection.add(gene.getGeneId());
		}
		GeneList geneList = new GeneList().source("Gene-list symmetric difference").attributes(NO_ATTRIBUTES);
		for (GeneInfo gene : union(geneListIds).getGenes()) {
			if (!intersection.contains(gene.getGeneId())) {
				geneList.addGenesItem(gene);
			}
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
		String id = idGenerator.nextId();
		geneList.setGeneListId(id);
		if (geneList.getAttributes() == null) {
			geneList.setAttributes(NO_ATTRIBUTES);
		}
		geneLists.put(id, geneList);
	}


	synchronized static GeneList findGeneList(String geneListId) {
		return geneLists.get(geneListId);
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
