package sharpener;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import apimodels.Attribute;
import apimodels.GeneInfo;
import apimodels.GeneInfoIdentifiers;
import play.Logger;

public class MyGene {

	private static final String myGeneInfoSearch = "https://mygene.info/v3/query?q=%s&species=9606";
	private static final String myGeneInfoQuery = "https://mygene.info/v3/gene/%s?fields=symbol,name,entrezgene,ensembl.gene,HGNC,MIM,alias";
	private static final String MY_GENE_INFO_ID = "myGene.info id";
	private static final String NCBI_GENE = "NCBIGene:";
	
	static class Info {

		private static ObjectMapper mapper = new ObjectMapper();
		
		private static HashMap<String, Gene> geneBySymbol = new HashMap<String, Gene>();
		private static HashMap<String, Gene> geneByAlias = new HashMap<String, Gene>();
		private static HashMap<String, Gene> geneByEntrez = new HashMap<String, Gene>();
		private static HashMap<String, Gene> geneByHGNC = new HashMap<String, Gene>();

		private static HashMap<String, Gene> geneByCURIE = new HashMap<String, Gene>();

		static {
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		}


		static GeneInfo addInfo(GeneInfo src) {
			String hgncId = null;
			String entrezGeneId = null;
			GeneInfoIdentifiers identifiers = src.getIdentifiers();
			if (identifiers != null) {
				if (identifiers.getMygeneInfo() != null && !identifiers.getMygeneInfo().equals("")) {
					return src;
				}
				hgncId = identifiers.getHgnc();
				entrezGeneId = identifiers.getEntrez();
			}
			for (Attribute attribute : src.getAttributes()) {
				if (MY_GENE_INFO_ID.equals(attribute.getName())) {
					identifiers.setMygeneInfo(attribute.getValue());
					return src;
				}
			}
			if (hgncId == null && src.getGeneId() != null && src.getGeneId().toUpperCase().startsWith("HGNC:")) {
				hgncId = src.getGeneId().toUpperCase();
			}
			if (hgncId != null) {
				try {
					Gene gene = geneByHGNC(hgncId);
					return gene.geneInfo(src);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (entrezGeneId != null) {
				try {
					Gene gene = geneByEntrez(entrezGeneId);
					return gene.geneInfo(src);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return src;
		}


		static GeneInfo querySymbol(String symbol) throws IOException {
			Gene gene = getGeneBySymbol(symbol);
			if (gene == null) {
				Search search = query(symbol);
				gene = geneBySymbol(symbol, search);
				if (gene == null) {
					gene = geneByAlias(symbol, search);
				}
			}
			if (gene == null) {
				return unknown(symbol);
			}
			return gene.geneInfo(new GeneInfo());
		}


		static GeneInfo unknown(String id) {
			return new GeneInfo().geneId(id)
					.addAttributesItem(new Attribute().name("name").value("unknown gene").source("sharpener"))
					.addAttributesItem(new Attribute().name("gene_symbol").value(id).source("user query"));
		}
		

		private static Search query(String id) throws IOException {
			URL url = new URL(String.format(myGeneInfoSearch, id));
			String json = HTTP.get(url);
			return mapper.readValue(json, Search.class);
		}


		private static Gene geneBySymbol(String symbol, Search searchResults) throws IOException {
			for (Hit hit : searchResults.getHits()) {
				if (symbol.equals(hit.getSymbol())) {
					return geneByEntrez(hit.getId());
				}
			}
			return null;
		}


		private static Gene geneByAlias(String symbol, Search searchResults) throws IOException {
			for (Hit hit : searchResults.getHits()) {
				Gene gene = geneByEntrez(hit.getId());
				if (gene.getAlias() != null) {
					for (String alias : gene.getAlias()) {
						if (alias.equals(symbol)) {
							return gene;
						}
					}
				}
			}
			return null;
		}


		private static Gene geneByHGNC(String hgncId) throws IOException {
			Gene gene = getGeneByHGNC(hgncId);
			if (gene != null) {
				return gene;
			}
			for (Hit hit : query(hgncId).getHits()) {
				gene = geneByEntrez(hit.getId());
				if (hgncId.equals(gene.getHGNC())) {
					save(gene);
					return gene;
				}
			}
			return null;
		}


		private static Gene geneByEntrez(String entrezGeneId) throws IOException {
			if (entrezGeneId.startsWith(NCBI_GENE)) {
				entrezGeneId = entrezGeneId.substring(NCBI_GENE.length());
			}
			Gene gene = getGeneByEntrez(entrezGeneId);
			if (gene == null) {
				URL url = new URL(String.format(myGeneInfoQuery, entrezGeneId));
				gene = mapper.readValue(HTTP.get(url), Gene.class);
				save(gene);
			}
			return gene;
		}


		private synchronized static void save(Gene gene) {
			if (gene.getSymbol() != null) {
				geneBySymbol.put(gene.getSymbol(), gene);
			}
			if (gene.getAlias() != null) {
				for (String alias : gene.getAlias()) {
					geneByAlias.put(alias, gene);
				}
			}
			if (gene.getEntrezgene() != null) {
				geneByEntrez.put(gene.getEntrezgene(), gene);
				geneByCURIE.put(NCBI_GENE + gene.getEntrezgene(), gene);
			}
			if (gene.getHGNC() != null) {
				geneByHGNC.put(gene.getHGNC(), gene);
				geneByCURIE.put(gene.getHGNC(), gene);
			}
		}


		private synchronized static Gene getGeneBySymbol(String symbol) {
			Gene gene = geneBySymbol.get(symbol);
			if (gene == null) {
				gene = geneByAlias.get(symbol);
			}
			return gene;
		}


		private synchronized static Gene getGeneByEntrez(String entrezgene) {
			return geneByEntrez.get(entrezgene);
		}


		private synchronized static Gene getGeneByHGNC(String hgncId) {
			return geneByHGNC.get(hgncId);
		}
	}

	
	static class Search {

		private Hit[] hits;

		public Hit[] getHits() {
			return hits;
		}

		public void setHits(Hit[] hits) {
			this.hits = hits;
		}

	}

	
	static class Hit {

		private String id;
		private String symbol;
		private String entrezgene;

		public String getId() {
			return id;
		}

		@JsonProperty("_id")
		public void setId(String id) {
			this.id = id;
		}

		public String getSymbol() {
			return symbol;
		}

		public void setSymbol(String symbol) {
			this.symbol = symbol;
		}

		public String getEntrezgene() {
			return entrezgene;
		}

		public void setEntrezgene(String entrezgene) {
			this.entrezgene = entrezgene;
		}
	}

	
	static class Gene {

		private String id;
		private String hgnc;
		private String mim;
		private Object alias;
		private String[] ensembl;
		private String entrezgene;
		private String name;
		private String symbol;

		public String getId() {
			return id;
		}

		@JsonProperty("_id")
		public void setId(String id) {
			this.id = id;
		}

		public String getHGNC() {
			return (hgnc == null) ? null : "HGNC:" + hgnc;
		}

		@JsonProperty("HGNC")
		public void setHGNC(String hGNC) {
			this.hgnc = hGNC;
		}

		public String getMIM() {
			return mim;
		}

		@JsonProperty("MIM")
		public void setMIM(String mIM) {
			this.mim = mIM;
		}

		@SuppressWarnings("unchecked")
		public String[] getAlias() {
			if (alias == null) {
				return null;
			}
			if (alias instanceof String)
				return new String[] { alias.toString() };
			if (alias instanceof ArrayList) {
				return ((ArrayList<String>) alias).toArray(new String[0]);
			}
			Logger.warn("did not convert alias " + alias.getClass().getName());
			return null;
		}

		public void setAlias(Object alias) {
			this.alias = alias;
		}

		public String[] getEnsembl() {
			return ensembl;
		}

		@SuppressWarnings("rawtypes")
		public void setEnsembl(Object ensembl) {
			if (ensembl instanceof Map) {
				String gene = getEnsemblGene((Map) ensembl);
				if (gene != null) {
					this.ensembl = new String[] { gene };
				}
			} else if (ensembl instanceof ArrayList) {
				ArrayList<String> genes = new ArrayList<String>();
				for (Object entry : (ArrayList) ensembl) {
					if (entry instanceof Map) {
						String gene = getEnsemblGene((Map) entry);
						if (gene != null) {
							genes.add(gene);
						}
					}
				}
				this.ensembl = genes.toArray(new String[genes.size()]);
			} else {
				Logger.warn("did not convert ensembl " + ensembl.getClass().getName());
			}
		}
		
		@SuppressWarnings("rawtypes")
		private String getEnsemblGene(Map map) {
			return map.get("gene").toString();
		}

		public String getEntrezgene() {
			return entrezgene;
		}

		public void setEntrezgene(String entrezgene) {
			this.entrezgene = entrezgene;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getSymbol() {
			return symbol;
		}

		public void setSymbol(String symbol) {
			this.symbol = symbol;
		}

		private void addIdentifiers(GeneInfoIdentifiers identifiers) {
			if (identifiers.getMygeneInfo() == null && getId() != null) {
				identifiers.setMygeneInfo(getId());
			}
			if (identifiers.getEntrez() == null && getEntrezgene() != null) {
				identifiers.setEntrez(NCBI_GENE + getEntrezgene());
			}
			if (identifiers.getHgnc() == null && getHGNC() != null) {
				identifiers.setHgnc(getHGNC());
			}
			if (identifiers.getMim() == null && getMIM() != null) {
				identifiers.setMim("MIM:" + getMIM());
			}
			if (identifiers.getEnsembl() == null && getEnsembl() != null && getEnsembl().length > 0) {
				ArrayList<String> ensembl = new ArrayList<>();
				for (String ensemblGeneId : getEnsembl()) {
					ensembl.add("ENSEMBL:" + ensemblGeneId);
				}
				identifiers.setEnsembl(ensembl);
			}
		}

		GeneInfo geneInfo(GeneInfo geneInfo) {
			if (getHGNC() != null) {
				geneInfo.setGeneId(getHGNC());
			} else if (getEntrezgene() != null) {
				geneInfo.setGeneId(NCBI_GENE + getEntrezgene());
			} else if (getEnsembl() != null && getEnsembl().length > 0) {
				geneInfo.setGeneId(getEnsembl()[0]);
			} else {
				geneInfo.setGeneId(getSymbol());
			}
			if (getId() != null) {
				geneInfo.addAttributesItem(new Attribute().name(MY_GENE_INFO_ID).value(getId()).source("myGene.info"));
			}
			if (getSymbol() != null) {
				geneInfo.addAttributesItem(
						new Attribute().name("gene_symbol").value(getSymbol()).source("myGene.info"));
			}
			if (getAlias() != null && getAlias().length > 0) {
				geneInfo.addAttributesItem(
						new Attribute().name("synonyms").value(String.join(";", getAlias())).source("myGene.info"));
			}
			if (getName() != null) {
				geneInfo.addAttributesItem(new Attribute().name("gene_name").value(getName()).source("myGene.info"));
			}
			if (geneInfo.getIdentifiers() == null) {
				geneInfo.setIdentifiers(new GeneInfoIdentifiers());
			}
			addIdentifiers(geneInfo.getIdentifiers());
			return geneInfo;
		}
	}


}
