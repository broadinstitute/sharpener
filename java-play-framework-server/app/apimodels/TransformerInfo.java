package apimodels;

import apimodels.Parameter;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.*;
import java.util.Set;
import javax.validation.*;
import java.util.Objects;
import javax.validation.constraints.*;
/**
 * Definition of the transformer.
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaPlayFrameworkCodegen", date = "2019-08-26T20:10:20.079Z")

@SuppressWarnings({"UnusedReturnValue", "WeakerAccess"})
public class TransformerInfo   {
  @JsonProperty("name")
  private String name = null;

  /**
   * Status of the transformer, one of 'online', 'offline'.
   */
  public enum StatusEnum {
    ONLINE("online"),
    
    OFFLINE("offline");

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

  /**
   * Function of the transfomer, one of 'producer', 'expander', 'filter'.
   */
  public enum FunctionEnum {
    PRODUCER("producer"),
    
    EXPANDER("expander"),
    
    FILTER("filter");

    private final String value;

    FunctionEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static FunctionEnum fromValue(String text) {
      for (FunctionEnum b : FunctionEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }

  @JsonProperty("function")
  private FunctionEnum function = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("parameters")
  private List<Parameter> parameters = null;

  @JsonProperty("required_attributes")
  private List<String> requiredAttributes = null;

  public TransformerInfo name(String name) {
    this.name = name;
    return this;
  }

   /**
   * Name of the transformer.
   * @return name
  **/
  @NotNull
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public TransformerInfo status(StatusEnum status) {
    this.status = status;
    return this;
  }

   /**
   * Status of the transformer, one of 'online', 'offline'.
   * @return status
  **/
  @NotNull
  public StatusEnum getStatus() {
    return status;
  }

  public void setStatus(StatusEnum status) {
    this.status = status;
  }

  public TransformerInfo function(FunctionEnum function) {
    this.function = function;
    return this;
  }

   /**
   * Function of the transfomer, one of 'producer', 'expander', 'filter'.
   * @return function
  **/
  @NotNull
  public FunctionEnum getFunction() {
    return function;
  }

  public void setFunction(FunctionEnum function) {
    this.function = function;
  }

  public TransformerInfo description(String description) {
    this.description = description;
    return this;
  }

   /**
   * Description of the transformer.
   * @return description
  **/
    public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public TransformerInfo parameters(List<Parameter> parameters) {
    this.parameters = parameters;
    return this;
  }

  public TransformerInfo addParametersItem(Parameter parametersItem) {
    if (parameters == null) {
      parameters = new ArrayList<>();
    }
    parameters.add(parametersItem);
    return this;
  }

   /**
   * Parameters used to control the transformer.
   * @return parameters
  **/
  @Valid
  public List<Parameter> getParameters() {
    return parameters;
  }

  public void setParameters(List<Parameter> parameters) {
    this.parameters = parameters;
  }

  public TransformerInfo requiredAttributes(List<String> requiredAttributes) {
    this.requiredAttributes = requiredAttributes;
    return this;
  }

  public TransformerInfo addRequiredAttributesItem(String requiredAttributesItem) {
    if (requiredAttributes == null) {
      requiredAttributes = new ArrayList<>();
    }
    requiredAttributes.add(requiredAttributesItem);
    return this;
  }

   /**
   * Gene attributes required by the transformer.
   * @return requiredAttributes
  **/
    public List<String> getRequiredAttributes() {
    return requiredAttributes;
  }

  public void setRequiredAttributes(List<String> requiredAttributes) {
    this.requiredAttributes = requiredAttributes;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TransformerInfo transformerInfo = (TransformerInfo) o;
    return Objects.equals(name, transformerInfo.name) &&
        Objects.equals(status, transformerInfo.status) &&
        Objects.equals(function, transformerInfo.function) &&
        Objects.equals(description, transformerInfo.description) &&
        Objects.equals(parameters, transformerInfo.parameters) &&
        Objects.equals(requiredAttributes, transformerInfo.requiredAttributes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, status, function, description, parameters, requiredAttributes);
  }

  @SuppressWarnings("StringBufferReplaceableByString")
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TransformerInfo {\n");
    
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    function: ").append(toIndentedString(function)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    parameters: ").append(toIndentedString(parameters)).append("\n");
    sb.append("    requiredAttributes: ").append(toIndentedString(requiredAttributes)).append("\n");
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

