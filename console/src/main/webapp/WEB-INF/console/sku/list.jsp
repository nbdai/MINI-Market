<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../head.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>babasport-list</title>
<script>
    function updateSku(id){
        if(!confirm("确定修改？")){
            return;
        }

        $('#m'+id).attr("disabled",'');
         $('#p'+id).attr("disabled",'');
         $('#i'+id).attr("disabled",'');
         $('#l'+id).attr("disabled",'');
         $('#f'+id).attr("disabled",'');
    }
    function addSku(id){
         if(!confirm("确定保存？")){
             return;
		 }



        $.ajax({
			url: "updateSku",
			type:"post",
            data:$("#m"+id).val()+","+$("#p"+id).val()+","+$("#i"+id).val()+","+$("#l"+id).val()+","+$("#f"+id).val()+","+id,
            datatype: "json",
			success:function(data) {
                $('#m'+id).attr("disabled",'disabled');
                $('#p'+id).attr("disabled",'disabled');
                $('#i'+id).attr("disabled",'disabled');
                $('#l'+id).attr("disabled",'disabled');
                $('#f'+id).attr("disabled",'disabled');
            }
		});
    }


</script>
</head>
<body>
<div class="box-positon">
	<div class="rpos">当前位置: 库存管理 - 列表</div>
	<div class="ropt">
		<a href="productList?name=${name}&page=${page}&isShow=${isShow}&brandId=${brandId}"><button class="return-button">返回</button></a>
	</div>
	<div class="clear"></div>

</div>
<div class="body-box">
<form method="post" id="tableForm">
<table cellspacing="1" cellpadding="0" border="0" width="100%" class="pn-ltable">
	<thead class="pn-lthead">
		<tr>
			<th width="20"><input type="checkbox" onclick="Pn.checkbox('ids',this.checked)"/></th>
			<th>商品编号</th>
			<th>商品颜色</th>
			<th>商品尺码</th>
			<th>市场价格</th>
			<th>销售价格</th>
			<th>库       存</th>
			<th>购买限制</th>
			<th>运       费</th>
			<th>是否赠品</th>
			<th>操       作</th>
		</tr>
	</thead>
	<tbody class="pn-ltbody">
	   <c:forEach items="${skuList}" var="sku">

		   <tr bgcolor="#ffffff" onmouseover="this.bgColor='#eeeeee'" onmouseout="this.bgColor='#ffffff'">
			   <td><input type="checkbox" name="ids"  value="${sku.id}"/></td>
			   <td>${sku.createTime}</td>
			   <td align="center">
				   <c:forEach items="${colorList}" var="col">
					    <c:if test="${col.id==sku.colorId}">${col.name}</c:if>
				   </c:forEach>
			   </td>
			   <td align="center">${sku.size}</td>
			   <td align="center"><input type="text" id="m${sku.id}" value="${sku.marketPrice}" disabled="disabled" size="10"/></td>
			   <td align="center"><input type="text" id="p${sku.id}" value="${sku.price}" disabled="disabled" size="10"/></td>
			   <td align="center"><input type="text" id="i${sku.id}" value="${sku.stock}" disabled="disabled" size="10"/></td>
			   <td align="center"><input type="text" id="l${sku.id}" value="${sku.upperLimit}" disabled="disabled" size="10"/></td>
			   <td align="center"><input type="text" id="f${sku.id}" value="${sku.deliveFee}" disabled="disabled" size="10"/></td>
			   <td align="center">不是</td>
			   <td align="center"><a href="javascript:updateSku('${sku.id}')" class="pn-opt">修改</a> | <a href="javascript:;" onclick="addSku('${sku.id}')" class="pn-opt">保存</a></td>
		   </tr>


	   </c:forEach>
	</tbody>
</table>
</form>
</div>
</body>
</html>