package com.study.querydslstudy.testpkg.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.study.querydslstudy.testpkg.entity.Member;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.study.querydslstudy.testpkg.entity.QMember.member;

@Repository
public class MemberRepository {

    private final EntityManager em;
    private final JPAQueryFactory jpaQueryFactory;

    public MemberRepository(EntityManager em){
        this.em = em;
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }

    public void save(Member member){
        em.persist(member);
    }

    public Optional<Member> findById(Long id){
        Member findMember = em.find(Member.class, id);
        return Optional.ofNullable(findMember);
    }

    public List<Member> findAll(){
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }

    public List<Member> findAllQueryDsl(){

        return jpaQueryFactory
                .selectFrom(member)
                .fetch();

    }

    public List<Member> findByUserName(String username){
        return em.createQuery("select m from Member m where m.username = :username", Member.class).setParameter("username",username).getResultList();
    }

}
