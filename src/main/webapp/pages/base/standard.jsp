<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 引入shiro的标签库 -->
<%@taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

	<head>
		<meta charset="UTF-8">
		<title>取派标准</title>
		<!-- 导入jquery核心类库 -->
		<script type="text/javascript" src="../../js/jquery-1.8.3.js"></script>
		<!-- 导入easyui类库 -->
		<link rel="stylesheet" type="text/css" href="../../js/easyui/themes/default/easyui.css">
		<link rel="stylesheet" type="text/css" href="../../js/easyui/themes/icon.css">
		<link rel="stylesheet" type="text/css" href="../../js/easyui/ext/portal.css">
		<link rel="stylesheet" type="text/css" href="../../css/default.css">
		<script type="text/javascript" src="../../js/easyui/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="../../js/easyui/ext/jquery.portal.js"></script>
		<script type="text/javascript" src="../../js/easyui/ext/jquery.cookie.js"></script>
		<script src="../../js/easyui/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
		<script type="text/javascript">
			$(function(){
				// 先将body隐藏，再显示，不会出现页面刷新效果
				$("body").css({visibility:"visible"});
				
				// 收派标准信息表格
				$('#grid').datagrid( {
					iconCls : 'icon-forward',
					fit : true,
					border : false,
					rownumbers : true,
					striped : true,
					//自定义每页最大记录数，默认是10,20,...
					pageList: [2,20,200],
					//分页工具条，在请求的时候会自动发送两个参数：page，rows
					pagination : true,
					toolbar : toolbar,
					//发起请求获取分页相关数据
					//url : "../../data/standard.json",
					url : "../../standard_listPage.action",
					idField : 'id',
					columns : columns
				});
				
				//给保存按钮绑定点击
				$("#save").click(function(){
					//表单校验
					if($("#standardForm").form("validate")){
						//提交表单
						$("#standardForm").submit();
					}
				});
				
				
				//初始化window
				$("#standardWindow").window({
					//键值对的属性
					//事件和属性写法一样
					onClose:function(){
						//alert(1);
						//目标：清空表单元素
						//三种
						//1)dom方法reset
						//$("#standardForm")[0].reset();
						//2)form组件的reset方法
						//$("#standardForm").form("reset");
						//3)form组件的clear方法
						$("#standardForm").form("clear");
						//reset方法都不能清除隐藏域,如果用的话，需要手动清除！$("#id").val("")
						
					}
				});
				
				
			});	
			
			//工具栏
			var toolbar = [ {
				id : 'button-add',
				text : '增加',
				iconCls : 'icon-add',
				handler : function(){
					//alert('增加');
					
					//弹出添加的window
					$("#standardWindow").window("open");
				}
			}, {
				id : 'button-edit',
				text : '修改',
				iconCls : 'icon-edit',
				handler : function(){
					//第一步：限制修改的窗口弹出，必须选中且只能选一个才谈
					//获取选中的所有行
					var rows=$("#grid").datagrid("getSelections");
					//判断
					if(rows.length!=1){
						//提示用户
						$.messager.alert("友情提示","请选中且只能选中一条记录进行修改","info");
						return;
					}
					//第二步：弹出修改表单窗口（和添加公用）
					$("#standardWindow").window("open");
					
					//第三步：填充表单
					//数据来自于页面列表即可rows[0]
					//填充数据，使用form的load方法自动填充,包括隐藏域
					$("#standardForm").form("load",rows[0]);
					
					
					//alert('修改');
				}
			},
			<shiro:hasPermission name="courier:add">
			{
				id : 'button-delete',
				text : '作废',
				iconCls : 'icon-cancel',
				handler : function(){
					alert('作废');
				}
			},
			</shiro:hasPermission>
			{
				id : 'button-restore',
				text : '还原',
				iconCls : 'icon-save',
				handler : function(){
					alert('还原');
				}
			}];
			
			// 定义列
			var columns = [ [ {
				field : 'id',
				checkbox : true
			},{
				field : 'name',
				title : '标准名称',
				width : 120,
				align : 'center'
			}, {
				field : 'minWeight',
				title : '最小重量',
				width : 120,
				align : 'center'
			}, {
				field : 'maxWeight',
				title : '最大重量',
				width : 120,
				align : 'center'
			}, {
				field : 'minLength',
				title : '最小长度',
				width : 120,
				align : 'center'
			}, {
				field : 'maxLength',
				title : '最大长度',
				width : 120,
				align : 'center'
			}, {
				field : 'operator',
				title : '操作人',
				width : 120,
				align : 'center'
			}, {
				field : 'operatingTime',
				title : '操作时间',
				width : 120,
				align : 'center'
			}, {
				field : 'company',
				title : '操作单位',
				width : 120,
				align : 'center'
			} ] ];
		</script>
	</head>

	<body class="easyui-layout" style="visibility:hidden;">
		<div region="center" border="false">
			<!-- 演示shiro标签 -->
			<shiro:hasRole name="base">
				<input/>
			</shiro:hasRole>
			<%-- <shiro:lacksRole name="base">
				<input/>
			</shiro:lacksRole> --%>
			欢迎您：<shiro:principal property="username"></shiro:principal>
			<shiro:hasPermission name="courier:*:*">
				<input/>
			</shiro:hasPermission>
			<table id="grid"></table>
		</div>
		
		<div class="easyui-window" title="对收派标准进行添加或者修改" id="standardWindow" collapsible="false" minimizable="false" maximizable="false" modal="true" closed="true" style="width:600px;top:50px;left:200px">
			<div region="north" style="height:31px;overflow:hidden;" split="false" border="false">
				<div class="datagrid-toolbar">
					<a id="save" icon="icon-save" href="#" class="easyui-linkbutton" plain="true">保存</a>
				</div>
			</div>

			<div region="center" style="overflow:auto;padding:5px;" border="false">
				
				<form id="standardForm" action="../../standard_add.action" method="post">
					<table class="table-edit" width="80%" align="center">
						<tr class="title">
							<td colspan="2">收派标准信息
								<!--提供隐藏域 装载id -->
								<input type="hidden" name="id" />
							</td>
						</tr>
						<tr>
							<td>收派标准名称</td>
							<td>
								<input type="text" name="name" 
									class="easyui-validatebox" data-options="required:true,validType:'length[1,10]'" />
							</td>
						</tr>
						<tr>
							<td>最小重量</td>
							<td>
								<input type="text" name="minWeight" 
										class="easyui-numberbox" required="true" />
							</td>
						</tr>
						<tr>
							<td>最大重量</td>
							<td>
								<input type="text" name="maxWeight" class="easyui-numberbox" required="true" />
							</td>
						</tr>
						<tr>
							<td>最小长度</td>
							<td>
								<input type="text" name="minLength" class="easyui-numberbox" required="true" />
							</td>
						</tr>
						<tr>
							<td>最大长度</td>
							<td>
								<input type="text" name="maxLength" class="easyui-numberbox" required="true" />
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</body>

</html>