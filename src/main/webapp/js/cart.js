$(function(){
    // $(".c-sum_num").click(function(){
    // 	console.info($(this).text());
    // })
    totl();
    goodsnum();
    // 全选
    $(".all").click(function() {
        all = $(this).prop("checked")
        $(".each").each(function() {
            $(this).prop("checked", all);
        })
    })
    // 减少商品数量
    $(".reduce").click(function(){
        var num = $(this).siblings(".text_num").val();
        if(num>0){
            num--;
            $(this).siblings(".text_num").val(num);
        };
        var price=$(this).parents().siblings(".c-price").children(".c-price_num").text();
        var sum_num = $(this).parents().siblings(".c-sum").children(".c-sum_num");
        var sum = parseFloat(price*num);
        // console.log(sum);
        $(sum_num).text(sum);
        totl();
        goodsnum();
    });
    // 增加商品数量
    $(".add").click(function(){
        var num = $(this).siblings(".text_num").val();
        num++;
        $(this).siblings(".text_num").val(num);
        var price=$(this).parents().siblings(".c-price").children(".c-price_num").text();
        var sum_num = $(this).parents().siblings(".c-sum").children(".c-sum_num");
        var sum = parseFloat(price*num);
        // console.log(sum);
        $(sum_num).text(sum);
        totl();
        goodsnum();
    });
    // 删除商品
    $(".remove").click(function(){
        $(this).parents(".carts-goods").remove();
        totl();
        goodsnum();
    });
    // 总价
    function totl(){
        let sumprice = 0;
        $.each($(".c-sum_num"),function() {
            sumprice+=parseFloat($(this).text());
            $(".show-money span").text(sumprice);
        });
		return sumprice;
    };
    // 统计商品数量
    function goodsnum(){
        let goods_num=0;
        $.each($(".text_num"),function() {
            goods_num+=parseInt($(this).val());
            $(".goods_num span").text(goods_num);
        });
    };
    // 结算
    $(".btn").click(function() {
        var formData = { total: totl() }; // 假设totl返回的是一个数字
        var $btn = $(this); // 缓存按钮的jQuery对象以便稍后使用
        $btn.prop('disabled', true); // 禁用按钮以防止重复点击
    
        $.ajax({
            type: "POST",
            url: "buyer/sale",
            data: JSON.stringify(formData), // 转换为JSON字符串
            contentType: "application/json; charset=utf-8", // 设置内容类型
            dataType: "json", // 设置期望的返回数据类型
            success: function(response) {
                // 处理成功响应
                $btn.prop('disabled', false); // 重新启用按钮
                alert(response);
            }
        });
    });
});