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
 * AggregationQuery
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaPlayFrameworkCodegen", date = "2019-11-07T16:49:46.789Z")

@SuppressWarnings({"UnusedReturnValue", "WeakerAccess"})
public class AggregationQuery   {
  @JsonProperty("operation")
  private String operation = null;

  @JsonProperty("controls")
  private List<Property> controls = null;

  @JsonProperty("gene_list_ids")
  private List<String> geneListIds = new ArrayList<>();

  public AggregationQuery operation(String operation) {
    this.operation = operation;
    return this;
  }

   /**
   * Gene-list aggregation operation, one of 'union', 'intersection', 'difference','symmetric difference'.
   * @return operation
  **/
  @NotNull
  public String getOperation() {
    return operation;
  }

  public void setOperation(String operation) {
    this.operation = operation;
  }

  public AggregationQuery controls(List<Property> controls) {
    this.controls = controls;
    return this;
  }

  public AggregationQuery addControlsItem(Property controlsItem) {
    if (controls == null) {
      controls = new ArrayList<>();
    }
    controls.add(controlsItem);
    return this;
  }

   /**
   * Values that control the behavior of the aggregator. Names of the controls must match the names specified in the aggregator's definition and values must match types (and possibly  allowed_values) specified in the aggregator's definition.
   * @return controls
  **/
  @Valid
  public List<Property> getControls() {
    return controls;
  }

  public void setControls(List<Property> controls) {
    this.controls = controls;
  }

  public AggregationQuery geneListIds(List<String> geneListIds) {
    this.geneListIds = geneListIds;
    return this;
  }

  public AggregationQuery addGeneListIdsItem(String geneListIdsItem) {
    geneListIds.add(geneListIdsItem);
    return this;
  }

   /**
   * Ids of the gene lists to be aggreagted.
   * @return geneListIds
  **/
  @NotNull
  public List<String> getGeneListIds() {
    return geneListIds;
  }

  public void setGeneListIds(List<String> geneListIds) {
    this.geneListIds = geneListIds;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AggregationQuery aggregationQuery = (AggregationQuery) o;
    return Objects.equals(operation, aggregationQuery.operation) &&
        Objects.equals(controls, aggregationQuery.controls) &&
        Objects.equals(geneListIds, aggregationQuery.geneListIds);
  }

  @Override
  public int hashCode() {
    return Objects.hash(operation, controls, geneListIds);
  }

  @SuppressWarnings("StringBufferReplaceableByString")
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AggregationQuery {\n");
    
    sb.append("    operation: ").append(toIndentedString(operation)).append("\n");
    sb.append("    controls: ").append(toIndentedString(controls)).append("\n");
    sb.append("    geneListIds: ").append(toIndentedString(geneListIds)).append("\n");
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

