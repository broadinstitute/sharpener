package apimodels;

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
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaPlayFrameworkCodegen", date = "2019-07-11T16:00:13.997Z")

@SuppressWarnings({"UnusedReturnValue", "WeakerAccess"})
public class GeneList   {
  @JsonProperty("gene_list_id")
  private String geneListId = null;

  @JsonProperty("genes")
  private List<GeneInfo> genes = null;

  public GeneList geneListId(String geneListId) {
    this.geneListId = geneListId;
    return this;
  }

   /**
   * Get geneListId
   * @return geneListId
  **/
    public String getGeneListId() {
    return geneListId;
  }

  public void setGeneListId(String geneListId) {
    this.geneListId = geneListId;
  }

  public GeneList genes(List<GeneInfo> genes) {
    this.genes = genes;
    return this;
  }

  public GeneList addGenesItem(GeneInfo genesItem) {
    if (genes == null) {
      genes = new ArrayList<>();
    }
    genes.add(genesItem);
    return this;
  }

   /**
   * Get genes
   * @return genes
  **/
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
        Objects.equals(genes, geneList.genes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(geneListId, genes);
  }

  @SuppressWarnings("StringBufferReplaceableByString")
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GeneList {\n");
    
    sb.append("    geneListId: ").append(toIndentedString(geneListId)).append("\n");
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

