package apimodels;

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
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaPlayFrameworkCodegen", date = "2019-07-11T16:00:13.997Z")

@SuppressWarnings({"UnusedReturnValue", "WeakerAccess"})
public class AggregationQuery   {
  @JsonProperty("operation")
  private String operation = null;

  @JsonProperty("gene_list_ids")
  private List<String> geneListIds = null;

  public AggregationQuery operation(String operation) {
    this.operation = operation;
    return this;
  }

   /**
   * Get operation
   * @return operation
  **/
    public String getOperation() {
    return operation;
  }

  public void setOperation(String operation) {
    this.operation = operation;
  }

  public AggregationQuery geneListIds(List<String> geneListIds) {
    this.geneListIds = geneListIds;
    return this;
  }

  public AggregationQuery addGeneListIdsItem(String geneListIdsItem) {
    if (geneListIds == null) {
      geneListIds = new ArrayList<>();
    }
    geneListIds.add(geneListIdsItem);
    return this;
  }

   /**
   * Get geneListIds
   * @return geneListIds
  **/
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
        Objects.equals(geneListIds, aggregationQuery.geneListIds);
  }

  @Override
  public int hashCode() {
    return Objects.hash(operation, geneListIds);
  }

  @SuppressWarnings("StringBufferReplaceableByString")
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AggregationQuery {\n");
    
    sb.append("    operation: ").append(toIndentedString(operation)).append("\n");
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

