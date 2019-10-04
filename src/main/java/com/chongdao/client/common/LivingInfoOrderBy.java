package com.chongdao.client.common;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 * @author fenglong
 * @date 2019-09-30 17:59
 */
public class LivingInfoOrderBy {

    public interface OrderBy{

        Set<String> DISTANCE_ASC_DESC = Sets.newHashSet("dis_desc","dis_asc");
        //价格排序
        Set<String> PRICE_ASC_DESC = Sets.newHashSet("price_desc","price_asc");

        Set<String> TIME_ASC_DESC = Sets.newHashSet("time_desc","time_asc");
    }



}
