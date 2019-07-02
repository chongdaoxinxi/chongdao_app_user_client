package com.chongdao.client.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.annotation.PostConstruct;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/7/2
 * @Version 1.0
 **/
@Configuration
public class SpringDateConverter {
    @Autowired
    private RequestMappingHandlerAdapter requestMappingHandlerAdapter;

    /**
     * 增加字符串转换为List集合
     */
    @PostConstruct
    public void addConversionConfig() {
        ConfigurableWebBindingInitializer initializer = (ConfigurableWebBindingInitializer) requestMappingHandlerAdapter.getWebBindingInitializer();
        if (initializer.getConversionService() != null) {
            GenericConversionService genericConversionService = (GenericConversionService)initializer.getConversionService();
            //添加字符串转换为日期类型的字符串
            genericConversionService.addConverter(new StringToDateConverter());

        }
    }
}
