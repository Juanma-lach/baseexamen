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
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.daw.bean.generic.specific.implementation.ClienteBeanGenSpImpl;
import net.daw.connection.implementation.BoneConnectionPoolImpl;
import net.daw.connection.publicinterface.ConnectionInterface;
import net.daw.control.operation.generic.specific.implementation.CompraControlOperationGenSpImpl;
import net.daw.control.route.generic.specific.implementation.CompraControlRouteGenSpImpl;
import net.daw.dao.generic.specific.implementation.ClienteDaoGenSpImpl;

/**
 *
 * @author Armando
 */
public class exe1 extends HttpServlet {

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
            throws ServletException, IOException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            ConnectionInterface DataConnectionSource = new BoneConnectionPoolImpl();
            Connection connection = DataConnectionSource.newConnection();

            String objeto = request.getParameter("ob");
            String operacion = request.getParameter("op");
            String jsonResult = "";

            // nivel 1 ejercicio de logout
            if (operacion.equalsIgnoreCase("logout")) {
                request.getSession().invalidate();
                out.println("Sesion finalizada");
            }
            //nivel 1 Ejercicio de login
            if (request.getSession().getAttribute("clienteBean") == null) {
                // nivel 2
                if (operacion.equalsIgnoreCase("login")) {
                    String ape1 = request.getParameter("ape1");
                    Integer id = Integer.parseInt(request.getParameter("id"));

                    ClienteDaoGenSpImpl oClienteDao = new ClienteDaoGenSpImpl("cliente", connection);

                    ClienteBeanGenSpImpl oCliente = new ClienteBeanGenSpImpl();
                    oCliente.setApe1(ape1);
                    oCliente = oClienteDao.getFromLogin(oCliente);
                    // nivel 3
                    if (id == oCliente.getId()) {
                        request.getSession().setAttribute("clienteBean", oCliente);
                        out.println("logueado con exito");
                    } else {
                        out.println("Error los ids no coinciden");
                    }
                }
            }
            // nivel 1 ejercicio numero 2 de insertar 
            if (operacion.equalsIgnoreCase("insertar") && objeto.equalsIgnoreCase("cliente")) {
                // nivel 2
                if (request.getSession().getAttribute("clienteBean") != null) {
                    ClienteDaoGenSpImpl oClienteDao = new ClienteDaoGenSpImpl("cliente", connection);
                    ClienteBeanGenSpImpl oCliente = new ClienteBeanGenSpImpl(0);

                    String nombre = request.getParameter("nombre");
                    String ape1 = request.getParameter("ape1");
                    String ape2 = request.getParameter("ape2");
                    oCliente.setNombre(nombre);
                    oCliente.setApe1(ape1);
                    oCliente.setApe2(ape2);

                    oCliente = oClienteDao.set(oCliente);
                    // nivel 3
                    if (oCliente.getId() != 0) {
                        out.println("Insertado con el id: " + oCliente.getId());
                    } else {
                        out.println("Error al insertar");
                    }
                    // nivel 2
                } else {
                    out.println("No tienes permisos");
                }
            }

            // url03: http://localhost:8081/servidor_nov_2014/exe?
            //ob=compra&op=insertar&nombre=sunombre&ape1=suapellido1&ape2=suapellido2&descripcion=Taladro&cantidad=3
            // nivel 1
            if (objeto.equalsIgnoreCase("compra") && operacion.equalsIgnoreCase("insertar")) {
                // nivel 2
                if (request.getSession().getAttribute("clienteBean") != null) {
                    CompraControlRouteGenSpImpl oCompraRoute = new CompraControlRouteGenSpImpl();
                    CompraControlOperationGenSpImpl oCompraControlOperation = new CompraControlOperationGenSpImpl(request);
                    jsonResult = oCompraRoute.execute(request, oCompraControlOperation);
                    out.println(jsonResult);
                } else {
                    out.println("No tienes permisos");
                }

            }

            // nivel 1 cierra sesion si no esta cerrada
            if (connection != null) {
                connection.close();
            }
            // nivel 1 cierra sesion si no esta cerrada
            if (DataConnectionSource != null) {
                DataConnectionSource.disposeConnection();
            }
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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(exe1.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(exe1.class.getName()).log(Level.SEVERE, null, ex);
        }
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
