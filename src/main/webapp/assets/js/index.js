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
                ele += ('<li data-level="sub">'
                + '<a data-url="' + m.url
                + '" data-title="' + m.name
                + '" href="javascript:void(0);"><i class="' + (m.icon == null ? "glyphicon glyphicon-list" : m.icon) + '"></i> '
                + m.name
                + '</a>');
                var sMenus = getSubMenu(menus, m.id);
                ele += '</li>';
            });
            ele += "</ul>";
        }
        return ele;
    }

    function initMenu(ul) {
        $.ajax(
            {
                type: 'GET',
                url: "./security/menu",
                contentType: "application/json",
                dataType: "json",
                success: function (result) {
                    if (result.code === 200) {
                        var menus = result.data;
                        
                        var topMenus = getTopMenu(menus);
                        $.each(topMenus, function (i, m) {
                            var ele = '<li data-level="top">'
                                + '<a data-url="' + m.url
                                + '" data-title="' + m.name
                                + '" href="javascript:void(0);"><i class="' + (m.icon == null ? "glyphicon glyphicon-list" : m.icon) + '"></i> '
                                + m.name
                                + '</a>';
                            var subMenus = getSubMenu(menus, m.id);
                            if (subMenus.length > 0) {
                                ele = recursionMenu(ele, menus, subMenus);
                            }
                            ele += '</li>';
                            var li = $(ele);
                            li.find("li[data-level=sub]").parents("li[data-level=top]").addClass("submenu").find("a:eq(0)").append('<span class="caret pull-right"></span>');
                            $(ul).append(li);
                        });
                        $(ul).find("li.submenu > a").click(function (e) {
                            e.preventDefault();
                            var $li = $(this).parent("li");
                            var $ul = $(this).next("ul");
                            if ($li.hasClass("open")) {
                                $ul.slideUp(150);
                                $li.removeClass("open");
                            } else {
                                if ($li.parent("ul").hasClass("nav")) {
                                    $(".nav > li > ul").slideUp(150);
                                    $(".nav > li").removeClass("open");
                                }
                                $ul.slideDown(150);
                                $li.addClass("open");
                            }
                        });

                        $(ul).find("li[class!=submenu] > a").click(function (e) {
                            e.preventDefault();
                            var $li = $(this).parent("li");
                            if ($li.parent("ul").hasClass("nav")) {
                                $(".nav > li > ul").slideUp(150);
                                $(".nav > li").removeClass("open");
                            }
                            $(ul).find("li.current").removeClass("current");
                            $(this).parents("li").addClass("current");
                            $li.parent("ul").parent("li").addClass("open");
                        });

                        $(ul).find("li[class!=submenu] > a")
                            .each(function () {
                                    var url = $(this).attr("data-url");
                                   
                                }
                            );
                        refreshHref(ul);
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
