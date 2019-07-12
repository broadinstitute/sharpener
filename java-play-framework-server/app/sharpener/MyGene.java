package sharpener;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import apimodels.Attribute;
import apimodels.GeneInfo;

public class MyGene {

	private static final String myGeneInfoSearch = "https://mygene.info/v3/query?q=%s&species=9606";
	private static final String myGeneInfoQuery = "https://mygene.info/v3/gene/%s?fields=symbol,name,entrezgene,ensembl.gene,HGNC,MIM,alias";

	static class Info {

		private static ObjectMapper mapper = new ObjectMapper();

		static {
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		}

		static GeneInfo query(String id) throws IOException {
			URL url = new URL(String.format(myGeneInfoSearch, id));
			System.out.println(url);
			String json = HTTP.get(url);
			Search search = mapper.readValue(json, Search.class);
			Gene gene = geneBySymbol(id, search);
			if (gene == null) {
				gene = geneByAlias(id, search);
			}
			if (gene == null) {
				return unknown(id);
			}
			return Gene.geneInfo(gene);
		}
		
		static GeneInfo unknown(String id) {
			return new GeneInfo().geneId(id)
					.addAttributesItem(new Attribute().name("name").value("unknown gene").source("sharpener"))
					.addAttributesItem(new Attribute().name("gene_symbol").value(id).source("user query"));
		}

		private static Gene geneBySymbol(String symbol, Search searchResults) throws IOException {
			for (Hit hit : searchResults.getHits()) {
				if (symbol.equals(hit.getSymbol())) {
					return gene(hit.getId());
				}
			}
			return null;
		}

		private static Gene geneByAlias(String symbol, Search searchResults) throws IOException {
			for (Hit hit : searchResults.getHits()) {
				Gene gene = gene(hit.getId());
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

		private static Gene gene(String entrezgene) throws IOException {
			URL url = new URL(String.format(myGeneInfoQuery, entrezgene));
			System.out.println(url);
			Gene gene = mapper.readValue(HTTP.get(url), Gene.class);
			System.out.println(gene);
			return gene;
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
		
		private String HGNC;
		private String MIM;
		private Object alias;
		private Ensembl ensembl;
		private String entrezgene;
		private String name;
		private String symbol;

		public String getHGNC() {
			return HGNC;
		}

		@JsonProperty("HGNC")
		public void setHGNC(String hGNC) {
			HGNC = hGNC;
		}

		public String getMIM() {
			return MIM;
		}

		@JsonProperty("MIM")
		public void setMIM(String mIM) {
			MIM = mIM;
		}

		public String[] getAlias() {
			if (alias == null) {
				return null;
			}
			if (alias instanceof String)
			return new String[] {alias.toString()};
			if (alias instanceof ArrayList) {
				return ((ArrayList<String>)alias).toArray(new String[0]);
			}
			System.out.println("Warning: did not convert alias "+alias.getClass().getName());
			return null;
		}

		public void setAlias(Object alias) {
			this.alias = alias;
		}

		public Ensembl getEnsembl() {
			return ensembl;
		}

		public void setEnsembl(Ensembl ensembl) {
			this.ensembl = ensembl;
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

		static GeneInfo geneInfo(Gene gene) {
			if (gene == null) {
				return null;
			}
			GeneInfo geneInfo = new GeneInfo();
			if (gene.getHGNC() != null) {
				geneInfo.setGeneId("HGNC:"+gene.getHGNC());
			} else if (gene.getEntrezgene() != null) {
				geneInfo.setGeneId("NCBIgene:"+gene.getEntrezgene());
			} else if (gene.getEnsembl() != null && gene.getEnsembl().getGene() != null) {
				geneInfo.setGeneId(gene.getEnsembl().getGene());
			} else {
				geneInfo.setGeneId(gene.getSymbol());
			}
			if (gene.getSymbol() != null) {
				geneInfo.addAttributesItem(new Attribute().name("gene_symbol").value(gene.getSymbol()).source("myGene.info"));
			}
			if (gene.getEntrezgene() != null) {
				geneInfo.addAttributesItem(new Attribute().name("entrez_gene_id").value(gene.getEntrezgene()).source("myGene.info"));
			}
			if (gene.getHGNC() != null) {
				geneInfo.addAttributesItem(new Attribute().name("HGNC").value("HGNC:"+gene.getHGNC()).source("myGene.info"));
			}
			if (gene.getMIM() != null) {
				geneInfo.addAttributesItem(new Attribute().name("MIM").value("MIM:"+gene.getMIM()).source("myGene.info"));
			}
			if (gene.getAlias() != null && gene.getAlias().length > 0) {
				geneInfo.addAttributesItem(new Attribute().name("synonyms").value(String.join(";", gene.getAlias())).source("myGene.info"));
			}		
			if (gene.getEnsembl() != null && gene.getEnsembl().getGene() != null) {
				geneInfo.addAttributesItem(new Attribute().name("ensembl_gene_id").value(gene.getEnsembl().getGene()).source("myGene.info"));
			}
			if (gene.getName() != null) {
				geneInfo.addAttributesItem(new Attribute().name("gene_name").value(gene.getName()).source("myGene.info"));
			}
			return geneInfo;
		}
	}

	static class Ensembl {
		private String gene;

		public String getGene() {
			return gene;
		}

		public void setGene(String gene) {
			this.gene = gene;
		}
	}

}
