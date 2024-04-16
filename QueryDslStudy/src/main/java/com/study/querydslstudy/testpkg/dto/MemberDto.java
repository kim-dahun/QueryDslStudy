package com.study.querydslstudy.testpkg.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberDto {

    private Long Id;

    private String username;

    private int age;

    private Long teamId;

    private String teamName;

    @QueryProjection
    public MemberDto (String username, int age){
        this.username = username;
        this.age = age;


    }

}
