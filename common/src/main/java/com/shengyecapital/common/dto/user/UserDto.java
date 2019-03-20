package com.shengyecapital.common.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "userName",
        "nickName",
        "email",
        "mobileNumber"})
@Data
public class UserDto implements Serializable {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("user_name")
    private String userName;

    @JsonProperty("password")
    private String password;

    @JsonProperty("nick_name")
    private String nickName;

    @JsonProperty("email")
    private String email;

    @JsonProperty("mobile_number")
    private String mobileNumber;

    @JsonProperty("authorities")
    private List<AuthorityDto> authorities;
}
