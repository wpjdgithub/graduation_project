<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="../jquery.js"></script>
<%@ include file="/Normal/bootstrap_cong.jsp" %>
<%@ include file="/Normal/highChart.jsp" %>
<!--可无视-->
<link rel="stylesheet" type="text/css" href="../Paginator/base.css"/>

<!--主要样式-->
<link rel="stylesheet" type="text/css" href="../Paginator/pageGroup.css"/>
</head>
<body>
<div id="container_key" style="min-width:400px;height:400px"></div>

	<script>
	$(document).ready(function () {
    $('#container_key').highcharts({
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false
        },
        title: {
            text: '2014 某网站各浏览器浏览量占比'
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: true,
                    format: '<b>{point.name}</b>: {point.percentage:.1f} %',
                    style: {
                        color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                    }
                }
            }
        },
        series: [{
            type: 'pie',
            name: '浏览器访问量占比',
            data: [
                ['Firefox',   45.0],
                ['IE',       26.8],
                {
                    name: 'Chrome',
                    y: 12.8,
                    sliced: true,
                    selected: true
                },
                ['Safari',    8.5],
                ['Opera',     6.2],
                ['其他',   0.7]
            ]
        }]
    });
});
</script>
</body>
</html>