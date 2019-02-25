$(function () {
    var listUrl='/shopadmin/getproductcategorylist';
    var addUrl='/shopadmin/addproductcategorys';
    var deleteUrl='/shopadmin/removeproductcategory';
    getList();
    function getList(){
        $.getJSON(listUrl,function (data) {
            if(data.success){
                var list=data.data;
                $('.category-wrap').html('');
                var tempHtml='';
                list.map(function (item, index) {
                    tempHtml +='<div class="row row-product-category now">'
                        + '<div class="col-33 product-category-name">'
                        + item.productCategoryName
                        + '</div>'
                        + '<div class="col-33 priority">'
                        + item.priority
                        + '</div>'
                        + '<div class="col-33 "><a href="#" class="button delete" data-id="'
                        + item.productCategoryId
                        + '">删除</a></div>'
                        + '</div>';
                });
                $('.category-wrap').append(tempHtml);
            }
        });
    }

    $('#new').click(function () {
        var tempHtml='<div class="row row-product-category temp">'
            +'<div class="col-33 "><input class="category-input category" type="text" placeholder="商品类别"></div>'
            +'<div class="col-33"><input class="category-input priority" type="number" placeholder="优先级"></div>'
            +'<div class="col-33"><a href="#" class="button delete">删除</a></div>'
            +'</div>';
        $('.category-wrap').append(tempHtml);
    });
    $('#submit').click(function () {
        var tempArr=$('.temp');
        var productCategoryList=[];
        tempArr.map(function (index, item) {
            var tempObj={};
            tempObj.productCategoryName=$(item).find('.category').val();
            tempObj.priority=$(item).find('.priority').val();
            if(tempObj.productCategoryName &&tempObj.priority){
                productCategoryList.push(tempObj);
            }
        });
        $.ajax({
            url:addUrl,
            type:'POST',
            data: JSON.stringify(productCategoryList),
            contentType:'application/json',
            success:function (data) {
                if(data.success){
                    $.toast('提交成功！');
                    getList();
                }else $.toast(data.errMsg);
            }
        });
    });

    $('.category-wrap').on('click','.row-product-category.temp .delete',function () {
        console.log($(this).parent().parent());
        $(this).parent().parent().remove();
    });

    $('.category-wrap').on('click','.row-product-category.now  .delete',function (e) {
        var target=e.currentTarget;
        $.confirm('是否删除？',function () {
            $.ajax({
                url:deleteUrl,
                type:'post',
                dataType:'json',
                data:{productCategoryId : target.dataset.id},
                success:function (data) {
                    if(data.success){
                        $.toast('删除成功！');
                        getList();
                    }else{
                        $.toast('删除失败：'+data.errMsg);
                    }
                }
            });
        });
    });
});