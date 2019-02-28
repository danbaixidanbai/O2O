$(function(){
    getShopList();
    function getShopList() {
        $.ajax({
            url:'/shopadmin/getshoplist',
            type:'get',
            dataType:'json',
            success: function (data) {
                if(data.success){
                    handlerShopList(data.shopList);
                    handlerUser(data.user);
                }
            }
        });
    }
    
    function handlerUser(data) {
        $('#user-name').text(data.name);
    }
    
    function handlerShopList(data) {
        var  html='';
        data.map(function (item, index) {
            html+='<div class="row row-shop"><div class="col-40">'
                + item.shopName+'</div><div class="col-40">'
                + shopStatus(item.enableStatus)+'</div><div class="col-20">'
                + goShop(item.enableStatus,item.shopId)+'</div></div>'
        });
        $(".shop-wrap").html(html);
    }

    function shopStatus(data){
        if(data==1){
            return '审核通过';
        }else if(data==0){
            return '审核中';
        }else if(data==-1){
            return '店铺非法';
        }
    }
    function goShop(status,shopId) {
        if(status==1){
            return '<a href="/shopadmin/shopmanage?shopId='+shopId+'">进入</a>';
        }else return '';
    }
});