package com.innovation.newneekclone.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Configuration
// 프로젝트 어디서든 JPAQueryFactory를 주입받아 Querydsl 사용이 가능함.
public class QuerydslConfiguration {

    @PersistenceContext
    // 스프링 컨테이너 시작 시, 영속성 관리를 위해 빈으로 등록해둔 EntityManager를 주입받을 때 사용.
    // 스레드간에는 EntityManager 공유가 금지되지만 스프링은 싱글톤 기반으로 동작하여 빈을 모든 스레드가 공유하는데,
    // 위 어노테이션을 사용하면 EntityManager를 Proxy로 감싸고 호출 시 마다 프록시를 통해 EntityManager를 생성하여 Thread-Safe를 보장한다.
    private EntityManager entityManager;

    @Bean
    public JPAQueryFactory jpaQueryFactory(){
        return new JPAQueryFactory(entityManager);
    }
}
