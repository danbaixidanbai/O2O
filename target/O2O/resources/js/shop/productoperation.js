$(function(){
    var productId=getQueryString('productId');
    var categoryUrl='/shopadmin/getproductcategorylist';
    var getproductUrl='/shopadmin/getproductbyid?productId='+productId;
    var productOperationUrl='/shopadmin/modifyproduct';

    var isEdit=false;

    if(productId){
        isEdit=true;
        getProductInfo();
    }else{
        getCategory();
        productOperationUrl='/shopadmin/addproduct';
    }

    function getProductInfo() {
        $.getJSON(getproductUrl,function (data) {
            if(data.success){
                var product=data.product;
                $('#product-name').val(product.productName);
                $('#product-desc').val(product.productDesc);
                $('#priority').val(product.priority);
                $('#normal-price').val(product.normalPrice);
                $('#promotion-price-').val(product.promotionPrice);

                var optionHtml='';
                var optionAddr=data.productCategoryList;
                var optionSelected=product.productCategory.productCategoryId;

                optionAddr.map(function (item, index) {
                    var isSelect =optionSelected === item.productCategoryId?'selected':'';
                    optionHtml += '<option data-value="'
                        + item.productCategoryId
                        + '"'
                        + isSelect
                        + '>'
                        + item.productCategoryName
                        + '</option>';
                });
                $('#category').html(optionHtml);
            }
        });
    }

    function getCategory() {
        $.getJSON(categoryUrl, function(data) {
            if (data.success) {
                var productCategoryList = data.data;
                var optionHtml = '';
                productCategoryList.map(function(item, index) {
                    optionHtml += '<option data-value="'
                        + item.productCategoryId + '">'
                        + item.productCategoryName + '</option>';
                });
                $('#category').html(optionHtml);
            }
        });

    }

    $('.detail-img-div').on('change', '.detail-img:last-child', function() {
        if ($('.detail-img').length < 6) {
            $('#detail-img').append('<input type="file" class="detail-img">');
        }
    });
    
    $('#submit').click(function () {
        var product={};
        product.productName = $('#product-name').val();
        product.productDesc = $('#product-desc').val();
        product.priority = $('#priority').val();
        product.normalPrice = $('#normal-price').val();
        product.promotionPrice = $('#promotion-price').val();
        product.productCategory={
            productCategoryId :  $('#category').find('option').not(
                function() {
                    return !this.selected;
                }).data('value')
        };
        product.productId=productId;

        var productImg=$('#small-img')[0].files[0];
        var formData=new FormData();
        formData.append('productImg',productImg);
        $('.detail-img').map(
            function(index, item) {
                if ($('.detail-img')[index].files.length > 0) {
                    formData.append('productImgs' + index,
                        $('.detail-img')[index].files[0]);
                }
            });
        formData.append("productStr",JSON.stringify(product));
        var verifyCodeActual = $('#j_captcha').val();
        if (!verifyCodeActual) {
            $.toast('请输入验证码！');
            return;
        }
        formData.append('verifyCodeActual',verifyCodeActual);
        $.ajax({
            url:productOperationUrl,
            type:'POST',
            data:formData,
            contentType : false,
            processData : false,
            cache : false,
            success : function(data) {
                if (data.success) {
                    $.toast('提交成功！');
                    $('#captcha_img').click();
                } else {
                    $.toast('提交失败！');
                    $('#captcha_img').click();
                }
            }
        });
    });

});