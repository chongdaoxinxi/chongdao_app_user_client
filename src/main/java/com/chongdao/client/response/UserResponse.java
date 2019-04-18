package com.chongdao.client.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse extends BaseResponse {
    private String userName;
    private Integer userId;
    private String token;
}
