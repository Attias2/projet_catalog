package com.nextu.controllers.categorie;

import java.io.IOException;
import java.util.*;

import com.nextu.app.servlets.entities.categorie.*;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.UserTransaction;
/**
 * Servlet implementation class moddCategorie
 */

@WebServlet("/moddCategorie")
public class moddCategorie extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 @PersistenceContext(unitName = "sample-jpa")
	 private EntityManager em;
	 @Resource
	 private UserTransaction userTransaction;
    /**
     * Default constructor. 
     */
    public moddCategorie() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		Long codeCategorie = Long.valueOf(request.getParameter("codeCategorie"));
		Categorie categorie = em.find(Categorie.class, codeCategorie);
	      request.setAttribute("categorie", categorie);
	      String errorMessage = request.getParameter("errorMessage");
	      request.setAttribute("errorMessage", errorMessage);
	      this.getServletContext().getRequestDispatcher("/moddCategorie").forward(request,
	            response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		String name = request.getParameter("name");
	    String message = "";
	    Long codeCategorie = Long.valueOf(request.getParameter("codeCategorie"));
	    TypedQuery<Categorie> query = em.createQuery("SELECT name FROM Categorie", Categorie.class);
	      List<Categorie> categories = query.getResultList();
	      request.setAttribute("categories", categories);
	      String uriRedirect = "modCategorie?codeCategorie=" + codeCategorie+"&errorMessage=%s";
	      if(categories.contains(name)) {
	    	  message = "Catégorie déjà existante";
		      redirectWithErrorMessage(response, uriRedirect, message);
	      }else {
	      if (name == null || name.isEmpty() || name.isBlank()) {
	         message = "Veuillez renseignez le nouveau nom de la Categorie";
	         redirectWithErrorMessage(response, uriRedirect, message);

	      } else {
	    	 Categorie categorie = em.find(Categorie.class, codeCategorie);
	         if (categorie == null) {
	            message = "Aucune categorie correspondant à ce code";
	            redirectWithErrorMessage(response, uriRedirect, message);
	         } else {
	            boolean transactionOk = false;
	            try {
	               userTransaction.begin();
	               categorie.setName(name);
	               em.merge(categorie);
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
	                     redirectWithErrorMessage(response, uriRedirect, message);

	                  }
	               } catch (Exception e) {
	                  message =
	                        "Une erreur est survenue lors de l'enregistrement veuillez contactez l'administrateur";
	                  redirectWithErrorMessage(response, uriRedirect, message);
	               }
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
}

