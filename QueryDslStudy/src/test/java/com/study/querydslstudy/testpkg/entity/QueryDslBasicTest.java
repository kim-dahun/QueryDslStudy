//package com.study.querydslstudy.testpkg.entity;
//
//import com.querydsl.core.BooleanBuilder;
//import com.querydsl.core.Tuple;
//import com.querydsl.core.types.Projections;
//import com.querydsl.core.types.dsl.CaseBuilder;
//import com.querydsl.core.types.dsl.Expressions;
//import com.querydsl.jpa.JPAExpressions;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import com.study.querydslstudy.testpkg.dto.MemberDto;
//import jakarta.persistence.EntityManager;
//import jakarta.transaction.Transactional;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.List;
//
//import static com.study.querydslstudy.testpkg.entity.QMember.member;
//import static com.study.querydslstudy.testpkg.entity.QTeam.team;
//
//@SpringBootTest
//@Transactional
//public class QueryDslBasicTest {
//
//    @Autowired
//    EntityManager em;
//
//    void contextLoads(){
//        Team teamA = new Team("teamA");
//        Team teamB = new Team("teamB");
//
//        em.persist(teamA);
//        em.persist(teamB);
//
//        Member member1 = new Member("member1",10,teamA);
//        Member member2 = new Member("member2",20,teamA);
//        Member member3 = new Member("member3",30,teamA);
//        Member member4 = new Member("member4",40,teamB);
//
//        em.persist(member1);
//        em.persist(member2);em.persist(member3);
//        em.persist(member4);
//
//    }
//
//    @Test
//    void startJPQL(){
//        //member1 찾기
//        contextLoads();
//        Member findByJPQL = em.createQuery("select m from Member m where m.username = :username", Member.class)
//                .setParameter("username","member1")
//                .getSingleResult();
//
//        Assertions.assertThat(findByJPQL.getUsername()).isEqualTo("member1");
//
//
//
//    }
//
//    @Test
//    void startQueryDsl(){
//        contextLoads();
//        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);
//
//        QMember m = new QMember("m");
//
//        Member findMember = jpaQueryFactory
//                .select(m)
//                .from(m)
//                .where(m.username.eq("member1"))
//                .fetchOne();
//
//        Assertions.assertThat(findMember.getUsername()).isEqualTo("member1");
//    }
//
//    @Test
//    void search(){
//        contextLoads();
//        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);
//
//        // 데이터 한개만 가져오기
//        Member member1 = jpaQueryFactory.selectFrom(member)
//                .where(member.username.eq("member1").and(member.age.eq(10)))
//                .fetchOne();
//
//
//        // 카운트 쿼리
//        long memberCnt = jpaQueryFactory.selectFrom(member)
//                .where(member.username.eq("member1").and(member.age.eq(10)))
//                .fetchCount();
//
//
//
//        System.out.println(memberCnt);
//
//    }
//
//    @Test
//    void sort(){
//
//        // 정렬
//        em.persist(new Member(null,100));
//        em.persist(new Member("member5",100));
//        em.persist(new Member("member6",100));
//
//        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);
//
//        List<Member> memberList = jpaQueryFactory.selectFrom(member)
//                .where(member.age.eq(100))
//                .orderBy(member.age.desc(), member.username.asc().nullsLast())
//                .fetch();
//
//        System.out.println(memberList);
//
//
//    }
//
//    @Test
//    void paging(){
//
//        em.persist(new Member(null,100));
//        em.persist(new Member("member5",100));
//        em.persist(new Member("member6",100));
//
//        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);
//
//        List<Member> memberList = jpaQueryFactory.selectFrom(member)
//                .where(member.age.eq(100))
//                .orderBy(member.age.desc(), member.username.asc().nullsLast())
//                .offset(1)
//                .limit(2)
//                .fetch();
//
//        System.out.println(memberList);
//
//    }
//
//    @Test
//    void aggregation(){
//        contextLoads();
//        em.persist(new Member(null,100));
//        em.persist(new Member("member5",100));
//        em.persist(new Member("member6",100));
//
//        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);
//
//        List<Tuple> memberList = jpaQueryFactory.select(
//                member.age.sum(),
//                member.age.min()
//                ).from(member)
//                .groupBy(member.id)
//                .fetch();
//
//        Tuple tuple = memberList.get(0);
//
//        System.out.println(tuple);
//
//        List<Tuple> result = jpaQueryFactory.select(team.name, member.age.avg()).from(member).join(member.team, team)
//                .groupBy(team.name).
//                having(member.age.avg().gt(30)).
//                fetch();
//
//        Tuple teamA = result.get(0);
////        Tuple teamB = result.get(1);
//
//        System.out.println(teamA);
////        System.out.println(teamB);
//
//    }
//
//    @Test
//    void join(){
//
//        contextLoads();
//        em.persist(new Member(null,100));
//        em.persist(new Member("member5",100));
//        em.persist(new Member("member6",100));
//
//        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);
//
//        List<Member> memberList = jpaQueryFactory.selectFrom(member)
////                .join(member.team, team) 연관관계 이너 조인
////                .leftJoin(member.team, team) 연관관계 레프트 조인
////                .from(member, team) 세타 조인.
////                .join(team).on(team.name.eq(member.team.name)) 연관관계 없는 경우에도 조인(일반 쿼리 조인)
//                .where(team.name
//                        .eq("teamA"))
//                .fetch();
//
//        System.out.println(memberList);
//
//    }
//
//    @Test
//    void fetchJoin(){
//
//        contextLoads();
//        em.persist(new Member(null,100));
//        em.persist(new Member("member5",100));
//        em.persist(new Member("member6",100));
//
//        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);
//
//        em.flush();
//        em.clear();
//
////        Member member1 = jpaQueryFactory
////                .selectFrom(member)
////                .where(member.username.eq("member1"))
////                .fetchOne();
////
////        System.out.println(member1.getTeam().getName());
//
//        Member member2 = jpaQueryFactory
//                .selectFrom(member)
//                .join(member.team,team)
//                .fetchJoin()
//                .where(member.username.eq("member1"))
//                .fetchOne();
//
//        System.out.println(member2);
//    }
//
//    @Test
//    void subQuery(){
//
//
//        contextLoads();
//        em.persist(new Member(null,100));
//        em.persist(new Member("member5",100));
//        em.persist(new Member("member6",100));
//
//        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);
//
//        QMember msub = new QMember("msub");
//
//        List<Member> memberList = jpaQueryFactory.selectFrom(member)
//                .where(member.age.gt(
//                        JPAExpressions.select(msub.age.max()).from(msub)
//                )).fetch();
//
//        System.out.println(memberList);
//
//
//    }
//
//    @Test
//    void caseWhen(){
//
//        contextLoads();
//        em.persist(new Member(null,100));
//        em.persist(new Member("member5",100));
//        em.persist(new Member("member6",100));
//
//        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);
//
//        List<String> list = jpaQueryFactory.select(
//                        member.age.when(10).then("열살")
//                                .when(20).then("스무살")
//                                .otherwise("아님")
//
//
//                ).from(member)
//                .fetch();
//
//        List<String> list2 = jpaQueryFactory.select(
//                        new CaseBuilder().when(member.age.between(10,20)).then("하이")
//                                .otherwise("ddd")
//                ).from(member)
//                .fetch();
//
//        System.out.println(list);
//
//    }
//
//    @Test
//    void appendStringConst(){
//
//        contextLoads();
//        em.persist(new Member(null,100));
//        em.persist(new Member("member5",100));
//        em.persist(new Member("member6",100));
//
//        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);
//
//        jpaQueryFactory.select(member.username, Expressions.constant("A"))
//                .from(member)
//                .fetch();
//
//        jpaQueryFactory.select(
//                member.username.concat("_").concat(member.age.stringValue())
//        ).from(member).fetch();
//
//    }
//
//    @Test
//    void tupleProjection(){
//
//        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);
//
//        List<Tuple> tuples = jpaQueryFactory.select(member.id
//                        , member.age
//                        , member.team
//                )
//                .from(member).fetch();
//
//        for(Tuple x : tuples){
//            System.out.println(x.get(member.age));
//        }
//
//    }
//
//    @Test
//    void findDto(){
//
//        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);
//
//        List<MemberDto> memberDtos = em.createQuery("select new com.study.querydslstudy.testpkg.dto.MemberDto(m.username, m.age) from Member m", MemberDto.class).getResultList();
//
//        System.out.println(memberDtos);
//
//
//    }
//
//    @Test
//    void findDtoBySetter(){ // 세터 주입
//
//        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);
//
//        jpaQueryFactory.select(Projections.bean(MemberDto.class
//            ,member.username
//                ,member.age
//        )).from(member)
//                .fetch();
//
//    }
//
//    @Test
//    void findDtoByField(){ // 필드연결
//
//        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);
//
//        jpaQueryFactory.select(Projections.fields(MemberDto.class
//                        ,member.username
//                        ,member.age
//                )).from(member)
//                .fetch();
//
//    }
//
//    @Test
//    void findDtoByConstructor(){ // 생성자
//
//        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);
//
//        jpaQueryFactory.select(Projections.constructor(MemberDto.class
//                        ,member.username
//                        ,member.age
//                )).from(member)
//                .fetch();
//
//    }
//
//    @Test
//    void dynamicQueryBooleanBuilder(){
//
//        JPAQueryFactory jqf = new JPAQueryFactory(em);
//
//        jqf.select(member.username)
//                .from(member)
//                .where(new BooleanBuilder(member.username.like("%m%"))).fetch();
//
//
//    }
//
//    @Test
//    void bulkUpdate(){
//        contextLoads();
//
//        JPAQueryFactory jqf = new JPAQueryFactory(em);
//
//        long count = jqf.update(member)
//                .set(member.username,"비회원")
//                .where(member.age.lt(20))
//                .execute();
//
//        // JPA는 영속성 컨텍스트를 최우선으로 여김
//        // update 벌크 연산의 경우, 영속성 컨텍스트 업데이트 없이 바로 DB로 들어가기 때문에
//        // update 벌크 연산 후 select를 바로 해올 경우, 영속성 컨텍스트에 남아있는 변화되기 전 값이 불러와짐
//        // 따라서 벌크 연산 이후, flush와 clear를 통해 영속성 컨텍스트를 초기화한 후, 현재 단계의 트랜잭션을 마무리하는 방식으로
//        // db를 강제로 동기화하여 해당 문제를 해결
//
//    }
//
//    @Test
//    void bulkAdd(){
//
//        JPAQueryFactory jqf = new JPAQueryFactory(em);
//
//        jqf.update(member)
//                .set(member.age, member.age.multiply(2))
//                .execute();
//
//    }
//
//    @Test
//    void bulkDelete(){
//        JPAQueryFactory jqf = new JPAQueryFactory(em);
//
//        jqf.delete(member)
//                .where(member.age.gt(10))
//                .execute();
//
//        jqf.select(
//                Expressions.stringTemplate("function('replace',{0},{1},{2}",member.username,"member","mem")
//        )
//                .from(
//                        member
//                )
//                .fetch();
//
//    }
//
//}
