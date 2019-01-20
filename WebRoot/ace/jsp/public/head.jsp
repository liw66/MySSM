<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<meta charset="utf-8" />
		<title>MySSM</title>

		<meta name="description" content="overview &amp; stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />

		<!-- bootstrap & fontawesome -->
		<link rel="stylesheet" href="<%=application.getInitParameter("assetsurl")%>/css/bootstrap.min.css" />
		<link rel="stylesheet" href="<%=application.getInitParameter("assetsurl")%>/font-awesome/4.5.0/css/font-awesome.min.css" />

		<!-- page specific plugin styles -->

		<!-- text fonts -->
		<link rel="stylesheet" href="<%=application.getInitParameter("assetsurl")%>/css/fonts.googleapis.com.css" />

		<!-- ace styles -->
		<link rel="stylesheet" href="<%=application.getInitParameter("assetsurl")%>/css/ace.min.css" class="ace-main-stylesheet" id="main-ace-style" />

		<!--[if lte IE 9]>
			<link rel="stylesheet" href="<%=application.getInitParameter("assetsurl")%>/css/ace-part2.min.css" class="ace-main-stylesheet" />
		<![endif]-->
		<link rel="stylesheet" href="<%=application.getInitParameter("assetsurl")%>/css/ace-skins.min.css" />
		<link rel="stylesheet" href="<%=application.getInitParameter("assetsurl")%>/css/ace-rtl.min.css" />

		<!--[if lte IE 9]>
		  <link rel="stylesheet" href="<%=application.getInitParameter("assetsurl")%>/css/ace-ie.min.css" />
		<![endif]-->

		<!-- inline styles related to this page -->

		<!-- ace settings handler -->
		<script src="<%=application.getInitParameter("assetsurl")%>/js/ace-extra.min.js"></script>

		<!-- HTML5shiv and Respond.js for IE8 to support HTML5 elements and media queries -->

		<!--[if lte IE 8]>
		<script src="<%=application.getInitParameter("assetsurl")%>/js/html5shiv.min.js"></script>
		<script src="<%=application.getInitParameter("assetsurl")%>/js/respond.min.js"></script>
		<![endif]-->
	</head>