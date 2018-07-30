<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../head.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>babasport-list</title>
<script type="text/javascript">
    //上架
    function isShow1(){
        //请至少选择一个
        var size = $("input[name='ids']:checked").size();
        if(size == 0){
            alert("请至少选择一个");
            return;
        }
        if(!confirm("你确定上架吗")){
            return;
        }
        //提交 Form表单
        $("#jvForm").attr("action","updateProducts1?page=${page}&name=${name}&isShow=${isShow}&brandId=${brandId}");
        $("#jvForm").attr("method","post");
        $("#jvForm").submit();

    }
    //下架
    function isShow0(){
        //请至少选择一个
        var size = $("input[name='ids']:checked").size();
        if(size == 0){
            alert("请至少选择一个");
            return;
        }

        if(!confirm("你确定下架吗")){
            return;
        }
        //提交 Form表单
        $("#jvForm").attr("action","updateProducts0?page=${page}&name=${name}&isShow=${isShow}&brandId=${brandId}");
        $("#jvForm").attr("method","post");
        $("#jvForm").submit();

    }
    //全选
    function checkBox(checked) {
        if(checked===true){
            $("input[name='ids']").attr("checked","checked");
        }else{
            $("input[name='ids']").attr("checked",false);
        }
    }
//去修改页面
    function toUpdatePage(id,page,name,isShow,brandId){

	     window.location.href="toUpdateProduct?id="+id+"&page="+page+"&name="+name+"&isShow="+isShow+"&brandId="+brandId;

    }
    //批量删除
    function optDelete(){
    	var size = $("input[name='ids']:checked").size();
    	if(size == 0){
    		alert("请至少选择一个");
    		return;
    	}
    	//你确定删除吗
    	if(confirm("你确定删除吗？")){
    		$('#jvForm').attr("action","deleteProducts?page=${page}&name=${name}&isShow=${isShow}&brandId=${brandId}").submit();
    	}
    }


</script>
</head>
<body>
<div class="box-positon">
	<div class="rpos">当前位置: 商品管理 - 列表</div>
	<form class="ropt">
		<input class="add" type="button" value="添加" onclick="window.location.href='toProductAdd?name=${name}&page=${page}&isShow=${isShow}&brandId=${brandId}'"/>
	</form>
	<div class="clear"></div>
</div>
<div class="body-box">
<form action="productList" method="post" style="padding-top:5px;">
名称: <input type="text" name="name" value="${name}"/>
	<select name="brandId">
		<option value="0">请选择品牌</option>
		<c:forEach items="${blist}" var="brand" >
		    <option value="${brand.id }" <c:if test="${brandId==brand.id }"> selected="selected"</c:if>>${brand.name}</option>
		</c:forEach>
	</select>
	<select name="isShow">
	    <option value="2">请选择</option>
		<option value="1" <c:if test="${isShow==1}"> selected="selected"</c:if>>上架</option>
		<option value="0" <c:if test="${isShow==0}"> selected="selected"</c:if>>下架</option>
	</select>
	<input type="submit" class="query" value="查询"/>
</form>
<form id="jvForm" method="post">
<table cellspacing="1" cellpadding="0" width="100%" border="0" class="pn-ltable">
	<thead class="pn-lthead">
		<tr>
			<th width="20"><input type="checkbox" onclick="checkBox(this.checked)"/></th>
			<th>商品编号</th>
			<th>商品名称</th>
			<th>图片</th>
			<th width="4%">新品</th>
			<th width="4%">热卖</th>
			<th width="4%">推荐</th>
			<th width="4%">上下架</th>
			<th width="12%">操作选项</th>
		</tr>
	</thead>
	<tbody class="pn-ltbody">
	    <c:forEach items="${pageInfo.list }" var="product">
		<tr bgcolor="#ffffff" onmouseover="this.bgColor='#eeeeee'" onmouseout="this.bgColor='#ffffff'">
			<td><input type="checkbox" name="ids" value="${product.id }"/></td>
			<td>${product.id }</td>
			<td align="center">${product.name }</td>

			<td align="center" >
				<c:forEach items="${product.imgUrls }" var="img">
				  <img width="40" height="40" src="${img }" />
			    </c:forEach>
			</td>
			<td align="center"><c:if test="${product.isNew==true }">是</c:if><c:if test="${product.isNew==false }">否</c:if></td>
			<td align="center"><c:if test="${product.isHot==true }">是</c:if><c:if test="${product.isHot==false }">否</c:if></td>
			<td align="center"><c:if test="${product.isCommend==true}">是</c:if><c:if test="${product.isCommend==false }">否</c:if></td>
			<td align="center"><c:if test="${product.isShow==true }">上架</c:if><c:if test="${product.isShow==false }">下架</c:if></td>
			<td align="center">
			<a href="productView?pid=${product.id}&page=${page}&name=${name}&isShow=${isShow}&brandId=${brandId}" class="pn-opt">查看</a> | <a href="javascript:;" class="pn-opt" onclick="toUpdatePage('${product.id }','${page}','${name}','${isShow}','${brandId}')">修改</a> | <a  onclick="if(!confirm('您确定删除吗？')){return false;}" href="deleteProducts?ids=${product.id}&page=${page}&name=${name}&isShow=${isShow}&brandId=${brandId}" class="pn-opt">删除</a> | <a href="javascript:;" onclick="window.location.href='skuList?pid=${product.id}&name=${name}&page=${page}&isShow=${isShow}&brandId=${brandId}'" class="pn-opt">库存</a>
			</td>
		</tr>
		</c:forEach>
	</tbody>
</table>
<div class="page pb15">
	<span class="r inb_a page_b">
	
	    <!--点击分页-->


                   <a href="productList?page=1&name=${name}&isShow=${isShow}&brandId=${brandId}"><font size="2">首页</font></a>

		<!--上一页-->

                        <c:if test="${pageInfo.hasPreviousPage}">
                            <a href="productList?page=${pageInfo.pageNum-1}&name=${name}&isShow=${isShow}&brandId=${brandId}" aria-label="Previous">
                              <font size="2">上一页</font>
                            </a>
						</c:if>


		<!--循环遍历连续显示的页面，若是当前页就高亮显示，并且没有链接-->
                    <c:forEach items="${pageInfo.navigatepageNums}" var="page_num">
						<c:if test="${page_num == pageInfo.pageNum}">
							<a href="#" style="background-color: #b2d6fa"><font size="2">${page_num}</font></a>
						</c:if>
						<c:if test="${page_num != pageInfo.pageNum}">
							<a href="productList?page=${page_num}&name=${name}&isShow=${isShow}&brandId=${brandId}"><font size="2">${page_num}</font></a>
						</c:if>
					</c:forEach>

		<!--下一页-->

                        <c:if test="${pageInfo.hasNextPage}">
                            <a href="productList?page=${pageInfo.pageNum+1}&name=${name}"&isShow=${isShow}&brandId=${brandId}
							   aria-label="Next">
                                <font size="2">下一页</font>
                            </a>
						</c:if>


                    <a href="productList?page=${pageInfo.pages}&name=${name}&isShow=${isShow}&brandId=${brandId}"><font size="2">尾页</font></a>




		共<var>${pageInfo.pages}</var>页 到第<input type="text" size="3" id="PAGENO"/>页 <input type="button" onclick="javascript:window.location.href = 'productList?name=${name}&isShow=${isShow}&brandId=${brandId}&page='+ $('#PAGENO').val() " value="确定" class="hand btn60x20" id="skip"/>

	
	</span>
</div>
<div style="margin-top:15px;"><input class="del-button" type="button" value="删除" onclick="optDelete();"/><input class="add" type="button" value="上架" onclick="isShow1();"/><input class="del-button" type="button" value="下架" onclick="isShow0();"/></div>
</form>
</div>

</body>
</html>