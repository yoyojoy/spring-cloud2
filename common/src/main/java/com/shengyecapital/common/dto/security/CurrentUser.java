package com.shengyecapital.common.dto.security;

import lombok.Data;

import java.io.Serializable;

@Data
public class CurrentUser implements Serializable {

    private Integer id;

    String userName;

    private String nickName;

}
