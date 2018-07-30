<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../head.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib  uri="http://java.sun.com/jsp/jstl/functions"   prefix="fn"%>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>babasport-add</title>
    <style type="">
        .h2_ch a:hover, .h2_ch a.here {
            color: #FFF;
            font-weight: bold;
            background-position: 0px -32px;
        }
        .h2_ch a {
            float: left;
            height: 32px;
            margin-right: -1px;
            padding: 0px 16px;
            line-height: 32px;
            font-size: 14px;
            font-weight: normal;
            border: 1px solid #C5C5C5;
            background: url('/images/admin/bg_ch.gif') repeat-x scroll 0% 0% transparent;
        }
        a {
            color: #06C;
            text-decoration: none;
        }
    </style>
    <script type="text/javascript">
        $(function(){
            var tObj;
            $("#tabs a").each(function(){
                if($(this).attr("class").indexOf("here") == 0){tObj = $(this)}
                $(this).click(function(){
                    var c = $(this).attr("class");
                    if(c.indexOf("here") == 0){return;}
                    var ref = $(this).attr("ref");
                    var ref_t = tObj.attr("ref");
                    tObj.attr("class","nor");
                    $(this).attr("class","here");
                    $(ref_t).hide();
                    $(ref).show();
                    tObj = $(this);
                    if(ref == '#tab_3'){
                        // 编辑器参数
                        var kingEditorParams = {
                            //指定上传文件参数名称
                            filePostName  : "pics",
                            //指定上传文件请求的url。
                            uploadJson : "/KindImgLoad",
                            //上传类型，分别为image、flash、media、file
                            dir : "image",
                            // width : "1000px",
                            // height : "400px"
                        };
                        KindEditor.create('#productdesc',kingEditorParams);
                        KindEditor.sync();
                    }
                });
            });
        });

    </script>

</head>
<body>
<div class="box-positon">
    <div class="rpos">当前位置: 商品管理 - 添加</div>
    <div class="ropt">
        <a href="productList?name=${name}&page=${page}&isShow=${isShow}&brandId=${brandId}"><button class="return-button">返回</button></a>
    </div>
    <div class="clear"></div>
</div>
<h2 class="h2_ch"><span id="tabs">
<a href="javascript:void(0);" ref="#tab_1" title="基本信息" class="here">基本信息</a>
<a href="javascript:void(0);" ref="#tab_2" title="商品图片" class="nor">商品图片</a>
<a href="javascript:void(0);" ref="#tab_3" title="商品描述" class="nor">商品描述</a>
<a href="javascript:void(0);" ref="#tab_4" title="包装清单" class="nor">包装清单</a>
</span></h2>
<div class="body-box" style="float:right">

        <table cellspacing="1" cellpadding="2" width="100%" border="0" class="pn-ftable">
            <tbody id="tab_1">
            <tr>
                <td width="20%" class="pn-flabel pn-flabel-h">
                    <span class="pn-frequired">*</span>
                    商品类型:</td><td width="80%" class="pn-fcontent">
                <select name="typeId">
                    <option value="">请选择</option>
                    <option value="2">瑜珈服</option>
                    <option value="3">瑜伽辅助</option>
                    <option value="4">瑜伽铺巾</option>
                    <option value="5">瑜伽垫</option>
                    <option value="6">舞蹈鞋服</option>
                    <option value="7">其它</option>
                </select>
            </td>
            </tr>
            <tr>
                <td width="20%" class="pn-flabel pn-flabel-h">
                    <span class="pn-frequired">*</span>
                    商品名称:</td><td width="80%" class="pn-fcontent">
                <input type="text" class="required" name="name" maxlength="100" size="100" value="${bbsProduct.name}"/>
            </td>
            </tr>
            <tr>
                <td width="20%" class="pn-flabel pn-flabel-h">
                    商品品牌:</td><td width="80%" class="pn-fcontent">
                <select name="brandId">
                    <option value="">请选择品牌</option>
                    <c:forEach items="${blist}" var="brand">
                        <option value="${brand.id }" <c:if test="${bbsProduct.brandId==brand.id }"> selected="selected"</c:if>>${brand.name }</option>
                    </c:forEach>
                </select>
            </td>
            </tr>
            <tr>
                <td width="20%" class="pn-flabel pn-flabel-h">
                    商品毛重:</td><td width="80%" class="pn-fcontent">
                <input type="text" value="${bbsProduct.weight}" class="required" name="weight" maxlength="10" />KG
            </td>
            </tr>
            <tr>
                <td width="20%" class="pn-flabel pn-flabel-h">
                    <span class="pn-frequired">*</span>
                    颜色:</td><td width="80%" class="pn-fcontent">
                <c:forEach items="${colorList}" var="col">
                    <input type="checkbox" value="${col.id}" name="colorss" <c:forEach items="${bbsProduct.colorss}" var="color"><c:if test="${col.id==color}">checked="checked"</c:if></c:forEach>/>${col.name}
                </c:forEach>
            </td>
            </tr>
            <tr>
                <td width="20%" class="pn-flabel pn-flabel-h">
                    <span class="pn-frequired">*</span>
                    尺码:</td><td width="80%" class="pn-fcontent">
                <input type="checkbox" value="S" name="sizess" <c:forEach items="${bbsProduct.sizess }" var="size"><c:if test="${size=='S'}">checked="checked"</c:if></c:forEach>/>S
                <input type="checkbox" value="M" name="sizess" <c:forEach items="${bbsProduct.sizess }" var="size"><c:if test="${size=='M'}">checked="checked"</c:if></c:forEach>/>M
                <input type="checkbox" value="L" name="sizess" <c:forEach items="${bbsProduct.sizess }" var="size"><c:if test="${size=='L'}">checked="checked"</c:if></c:forEach>/>L
                <input type="checkbox" value="XL" name="sizess" <c:forEach items="${bbsProduct.sizess }" var="size"><c:if test="${size=='XL'}">checked="checked"</c:if></c:forEach>/>XL
                <input type="checkbox" value="XXL" name="sizess" <c:forEach items="${bbsProduct.sizess }" var="size"><c:if test="${size=='XXL'}">checked="checked"</c:if></c:forEach>/>XXL
            </td>
            </tr>
            <tr>
                <td width="20%" class="pn-flabel pn-flabel-h">
                    状态:</td><td width="80%" class="pn-fcontent">
                <input type="checkbox" name="isNew" value="1" <c:if test="${bbsProduct.isNew==true }">checked="checked"</c:if>/>新品
                <input type="checkbox" name="isCommend" value="1" <c:if test="${bbsProduct.isCommend==true }">checked="checked"</c:if>/>推荐
                <input type="checkbox" name="isHot" value="1" <c:if test="${bbsProduct.isHot==true }">checked="checked"</c:if>/>热卖
            </td>
            </tr>
            </tbody>
            <tbody id="tab_2" style="display: none">
            <tr>
                <td width="20%" class="pn-flabel pn-flabel-h">
                    <span class="pn-frequired">*</span>
                    上传商品图片(90x150尺寸):</td>
                <td width="80%" class="pn-fcontent">
                    注:该尺寸图片必须为90x150。
                </td>



            </tr>



            <tr>

                <td width="20%" class="pn-flabel pn-flabel-h"></td>
                <td width="80%" class="pn-fcontent" <%--id="imgurl"--%>>

                    <c:forEach items="${bbsProduct.imgUrls }" var="img">
                        <ul>
                            <img width="40" height="40" src="${img}" />

                        </ul>
                    </c:forEach>

                </td>

            </tr>
            </tbody>
            <tbody id="tab_3" style="display: none">
            <tr>
                <td >
                    <textarea rows="20" cols="180" id="productdesc" name="description">${bbsProduct.description}</textarea>
                </td>
            </tr>
            </tbody>
            <tbody id="tab_4" style="display: none">
            <tr>
                <td >
                    <textarea rows="20" cols="180" id="productList" name="packageList">${bbsProduct.packageList }</textarea>
                </td>
            </tr>
            </tbody>
            <tbody>
            <tr>
                <td class="pn-fbutton" colspan="2">

                </td>
            </tr>
            </tbody>
        </table>
        <input type="hidden" name="id" value="${bbsProduct.id }"/>

</div>
</body>
</html>