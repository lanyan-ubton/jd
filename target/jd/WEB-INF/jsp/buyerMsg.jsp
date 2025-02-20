<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <meta charset="utf-8">
        <title>用户信息</title>
        <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/page.css" />
        <link href="${pageContext.request.contextPath}/css/bootstrap.css" rel="stylesheet" type="text/css" />
        <link href="${pageContext.request.contextPath}/css/icons.min.css" rel="stylesheet" type="text/css" />
        <script src="${pageContext.request.contextPath}/js/jquery-3.6.0.js" type="text/javascript" charset="utf-8"></script>
    </head>
    <body>
        <div class="page" id="app">
            <div class="nav-left" v-show="navLeftFlag" ref="navLeft">
                <div class="LogoName">
                    用户信息管理
                </div>
                <div class="navDiv">
                    <div class="navName">导航</div>
                    <div class="nav-list">
                        <ul>
                            <li class="nav-tab a_active waves-effect">
                                <a href="${pageContext.request.contextPath}/view/buyerMeg" class="li-a active" target="iframe">
                                    <i class='bx bx-home-smile'></i>
                                    基本信息
                                </a>
                            </li>
                            <li class="nav-tab nav-ul">
                                <a href="javascript:void[0]" class="li-a" target="iframe"><i class='bx bx-layer'></i> 购物车管理
                                    <i class='bx bx-chevron-right' style="float: right;"></i></a>
                                <div class="nav-box">
                                    <a href="${pageContext.request.contextPath}/buyer/shoppingCart" class="li-a-a" target="iframe">购物车</a>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="nav-right" ref="navRight">
                <div class="nav-top">
                    <button type="button" class="btn btn-primary btn-sm hiddenBtn" style="line-height: 10px;" @click="isShowLeft">
                        <i class="bx bx-grid-alt" style="font-size: 16px;"></i>
                    </button>
                </div>
                 <div class="content-page" ref="cPage">
                    <iframe name="iframe" width="100%" height="100%" frameborder="0" src="${pageContext.request.contextPath}/view/buyerMeg"></iframe>
                </div>
            </div>
        </div>
        <script src="${pageContext.request.contextPath}/js/vue.js" type="text/javascript" charset="utf-8"></script>
        <script type="text/javascript">
            $(function() {
                let navflag = false;
                $('.nav-tab').click(function() {
                    $(this).siblings().each(function() {
                        $(this).removeClass('a_active');
                        // $(this).removeClass('a_active');
                        $(this).find('.nav-box').css('height', '0')
                        //关闭右侧箭头
                        if ($(this).attr('class').indexOf('nav-ul') != -1) {
                            $(this).find('.bx-chevron-right').css('transform', 'rotateZ(0deg)')
                            $(this).find('.bx-chevron-right').css('transition', 'all .5s')
                            $(this).removeClass('nav-show')
                            // $(this).find('div').removeClass('nav-box')
                        }
                    })
                    //当前选中
                    $(this).addClass('a_active')
                    $(this).find('.li-a').addClass('active')
                    // 打开右侧箭头
                    $(this).find('.bx-chevron-right').css('transform', 'rotateZ(90deg)')
                    $(this).find('.bx-chevron-right').css('transition', 'all .5s')
                    $(this).addClass('nav-show')
                    // $(this).find('div').addClass('nav-box')
                })
                /* 二级菜单a点击事件 */
                $(".li-a-a").click(function() {
                    $(".li-a-a").each(function() {
                        $(this).removeClass('active-li-a');
                    })
                    $(this).addClass('active-li-a');
                })

            })
            const vue = new Vue({
                el: '#app',
                data: {
                    navLeftFlag: true
                },
                methods: {
                    isShowLeft() {
                        if (this.navLeftFlag ) {
                            this.$refs.navRight.style.paddingLeft = '0px'
                            this.$refs.cPage.style.left = '0px';
                            this.navLeftFlag = false;
                        } else {
                            this.$refs.navRight.style.paddingLeft = '240px';
                            this.$refs.cPage.style.left = '240px';
                            this.navLeftFlag = true; 
                        }
                    }
                }
            })
        </script>
    </body>
</html>
