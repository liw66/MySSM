<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="page" value="${sessionScope.page}" />
<c:set var="path" value="${pageContext.request.contextPath}" />
<c:set var="url" value="${param.url}" />
<c:set var="urlParams" value="${param.urlParams}" />
<c:set var="pathurl" value="${path}/${url}" />
<tr>
	<td colspan="8">
		共${page.totalCount}条记录 共${page.totalPage}页 每页显示${page.everyPage}条
		当前第${page.currentPage}页&nbsp;
		<c:choose>
			<c:when test="${page.hasPrePage eq false}">
 			    &lt&lt首页&nbsp;&lt上页&nbsp;
 			</c:when>
			<c:otherwise>
				<a href="${pathurl}?&pageAction=first${urlParams}">&lt&lt首页&nbsp;</a>&nbsp;
				<a href="${pathurl}?pageAction=previous${urlParams}" />&lt上一页</a>
			</c:otherwise>
		</c:choose>
		&nbsp;||&nbsp;
		<c:choose>
			<c:when test="${page.hasNextPage eq false}">
 			    &nbsp;下页&gt&nbsp;尾页&gt&gt
 			</c:when>
			<c:otherwise>
				<a href="${pathurl}?&pageAction=next${urlParams}">下一页&gt&nbsp;</a>&nbsp;
				<a href="${pathurl}?pageAction=last${urlParams}" />末页&gt&gt</a>
			</c:otherwise>
		</c:choose>
		&nbsp;
		<SELECT name="indexChange" id="indexChange"
			onchange="getCurrentPage(this.value);">
			<c:forEach var="index" begin="1" end="${page.totalPage}" step="1">
				<option value="${index}" ${page.currentPage eq index ? "selected" : ""}>
					第${index}页
				</option>
			</c:forEach>
		</SELECT>
		&nbsp;
		每页显示:<select name="everyPage" id="everyPage" onchange="setEveryPage(this.value);">
			       <c:forEach var="pageCount" begin="10" end="${page.totalCount}" step="10">			       	    
						<option value="${pageCount}" ${page.everyPage eq pageCount ? "selected" : ""}>
							${pageCount}条
						</option>
					</c:forEach>
		       </select>
	</td>
</tr>
<div style='display: none'>
	<a class=listlink id="indexPageHref" href='#'></a>
</div>
<script>
function getCurrentPage(index){	
	var a = document.getElementById("indexPageHref");	
	a.href = '${pathurl}?pageAction=gopage&pageKey='+index+'${urlParams}';        
    a.setAttribute("onclick",'');          
    a.click("return false");   
}
function setEveryPage(everyPage){	
	var a = document.getElementById("indexPageHref");
	var currentPage = document.getElementById('indexChange').value;
	a.href = '${pathurl}?pageAction=setpage&pageKey='+everyPage+'${urlParams}';       
    a.setAttribute("onclick",'');          
    a.click("return false");   
}
function sortPage(sortName){	
	var a = document.getElementById("indexPageHref");	
	a.href = '${pathurl}?pageAction=sort&pageKey='+sortName+'${urlParams}';      
    a.setAttribute("onclick",'');      
    a.click("return false");   
}
</script>