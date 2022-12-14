<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
  <%@ page 
    
    import="java.util.ArrayList,java.util.List,com.nextu.app.servlets.entities.categorie.*"
 %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
  List<Categorie> categories = new ArrayList<Categorie>();
  if(request.getAttribute("categories")!=null){
	  categories = (ArrayList<Categorie>)request.getAttribute("categories");
  }
%>
<form method="post" action="creerProduit">
  <div class="">
    <label for="name">Nom : </label>
    <input type="text" id="name">
  </div>
  <div class="">
    <label for="quantite">Quantite : </label>
    <input type="number" id="quantite">
  </div>
   <div class="">
    <label for="prix">Prix : </label>
    <input type="number" id="prix">
  </div>
  <div>
  	<label for="codeCategorie">Categorie : </label>
	  <select name="codeCategorie" id="codeCategorie">
					<option value="null">--</option>
	                  <%
	                     for(Categorie ca :categories){
	                  %>
	                    <option value="${categories.getCode()}">
	                    	${categories.getName()}
	                    </option>
	                  <%
	                     }
	                  %>
	   </select>
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