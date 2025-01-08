package com.ohgiraffers.mapping.section01.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity(name="member")
@Table(name = "tbl_member")
// 테이블화
public class Member {

    /* comment.
    *   @Column 속성
    *   - name : 매핑할 테이블의 컬럼 이름
    *   - nullable : null 값 허용
    *   - unique : 컬럼의 유일성 제약 조건
    *   - columnDefinition : 직접 DDL 지정 가능
    *   - length : 문자열 길이 String 타입만 사용 가능(기본 255)
    * */

    @Id // PK
    @Column(name = "member_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int memberNo;

    @Column(name = "member_id" , nullable = false , unique = true , columnDefinition = "varchar(10)")
    private String memberId;

    @Column(name = "member_pwd" , nullable = false)
    private String memberPwd;

    @Column(name = "member_name")
    private String memberName;

    @Column(name = "phone" , unique = true)
    private String phone;

    @Column(name = "address" , length = 900)
    private String address;

    @Column(name = "enroll_date")
    private LocalDateTime enrollDate;

    /* comment.
    *   @Enumerated
    *   - enum 타입 매핑시 사용
    *   - ORDINAL : Enum 타입을 필드 순서로 매핑
    *   - 장점 : 0, 1 이런 식으로 저장되기 때문에 메모리 효율성 좋음
    *   - 단점 : 가독성이 떨어지며 필드 추가, 순서 변경 시 데이터 혼돈
    *   - String : Enum 타입을 문자열로 매핑
    *   - 장점 : 가독성이 좋으며 , 이름으로 저장하기 때문에 순서 변경 지정 없음
    *   - 단점 : 데이터의 크기가 숫자보다 크다. <- 하지만 큰 단점은 아니다.
    * */
    @Column(name="member_role")
    @Enumerated(EnumType.STRING)
    // @Enumerated(EnumType.ORDINAL) 이걸로 하면 숫자로 들어감 아깐  ROLE_MEMBER , ROLE_ADMIN 이었는데요. 문제는 구분을 못해요 . 왠만해선 String 타입사용
    private MemberRole memberRole;

    @Column(name="status", columnDefinition = "char(1) default 'Y'")
    private String status;

    public Member(){}

    public Member(String memberId, String memberPwd, String memberName, String phone, String address, LocalDateTime enrollDate, MemberRole memberRole, String status) {
        this.memberId = memberId;
        this.memberPwd = memberPwd;
        this.memberName = memberName;
        this.phone = phone;
        this.address = address;
        this.enrollDate = enrollDate;
        this.memberRole = memberRole;
        this.status = status;
    }
}
