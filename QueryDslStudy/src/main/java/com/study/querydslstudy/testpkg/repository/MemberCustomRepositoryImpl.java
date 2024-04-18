package com.study.querydslstudy.testpkg.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.study.querydslstudy.testpkg.entity.Member;
import com.study.querydslstudy.testpkg.entity.QMember;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public class MemberCustomRepositoryImpl implements MemberCustomRepository{

    private JPAQueryFactory jpaQueryFactory;

    public MemberCustomRepositoryImpl(EntityManager em){
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }


    @Override
    public List<Member> search(Member member) {
        return null;
    }

    @Override
    public List<Member> searchPageSimple(Pageable pageable) {
        return jpaQueryFactory.selectFrom(QMember.member).fetch();
    }

    @Override
    public List<Member> searchPageComplex(Pageable pageable) {
        return null;
    }

    
}
