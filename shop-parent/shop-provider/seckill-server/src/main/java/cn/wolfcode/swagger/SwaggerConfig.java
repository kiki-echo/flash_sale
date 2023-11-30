package cn.wolfcode.swagger;

import cn.wolfcode.common.domain.SwaggerProperties;
import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;

@Configuration
@EnableOpenApi //swagger3启动注释
@EnableKnife4j
public class SwaggerConfig {
    @Autowired
    private SwaggerProperties swaggerProperties;

    @Bean
    public Docket initDocket(Environment env) {

        //设置要暴漏接口文档的配置环境
        //设置要显示的Swagger环境
        Profiles profile = Profiles.of("test","dev");
        //获取项目的环境：
        //通过environment.acceptsProfiles判断是否处在自己设定的环境当中
        boolean flag = env.acceptsProfiles(profile);
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .enable(flag)//是否启动swagger 默认为true ,如果为false，则Swagger不能再浏览器中访问
                .select()
                //RequestHandlerSelectors,配置要扫描接口的方式
                .apis(RequestHandlerSelectors.basePackage("com.zdsoft.datafactory.controller")) //指定要扫描的包
//                .apis(RequestHandlerSelectors.any())   //扫描全部
                //.apis(RequestHandlerSelectors.none()):不扫描
                //.apis(RequestHandlerSelectors.withClassAnnotation(RestController.class)):扫描类上的注解，参数是一个注解的反射对象
                //.apis(RequestHandlerSelectors.withMethodAnnotation(GetMapping.class)):扫描方法上的注解
//                .apis(RequestHandlerSelectors.basePackage("com.zhao.controller"))
                //paths()过滤什么路径(url)
                //paths(PathSelectors.ant("/zhao/**")) 就是在localhost:8080/zhao  后面的路径
//                .paths(PathSelectors.ant("/zhao/**"))
//                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build()
                //右上角 组（有几个Docket，有几个组）
                .groupName("第一组");
    }

    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.OAS_30)
                //定义是否开启swagger
                .enable(swaggerProperties.getEnable())
                .groupName("测试");
    }

    private ApiInfo apiInfo(){
        //作者信息
        Contact contact = new Contact("bbx","https://blog.csdn.net/BBQ__ZXB?type=blog","1101249732@qq.com");
        return new ApiInfo(
                swaggerProperties.getApplicationName() + "APi Doc",
                swaggerProperties.getApplicationDescription(),
                swaggerProperties.getApplicationVersion(),
                "urn:tos",
                contact,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList());
    }
}