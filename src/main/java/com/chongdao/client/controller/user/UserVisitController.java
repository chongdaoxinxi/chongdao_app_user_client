package com.chongdao.client.controller.user;

import com.chongdao.client.common.ResultResponse;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/10/10
 * @Version 1.0
 **/
public class UserVisitController {

    /**
     * 访问系统记录
     * @param token
     * @return
     */
    @PostMapping("systemVisit")
    public ResultResponse systemVisit(String token){
        return null;
    }

    /**
     * 访问商店记录
     * @param token
     * @return
     */
    @PostMapping("shopVisit")
    public ResultResponse shopVisit(String token) {
        return null;
    }
}
