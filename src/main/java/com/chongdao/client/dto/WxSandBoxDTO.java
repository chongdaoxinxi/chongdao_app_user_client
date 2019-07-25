package com.chongdao.client.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/7/23
 * @Version 1.0
 **/
@Setter
@Getter
public class WxSandBoxDTO {
    private String return_code;
    private String return_msg;
    private String sandbox_signkey;
}
