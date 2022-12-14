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
 * Servlet implementation class supCategorie
 */
@WebServlet("/supCategorie")
public class supCategorie extends HttpServlet {
	private static final long serialVersionUID = 1L;
	   @PersistenceContext(unitName = "sample-jpa")
	   private EntityManager em;
	   @Resource
	   private UserTransaction userTransaction;

    /**
     * Default constructor. 
     */
    public supCategorie() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         // TODO Auto-generated method stub
         String message = "";
         String uriRedirect = """
               Categorie.jsp?errorMessage=%s
               """;
         try {
            Long codeCategorie = Long.parseLong(request.getParameter("codeCategorie"));
            Categorie categorie = em.find(Categorie.class, codeCategorie);
            if (categorie == null) {
               message = "Aucune Catgorie correspondant à ce code";
               redirectWithErrorMessage(response, uriRedirect, message);
            } else {
               boolean transactionOk = false;
               try {
                  userTransaction.begin();
                  em.remove(em.merge(categorie));
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
         } catch (Exception ex) {
            message =
                  "Une erreur est survenue lors de la suppression veuillez contacter l'administrateur";
            redirectWithErrorMessage(response, uriRedirect, message);
         }


      }

      private void redirectWithErrorMessage(HttpServletResponse response, String uriRedirect,
            String message) throws IOException {

         String.format(uriRedirect, message);
         response.sendRedirect(uriRedirect);
      }

}
