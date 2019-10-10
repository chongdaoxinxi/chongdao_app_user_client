package com.chongdao.client.controller.user;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.service.UserVisitService;
import com.chongdao.client.utils.LoginUserUtil;
import com.chongdao.client.vo.ResultTokenVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/10/10
 * @Version 1.0
 **/
public class UserVisitController {
    @Autowired
    private UserVisitService userVisitService;

    /**
     * 访问系统记录
     * @param token
     * @return
     */
    @PostMapping("systemVisit")
    public ResultResponse systemVisit(String token, Integer source){
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        return userVisitService.addUserSystemVisit(tokenVo.getUserId(), source);
    }

    /**
     * 访问商店记录
     * @param token
     * @return
     */
    @PostMapping("shopVisit")
    public ResultResponse shopVisit(String token, Integer shopId, Integer source) {
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        return userVisitService.addUserShopVisit(tokenVo.getUserId(), shopId, source);
    }
}
