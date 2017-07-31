package com.shiy.springmvc.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import java.io.IOException;

@Configuration
@EnableWebMvc   //启用Spring MVC
@ComponentScan("spittr.web")//启动组件扫描
public class WebConfig extends WebMvcConfigurerAdapter {

  @Bean
  public ViewResolver viewResolver() { //配置jsp视图解析器

    /**
     * Spring自带13种视图解析器
     *            BeanNameViewResolver
     *            ContentNegotiatingViewResolver
     *            DefaultRequestToViewNameTranslator
     *            InternalResourceView
     *            InternalResourceViewResolver                 一般用于jsp
     *            JstlView
     *            RedirectView
     *            ResourceBundleViewResolver
     *            UrlBasedViewResolver
     *            XmlViewResolver
     *            FreeMarkerViewResolver
     *            TilesViewResolver                             一般用于Apache Tiles
     *            JasperReportsViewResolver
     */
    InternalResourceViewResolver resolver = new InternalResourceViewResolver();
    resolver.setPrefix("/WEB-INF/views/");
    resolver.setSuffix(".jsp");
    return resolver;
  }
  
  @Override
  public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {//配置静态资源的处理
    configurer.enable();
  }
  
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    // TODO Auto-generated method stub
    super.addResourceHandlers(registry);
  }

  /**
   * 文件上传
   *
   * 解析multipart请求内容,Spring内置了两个MultipartResolver的实现：
   *  1. CommonMultipartResolver
   *  2. StandardServletMultipartResolver（servlet3.0以上，Spring3.1）
   *
   *  StandardServletMultipartResolver会要求DispatcherServlet配置临时上传路径
   *
   * 具体来说，必须在web.xml或Servlet初始化时，将Multipart的具体细节作为DispatcherServlet配置的一部分
   */
  @Bean
  public MultipartResolver multipartResolver() throws IOException {
    return new StandardServletMultipartResolver();
  }

}
