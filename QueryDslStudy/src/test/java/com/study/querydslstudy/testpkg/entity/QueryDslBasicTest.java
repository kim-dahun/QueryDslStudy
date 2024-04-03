package com.study.querydslstudy.testpkg.entity;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.study.querydslstudy.testpkg.entity.QMember.member;
import static com.study.querydslstudy.testpkg.entity.QTeam.team;

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
        Member member4 = new Member("member4",40,teamB);

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
        contextLoads();
        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);

        // 데이터 한개만 가져오기
        Member member1 = jpaQueryFactory.selectFrom(member)
                .where(member.username.eq("member1").and(member.age.eq(10)))
                .fetchOne();


        // 카운트 쿼리
        long memberCnt = jpaQueryFactory.selectFrom(member)
                .where(member.username.eq("member1").and(member.age.eq(10)))
                .fetchCount();



        System.out.println(memberCnt);

    }

    @Test
    void sort(){

        // 정렬
        em.persist(new Member(null,100));
        em.persist(new Member("member5",100));
        em.persist(new Member("member6",100));

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);

        List<Member> memberList = jpaQueryFactory.selectFrom(member)
                .where(member.age.eq(100))
                .orderBy(member.age.desc(), member.username.asc().nullsLast())
                .fetch();

        System.out.println(memberList);


    }

    @Test
    void paging(){

        em.persist(new Member(null,100));
        em.persist(new Member("member5",100));
        em.persist(new Member("member6",100));

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);

        List<Member> memberList = jpaQueryFactory.selectFrom(member)
                .where(member.age.eq(100))
                .orderBy(member.age.desc(), member.username.asc().nullsLast())
                .offset(1)
                .limit(2)
                .fetch();

        System.out.println(memberList);

    }

    @Test
    void aggregation(){
        contextLoads();
        em.persist(new Member(null,100));
        em.persist(new Member("member5",100));
        em.persist(new Member("member6",100));

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);

        List<Tuple> memberList = jpaQueryFactory.select(
                member.age.sum(),
                member.age.min()
                ).from(member)
                .groupBy(member.id)
                .fetch();

        Tuple tuple = memberList.get(0);

        System.out.println(tuple);

        List<Tuple> result = jpaQueryFactory.select(team.name, member.age.avg()).from(member).join(member.team, team)
                .groupBy(team.name).
                having(member.age.avg().gt(30)).
                fetch();

        Tuple teamA = result.get(0);
//        Tuple teamB = result.get(1);

        System.out.println(teamA);
//        System.out.println(teamB);

    }

}
