<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../head.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>babasport-edit</title>
	<script type="text/javascript">
        //上传图片
        function uploadPic(){
            //上传图片 异步的  	Jquery.form.js
            var options = {
                url : "/ImgLoad",
                type : "post",
                dataType : "json",
                success : function(data){
                    //多图片回显
                    var html = '<div>';
                    for(var i=0;i<data.length;i++){

                        html += '<img width="100" height="100" src="' + data[i] + '" />'
                            +  '<input type="hidden" name="imgUrls" value="' + data[i] + '"/>'
                    }
                    html += '</div>';
                    //回显
                   /* $("#temptable").append(html);*/
                    $("#imgurl").html(html);
                }
            }
            $("#jvForm").ajaxSubmit(options);
        }

	</script>

</head>
<body>
<div class="box-positon">
	<div class="rpos">当前位置: 品牌管理 - 添加</div>
	<div class="ropt">
		<a href="brandList?name=${name}&page=${page}&isDisplay=${isDisplay}"><button class="return-button">返回</button></a>
	</div>
	<div class="clear"></div>
</div>
<div class="body-box" style="float:right">
	<form id="jvForm" action="updateBrand?names=${name}&page=${page}&isDisplay=${isDisplay}" method="post" enctype="multipart/form-data">
		<table cellspacing="1" cellpadding="2" width="100%" border="0" class="pn-ftable">
			<tbody>
				<tr>
					<td width="20%" class="pn-flabel pn-flabel-h">
						<span class="pn-frequired">*</span>
						品牌名称:</td><td width="80%" class="pn-fcontent">
						<input type="text" class="required" name="name" maxlength="100" value="${bbsBrand.name}"/>
					    <input type="hidden" class="required" name="id" value="${bbsBrand.id}" />
					</td>
				</tr>
			</tbody>

			<tbody id="temptable">
				<tr>
					<td width="20%" class="pn-flabel pn-flabel-h">
						<span class="pn-frequired">*</span>
						上传商品图片(90x150尺寸):</td>
					<td width="80%" class="pn-fcontent">
						注:该尺寸图片必须为90x150。
					</td>
				</tr>
				<tr>
					<td width="20%" class="pn-flabel pn-flabel-h">

					</td>
					<td width="80%" class="pn-fcontent">

						<p id="imgurl"><img width="100" height="100"  src="${bbsBrand.imgUrl}"/></p>
						<%--	<input type="hidden" name="imgUrl" id="imgUrl" value="${bbsBrand.imgUrl}"/>--%>
						<input type="file" onchange="uploadPic()" name="pics" multiple="multiple"/>

					 </td>
				</tr>
			</tbody>


			<tbody>
				<tr>
					<td width="20%" class="pn-flabel pn-flabel-h">
						品牌描述:</td><td width="80%" class="pn-fcontent">
						<input type="text" class="required" name="description" value="${bbsBrand.description}" maxlength="80"  size="60"/>
					</td>
				</tr>
				<tr>
					<td width="20%" class="pn-flabel pn-flabel-h">
						排序:</td><td width="80%" class="pn-fcontent">
						<input type="text" class="required" name="sort" value="${bbsBrand.sort}" maxlength="80"/>
					</td>
				</tr>
				<tr>
					<td width="20%" class="pn-flabel pn-flabel-h">
						是否可用:</td><td width="80%" class="pn-fcontent">
					<c:if test="${bbsBrand.isDisplay==true}">
						<input type="radio" name="isDisplay" value="1" checked="checked"/>可用
						<input type="radio" name="isDisplay" value="0"/>不可用
					</c:if>
					<c:if test="${bbsBrand.isDisplay==false}">
						<input type="radio" name="isDisplay" value="1" />可用
						<input type="radio" name="isDisplay" value="0" checked="checked"/>不可用
					</c:if>


					</td>
				</tr>
			</tbody>
			<tbody>
				<tr>
					<td class="pn-fbutton" colspan="2">
						<input type="submit" class="submit" value="提交"/> &nbsp; <input type="reset" class="reset" value="重置"/>
					</td>
				</tr>
			</tbody>
		</table>
	</form>
</div>
<br/>
<div>

</div>

</body>
</html>