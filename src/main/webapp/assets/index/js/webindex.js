// JavaScript Document
		$(function(){
				$("#newsList li").click(function(){
					$(this).addClass("activeNav").siblings().removeClass("activeNav");
					});
				})