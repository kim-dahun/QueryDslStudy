package com.study.querydslstudy.testpkg.entity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.study.querydslstudy.testpkg.entity.QMember.member;

@SpringBootTest
@Transactional
public class QueryDslBasicTest {

    @Autowired
    EntityManager em;

    void contextLoads(){
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");

        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1",10,teamA);
        Member member2 = new Member("member2",20,teamA);
        Member member3 = new Member("member3",30,teamA);
        Member member4 = new Member("member4",40,teamA);

        em.persist(member1);
        em.persist(member2);em.persist(member3);
        em.persist(member4);

    }

    @Test
    void startJPQL(){
        //member1 찾기
        contextLoads();
        Member findByJPQL = em.createQuery("select m from Member m where m.username = :username", Member.class)
                .setParameter("username","member1")
                .getSingleResult();

        Assertions.assertThat(findByJPQL.getUsername()).isEqualTo("member1");



    }

    @Test
    void startQueryDsl(){
        contextLoads();
        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);

        QMember m = new QMember("m");

        Member findMember = jpaQueryFactory
                .select(m)
                .from(m)
                .where(m.username.eq("member1"))
                .fetchOne();

        Assertions.assertThat(findMember.getUsername()).isEqualTo("member1");
    }

    @Test
    void search(){

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);

        jpaQueryFactory.selectFrom(member)
                .where(member.username.eq("member1").and(member.age.eq(10)))
                .fetchOne();

    }

}
