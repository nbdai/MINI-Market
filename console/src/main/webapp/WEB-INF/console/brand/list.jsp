<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../head.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>babasport-list</title>
	<script type="text/javascript">
		//全选
       function checkBox(checked) {
		   if(checked===true){
		       $("input[name='ids']").attr("checked","checked");
		   }else{
               $("input[name='ids']").attr("checked",false);
		   }
       }


        //上架

        //去修改页面
        function toUpdatePage(id,page,name,isDisplay){

            window.location.href="toUpdatePage?id="+id+"&page="+page+"&name="+name+"&isDisplay="+isDisplay;

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
                $('#jvForm').attr("action","deleteBrands?name=${name}&page=${page}&isDisplay=${isDisplay}").submit();
            }
        }
	</script>
</head>
<body>
<div class="box-positon">
	<div class="rpos">当前位置: 品牌管理 - 列表</div>
	<form class="ropt">
		<input class="add" type="button" value="添加" onclick="javascript:window.location.href='toBrandAdd?name=${name}&page=${page}&isDisplay=${isDisplay}'"/>
	</form>
	<div class="clear"></div>
</div>
<div class="body-box">
<form action="brandList" method="post" style="padding-top:5px;">
	品牌名称: <input type="text" name="name" <c:if test="${name!=null}">value="${name}"</c:if> />
	是否有效：<select name="isDisplay">
		<c:if test="${isDisplay==1}">
			<option value="1" selected="selected">是</option>
			<option value="0">否</option>
		</c:if>
		<c:if test="${isDisplay==0}">
			<option value="1">是</option>
			<option value="0" selected="selected">否</option>
		</c:if>
		<c:if test="${isDisplay==null}">
			<option value="1">是</option>
			<option value="0" >否</option>
		</c:if>
	</select>
	<input type="submit" class="query" value="查询"/>
</form>
	<form id="jvForm" method="post">
		<table cellspacing="1" cellpadding="0" border="0" width="100%" class="pn-ltable">
			<thead class="pn-lthead">
			<tr>
				<th width="20"><input type="checkbox" onclick="checkBox(this.checked)"/></th>
				<th>品牌ID</th>
				<th>品牌名称</th>
				<th>品牌图片</th>
				<th>品牌描述</th>
				<th>排序</th>
				<th>是否可用</th>
				<th>操作选项</th>
			</tr>
			</thead>
			<tbody class="pn-ltbody">
			<c:forEach  items="${pageInfo.list}" var="brand" varStatus="i">
				<tr bgcolor="#ffffff" onmouseout="this.bgColor='#ffffff'" onmouseover="this.bgColor='#eeeeee'">
					<td><input type="checkbox" value="${brand.id}" name="ids" /></td>
					<td align="center">${brand.id}</td>
					<td align="center">${brand.name}</td>
					<td align="center"><img width="40" height="40" src="${brand.imgUrl}"/></td>
					<td align="center"></td>
					<td align="center">${brand.sort}</td>
					<c:if test="${brand.isDisplay==true}">
						<td align="center">是</td>
					</c:if>
					<c:if test="${brand.isDisplay==false}">
						<td align="center">否</td>
					</c:if>
					<td align="center">
						<a class="pn-opt" onclick="toUpdatePage('${brand.id}','${page}','${name}','${isDisplay}') "  href="javascript:;">修改</a> | <a class="pn-opt" onclick="if(!confirm('您确定删除吗？')) {return false;}" href="deleteBrands?name=${name}&page=${page}&ids=${brand.id}&isDisplay=${isDisplay}">删除</a>
					</td>
				</tr>
			</c:forEach>

			</tbody>
		</table>
	</form>
<div class="page pb15">
	<span class="r inb_a page_b">

              <!--点击分页-->




                   <a href="brandList?page=1&name=${name}"><font size="2">首页</font></a>

					<!--上一页-->

                        <c:if test="${pageInfo.hasPreviousPage}">
                            <a href="brandList?page=${pageInfo.pageNum-1}&name=${name}" aria-label="Previous">
                              <font size="2">上一页</font>
                            </a>
						</c:if>


					<!--循环遍历连续显示的页面，若是当前页就高亮显示，并且没有链接-->
                    <c:forEach items="${pageInfo.navigatepageNums}" var="page_num">
						<c:if test="${page_num == pageInfo.pageNum}">
							<a href="#" style="background-color: #b2d6fa"><font size="2">${page_num}</font></a>
						</c:if>
						<c:if test="${page_num != pageInfo.pageNum}">
							<a href="brandList?page=${page_num}&name=${name}"><font size="2">${page_num}</font></a>
						</c:if>
					</c:forEach>

					<!--下一页-->

                        <c:if test="${pageInfo.hasNextPage}">
                            <a href="brandList?page=${pageInfo.pageNum+1}&name=${name}"
							   aria-label="Next">
                                <font size="2">下一页</font>
                            </a>
						</c:if>


                    <a href="brandList?page=${pageInfo.pages}&name=${name}"><font size="2">尾页</font></a>



	
		共<var>${pageInfo.pages}</var>页 到第<input type="text" size="3" id="PAGENO"/>页 <input type="button" onclick="javascript:window.location.href = 'brandList?name=${name}&page='+ $('#PAGENO').val() " value="确定" class="hand btn60x20" id="skip"/>
	
	</span>
</div>
<div style="margin-top:15px;">

	<input class="del-button" type="button" value="删除" onclick="optDelete();"/></div>
</div>
</body>
</html>