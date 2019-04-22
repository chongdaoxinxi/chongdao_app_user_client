package com.chongdao.client.utils.sms;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author tianyh
 * @Description:变量短信发送响应实体类
 */
@Getter
@Setter
public class SmsVariableResponse {
    /**
     * 响应时间
     */
    private String time;
    /**
     * 消息id
     */
    private String msgId;
    /**
     * 状态码说明（成功返回空）
     */
    private String errorMsg;
    /**
     * 失败的个数
     */
    private String failNum;
    /**
     * 成功的个数
     */
    private String successNum;
    /**
     * 状态码（详细参考提交响应状态码）
     */
    private String code;

    @Override
    public String toString() {
        return "SmsVarableResponse [time=" + time + ", msgId=" + msgId + ", errorMsg=" + errorMsg + ", failNum="
                + failNum + ", successNum=" + successNum + ", code=" + code + "]";
    }
}
