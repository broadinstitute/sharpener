package apimodels;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.*;
import java.util.Set;
import javax.validation.*;
import java.util.Objects;
import javax.validation.constraints.*;
/**
 * GeneInfoIdentifiers
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaPlayFrameworkCodegen", date = "2019-08-26T20:10:20.079Z")

@SuppressWarnings({"UnusedReturnValue", "WeakerAccess"})
public class GeneInfoIdentifiers   {
  @JsonProperty("entrez")
  private String entrez = null;

  @JsonProperty("hgnc")
  private String hgnc = null;

  @JsonProperty("mim")
  private String mim = null;

  @JsonProperty("ensembl")
  private List<String> ensembl = null;

  public GeneInfoIdentifiers entrez(String entrez) {
    this.entrez = entrez;
    return this;
  }

   /**
   * Entrez gene id (CURIE).
   * @return entrez
  **/
    public String getEntrez() {
    return entrez;
  }

  public void setEntrez(String entrez) {
    this.entrez = entrez;
  }

  public GeneInfoIdentifiers hgnc(String hgnc) {
    this.hgnc = hgnc;
    return this;
  }

   /**
   * HGNC gene id (CURIE).
   * @return hgnc
  **/
    public String getHgnc() {
    return hgnc;
  }

  public void setHgnc(String hgnc) {
    this.hgnc = hgnc;
  }

  public GeneInfoIdentifiers mim(String mim) {
    this.mim = mim;
    return this;
  }

   /**
   * OMIM gene id (CURIE).
   * @return mim
  **/
    public String getMim() {
    return mim;
  }

  public void setMim(String mim) {
    this.mim = mim;
  }

  public GeneInfoIdentifiers ensembl(List<String> ensembl) {
    this.ensembl = ensembl;
    return this;
  }

  public GeneInfoIdentifiers addEnsemblItem(String ensemblItem) {
    if (ensembl == null) {
      ensembl = new ArrayList<>();
    }
    ensembl.add(ensemblItem);
    return this;
  }

   /**
   * ENSEMBL gene id (CURIE).
   * @return ensembl
  **/
    public List<String> getEnsembl() {
    return ensembl;
  }

  public void setEnsembl(List<String> ensembl) {
    this.ensembl = ensembl;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GeneInfoIdentifiers geneInfoIdentifiers = (GeneInfoIdentifiers) o;
    return Objects.equals(entrez, geneInfoIdentifiers.entrez) &&
        Objects.equals(hgnc, geneInfoIdentifiers.hgnc) &&
        Objects.equals(mim, geneInfoIdentifiers.mim) &&
        Objects.equals(ensembl, geneInfoIdentifiers.ensembl);
  }

  @Override
  public int hashCode() {
    return Objects.hash(entrez, hgnc, mim, ensembl);
  }

  @SuppressWarnings("StringBufferReplaceableByString")
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GeneInfoIdentifiers {\n");
    
    sb.append("    entrez: ").append(toIndentedString(entrez)).append("\n");
    sb.append("    hgnc: ").append(toIndentedString(hgnc)).append("\n");
    sb.append("    mim: ").append(toIndentedString(mim)).append("\n");
    sb.append("    ensembl: ").append(toIndentedString(ensembl)).append("\n");
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

