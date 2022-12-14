package com.nextu.controllers.categorie;

import java.io.IOException;
import java.util.*;

import com.nextu.app.servlets.entities.categorie.*;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.UserTransaction;
/**
 * Servlet implementation class CreerCategorie
 */
public class CreerCategorie extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Resource
	private UserTransaction userTransaction;
	
	@PersistenceContext(unitName = "sample-jpa")
	private EntityManager em;
    /**
     * Default constructor. 
     */
    public CreerCategorie() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		 String name = request.getParameter("name");
	     String uriRedirect = """
	            newCategorie.jsp?errorMessage=%s
	            """;
	      String message = "";
	      TypedQuery<Categorie> query = em.createQuery("SELECT name FROM Categorie", Categorie.class);
	      List<Categorie> categories = query.getResultList();
	      request.setAttribute("categories", categories);
	      if(categories.contains(name)) {
	    	  message = "Catégorie déjà existante";
		      redirectWithErrorMessage(response, uriRedirect, message);
	      }else {
	      if (name == null || name.isEmpty() || name.isBlank()) {
	         message = "Veuillez nomer la catégorie";
	         redirectWithErrorMessage(response, uriRedirect, message);
	      } else {
	         boolean transactionOk = false;
	         try {
	            userTransaction.begin();
	            Categorie categorie = new Categorie();
	            categorie.setName(name);
	            em.persist(categorie);
	            transactionOk = true;
	         } catch (Exception e) {
	            System.out.print("Une erreur est survennue lors de l'enregistrement");
	         } finally {
	            try {
	               if (transactionOk) {
	                  userTransaction.commit();
	                  response.sendRedirect("");
	               } else {
	                  message =
	                        "Une erreur est survenue lors de l'enregistrement veuillez contactez l'administrateur";
	                  userTransaction.rollback();
	                  String.format(uriRedirect, message);
	                  response.sendRedirect(uriRedirect);
	               }
	            } catch (Exception e) {
	               message =
	                     "Une erreur est survenue lors de l'enregistrement veuillez contactez l'administrateur";
	               String.format(uriRedirect, message);
	               response.sendRedirect(uriRedirect);
	            }
	         }
	      }
	     
	}
	}
	 private void redirectWithErrorMessage(HttpServletResponse response, String uriRedirect,
	         String message) throws IOException {
	      String urlRediret = String.format(uriRedirect, message);
	      response.sendRedirect(urlRediret);
	 }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	/*rotected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}*/

}
