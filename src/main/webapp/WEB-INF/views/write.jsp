<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<link rel="stylesheet" href="resources/css/summernote-lite.css"/>
	<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
	<script src="resources/js/summernote-lite.js"></script>
	<script src="resources/js/lang/summernote-ko-KR.js"></script>
	<style type="text/css">
		#bbs table {
		    width:580px;
		    margin-left:10px;
		    border:1px solid black;
		    border-collapse:collapse;
		    font-size:14px;
		    
		}
		
		#bbs table caption {
		    font-size:20px;
		    font-weight:bold;
		    margin-bottom:10px;
		}
		
		#bbs table th {
		    text-align:center;
		    border:1px solid black;
		    padding:4px 10px;
		}
		
		#bbs table td {
		    text-align:left;
		    border:1px solid black;
		    padding:4px 10px;
		}
		
		.no {width:15%}
		.subject {width:30%}
		.writer {width:20%}
		.reg {width:20%}
		.hit {width:15%}
		.title{background:lightsteelblue}
		
		.odd {background:silver}
		
			
	</style>
	
</head>
<body>
	<div id="bbs">
	<form action="write.inc" method="post" 
	encType="multipart/form-data">
		<table summary="게시판 글쓰기">
			<caption>게시판 글쓰기</caption>
			<tbody>
				<tr>
					<th>제목:</th>
					<td><input type="text" name="subject" size="45"/></td>
				</tr>
				<tr>
					<th>이름:</th>
					<td><input type="text" name="writer" size="12"/></td>
				</tr>
				<tr>
					<th>내용:</th>
					<td><textarea name="content" cols="50" id="content"
							rows="8"></textarea></td>
				</tr>
				<tr>
					<th>첨부파일:</th>
					<td><input type="file" name="file"/></td>
				</tr>

				<tr>
					<th>비밀번호:</th>
					<td><input type="password" name="pwd" size="12"/></td>
				</tr>

				<tr>
					<td colspan="2">
						<input type="button" value="보내기"
						onclick="sendData()"/>
						<input type="button" value="다시"/>
						<input type="button" value="목록"/>
					</td>
				</tr>
			</tbody>
		</table>
	</form>
	</div>
	<script type="text/javascript">
	$(function() {
		$("#content").summernote({
			height:200,
			leng:"ko-KR",
			focus:true,
			callbacks:{
				onImageUpload: function (files, editor) {
					//이미지가 에디터에 추가될 때마다 수행하는 곳!
					//추가되어 들어온 이미지가 배열로 인식됨
					for(var i=0; i<files.length; i++){
						sendImage(files[i], editor);
					}
				}
			}
		});
		$("#content").summernote("lineHeight",.7);
	});
	
	function sendImage(file, editor) {
		var frm = new FormData();
		
		//보내고자하는 자원을 위해서 만든 폼객체에 파라미터로 넣어준다.
		frm.append("s_file",file);
		
		$.ajax({
			url:"saveImg.inc",
			data:frm,
			type:"post",
			contentType:false,
			processData:false,
			cache: false,
			dataType:"json", //서버로부터 받을 데이터 형식
			
		}).done(function(data) {
			$("#content").summernote("editor.insertImage",data.url+"/"+data.fname);
		}).fail(function (err) {
			
		});
	}
	
	function sendData(){
		for(var i=0 ; i<document.forms[0].elements.length ; i++){
			if(document.forms[0].subject.value.trim().length<0){
				alert("제목 입력");
				document.forms[0].subject.focus();
				return;//수행 중단
			}
			
			if(document.forms[0].writer.value.trim().length<0){
				alert("작성자 입력");
				document.forms[0].writer.focus();
				return;//수행 중단
			}
			
			if(document.forms[0].pwd.value.trim().length<0){
				alert("비밀번호 입력");
				document.forms[0].pwd.focus();
				return;//수행 중단
			}
		}

		document.forms[0].submit();
	}

	</script>
</body>
</html>












