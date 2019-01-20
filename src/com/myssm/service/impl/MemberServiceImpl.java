package com.myssm.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myssm.dao.MemberDao;
import com.myssm.entity.Member;
import com.myssm.service.MemberService;

@Service
public class MemberServiceImpl implements MemberService {
	@Resource
	private MemberDao MemberDao;

	public Member getMember(Member member) {
		Member m = MemberDao.getMember(member);
		if (m != null) {
			return m;
		}
		return null;
	}
	public List<Member> getMemberList() {
		return MemberDao.getMemberList();
	}
	public int updateLogin(int uid) {
		return MemberDao.updateLogin(uid);
	}
	public int regMember(String user,String password,String qq) {
		return MemberDao.regMember(user,password,qq);
	}
	public int deleteMember(int uid) {
		return MemberDao.deleteMember(uid);
	}
	public int saveMember(Member member){
		return MemberDao.saveMember(member);
	}
	public Member queryMember(int uid){
		return MemberDao.queryMember(uid);
	}
	public int updateMember(Member member){
		return MemberDao.updateMember(member);
	}
	public List<Member> pageList(Map<String,Object> params){
		return this.MemberDao.pageList(params);
	}
	
	public Long pageCounts(Map<String,Object> p){
		return this.MemberDao.pageCounts(p);
	}
	public Long checkMember(Map<String,Object> p){
		return this.MemberDao.checkMember(p);
	}
	/** 
     * 事务处理必须抛出异常，Spring才会帮助事务回滚 
     * rollbackFor=Exception.class 不加只能回滚unchecked例外RuntimeException，加了可以回滚checked例外Exception
     * @param Members 
     */  
      
	@Transactional(rollbackFor=Exception.class)
    public void insertMember(List<Member> members) {  
        // TODO Auto-generated method stub  
        for (int i = 0; i < members.size(); i++) {  
            if(i<2){  
                this.MemberDao.saveMember(members.get(i));  
            }  
            else {  
                throw new RuntimeException();  
            }  
        }  
    } 
}
