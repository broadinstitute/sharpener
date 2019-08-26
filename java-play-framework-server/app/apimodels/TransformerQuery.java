package apimodels;

import apimodels.Property;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.*;
import java.util.Set;
import javax.validation.*;
import java.util.Objects;
import javax.validation.constraints.*;
/**
 * TransformerQuery
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaPlayFrameworkCodegen", date = "2019-08-26T20:10:20.079Z")

@SuppressWarnings({"UnusedReturnValue", "WeakerAccess"})
public class TransformerQuery   {
  @JsonProperty("name")
  private String name = null;

  @JsonProperty("gene_list_id")
  private String geneListId = null;

  @JsonProperty("controls")
  private List<Property> controls = new ArrayList<>();

  public TransformerQuery name(String name) {
    this.name = name;
    return this;
  }

   /**
   * Name of the transformer that will be executed.
   * @return name
  **/
  @NotNull
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public TransformerQuery geneListId(String geneListId) {
    this.geneListId = geneListId;
    return this;
  }

   /**
   * Id of the gene list that will be transformed. Required for expanders and filters;  should be ommited for producers.
   * @return geneListId
  **/
    public String getGeneListId() {
    return geneListId;
  }

  public void setGeneListId(String geneListId) {
    this.geneListId = geneListId;
  }

  public TransformerQuery controls(List<Property> controls) {
    this.controls = controls;
    return this;
  }

  public TransformerQuery addControlsItem(Property controlsItem) {
    controls.add(controlsItem);
    return this;
  }

   /**
   * Values that control the behavior of the transformer. Names of the controls must match the names specified in the transformer's definition and values must match types (and possibly  allowed_values) specified in the transformer's definition.
   * @return controls
  **/
  @NotNull
@Valid
  public List<Property> getControls() {
    return controls;
  }

  public void setControls(List<Property> controls) {
    this.controls = controls;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TransformerQuery transformerQuery = (TransformerQuery) o;
    return Objects.equals(name, transformerQuery.name) &&
        Objects.equals(geneListId, transformerQuery.geneListId) &&
        Objects.equals(controls, transformerQuery.controls);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, geneListId, controls);
  }

  @SuppressWarnings("StringBufferReplaceableByString")
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TransformerQuery {\n");
    
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    geneListId: ").append(toIndentedString(geneListId)).append("\n");
    sb.append("    controls: ").append(toIndentedString(controls)).append("\n");
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

