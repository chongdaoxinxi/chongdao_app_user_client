package com.chongdao.client.service.iml;

import com.chongdao.client.common.Const;
import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.Category;
import com.chongdao.client.entitys.Good;
import com.chongdao.client.enums.ResultEnum;
import com.chongdao.client.mapper.CategoryMapper;
import com.chongdao.client.mapper.GoodMapper;
import com.chongdao.client.service.GoodsService;
import com.chongdao.client.vo.GoodsListVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static com.chongdao.client.common.Const.goodsListOrderBy.*;

@Service
public class GoodsServiceImpl implements GoodsService {


    @Autowired
    private GoodMapper goodMapper;

    @Autowired
    private CategoryMapper categoryMapper;


    /**
     * 分页查询商品
     * @param keyword 搜索关键词
     * @param pageNum 页数
     * @param pageSize 每页数据数量
     * @param orderBy 排序方式(价格、销量、好评等)
     * @return
     */
    @Override
    public ResultResponse<PageInfo> getGoodsByKeyword(String keyword, int pageNum, int pageSize,String categoryId, String orderBy) {
        //搜索关键词不为空
        if (StringUtils.isNotBlank(keyword)){
            keyword =new StringBuilder().append("%").append(keyword).append("%").toString();
        }
        PageHelper.startPage(pageNum,pageSize);
        //排序规则
        if (StringUtils.isNotBlank(orderBy)){
            if (PRICE_ASC_DESC.contains(orderBy) || SALES_ASC_DESC.contains(orderBy)){
                String[] orderByArray = orderBy.split("_");
                //价格排序、销量排序
                orderBy = orderByArray[0] + " " + orderByArray[1];
            }else if (ARRANGEMENT_KEY.contains(orderBy)){
                //综合排序
                orderBy = ARRANGEMENT_VALUE;
            }
        }
        //查询所有上架商品
        List<Good> goodList = goodMapper.selectByName(StringUtils.isBlank(keyword) ? null: keyword, orderBy,StringUtils.isBlank(categoryId) ? null : keyword);
        List<GoodsListVO> goodsListVOList = Lists.newArrayList();
        goodList.forEach(good -> {
            GoodsListVO goodsListVO = new GoodsListVO();
            BeanUtils.copyProperties(good,goodsListVO);
            //折扣大于0时，才会显示折扣价
            if (good.getDiscount() > 0.0D && good.getDiscount() != null ){
                goodsListVO.setDiscountPrice(good.getPrice().multiply(new BigDecimal(good.getDiscount())));
            }
            goodsListVOList.add(goodsListVO);
        });
        PageInfo pageInfo = new PageInfo(goodList);
        pageInfo.setOrderBy(orderBy);
        pageInfo.setList(goodsListVOList);
        return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(),pageInfo);
    }
}
