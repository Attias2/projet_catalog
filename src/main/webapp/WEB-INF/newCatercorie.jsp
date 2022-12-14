<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page 
    import="java.util.ArrayList,java.util.List,com.nextu.app.servlets.entities.categorie.Categorie"
 %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">

<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<form method="post" action="creerCategorie">
  <div class="">
    <label for="name">Nom : </label>
    <input type="text" id="name">
  </div>
  <div class="">
    <input type="submit" value="Envoyer">
   </div>
</form>

<div class="row">
		 <% 
          String message = request.getParameter("errorMessage")!=null?request.getParameter("errorMessage"):(String)request.getAttribute("errorMessage");
		  
          if(message!=null && message!="") {
        %>
        <div class="col-lg-12 col-md-12 col-sm-12 pr-0 alert alert-danger">
            <%=message %>
        </div>
        <% } %>
</div>

</body>
</html>