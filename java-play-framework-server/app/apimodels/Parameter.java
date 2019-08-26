package apimodels;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.*;
import java.util.Set;
import javax.validation.*;
import java.util.Objects;
import javax.validation.constraints.*;
/**
 * Parameter
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaPlayFrameworkCodegen", date = "2019-08-26T20:28:29.551Z")

@SuppressWarnings({"UnusedReturnValue", "WeakerAccess"})
public class Parameter   {
  @JsonProperty("name")
  private String name = null;

  /**
   * Type of the parameter, one of 'Boolean', 'int', 'double', 'string'.
   */
  public enum TypeEnum {
    BOOLEAN("Boolean"),
    
    INT("int"),
    
    DOUBLE("double"),
    
    STRING("string");

    private final String value;

    TypeEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static TypeEnum fromValue(String text) {
      for (TypeEnum b : TypeEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }

  @JsonProperty("type")
  private TypeEnum type = null;

  @JsonProperty("default")
  private String _default = null;

  @JsonProperty("allowed_values")
  private List<String> allowedValues = null;

  public Parameter name(String name) {
    this.name = name;
    return this;
  }

   /**
   * Name of the parameter.
   * @return name
  **/
  @NotNull
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Parameter type(TypeEnum type) {
    this.type = type;
    return this;
  }

   /**
   * Type of the parameter, one of 'Boolean', 'int', 'double', 'string'.
   * @return type
  **/
  @NotNull
  public TypeEnum getType() {
    return type;
  }

  public void setType(TypeEnum type) {
    this.type = type;
  }

  public Parameter _default(String _default) {
    this._default = _default;
    return this;
  }

   /**
   * Default value of the parameter.
   * @return _default
  **/
  @NotNull
  public String getDefault() {
    return _default;
  }

  public void setDefault(String _default) {
    this._default = _default;
  }

  public Parameter allowedValues(List<String> allowedValues) {
    this.allowedValues = allowedValues;
    return this;
  }

  public Parameter addAllowedValuesItem(String allowedValuesItem) {
    if (allowedValues == null) {
      allowedValues = new ArrayList<>();
    }
    allowedValues.add(allowedValuesItem);
    return this;
  }

   /**
   * Allowed values for the parameter.
   * @return allowedValues
  **/
    public List<String> getAllowedValues() {
    return allowedValues;
  }

  public void setAllowedValues(List<String> allowedValues) {
    this.allowedValues = allowedValues;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Parameter parameter = (Parameter) o;
    return Objects.equals(name, parameter.name) &&
        Objects.equals(type, parameter.type) &&
        Objects.equals(_default, parameter._default) &&
        Objects.equals(allowedValues, parameter.allowedValues);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, type, _default, allowedValues);
  }

  @SuppressWarnings("StringBufferReplaceableByString")
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Parameter {\n");
    
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    _default: ").append(toIndentedString(_default)).append("\n");
    sb.append("    allowedValues: ").append(toIndentedString(allowedValues)).append("\n");
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

