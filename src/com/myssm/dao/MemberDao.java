package com.myssm.dao;

import java.util.List;
import java.util.Map;

import com.myssm.entity.Member;
public interface MemberDao {
	public Member getMember(Member member);
	public List<Member> getMemberList();
	public int updateLogin(int uid);
	public int regMember(String user,String password,String qq);
	public int deleteMember(int uid);
	public int saveMember(Member member);
	public Member queryMember(int uid);
	public int updateMember(Member member);
	public List<Member> pageList(Map<String,Object> params);	
	public Long pageCounts(Map<String,Object> p);
	public Long checkMember(Map<String,Object> p);
}
