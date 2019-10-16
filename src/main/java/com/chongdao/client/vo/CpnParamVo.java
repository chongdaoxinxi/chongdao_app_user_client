package com.chongdao.client.vo;

import com.chongdao.client.entitys.CpnParam;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author fenglong
 * @date 2019-10-16 14:04
 */
@Getter
@Setter
public class CpnParamVo {

    private List<CpnParam> cpnTypeList;

    private List<CpnParam> cpnScopeList;

}
