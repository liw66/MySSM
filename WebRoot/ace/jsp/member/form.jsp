<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
					<!-- /section:settings.box -->
					<div class="row">
						<div class="col-xs-12">
							<!-- PAGE CONTENT BEGINS -->
							<form action="../member/update" method="post" class="form-horizontal" id="memberForm">
								<input type="hidden" id="uid" name="uid" value="${Member.uid}" /> 	
								<input type="hidden" id="birthday" name="birthday" value="" />
								<input type="hidden" id="t" name="t" value="" /> 
								<div class="form-group">
									<label class="col-sm-1 control-label no-padding-right" for="form-field-1"> 用户名 </label>
									<div class="col-sm-9">
										<input type="text" id="user" name="user" value="${Member.user}" class="rcol-xs-10 col-sm-5" ${Member.uid gt 0  ?"disabled":""} />

									</div>
								</div>
								<div class="space-4"></div>

								<div class="form-group">
									<label class="col-sm-1 control-label no-padding-right" for="form-field-10"> 用户组 </label>
									<div class="col-sm-9">

										<span class="help-inline col-xs-12 col-sm-7"> <span class="middle">请慎重选择用户组</span>
										</span>
									</div>
								</div>
								<div class="space-4"></div>

								<div class="form-group">
									<label class="col-sm-1 control-label no-padding-right" for="form-field-2"> 用户密码 </label>
									<div class="col-sm-9">
										<input type="password" name="password" id="password" placeholder="用户密码" class="col-xs-10 col-sm-5" value=""> <span class="help-inline col-xs-12 col-sm-7">
											<span class="middle">留空不修改</span>
										</span>
									</div>
								</div>
								<div class="space-4"></div>

								<div class="form-group">
									<label class="col-sm-1 control-label no-padding-right" for="form-field-4"> 头像 </label>
									<div class="col-sm-9">
										<input type="text" name="head" id="head" placeholder="head" class="col-xs-10 col-sm-5" value="${Member.head}"> <span
											class="help-inline col-xs-12 col-sm-7"> <span class="middle">仅支持jpg、gif、png、bmp、jpeg，且小于1MB。</span>
										</span>
									</div>
								</div>
								<div class="space-4"></div>

								<div class="form-group">
									<label class="col-sm-1 control-label no-padding-right" for="form-field-4"> 性别 </label>
									<div class="col-sm-9">
										<select id="sex" name="sex" class="multiselect">
											<option value="0" ${Member.sex eq 0  ?"selected":""}>保密</option>
											<option value="1" ${Member.sex eq 1  ?"selected":""}>男</option>
											<option value="2" ${Member.sex eq 2  ?"selected":""}>女</option>
										</select>
									</div>
								</div>

								<div class="space-4"></div>

								<div class="form-group">
									<label class="col-sm-1 control-label no-padding-right" for="id-date-picker-1">生日</label>
									<div class="col-xs-9 col-sm-9">
										<div class="input-group col-xs-5">
											<input class="form-control" id="birthday1" name="birthday1" value="${Member.birthday}" type="text" data-date-format="yyyy-mm-dd" /> <span
												class="input-group-addon"> <i class="ace-icon fa fa-calendar bigger-110"></i>
											</span>
										</div>
									</div>
								</div>
								<div class="space-4"></div>

								<div class="form-group">
									<label class="col-sm-1 control-label no-padding-right" for="form-field-6"> 电话号码 </label>
									<div class="col-sm-9">
										<input type="text" name="phone" id="phone" placeholder="电话号码" class="col-xs-10 col-sm-5" value="${Member.phone}"> <span
											class="help-inline col-xs-12 col-sm-7"> <span class="middle"></span>
										</span>
									</div>
								</div>

								<div class="space-4"></div>

								<div class="form-group">
									<label class="col-sm-1 control-label no-padding-right" for="form-field-7"> Q&nbsp;&nbsp;Q </label>
									<div class="col-sm-9">
										<input type="text" name="qq" id="qq" placeholder="QQ" class="col-xs-10 col-sm-5" value="${Member.qq}"> <span class="help-inline col-xs-12 col-sm-7">
											<span class="middle"></span>
										</span>
									</div>
								</div>

								<div class="space-4"></div>

								<div class="form-group">
									<label class="col-sm-1 control-label no-padding-right" for="form-field-8"> E-mail </label>
									<div class="col-sm-9">
										<input type="email" name="email" id="email" placeholder="E-mail" class="col-xs-10 col-sm-5" value="${Member.email}"> <span
											class="help-inline col-xs-12 col-sm-7"> <span class="middle"></span>
										</span>
									</div>
								</div>
								<div class="space-4"></div>

								<div class="col-md-offset-2 col-md-9">
									<button class="btn btn-info" type="submit">
										<i class="icon-ok bigger-110"></i> 提交
									</button>

									&nbsp; &nbsp; &nbsp;
									<button class="btn" type="button" id="re">
										<i class="icon-undo bigger-110"></i> 重置
									</button>
								</div>								
							</form>
						</div>
					</div>
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

			$('#birthday1').datepicker({
				format : 'yyyy-mm-dd',//选择日期后，文本框显示的日期格式 
				weekStart : 1,//一周从哪一天开始。0（星期日）到6（星期六）
				autoclose : true,//选择日期后自动关闭 
				todayBtn : 'linked',//显示今天
				language : 'en'//汉化 
			});
			var d = new Date(parseInt($('#birthday1').val()) * 1000);
			$('#birthday1').datepicker('setDate', d);
			$('#t').val(parseInt(new Date().getTime() / 1000));
			
			$('#re').click(function(){
				$('#memberForm')[0].reset();
				var d = new Date(parseInt($('#birthday1').val()) * 1000);
				$('#birthday1').datepicker('setDate', d);
				$('#t').val(parseInt(new Date().getTime() / 1000));
			});

			$('#memberForm').submit(function() {

				if ($('#password').val() == '') {
					$('#password').prop('name', 'password1');
				}
				if ($('#uid').val() != '') {
					$('#t').prop('name', 't1');
				}
				var str;
				if ($('#birthday1').val() == '') {
					str = '1970-01-01';
				} else {
					str = $('#birthday1').val();
				}
				var dTime = new Date(str.replace(new RegExp('-', 'g'), '/'));
				//日期转长整型
				var lTime = parseInt(dTime.getTime() / 1000);//秒级
				$('#birthday').val(lTime);
				
				if ($('#uid').val() == '') {
					//flag用于判断是否提交表单
					var flag = false;
					if ($('#user').val() == '') {
						bootbox.alert('请输入您的用户名！');
						return flag;
					} else {
						$.ajax({
							type : 'POST',
							url : '../member/check',
							data : 'user=' + $('#user').val(),
							dataType : 'text',
							//同步  
							async : false,
							success : function(data) {
								if ('false' == data) {
									bootbox.alert('用户名已经存在！');
									flag = false;
								} else if ('true' == data) {
									flag = true;
								}
							},
							error : function() {
								bootbox.alert('请求失败！');
								flag = false;
							}
						});
					}
					return flag;
				}
			});
		});
	</script>
</body>
</html>

