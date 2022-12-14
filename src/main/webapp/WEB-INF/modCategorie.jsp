<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="com.nextu.app.servlets.entities.categorie.*" %>
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
	<form method="post" action="moddCategorie">
                <label for="codeCategorie">Catégorie à modifier:</label>
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
                  
                  <label for="name">Nouveau nom:</label>
                  <input type="text" id="name" name="name" required>

                  <button type="submit" class="btn btn-primary">
                     <i class="fa fa-send"></i>
                        Enregistrer
                  </button>
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