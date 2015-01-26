/*
 * Copyright (C) 2015 Armando
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package net.daw.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.daw.control.operation.generic.specific.implementation.DiscoControlOperationGenSpImpl;
import net.daw.control.operation.generic.specific.implementation.GeneroControlOperationGenSpImpl;
import net.daw.control.route.generic.specific.implementation.DiscoControlRouteGenSpImpl;
import net.daw.control.route.generic.specific.implementation.GeneroControlRouteGenSpImpl;

/**
 *
 * @author Armando
 */
public class exe extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            String ob = request.getParameter("ob");
            String op = request.getParameter("op");
            String jsonResult = "";

            if (ob != null && op != null) {
                // nivel 1
                if (ob.equalsIgnoreCase("usuario")) {
                    //nivel 2
                    if (op.equalsIgnoreCase("login")) {
                        getServletContext().getRequestDispatcher("/formulario.jsp").forward(request, response);
                    }
                    //nivel 2
                    if (op.equalsIgnoreCase("logout")) {
                        request.getSession().invalidate();
                        out.println("Sesion finalizada");
                    }
                    //nivel 2
                    if (op.equalsIgnoreCase("logindesdeformulario")) {
                        Integer numero1 = Integer.parseInt(request.getParameter("numero1"));
                        Integer numero2 = Integer.parseInt(request.getParameter("numero2"));

                        Integer resultado = Integer.parseInt(request.getParameter("login"));

                        Integer suma = numero1 + numero2;
                        Integer multiplicacion = numero1 * numero2;
                        // nivel 3
                        if (resultado == suma) {
                            request.getSession().setAttribute("permiso", "suma");
                            out.println("Logueado con exito para ejer 2");
                        } else if (resultado == multiplicacion) {
                            request.getSession().setAttribute("permiso", "multiplicacion");
                            out.println("Logueado con exito para ejer 3");
                        } else {
                            out.println("El capcha no es correcto");
                        }

                    }
                }
                // Ejercicios que requieren sesion
                //nivel 1
                if (request.getSession().getAttribute("permiso") != null) {
                    // nivel 2
                    if (ob.equalsIgnoreCase("genero") && op.equalsIgnoreCase("getall")) {
                        String permiso = (String) request.getSession().getAttribute("permiso");
                        //nivel 3
                        if (permiso.equalsIgnoreCase("suma")) {
                            GeneroControlRouteGenSpImpl oGeneroRoute = new GeneroControlRouteGenSpImpl();
                            GeneroControlOperationGenSpImpl oGeneroControlOperation = new GeneroControlOperationGenSpImpl(request);
                            jsonResult = oGeneroRoute.execute(request, oGeneroControlOperation);
                            out.println(jsonResult);
                        } else {
                            out.println("No tienes permisos para el ejer 2");
                        }
                    }
                    // nivel 2
                    if (ob.equalsIgnoreCase("genero") && op.equalsIgnoreCase("getcount")) {
                        String permiso = (String) request.getSession().getAttribute("permiso");
                        //nivel 3
                        if (permiso.equalsIgnoreCase("multiplicacion")) {
                            GeneroControlRouteGenSpImpl oGeneroRoute = new GeneroControlRouteGenSpImpl();
                            GeneroControlOperationGenSpImpl oGeneroControlOperation = new GeneroControlOperationGenSpImpl(request);
                            jsonResult = oGeneroRoute.execute(request, oGeneroControlOperation);
                            out.println(jsonResult);
                        } else {
                            out.println("No tienes permisos para el ejer 3");
                        }
                    }
                    // nivel 2
                    if (ob.equalsIgnoreCase("disco") && op.equalsIgnoreCase("populate")) {
                        DiscoControlRouteGenSpImpl oDiscoRoute = new DiscoControlRouteGenSpImpl();
                        DiscoControlOperationGenSpImpl oDiscoControlOperation = new DiscoControlOperationGenSpImpl(request);
                        jsonResult = oDiscoRoute.execute(request, oDiscoControlOperation);
                        out.println(jsonResult);
                    }
                    // nivel 2
                    if (ob.equalsIgnoreCase("disco") && op.equalsIgnoreCase("getpage")) {
                        DiscoControlRouteGenSpImpl oDiscoRoute = new DiscoControlRouteGenSpImpl();
                        DiscoControlOperationGenSpImpl oDiscoControlOperation = new DiscoControlOperationGenSpImpl(request);
                        jsonResult = oDiscoRoute.execute(request, oDiscoControlOperation);
                        out.println(jsonResult);
                    }
                    //nivel 2
                    if (ob.equalsIgnoreCase("disco") && op.equalsIgnoreCase("changeforeign")) {
                        DiscoControlRouteGenSpImpl oDiscoRoute = new DiscoControlRouteGenSpImpl();
                        DiscoControlOperationGenSpImpl oDiscoControlOperation = new DiscoControlOperationGenSpImpl(request);
                        jsonResult = oDiscoRoute.execute(request, oDiscoControlOperation);
                        out.println(jsonResult);
                    }
                } else {
                    out.println("No hay sesion");
                }
            }

        } catch (Exception ex) {
            Logger.getLogger(exe.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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

}
