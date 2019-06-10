package com.chongdao.client.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultTokenVo {
    private Integer status;

    private String role;

    private String message;

    private Integer userId;
}
