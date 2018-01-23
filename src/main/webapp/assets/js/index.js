/**
 * Created by chenguojun on 8/10/16.
 */
;
(function ($, window, document, undefined) {
   
    function getSubMenu(menus, menuId) {
        var subMenus = [];
        $.each(menus, function (i, m) {
            if (m.pId == menuId) {
                subMenus.push(m);
            }
        });
        return subMenus;
    }

    function getMenu(menus, menuId) {
        var subMenus = [];
        $.each(menus, function (i, m) {
            if (m.id == menuId) {
                subMenus.push(m);
            }
        });
        return subMenus;
    }

    function getTopMenu(menus) {
        var topMenus = [];
        $.each(menus, function (i, m) {
            if (m.pId == 0) {
                topMenus.push(m);
            } else {
                var subMenus = getMenu(menus, m.pId);
                if (subMenus.length == 0) {
                    topMenus.push(m);
                }
            }
        });
        return topMenus;
    }

    function recursionMenu(ele, menus, subMenus) {
        if (subMenus.length > 0) {
            ele += "<ul>";
            $.each(subMenus, function (i, m) {
            	var on = m.url=="/infoCenter"?"on":"";
                ele += ('<li class="'+on+'">'
                + '<a href="javascript:void();" data-url="' +root+ m.url
                + '" data-title="' + m.name
                + '"><i class="' + (m.icon == null ? "" : m.icon) + '"></i> '
                + m.name
                + '</a>');
                var sMenus = getSubMenu(menus, m.id);
                ele += '</li>';
            });
            ele += "</ul>";
        }
        return ele;
    }

    function initMenu(div) {
        $.ajax(
            {
                type: 'GET',
                url: "./menu",
                contentType: "application/json",
                dataType: "json",
                success: function (result) {
                    if (result.code === 200) {
                        var menus = result.data;
                        
                        var topMenus = getTopMenu(menus);
                        $.each(topMenus, function (i, m) {
                        	var class_="";
                        	if(i==0){
                        		class_ = "on";
                        	}
                            var ele = '<h3 class="'+class_+'">' + m.name
                                + '</h3>';
                            var subMenus = getSubMenu(menus, m.id);
                            if (subMenus.length > 0) {
                                ele = recursionMenu(ele, menus, subMenus);
                            }
                            var h3 = $(ele);
                            
                            $(div).append(h3);
                        });
                        var lis = $(div).find("li");
                        lis.on("mousedown",function(e){
                        	 e.stopPropagation ? e.stopPropagation() : e.cancelBubble = true;
                        	 $(div).find("li.on").removeClass("on");
                        	var li = $(this);
                        	li.addClass("on");
                        	var url = li.find("a").data("url");
                        	$("#rightMain").attr("src",url);
                        	
                        });
                        
                            $(".sideMenu").slide({
                                titCell: "h3",
                                targetCell: "ul",
                                defaultIndex: 0,
                                effect: 'slideDown',
                                delayTime: '500',
                                trigger: 'click',
                                triggerTime: '150',
                                defaultPlay: true,
                                returnDefault: false,
                                easing: 'easeInQuint',
                                endFun: function () {
                                    scrollWW();
                                }
                            });
                            $(window).resize(function () {
                                scrollWW();
                            });

                      
                       
                    } else if (result.code === 401) {
                        alert("token失效,请登录!");
                        window.location.href = './login';
                    }
                },
                error: function (err) {
                }
            }
        );
    }


    $(document).ready(function () {
        initMenu("#side-menu");
    });
})(jQuery, window, document);

function scrollWW() {
    if ($(".side").height() < $(".sideMenu").height()) {
        $(".scroll").show();
        var pos = $(".sideMenu ul:visible").position().top - 38;
        $('.sideMenu').animate({top: -pos});
    } else {
        $(".scroll").hide();
        $('.sideMenu').animate({top: 0});
        n = 1;
    }
}
function menuHide(){
	$(".main").toggleClass("menuHide");
	$(".menubtn").toggleClass("menuHide");
}
function menuHide(){
	$(".main").toggleClass("menuHide");
	$(".menubtn").toggleClass("menuHide");
}

var n = 1;

function menuScroll(num) {
    var Scroll = $('.sideMenu');
    var ScrollP = $('.sideMenu').position();
    /*alert(n);
    alert(ScrollP.top);*/
    if (num == 1) {
        Scroll.animate({top: ScrollP.top - 38});
        n = n + 1;
    } else {
        if (ScrollP.top > -38 && ScrollP.top != 0) {
            ScrollP.top = -38;
        }
        if (ScrollP.top < 0) {
            Scroll.animate({top: 38 + ScrollP.top});
        } else {
            n = 1;
        }
        if (n > 1) {
            n = n - 1;
        }
    }
}
function alertSucc(msg){
	layer.msg(msg, {icon: 1,offset: 'rb'});
}
function alertInfo(msg,callBack){
	layer.alert(msg, {
//		  skin: 'layui-layer-molv', //样式类名
		  closeBtn: 1
		}, function(){
			callBack();
		});
}