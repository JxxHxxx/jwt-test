package com.jxx.jwt.member.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "jxx_member")
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;
    private String memberName;
    private String employeeId;
    private String roleLevel;
    private String email;
    private String orgCode;
    private String password;
    private LocalDateTime createDate;

    @Builder
    public Member(String memberName, String employeeId, String roleLevel, String email, String orgCode, String password, LocalDateTime createDate) {
        this.memberName = memberName;
        this.employeeId = employeeId;
        this.roleLevel = roleLevel;
        this.email = email;
        this.orgCode = orgCode;
        this.password = password;
        this.createDate = createDate;
    }
}
