package com.study.querydslstudy.testpkg.repository;

import com.study.querydslstudy.testpkg.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberSpringRepository extends JpaRepository<Member, Long>, MemberCustomRepository {


}
