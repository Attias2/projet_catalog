package com.nextu.controllers.categorie;
import java.io.IOException;
import java.util.*;


import com.nextu.app.servlets.entities.produit.Produit;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
/**
 * Servlet implementation class listerProduit
 */
@WebServlet("/listerProduit")
public class listerProduit extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@PersistenceContext(unitName = "sample-jpa")
	private EntityManager em;
    /**
     * Default constructor. 
     */
    public listerProduit() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	         throws ServletException, IOException {
	      TypedQuery<Produit> query = em.createQuery("SELECT d FROM Produit d", Produit.class);
	      List<Produit> produits = query.getResultList();
	      request.setAttribute("produits", produits);
	      this.getServletContext().getRequestDispatcher("/lstProduit.jsp").forward(request, response);
	   }

	

}
