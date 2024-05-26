<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.ArrayList,bean.Book"%>

<%
String error = (String)request.getAttribute("error");  
String message = (String)request.getAttribute("message");  
%>

<html>
<head>
<title>update</title>
</head>
<body>
	<!-- ヘッダー開始 -->
	<jsp:include page="../common/header.jsp" flush="true" />
	<!-- ヘッダー終了 -->

	<div style="text-align: center; margin-top: 80px;">

		<% if (message != null && !message.isEmpty()) { %>
		<div style="color: red;">
			<%= message %>
		</div>
		<% } %>
		<!-- サーブレットのアノテーションを指定する -->
		<form action="<%= request.getContextPath() %>/login" method="post">

			<table style="margin: 0 auto">
				<tr>
					<th style="background-color: #6666ff; width: 150">ユーザー</th>
					<td><input type=text size="30" name="userid"></input></td>
				</tr>

				<tr>
					<th style="background-color: #6666ff; width: 150">パスワード</th>
					<td><input type=text size="30" name="password"></input></td>
				</tr>


				<tr>

					<td colspan=2 style="text-align: center; padding-top: 60px;"><input
						type="submit" value="ログイン"></td>
				</tr>

			</table>
		</form>
		<br>
		<br>
		<br>
		<br>
		<br>



	</div>


	<!-- -------------ここまからフッター ---------------------------------------------------------------->


	<!-- フッター開始 -->
	<jsp:include page="../common/footer.jsp" flush="true" />
	<!-- フッター終了 -->
</body>
</html>