package com.confluenciacreativa.fundamentos.configuration;

import com.confluenciacreativa.fundamentos.caseuse.GetUser;
import com.confluenciacreativa.fundamentos.caseuse.GetUserImplement;
import com.confluenciacreativa.fundamentos.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CaseUseConfiguration {

    @Bean
    GetUser getUser(UserService userService){
        return new GetUserImplement(userService);
    }
}
