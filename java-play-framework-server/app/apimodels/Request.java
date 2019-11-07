package apimodels;

import com.fasterxml.jackson.annotation.*;
import java.util.Set;
import javax.validation.*;
import java.util.Objects;
import javax.validation.constraints.*;
/**
 * Request
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaPlayFrameworkCodegen", date = "2019-11-07T16:49:46.789Z")

@SuppressWarnings({"UnusedReturnValue", "WeakerAccess"})
public class Request   {
  @JsonProperty("request_id")
  private String requestId = null;

  /**
   * Status of the request.
   */
  public enum StatusEnum {
    SUCCESS("success"),
    
    FAILED("failed"),
    
    RUNNING("running");

    private final String value;

    StatusEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static StatusEnum fromValue(String text) {
      for (StatusEnum b : StatusEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }

  @JsonProperty("status")
  private StatusEnum status = null;

  @JsonProperty("gene_list_id")
  private String geneListId = null;

  @JsonProperty("current_step")
  private Integer currentStep = null;

  @JsonProperty("total_steps")
  private Integer totalSteps = null;

  @JsonProperty("error_msg")
  private String errorMsg = null;

  public Request requestId(String requestId) {
    this.requestId = requestId;
    return this;
  }

   /**
   * Id of the request.
   * @return requestId
  **/
  @NotNull
  public String getRequestId() {
    return requestId;
  }

  public void setRequestId(String requestId) {
    this.requestId = requestId;
  }

  public Request status(StatusEnum status) {
    this.status = status;
    return this;
  }

   /**
   * Status of the request.
   * @return status
  **/
  @NotNull
  public StatusEnum getStatus() {
    return status;
  }

  public void setStatus(StatusEnum status) {
    this.status = status;
  }

  public Request geneListId(String geneListId) {
    this.geneListId = geneListId;
    return this;
  }

   /**
   * Id of a gene list produced by successful completion this request.
   * @return geneListId
  **/
    public String getGeneListId() {
    return geneListId;
  }

  public void setGeneListId(String geneListId) {
    this.geneListId = geneListId;
  }

  public Request currentStep(Integer currentStep) {
    this.currentStep = currentStep;
    return this;
  }

   /**
   * Current step of the transformation (optional).
   * @return currentStep
  **/
    public Integer getCurrentStep() {
    return currentStep;
  }

  public void setCurrentStep(Integer currentStep) {
    this.currentStep = currentStep;
  }

  public Request totalSteps(Integer totalSteps) {
    this.totalSteps = totalSteps;
    return this;
  }

   /**
   * Total number of steps in the transformation request (optional).
   * @return totalSteps
  **/
    public Integer getTotalSteps() {
    return totalSteps;
  }

  public void setTotalSteps(Integer totalSteps) {
    this.totalSteps = totalSteps;
  }

  public Request errorMsg(String errorMsg) {
    this.errorMsg = errorMsg;
    return this;
  }

   /**
   * Error message (for failed requests).
   * @return errorMsg
  **/
    public String getErrorMsg() {
    return errorMsg;
  }

  public void setErrorMsg(String errorMsg) {
    this.errorMsg = errorMsg;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Request request = (Request) o;
    return Objects.equals(requestId, request.requestId) &&
        Objects.equals(status, request.status) &&
        Objects.equals(geneListId, request.geneListId) &&
        Objects.equals(currentStep, request.currentStep) &&
        Objects.equals(totalSteps, request.totalSteps) &&
        Objects.equals(errorMsg, request.errorMsg);
  }

  @Override
  public int hashCode() {
    return Objects.hash(requestId, status, geneListId, currentStep, totalSteps, errorMsg);
  }

  @SuppressWarnings("StringBufferReplaceableByString")
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Request {\n");
    
    sb.append("    requestId: ").append(toIndentedString(requestId)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    geneListId: ").append(toIndentedString(geneListId)).append("\n");
    sb.append("    currentStep: ").append(toIndentedString(currentStep)).append("\n");
    sb.append("    totalSteps: ").append(toIndentedString(totalSteps)).append("\n");
    sb.append("    errorMsg: ").append(toIndentedString(errorMsg)).append("\n");
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

