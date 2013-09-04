/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dreamer8.yosimce.server;

import java.io.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;

import com.dreamer8.yosimce.server.hibernate.dao.ArchivoDAO;
import com.dreamer8.yosimce.server.hibernate.dao.HibernateUtil;
import com.dreamer8.yosimce.server.hibernate.pojo.Archivo;
import com.dreamer8.yosimce.server.hibernate.pojo.Usuario;
import com.dreamer8.yosimce.server.utils.StringUtils;

import org.hibernate.Hibernate;
import org.hibernate.Session;

/**
 * 
 * @author jorge
 */
public class DownloadServlet extends HttpServlet {

	/**
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
	 * methods.
	 * 
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             if a servlet-specific error occurs
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		HttpSession hs = request.getSession();
		Usuario u = (Usuario) hs.getAttribute("usuario");

		if (u == null) {
			showMessage(response, "Sin sesión",
					"Para poder descargar un archivo debe haber iniciado sesión previamente.");
			return;
		}

		String idArchivo = request.getParameter("id");
		String nombreArchivo = request.getParameter("file");

		if (idArchivo == null || idArchivo.equals("") || nombreArchivo == null
				|| nombreArchivo.equals("")) {
			showMessage(response, "Error", "Faltan parámetros obligatorios.");
			return;
		}

		Integer id = null;
		try {
			id = new Integer(idArchivo);
		} catch (NumberFormatException ex) {
		}

		if (id == null) {
			showMessage(response, "Error", "Los parámetros son incorrectos.");
			return;
		}

		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		s.beginTransaction();
		ArchivoDAO adao = new ArchivoDAO();
		Archivo a = adao.getById(id);

		if (a == null || !a.getTitulo().equals(nombreArchivo)) {
			showMessage(response, "Error", "No se ha encontrado el archivo.");
			return;
		}

		File f = new File(a.getRutaArchivo());

		if (f != null) {
			response.setContentType(a.getMimeType());
			response.setHeader(
					"Content-Disposition",
					"attachment; filename=\""
							+ StringUtils.getDatePathSafe(a.getTitulo())
							+ StringUtils.getExtension(a.getRutaArchivo())
							+ "\"");
			FileInputStream fis = new FileInputStream(f);
			IOUtils.copy(fis, response.getOutputStream());
		} else {
			showMessage(response, "Error", "No se ha encontrado el archivo.");
		}

		s.getTransaction().commit();
	}

	// <editor-fold defaultstate="collapsed"
	// desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
	/**
	 * Handles the HTTP <code>GET</code> method.
	 * 
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             if a servlet-specific error occurs
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 * 
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             if a servlet-specific error occurs
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Returns a short description of the servlet.
	 * 
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "Short description";
	}// </editor-fold>

	private void showMessage(HttpServletResponse response, String title,
			String message) throws IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		try {
			out.println("<html>");
			out.println("<head>");
			out.println("<title>" + title + "</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<script type=\"text/javascript\">");
			out.println("alert(\"" + message + "\");");
			out.println("</script>");
			out.println("</body>");
			out.println("</html>");

		} finally {
			out.close();
		}
	}
}
