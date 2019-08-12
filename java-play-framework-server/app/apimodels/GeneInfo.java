package apimodels;

import apimodels.Attribute;
import apimodels.GeneInfoIdentifiers;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.*;
import java.util.Set;
import javax.validation.*;
import java.util.Objects;
import javax.validation.constraints.*;
/**
 * GeneInfo
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaPlayFrameworkCodegen", date = "2019-08-12T19:29:34.374Z")

@SuppressWarnings({"UnusedReturnValue", "WeakerAccess"})
public class GeneInfo   {
  @JsonProperty("gene_id")
  private String geneId = null;

  @JsonProperty("identifiers")
  private GeneInfoIdentifiers identifiers = null;

  @JsonProperty("attributes")
  private List<Attribute> attributes = null;

  public GeneInfo geneId(String geneId) {
    this.geneId = geneId;
    return this;
  }

   /**
   * Id of the gene. Preferably HGNC id; can be Entrez or ENSEMBL id if HGNC id is not available.
   * @return geneId
  **/
  @NotNull
  public String getGeneId() {
    return geneId;
  }

  public void setGeneId(String geneId) {
    this.geneId = geneId;
  }

  public GeneInfo identifiers(GeneInfoIdentifiers identifiers) {
    this.identifiers = identifiers;
    return this;
  }

   /**
   * Get identifiers
   * @return identifiers
  **/
  @Valid
  public GeneInfoIdentifiers getIdentifiers() {
    return identifiers;
  }

  public void setIdentifiers(GeneInfoIdentifiers identifiers) {
    this.identifiers = identifiers;
  }

  public GeneInfo attributes(List<Attribute> attributes) {
    this.attributes = attributes;
    return this;
  }

  public GeneInfo addAttributesItem(Attribute attributesItem) {
    if (attributes == null) {
      attributes = new ArrayList<>();
    }
    attributes.add(attributesItem);
    return this;
  }

   /**
   * Additional information about the gene and provenance about gene-list menbership. Sharpener will use myGene.info to add the following attributes to every gene: 'gene_symbol',  'entrez_gene_id', 'HGNC', 'MIM', 'ensembl_gene_id', 'synonyms', 'gene_name',  and 'myGene.info id'. Multiple synonyms and ensembl_gene_id are separated by semicolons.
   * @return attributes
  **/
  @Valid
  public List<Attribute> getAttributes() {
    return attributes;
  }

  public void setAttributes(List<Attribute> attributes) {
    this.attributes = attributes;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GeneInfo geneInfo = (GeneInfo) o;
    return Objects.equals(geneId, geneInfo.geneId) &&
        Objects.equals(identifiers, geneInfo.identifiers) &&
        Objects.equals(attributes, geneInfo.attributes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(geneId, identifiers, attributes);
  }

  @SuppressWarnings("StringBufferReplaceableByString")
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GeneInfo {\n");
    
    sb.append("    geneId: ").append(toIndentedString(geneId)).append("\n");
    sb.append("    identifiers: ").append(toIndentedString(identifiers)).append("\n");
    sb.append("    attributes: ").append(toIndentedString(attributes)).append("\n");
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

