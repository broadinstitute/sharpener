package apimodels;

import apimodels.Attribute;
import apimodels.GeneInfo;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.*;
import java.util.Set;
import javax.validation.*;
import java.util.Objects;
import javax.validation.constraints.*;
/**
 * GeneList
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaPlayFrameworkCodegen", date = "2019-11-07T16:49:46.789Z")

@SuppressWarnings({"UnusedReturnValue", "WeakerAccess"})
public class GeneList   {
  @JsonProperty("gene_list_id")
  private String geneListId = null;

  @JsonProperty("source")
  private String source = null;

  @JsonProperty("attributes")
  private List<Attribute> attributes = null;

  @JsonProperty("genes")
  private List<GeneInfo> genes = new ArrayList<>();

  public GeneList geneListId(String geneListId) {
    this.geneListId = geneListId;
    return this;
  }

   /**
   * Id of the gene list.
   * @return geneListId
  **/
  @NotNull
  public String getGeneListId() {
    return geneListId;
  }

  public void setGeneListId(String geneListId) {
    this.geneListId = geneListId;
  }

  public GeneList source(String source) {
    this.source = source;
    return this;
  }

   /**
   * Transformer that produced the gene list.
   * @return source
  **/
  @NotNull
  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }

  public GeneList attributes(List<Attribute> attributes) {
    this.attributes = attributes;
    return this;
  }

  public GeneList addAttributesItem(Attribute attributesItem) {
    if (attributes == null) {
      attributes = new ArrayList<>();
    }
    attributes.add(attributesItem);
    return this;
  }

   /**
   * Additional information and provenance about the gene list.
   * @return attributes
  **/
  @Valid
  public List<Attribute> getAttributes() {
    return attributes;
  }

  public void setAttributes(List<Attribute> attributes) {
    this.attributes = attributes;
  }

  public GeneList genes(List<GeneInfo> genes) {
    this.genes = genes;
    return this;
  }

  public GeneList addGenesItem(GeneInfo genesItem) {
    genes.add(genesItem);
    return this;
  }

   /**
   * Members of the gene list.
   * @return genes
  **/
  @NotNull
@Valid
  public List<GeneInfo> getGenes() {
    return genes;
  }

  public void setGenes(List<GeneInfo> genes) {
    this.genes = genes;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GeneList geneList = (GeneList) o;
    return Objects.equals(geneListId, geneList.geneListId) &&
        Objects.equals(source, geneList.source) &&
        Objects.equals(attributes, geneList.attributes) &&
        Objects.equals(genes, geneList.genes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(geneListId, source, attributes, genes);
  }

  @SuppressWarnings("StringBufferReplaceableByString")
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GeneList {\n");
    
    sb.append("    geneListId: ").append(toIndentedString(geneListId)).append("\n");
    sb.append("    source: ").append(toIndentedString(source)).append("\n");
    sb.append("    attributes: ").append(toIndentedString(attributes)).append("\n");
    sb.append("    genes: ").append(toIndentedString(genes)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

