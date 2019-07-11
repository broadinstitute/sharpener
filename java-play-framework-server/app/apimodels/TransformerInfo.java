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
 * TransformerInfo
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaPlayFrameworkCodegen", date = "2019-07-11T16:00:13.997Z")

@SuppressWarnings({"UnusedReturnValue", "WeakerAccess"})
public class TransformerInfo   {
  @JsonProperty("name")
  private String name = null;

  @JsonProperty("function")
  private String function = null;

  @JsonProperty("parameters")
  private List<Parameter> parameters = null;

  public TransformerInfo name(String name) {
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

  public TransformerInfo function(String function) {
    this.function = function;
    return this;
  }

   /**
   * Get function
   * @return function
  **/
    public String getFunction() {
    return function;
  }

  public void setFunction(String function) {
    this.function = function;
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
   * Get parameters
   * @return parameters
  **/
  @Valid
  public List<Parameter> getParameters() {
    return parameters;
  }

  public void setParameters(List<Parameter> parameters) {
    this.parameters = parameters;
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
        Objects.equals(function, transformerInfo.function) &&
        Objects.equals(parameters, transformerInfo.parameters);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, function, parameters);
  }

  @SuppressWarnings("StringBufferReplaceableByString")
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TransformerInfo {\n");
    
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    function: ").append(toIndentedString(function)).append("\n");
    sb.append("    parameters: ").append(toIndentedString(parameters)).append("\n");
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

