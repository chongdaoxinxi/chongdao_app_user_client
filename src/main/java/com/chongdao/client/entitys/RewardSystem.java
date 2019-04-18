package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@NoArgsConstructor
@Getter
public class RewardSystem implements Serializable {
   private static final long serialVersionUID = 1L;

   //商家的id
    private Integer shopid;
   //起始单(从第几单开始算奖励)
    private Integer actually;
    //终止单(第几单结束奖励)
    private Integer termination;
    //奖励金额
    private Integer AmountOfMoney;
    //活动开始时间
    private String startTime;
    //活动结束时间
    private String EndTime;

 @Override
 public String toString() {
  return "RewardSystem{" +
          "shopid=" + shopid +
          ", actually=" + actually +
          ", termination=" + termination +
          ", AmountOfMoney=" + AmountOfMoney +
          ", startTime='" + startTime + '\'' +
          ", EndTime='" + EndTime + '\'' +
          '}';
 }
}
