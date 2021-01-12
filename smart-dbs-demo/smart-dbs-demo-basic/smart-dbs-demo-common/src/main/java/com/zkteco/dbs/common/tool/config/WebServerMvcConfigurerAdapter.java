package com.zkteco.dbs.common.tool.config;


import com.zkteco.dbs.common.tool.filter.RequestWrapperFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import javax.servlet.MultipartConfigElement;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName WebServerMvcConfigurerAdapter
 * @Description: MVC控制配置，自定义切入实现类
 * @Author able.lee
 * @Date 2020/11/18 10:04
 * @Since v1.0.0
 */
@Configuration
public class WebServerMvcConfigurerAdapter implements WebMvcConfigurer {

    @Resource
    private DbsConfig dbsConfig;

    /**
     * 注册请求包装过滤器，允许请求前读取流后，后续控制器仍能使用流
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean<RequestWrapperFilter> filterFilterRegistrationBean() {
        FilterRegistrationBean<RequestWrapperFilter> filterFilterRegistrationBean = new FilterRegistrationBean<>();

        //拦截路径配置
        List<String> uriList = new ArrayList<>(10);
        uriList.add("/*");

        filterFilterRegistrationBean.setFilter(new RequestWrapperFilter());
        filterFilterRegistrationBean.setEnabled(true);
        filterFilterRegistrationBean.setUrlPatterns(uriList);
        filterFilterRegistrationBean.setName("requestWrapperFilter");
        filterFilterRegistrationBean.setOrder(1);

        return filterFilterRegistrationBean;
    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //  单个数据大小 // KB,MB
        factory.setMaxFileSize(DataSize.parse("2MB"));
        /// 总上传数据大小
        factory.setMaxRequestSize(DataSize.parse("2MB"));
        return factory.createMultipartConfig();
    }
}
