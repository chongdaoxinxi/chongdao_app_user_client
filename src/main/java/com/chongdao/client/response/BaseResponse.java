package com.chongdao.client.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BaseResponse {
    private int errorNum;
    private String errorMsg;
}
