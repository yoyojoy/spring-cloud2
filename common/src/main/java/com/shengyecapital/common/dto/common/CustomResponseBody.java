package com.shengyecapital.common.dto.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "code",
        "message",
        "meta",
        "data"})
@Data
@ApiModel(value = "Response", description = "返回结果")
public class CustomResponseBody<T> implements Serializable {

    @JsonProperty("code")
    @ApiModelProperty(value = "执行结果CODE", position = 1)
    private String code;

    @JsonProperty("message")
    @ApiModelProperty(value = "执行结果消息", position = 2)
    private String message;

    @JsonProperty("meta")
    @ApiModelProperty(value = "元数据信息", position = 3)
    private Meta meta;

    @JsonProperty("data")
    @ApiModelProperty(value = "单个内容", position = 4)
    private T data;

    @Data
    private class Meta {

        @JsonProperty("total_records")
        @ApiModelProperty(value = "总记录数", position = 1)
        private Long totalRecords;

        @JsonProperty("total_pages")
        @ApiModelProperty(value = "总页数", position = 2)
        private Integer totalPages;

    }

    public CustomResponseBody() {
    }

    public CustomResponseBody(String code) {
        this.code = code;
    }

    public CustomResponseBody(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public CustomResponseBody(String code, T data) {
        this.code = code;
        this.data = data;
    }

    public CustomResponseBody(String code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public void setTotalRecords(long totalRecords) {
        meta.setTotalRecords(totalRecords);
    }

    public void setTotalPages(int totalPages) {
        meta.setTotalPages(totalPages);
    }

    public void setPageResult(PageResult<T> pageResult) {
        if (meta == null) {
            meta = new Meta();
        }

        meta.setTotalRecords(pageResult.getTotalRecords());
        meta.setTotalPages(pageResult.getTotalPages());

        setData((T) pageResult.getRecords());
    }

}
