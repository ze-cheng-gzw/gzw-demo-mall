package com.demo.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.demo.dao.MemberMapper;
import com.demo.entity.Member;
import com.demo.interfaceService.MemberService;

import javax.annotation.Resource;

@Service
public class MemberServiceImpl implements MemberService {

    @Resource
    private MemberMapper memberMapper;

    public Member findMemberById() {
        return memberMapper.selectByPrimaryKey(1L);
    }
}
