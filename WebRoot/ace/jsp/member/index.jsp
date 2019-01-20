<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<jsp:include page="../public/head.jsp" />

<body class="no-skin">
	<jsp:include page="../public/header.jsp" />

	<div class="main-container ace-save-state" id="main-container">

		<jsp:include page="../public/sidebar.jsp" />
		<div class="main-content">
			<div class="main-content-inner">
				<jsp:include page="../public/breadcrumbs.jsp" />
				<div class="page-content">
					<jsp:include page="../public/set.jsp" />
					<div class="page-header">
						<form action="../member/index" method="post" class="form-inline definewidth m20">
							用户名称： <input type="text" name="user" id="user"
								class="abc input-default" placeholder="" value="">&nbsp;&nbsp;
							<button type="submit" class="btn btn-primary" type="button">查询</button>
							&nbsp;&nbsp; <a class="btn btn-success" href="../member/add">新增用户</a>
							<a class="btn btn-success" href="../member/import1">导入EXCEL</a> <a
								class="btn btn-success" href="../member/export">导出EXCEL</a> <a
								class="btn btn-success" href="../member/mail">发送邮件</a>
						</form>
					</div>
					<!-- /.page-header -->
					<div class="row">
						<div class="col-xs-12">
							<!-- PAGE CONTENT BEGINS -->
							<div class="row">
								<div class="col-xs-12">
									<table id="simple-table"
										class="table  table-bordered table-hover">
										<thead>
											<tr>
												<th>用户名</th>
												<th>用户组</th>
												<th>性别</th>
												<th>生日</th>
												<th>电话</th>
		                                        <th>Q&nbsp;Q</th>
		                                        <th>邮箱</th>
												<th>操作</th>
											</tr>
										</thead>

										<c:forEach items="${list}" var="Member">
											<tr>
												<td>${Member.user}</td>
												<td>${Member.uid}</td>
												<td><script type="text/javascript">
													var s = ${Member.sex};
													if (s==0) {document.write("保密");}
													else if (s==1) {document.write("男");}
													else if (s==2) {document.write("女");}
												</script></td>
												<td><script type="text/javascript">
													Date.prototype.format=function(format){var o={"M+":this.getMonth()+1,"d+":this.getDate(),"h+":this.getHours(),"m+":this.getMinutes(),"s+":this.getSeconds(),"q+":Math.floor((this.getMonth()+3)/3),"S":this.getMilliseconds()};if(/(y+)/.test(format)){format=format.replace(RegExp.$1,(this.getFullYear()+"").substr(4-RegExp.$1.length))}for(var k in o){if(new RegExp("("+k+")").test(format)){format=format.replace(RegExp.$1,RegExp.$1.length==1?o[k]:("00"+o[k]).substr((""+o[k]).length))}}return format};
													var b = ${Member.birthday};
													var d = new Date(parseInt(b) * 1000);
													document.write(d.format("yyyy-MM-dd"));
												</script></td>
												<td>${Member.phone}</td>
												<td>${Member.qq}</td>
												<td>${Member.email}</td>
												<td><a href="edit?uid=${Member.uid}">编辑</a>&nbsp;<c:if test="${Member.uid!=1}">
														<a class="del" href="javascript:;" val='del?uid=${Member.uid}'> 删除</a>
													</c:if></td>
											</tr>
										</c:forEach>
										<jsp:include page="../public/page.jsp">
											<jsp:param name="url" value="member/index" />
										</jsp:include>
									</table>
								</div>
								<!-- /.span -->
							</div>
							<!-- /.row -->
							<!-- PAGE CONTENT ENDS -->
						</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->
				</div>

			</div>
		</div>
		<!-- /.main-content -->
		<jsp:include page="../public/footer.jsp" />

	</div>
	<!-- /.main-container -->

	<jsp:include page="../public/footerjs.jsp" />
	<script type="text/javascript">
		$(function () {
			$('.del').click(function(){
				var url = $(this).attr('val');
				bootbox.confirm({
					title : "系统提示",
					message : "是否要删除该用户?",
					callback : function(result) {
						if (result) {
							location.href = url;
						}
					},
					buttons : {
						"cancel" : {
							"label" : "取消"
						},
						"confirm" : {
							"label" : "确定",
							"className" : "btn-danger"
						}
					}
				});
			});
		});		
	</script>
</body>
</html>
