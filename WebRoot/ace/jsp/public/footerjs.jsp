<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!-- basic scripts -->

<!--[if !IE]> -->
<script
	src="<%=application.getInitParameter("assetsurl")%>/js/jquery-2.1.4.min.js"></script>

<!-- <![endif]-->

<!--[if IE]>
<script src="<%=application.getInitParameter("assetsurl")%>/js/jquery-1.11.3.min.js"></script>
<![endif]-->
<script type="text/javascript">
			if('ontouchstart' in document.documentElement) document.write("<script src='<%=application.getInitParameter("assetsurl")%>/js/jquery.mobile.custom.min.js'>"
						+ "<"+"/script>");
</script>
<script
	src="<%=application.getInitParameter("assetsurl")%>/js/bootstrap.min.js"></script>

<!-- page specific plugin scripts -->

<!--[if lte IE 8]>
		  <script src="<%=application.getInitParameter("assetsurl")%>/js/excanvas.min.js"></script>
		<![endif]-->
<script
	src="<%=application.getInitParameter("assetsurl")%>/js/jquery-ui.custom.min.js"></script>
<script
	src="<%=application.getInitParameter("assetsurl")%>/js/jquery.ui.touch-punch.min.js"></script>
<script
	src="<%=application.getInitParameter("assetsurl")%>/js/jquery.easypiechart.min.js"></script>
<script
	src="<%=application.getInitParameter("assetsurl")%>/js/jquery.sparkline.index.min.js"></script>
<script
	src="<%=application.getInitParameter("assetsurl")%>/js/jquery.flot.min.js"></script>
<script
	src="<%=application.getInitParameter("assetsurl")%>/js/jquery.flot.pie.min.js"></script>
<script
	src="<%=application.getInitParameter("assetsurl")%>/js/jquery.flot.resize.min.js"></script>

<!-- ace scripts -->
<script
	src="<%=application.getInitParameter("assetsurl")%>/js/ace-elements.min.js"></script>
<script
	src="<%=application.getInitParameter("assetsurl")%>/js/ace.min.js"></script>
<script
	src="<%=application.getInitParameter("assetsurl")%>/js/bootbox.js"></script>
<script
	src="<%=application.getInitParameter("assetsurl")%>/js/bootstrap-datepicker.min.js"></script>
