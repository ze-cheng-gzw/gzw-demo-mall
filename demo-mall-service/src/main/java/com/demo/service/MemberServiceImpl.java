package com.demo.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.demo.dao.MemberMapper;
import com.demo.entity.Member;
import com.demo.interfaceService.MemberService;

import javax.annotation.Resource;

@Service()
public class MemberServiceImpl implements MemberService {

    @Resource
    private MemberMapper memberMapper;

    public Member findMemberById() {

//        Member member = new Member();
//        member.setMemberId(1L);
//        return member;
        return memberMapper.selectByPrimaryKey(1L);
    }

    public int addMember(Member member) {
        return 0;
    }
}
