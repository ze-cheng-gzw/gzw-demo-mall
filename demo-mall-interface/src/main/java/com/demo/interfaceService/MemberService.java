package com.demo.interfaceService;

import com.demo.entity.Member;

public interface MemberService {

    Member findMemberById();

    int addMember(Member member);
}
