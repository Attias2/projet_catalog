package com.nextu.controllers.categorie;

import java.io.IOException;
import java.util.List;
import com.nextu.app.servlets.entities.categorie.Categorie;
import com.nextu.app.servlets.entities.produit.Produit;

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
 * Servlet implementation class moddProduit
 */
@WebServlet("/moddProduit")
public class moddProduit extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 @PersistenceContext(unitName = "sample-jpa")
	   private EntityManager em;
	   @Resource
	   private UserTransaction userTransaction;
    /**
     * Default constructor. 
     */
    public moddProduit() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Long codeProduit = Long.valueOf(request.getParameter("codeProduit"));
	      Produit produit = em.find(Produit.class, codeProduit);
	      request.setAttribute("produit", produit);
	      String errorMessage = request.getParameter("errorMessage");
	      request.setAttribute("errorMessage", errorMessage);
	      TypedQuery<Categorie> query = em.createQuery("SELECT s FROM Categorie s", Categorie.class);
	      List<Categorie> categories = query.getResultList();
	      request.setAttribute("categories", categories);
	      this.getServletContext().getRequestDispatcher("/modProduit.jsp").forward(request,
	            response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	 protected void doPost(HttpServletRequest request, HttpServletResponse response)
	         throws ServletException, IOException {
	      // TODO Auto-generated method stub
	      String message;
	      String uriRedirect = "modProduit?codeProduit="
	            + request.getParameter("codeProduit") + "error=%s";
	     
	      Produit produit = getProduit(request);
	      Categorie  categorie = getCategorie (request);
	      if (produit == null) {
	         message = "Aucune discipline trouvé";
	         redirectWithErrorMessage(response, uriRedirect, message);
	      }
	      if (categorie == null) {
	         message = "Aucune Categorie trouvé";
	         redirectWithErrorMessage(response, uriRedirect, message);
	      }
	      String name = request.getParameter("name");
		  int quantite = Integer.parseInt(request.getParameter("quantite"));
		  long prix = Long.parseLong(request.getParameter("prix"));
	      if (name == null || name.isEmpty() || name.isBlank() || prix < 0 || quantite < 0) {
		         message = "Donnée non valide";
	         redirectWithErrorMessage(response, uriRedirect, message);

	      } else {
	         boolean transactionOk = false;
	         try {
	            userTransaction.begin();
	            produit.setName(name);
		        produit.setQuantite(quantite);
		        produit.setPrix(prix);
		        if(quantite == 0 ) {produit.setDisponible(false);} else {produit.setDisponible(true);}
	            produit.setCategorie(em.merge(categorie));
	            em.merge(produit);
	            transactionOk = true;
	         } catch (Exception e) {
	            System.out.print("Une erreur est survennue lors de l'enregistrement");
	         } finally {
	            try {
	               if (transactionOk) {
	                  userTransaction.commit();
	                  response.sendRedirect("produits");
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

	   private Produit getProduit(HttpServletRequest request) {
		  Produit produit = null;
	      try {
	         Long codeProduit = Long.valueOf(request.getParameter("codeProduit"));
	         produit = em.find(Produit.class, codeProduit);
	      } catch (EntityNotFoundException ex) {

	      }
	      return produit;
	   }

	   private Categorie getCategorie(HttpServletRequest request) {
		   Categorie categorie = null;
	      try {
	         Long codeCategorie = Long.valueOf(request.getParameter("codeCategorie"));
	         categorie = em.find(Categorie.class, codeCategorie);
	      } catch (EntityNotFoundException ex) {}
	      return categorie;
	   }

	   private void redirectWithErrorMessage(HttpServletResponse response, String uriRedirect,
	         String message) throws IOException {
	      String urlRediret = String.format(uriRedirect, message);
	      response.sendRedirect(urlRediret);
	   }

}
