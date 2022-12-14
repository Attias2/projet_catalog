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

@WebServlet("/")
/**
 * Servlet implementation class lstCategorie
 */
public class lstCategorie extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 @PersistenceContext(unitName = "sample-jpa")
	   private EntityManager em;
    /**
     * Default constructor. 
     */
    public lstCategorie() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         // TODO Auto-generated method stub
         TypedQuery<Categorie> query = em.createQuery("SELECT s FROM Categorie s", Categorie.class);
         List<Categorie> categories = query.getResultList();
         request.setAttribute("categories", categories);
         this.getServletContext().getRequestDispatcher("/Categorie.jsp").forward(request, response);

      }
	

}
