package com.nextu.controllers.categorie;

import java.io.IOException;
import java.util.*;

import com.nextu.app.servlets.entities.categorie.Categorie;
import com.nextu.app.servlets.entities.produit.*;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.UserTransaction;

/**
 * Servlet implementation class creerProduit
 */
@WebServlet("/creerProduit")
public class creerProduit extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@PersistenceContext(unitName = "sample-jpa")
	private EntityManager em;
	@Resource
	private UserTransaction userTransaction;
    /**
     * Default constructor. 
     */
    public creerProduit() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         TypedQuery<Categorie> query = em.createQuery("SELECT s FROM Categorie s", Categorie.class);
         List<Categorie> categories = query.getResultList();
         request.setAttribute("categories", categories);
         this.getServletContext().getRequestDispatcher("/form-discipline.jsp").forward(request,
               response);
      }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String message = "Veuillez selectionner une categorie";
	    String name = request.getParameter("name");
	    int quantite = Integer.parseInt(request.getParameter("quantite"));
	    long prix = Long.parseLong(request.getParameter("prix"));
	    TypedQuery<Produit> query = em.createQuery("SELECT name FROM Produit", Produit.class);
	    List<Produit> produits = query.getResultList();
	      request.setAttribute("produits", produits);
	      if(produits.contains(name)) {
	    	  message = "Produit existant";
		      request.setAttribute("errorMessage", message);
		      redirectWithErrorMessage(response, request);
	      }else {
	      if (name == null || name.isEmpty() || name.isBlank() || prix < 0 || quantite < 0) {
	         message = "Donnée non valide";
	         request.setAttribute("errorMessage", message);
	         redirectWithErrorMessage(response, request);
	      } else {
	    	  Categorie categorie = this.findCategorie(request.getParameter("codeCategorie"));
	         if (categorie == null) {
	            message = "Aucun enregistrement de sport trouvé";
	            request.setAttribute("errorMessage", message);
	            redirectWithErrorMessage(response, request);
	         } else {
	            saveProduit(response, request, name, categorie, quantite, prix);
	         }
	      }
	   }
	}


	 private void saveProduit(HttpServletResponse response, HttpServletRequest request,
	         String name, Categorie categorie, int quantite, long prix) throws IOException {
	      String message;
	      boolean transactionOk = false;
	      try {
	         userTransaction.begin();
	         Produit produit = new Produit();
	         produit.setName(name);
	         produit.setQuantite(quantite);
	         produit.setPrix(prix);
	         produit.setCategorie(em.merge(categorie));
	         if(quantite == 0) { produit.setDisponible(false);}else {produit.setDisponible(true);}
	         em.persist(produit);
	         transactionOk = true;
	      } catch (Exception e) {
	         System.out.print("Une erreur est survennue lors de l'enregistrement");
	      } finally {
	         try {
	            if (transactionOk) {
	               userTransaction.commit();
	               response.sendRedirect("disciplines");
	            } else {
	               message =
	                     "Une erreur est survenue lors de l'enregistrement veuillez contactez l'administrateur";
	               userTransaction.rollback();
	               request.setAttribute("errorMessage", message);
	               redirectWithErrorMessage(response, request);
	            }
	         } catch (Exception e) {
	            message =
	                  "Une erreur est survenue lors de l'enregistrement veuillez contactez l'administrateur";
	            request.setAttribute("errorMessage", message);
	            redirectWithErrorMessage(response, request);
	         }
	      }
	   }

	   private Categorie findCategorie(String codeCategorie) throws IOException {
		   Categorie categorie = null;
	      try {
	    	  categorie = em.find(Categorie.class, Long.valueOf(codeCategorie));
	      } catch (EntityNotFoundException e) {
	         System.out.print("Aucune entité trouvé");
	      } catch (Exception ex) {
	         System.out.print(
	               "Une erreur est survenue lors de l'enregistrement veuillez contactez l'administrateur ");
	      }
	      return categorie;
	   }

	   private void redirectWithErrorMessage(HttpServletResponse response, HttpServletRequest request)
	         throws IOException {
	      try {
	         this.getServletContext().getRequestDispatcher("/form-discipline.jsp").forward(request,
	               response);
	      } catch (ServletException e) {
	         // TODO Auto-generated catch block
	         e.printStackTrace();
	      } catch (IOException e) {
	         // TODO Auto-generated catch block
	         e.printStackTrace();
	      }
	   }
}
