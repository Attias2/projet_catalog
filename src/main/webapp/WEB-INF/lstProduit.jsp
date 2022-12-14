<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%@ page 
    
     import="java.util.ArrayList,java.util.List,com.nextu.app.servlets.entities.categorie.Categorie,com.nextu.app.servlets.entities.produit.Produit"
 %>
 <%
  List<Produit> produits = new ArrayList<Produit>();
  if(request.getAttribute("produits")!=null){
	  produits = (ArrayList<Produit>)request.getAttribute("produits");
  }
  List<Categorie> categories = new ArrayList<Categorie>();
  if(request.getAttribute("categories")!=null){
	  categories = (ArrayList<Categorie>)request.getAttribute("categories");
  }
%>

          <div class="col-lg-12 col-md-12 col-sm-12 pr-0 mb-3">
            <div class="card-collapsible card">
              <div class="card-header">
                Table <i class="fa fa-caret-down caret"></i>
              </div>
              <div class="card-body">
                <table class="table">
                  <thead class="thead bg-primary text-white">
                    <tr>
                      <th scope="col">#</th>
                      <th scope="col">Code</th>
                      <th scope="col">Name</th>
                      <th scope="col">Categorie</th>
                      <th scope="col">Quantite</th>
                      <th scope="col">Prix</th>
                      <th scope="col">Disponible</th>
                    </tr>
                  </thead>
                  <tbody>
                 	<%
                     for(Produit dsp :produits){
                    	 String n = dsp.getCategorie().getName();
                  %>
                    <tr>
                    	<td><%= dsp.getCode()%></td>
                      	<td><input name="name" value="<%= dsp.getName()%>"/></td>
                      	
                       <td>
                      	 <select name="codeCategorie" id="categorie">
                      		 <option value="<%=dsp.getCategorie().getCode() %>"><%=dsp.getCategorie().getName() %></option>
	                       	<% for(Categorie sp :categories){ 
	  									if(sp.getName() != n){		%>
	                       	<option value="<%=sp.getCode() %>"><%=sp.getName() %></option>
	                        <%= }
	  					} %>		
                   		 </select>
                    </td>
                      	<td><input name="codeCategorie" value="<%= dsp.getCategorie().getName()	%>"/></td>
                      <td>
                          <a class="btn btn-sm btn-success" href="moddProduit?codeProduit=<%= dsp.getCode()%>">Modifier</a>
                            <!-- Button trigger modal -->
                          <a href="supCategorie?codeCategorie=<%= dsp.getCode()%>" class="btn btn-sm btn-danger">
                            supprimer
                          </a>
                      </td>
                      <td><input name="quantite" value="<%= dsp.getQuantite()%>"/></td>
                      <td><input name="prix" value="<%= dsp.getPrix()%>"/></td>
                    </tr>
                  <%
                    }
                  %>
                  </tbody>
                </table>
              </div>
            </div>
          </div>
       
</body>
</html>