package com.myssm.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.myssm.dao.MemberDao;
import com.myssm.entity.Member;
import com.myssm.mapper.MemberMapper;
@Repository
public class MemberDaoImpl implements MemberDao{
	@Resource
	private MemberMapper MemberMapper;
	
	public Member getMember(Member member)
	{
		return MemberMapper.getMember(member);
	}
	public List<Member> getMemberList() {
		return MemberMapper.getMemberList();
	}
	public int updateLogin(int uid)
	{
		return MemberMapper.updateLogin(uid);
	}
	public int regMember(String user,String password,String qq) {
		return MemberMapper.regMember(user,password,qq);
	}
	public int deleteMember(int uid){
		return MemberMapper.deleteMember(uid);
	}
	public int saveMember(Member member){
		return MemberMapper.saveMember(member);
	}
	public Member queryMember(int uid){
		return MemberMapper.queryMember(uid);
	}
	public int updateMember(Member member){
		return MemberMapper.updateMember(member);
	}
	public List<Member> pageList(Map<String,Object> params){
		return this.MemberMapper.pageList(params);
	}
	public Long pageCounts(Map<String,Object> p){
		return this.MemberMapper.pageCounts(p);
	}
	public Long checkMember(Map<String,Object> p){
		return this.MemberMapper.checkMember(p);
	}
}
