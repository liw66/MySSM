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
					
				</div>

			</div>
		</div>
		<!-- /.main-content -->
		<jsp:include page="../public/footer.jsp" />
		
	</div>
	<!-- /.main-container -->

	<jsp:include page="../public/footerjs.jsp" />

</body>
</html>
