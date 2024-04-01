package com.study.querydslstudy.testpkg.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class JPAConfig {


    private final EntityManager em;

    @Bean
    public JPAQueryFactory registryJPAQueryFactory(EntityManager em){
        return new JPAQueryFactory(em);
    }

}
