/*
 * Yagna Activity API
 * It conforms with capability level 1 of the [Activity API specification](https://docs.google.com/document/d/1BXaN32ediXdBHljEApmznSfbuudTU8TmvOmHKl0gmQM).
 *
 * OpenAPI spec version: v1
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package network.golem.yajapi.activity.models;

import java.util.Objects;
import java.util.Arrays;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
/**
 * ExeScriptCommandResult
 */


public class ExeScriptCommandResult implements Serializable{
  private static final long serialVersionUID = 1L;
  @JsonProperty("index")
  private Integer index = null;

  /**
   * Gets or Sets result
   */
  public enum ResultEnum {
    OK("Ok"),
    ERROR("Error");

    private String value;

    ResultEnum(String value) {
      this.value = value;
    }
    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }
    @JsonCreator
    public static ResultEnum fromValue(String text) {
      for (ResultEnum b : ResultEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }

  }  @JsonProperty("result")
  private ResultEnum result = null;

  @JsonProperty("stdout")
  private String stdout = null;

  @JsonProperty("stderr")
  private String stderr = null;

  @JsonProperty("message")
  private String message = null;

  @JsonProperty("isBatchFinished")
  private Boolean isBatchFinished = null;

  public ExeScriptCommandResult index(Integer index) {
    this.index = index;
    return this;
  }

   /**
   * Get index
   * minimum: 0
   * @return index
  **/
  @Schema(required = true, description = "")
  public Integer getIndex() {
    return index;
  }

  public void setIndex(Integer index) {
    this.index = index;
  }

  public ExeScriptCommandResult result(ResultEnum result) {
    this.result = result;
    return this;
  }

   /**
   * Get result
   * @return result
  **/
  @Schema(required = true, description = "")
  public ResultEnum getResult() {
    return result;
  }

  public void setResult(ResultEnum result) {
    this.result = result;
  }

  public ExeScriptCommandResult stdout(String stdout) {
    this.stdout = stdout;
    return this;
  }

   /**
   * Get stdout
   * @return stdout
  **/
  @Schema(description = "")
  public String getStdout() {
    return stdout;
  }

  public void setStdout(String stdout) {
    this.stdout = stdout;
  }

  public ExeScriptCommandResult stderr(String stderr) {
    this.stderr = stderr;
    return this;
  }

   /**
   * Get stderr
   * @return stderr
  **/
  @Schema(description = "")
  public String getStderr() {
    return stderr;
  }

  public void setStderr(String stderr) {
    this.stderr = stderr;
  }

  public ExeScriptCommandResult message(String message) {
    this.message = message;
    return this;
  }

   /**
   * Get message
   * @return message
  **/
  @Schema(description = "")
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public ExeScriptCommandResult isBatchFinished(Boolean isBatchFinished) {
    this.isBatchFinished = isBatchFinished;
    return this;
  }

   /**
   * Get isBatchFinished
   * @return isBatchFinished
  **/
  @Schema(description = "")
  public Boolean isIsBatchFinished() {
    return isBatchFinished;
  }

  public void setIsBatchFinished(Boolean isBatchFinished) {
    this.isBatchFinished = isBatchFinished;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ExeScriptCommandResult exeScriptCommandResult = (ExeScriptCommandResult) o;
    return Objects.equals(this.index, exeScriptCommandResult.index) &&
        Objects.equals(this.result, exeScriptCommandResult.result) &&
        Objects.equals(this.stdout, exeScriptCommandResult.stdout) &&
        Objects.equals(this.stderr, exeScriptCommandResult.stderr) &&
        Objects.equals(this.message, exeScriptCommandResult.message) &&
        Objects.equals(this.isBatchFinished, exeScriptCommandResult.isBatchFinished);
  }

  @Override
  public int hashCode() {
    return Objects.hash(index, result, stdout, stderr, message, isBatchFinished);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ExeScriptCommandResult {\n");
    
    sb.append("    index: ").append(toIndentedString(index)).append("\n");
    sb.append("    result: ").append(toIndentedString(result)).append("\n");
    sb.append("    stdout: ").append(toIndentedString(stdout)).append("\n");
    sb.append("    stderr: ").append(toIndentedString(stderr)).append("\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
    sb.append("    isBatchFinished: ").append(toIndentedString(isBatchFinished)).append("\n");
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
