<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page 
    
    import="java.util.ArrayList,java.util.List,com.nextu.app.servlets.entities.categorie.Categorie,com.nextu.app.servlets.entities.produit.Produit"
 %>
 <%
  List<Categorie> categories = new ArrayList<Categorie>();
  if(request.getAttribute("categories")!=null){
	  categories = (ArrayList<Categorie>)request.getAttribute("categories");
  }
  Produit produit = (Produit)request.getAttribute("produit");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

        <div class="row">
        <div class="col-lg-12 col-md-12 col-sm-12 pr-0 mb-3">
            <div class="card-collapsible card">
              <div class="card-header">
                Modifier un produit <i class="fa fa-caret-down caret"></i>
              </div>
              <div class="card-body">
                <form method="post" action="modifierProduit">
                 <div class="form-group">
                    <input type="text" class="form-control" placeholder="Intitulé du produit" name="name" value="<%= produit.getName()%>">
                  </div>
                  <div class="form-group">
                    <select name="codeCategorie" id="sport">
                       <option value="-1">Choix de la Categorie</option>
                       	<%
                    		 for(Categorie sp :categories){
                  	    %>
                       	<option 
                       		value="<%=sp.getCode() %>" 
                       		<%if(sp.getCode().equals(produit.getCategorie().getCode())){ %>
                       		selected=selected
                       		<%} %>
                       		><%=sp.getName() %>
                        </option>
                        <%} %>		
                    </select>
                  </div>
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
                    <div class="col-sm-10">
                      <button type="submit" class="btn btn-primary">
                        <i class="fa fa-send"></i>
                        Enregistrer
                      </button>
                    </div>
                     <div class="form-group row">
                    <div class="col-sm-10">
                       <input type="hidden" class="form-control" name="codeDiscipline" value="<%= produit.getCode()%>">
                    </div>
                  </div>
                </form>
              </div>
            </div>
          </div>
        </div>
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