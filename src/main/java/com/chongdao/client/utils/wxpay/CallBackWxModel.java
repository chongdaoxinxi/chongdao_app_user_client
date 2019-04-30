package com.chongdao.client.utils.wxpay;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Description 微信回调实体
 * @Author onlineS
 * @Date 2019/4/30
 * @Version 1.0
 **/
@Setter
@Getter
@NoArgsConstructor
public class CallBackWxModel implements Serializable {
    private static final long serialVersionUID = 1L;

    private String return_code;
    private String return_msg;
}
