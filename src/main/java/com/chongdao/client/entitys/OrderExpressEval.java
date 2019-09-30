    package com.chongdao.client.entitys;

    import lombok.Getter;
    import lombok.Setter;

    import javax.persistence.Entity;
    import javax.persistence.GeneratedValue;
    import javax.persistence.GenerationType;
    import javax.persistence.Id;
    import javax.validation.constraints.NotEmpty;
    import java.util.Date;

    /**
     * @author fenglong
     * @date 2019-07-10 13:49
     */
    @Entity
    @Getter
    @Setter
    public class OrderExpressEval {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        @NotEmpty(message = "userId不能为空")
        private Integer userId;

        @NotEmpty(message = "shopId不能为空")
        private Integer shopId;

        @NotEmpty(message = "订单号必填")
        private String orderNo;

        @NotEmpty(message = "评价内容必填")
        private String content;

        private Double grade;

        private Integer status = 1;

        private String img;

        private Integer enabledAnonymous; //匿名 0 否 1是

        private Integer expressId;

        private Date createTime;

        private Date updateTime;
    }
