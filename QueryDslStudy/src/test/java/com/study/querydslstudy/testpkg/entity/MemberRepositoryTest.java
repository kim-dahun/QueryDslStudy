//package com.study.querydslstudy.testpkg.entity;
//
//import com.study.querydslstudy.testpkg.repository.MemberRepository;
//import jakarta.persistence.EntityManager;
//import jakarta.transaction.Transactional;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest
//@Transactional
//public class MemberRepositoryTest {
//
//    @Autowired
//    EntityManager em;
//
//    @Autowired
//    private MemberRepository memberRepository;
//
//
//    @Test
//    void basicTest(){
//
//        Member member = new Member("member1",10);
//        memberRepository.save(member);
//
//        Member findMember = memberRepository.findById(member.getId()).get();
//
//        assertThat(findMember).isEqualTo(member);
//
//        List<Member> result1 = memberRepository.findAll();
//        assertThat(result1).containsExactly(member);
//
//        List<Member> result2 = memberRepository.findByUserName("member1");
//        assertThat(result2).containsExactly(member);
//
//    }
//
//}
