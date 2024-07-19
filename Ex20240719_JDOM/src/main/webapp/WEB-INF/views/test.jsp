<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Insert title here</title>
		<style>
	
			#t1{
				border-collapse: collapse;
				width: 400px;
			}
			
			#t1 caption{
				text-indent: -9999px;
				height: 0;
			}
			
			#t1 th{
				padding: 6px;
				background-color: #dedede;
				border: 1px solid black;
			}
			
			#t1 tbody td{
				text-align: center;
			}
			#t1 td{
				padding: 4px;
				border: 1px solid black;
			}
			
			#t1 .no-border{ border: none; }
			
			#type, #value, #btn1{
				padding: 5px;
			}
			
			.w150{ width: 150px; }
			
			.modal_bg {
		      display: none;
		      width: 100%;
		      height: 100%;
		      position: fixed; 
		      top: 0;
		      left: 0;
		      right: 0;
		      background: rgba(0, 0, 0, 0.6);
		      z-index: 999; 
		   }
		   
		   .modal_wrap {
		      display: none;
		      position: absolute; 
		      top: 50%;
		      left: 50%;
		      transform:translate(-50%,-50%);
		      width: 400px;
		      height: 400px;
		      background: #fff;
		      z-index: 1000; 
		   }
		   
		   .modal_btn{
		   	text-align: center;
		    margin-top: 20px;
		    margin-left: 10px;
		    position: relative;
		    bottom: 0;
		    }
		    
		    header{
		    text-align: center;
		    }
    
		   .table{
		   border-collapse: collapse;
		   width: 380px;
		   margin: auto;
		   }
		   .table th, .table td {
		   border: 1px solid black;
		   padding: 4px;
		   }
		   .table thead tr{
		   background-color: #cdcdcd;
		   }
			
		
		</style>
	</head>
	<body>
		<h1>회원 목록</h1>
		<hr/>
		<table id="t1">
			<caption>회원목록테이블</caption>
			<thead>
				<tr>
					<td colspan="3" class="no-border">
						<select id="type">
							<option value="1">이름</option>
							<option value="2">이메일</option>
							<option value="3">연락처</option>
						</select>
						<input type="text" id="value" class="w150"/>
						<button type="button" id="btn1">검색</button>
					</td>
				</tr>
				<tr>
					<th>회원명</th>
					<th>이메일</th>
					<th>전화번호</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${ar }" var="vo">
				<tr>
					<td>${vo.name }</td>
					<td>${vo.email }</td>
					<td>${vo.phone }</td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
		
		<div class="modal_bg" onClick="javascript:popClose();"></div>
	   <div class="modal_wrap">
	   <header>
	   	<h2>상세 정보</h2>
	   </header>
	   	 <table class="table">
           	<colgroup>
           		<col width="100px"/>
           		<col width="*"/>
           	</colgroup>
            <thead>
                <tr>
                	<th colspan="2">상세정보</th>
                </tr>
            </thead>
            <tbody></tbody>
        </table>
	      <button class="modal_btn modal_close" onClick="javascript:popClose();">닫기</button>
	   </div>
	
		<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
		<script>
		
		$("#t1 tbody tr").click(function() {
			viewData(this);
		});
		
		function viewData(tr){
			let th_ar = $("#t1 thead tr").children();
			// 클릭한 tr객체가 인자로 넘어온다.
			// 그 tr의 자식들(td들)을 얻어낸다.
			let td_ar = $(tr).children();
			let str = "";
			for(let i=0;i<td_ar.length;i++){
				str += "<tr><th>";
				str += th_ar.eq(i+1).text();
				str += "</th><td>";
				str += td_ar.eq(i).text();
				str += "</td></tr>";
			}
			
			$(".table tbody").html(str);
			popOpen();
		}
		
		function popOpen(){
			var modalPop = $('.modal_wrap');
			var modalBg = $('.modal_bg');
			$(modalPop).show();
			$(modalBg).show();
		}

		function popClose(){
			var modalPop = $('.modal_wrap');
			var modalBg = $('.modal_bg');
			$(modalPop).hide();
			$(modalBg).hide();
		}
		
		
		
		
		$(function(){
			
			$("#btn1").click(function(){
				
				let value = $("#value").val().trim();

				if(value == '' || value.length<1){
					alert("검색어를 입력하세요");
					$("#value").val("");
					$("#value").focus();
					return;
				}
				
				$.ajax({
					url: "search",
					data: {
						"searchType": $("#type").val(),
						"searchValue": value,
					},
					type:"post",
					dataType:"json",
				}).done(function(res){
					let m_ar = res.ar;
					let str = "";
					for(let i =0; i<res.len; i++){
						str += "<tr onclick='viewData(this)'>";
						str +=   "<td>";
						str +=     m_ar[i].name;
						str +=   "</td>";
						str +=   "<td>";
						str +=     m_ar[i].email;
						str +=   "</td>";
						str +=   "<td>";
						str +=     m_ar[i].phone;
						str +=   "</td>";
						str += "</tr>";
					}
					
					$("#t1 tbody").html(str);
					
				});
				
				
			});
			
			
			
			
		
		});
		
		
		</script>
	</body>
</html>