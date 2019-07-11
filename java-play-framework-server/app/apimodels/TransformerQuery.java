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
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaPlayFrameworkCodegen", date = "2019-07-11T16:00:13.997Z")

@SuppressWarnings({"UnusedReturnValue", "WeakerAccess"})
public class TransformerQuery   {
  @JsonProperty("name")
  private String name = null;

  @JsonProperty("gene_list_id")
  private String geneListId = null;

  @JsonProperty("controls")
  private List<Property> controls = null;

  public TransformerQuery name(String name) {
    this.name = name;
    return this;
  }

   /**
   * Get name
   * @return name
  **/
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
   * Get geneListId
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
    if (controls == null) {
      controls = new ArrayList<>();
    }
    controls.add(controlsItem);
    return this;
  }

   /**
   * Get controls
   * @return controls
  **/
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

