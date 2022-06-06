package com.confluenciacreativa.fundamentos.configuration;

import com.confluenciacreativa.fundamentos.bean.MyBean;
import com.confluenciacreativa.fundamentos.bean.MyBean2Implement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfigurationBean {
    @Bean
    public MyBean beanOperation() {
        return new MyBean2Implement();
    }
}
