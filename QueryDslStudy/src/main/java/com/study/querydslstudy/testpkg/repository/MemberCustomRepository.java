package com.study.querydslstudy.testpkg.repository;

import com.study.querydslstudy.testpkg.entity.Member;

import java.awt.print.Pageable;
import java.util.List;

public interface MemberCustomRepository {

    List<Member> search(Member member);


    List<Member> searchPageSimple(Pageable pageable);

    List<Member> searchPageComplex(Pageable pageable);

}
