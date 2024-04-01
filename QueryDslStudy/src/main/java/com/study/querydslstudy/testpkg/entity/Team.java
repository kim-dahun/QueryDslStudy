package com.study.querydslstudy.testpkg.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "team")
@Data
public class Team {

    @Id @GeneratedValue
    @Column(name = "team_id")
    Long id;

    @Column(name = "team_name")
    private String name;

    @OneToMany
    @JoinColumn(name = "member_id")
    private List<Member> members = new ArrayList<>();

}
