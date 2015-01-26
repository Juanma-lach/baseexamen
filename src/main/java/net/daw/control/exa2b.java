/*
 * Copyright (C) 2015 Juanma
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
import net.daw.bean.generic.specific.implementation.CompraBeanGenSpImpl;
import net.daw.bean.generic.specific.implementation.ProductoBeanGenSpImpl;
import net.daw.connection.implementation.BoneConnectionPoolImpl;
import net.daw.connection.publicinterface.ConnectionInterface;
import net.daw.dao.generic.specific.implementation.ClienteDaoGenSpImpl;
import net.daw.dao.generic.specific.implementation.CompraDaoGenSpImpl;
import net.daw.dao.generic.specific.implementation.ProductoDaoGenSpImpl;

/**
 *
 * @author Juanma
 */
public class exa2b extends HttpServlet {

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

            String op = request.getParameter("op");
            String ob = request.getParameter("ob");

            //Ejercicio1
            if (request.getSession().getAttribute("sesion") == null) {
                if (op.equalsIgnoreCase("login")) {

                    String Ape1 = request.getParameter("ape1");
                    Integer Id = Integer.parseInt(request.getParameter("id"));

                    ClienteBeanGenSpImpl oCliente = new ClienteBeanGenSpImpl();
                    ClienteDaoGenSpImpl oClienteDao = new ClienteDaoGenSpImpl("cliente", connection);

                    oCliente.setApe1(Ape1);

                    oCliente = oClienteDao.getFromLogin(oCliente);

                    if (oCliente.getId() == Id) {
                        request.getSession().setAttribute("sesion", oCliente);
                        out.println("te logiaste con exito wee");
                    } else {
                        out.println("el logeo no funsiono");
                    }
                }
            }
            //ejer1 logout
            if (op.equalsIgnoreCase("logout")) {
                request.getSession().invalidate();
                out.println("has cerrado sesion");
            }

            //ejercicio2
            if (request.getSession().getAttribute("sesion") != null) {
                if (ob.equalsIgnoreCase("cliente") && op.equalsIgnoreCase("insertar")) {

                    String nombre = request.getParameter("nombre");
                    String ape1 = request.getParameter("ape1");
                    String ape2 = request.getParameter("ape2");

                    ClienteBeanGenSpImpl oCliente = new ClienteBeanGenSpImpl(0);//Al poner el 0 ya no hace falta poner el setID abajo
                    ClienteDaoGenSpImpl oClienteDao = new ClienteDaoGenSpImpl("cliente", connection);

                    oCliente.setNombre(nombre);
                    oCliente.setApe1(ape1);
                    oCliente.setApe2(ape2);

                    oCliente = oClienteDao.set(oCliente);//el pojo relleno lo mete en el dao y el dao lo envia a la bdd.

                    if (oCliente.getId() != 0) {
                        out.println("El cliente " + nombre + " se ha creado correctamente");
                    } else {
                        out.println("ERROR");
                    }
                }

            }

            if (request.getSession().getAttribute("sesion") != null) {
                if (ob.equalsIgnoreCase("compra") && op.equalsIgnoreCase("insertar")) {

                    String nombre = request.getParameter("nombre");
                    String ape1 = request.getParameter("ape1");
                    String ape2 = request.getParameter("ape2");

                    String descripcion = request.getParameter("descripcion");
                    Integer cantidad = Integer.parseInt(request.getParameter("cantidad"));
                    
                    
                    //CREAMOS LOS POJOS Y LOS DAO
                    ClienteBeanGenSpImpl oCliente = new ClienteBeanGenSpImpl();
                    ClienteDaoGenSpImpl oClienteDao = new ClienteDaoGenSpImpl("cliente", connection);
                    CompraBeanGenSpImpl oCompra = new CompraBeanGenSpImpl(0);//Al poner el 0 ya no hace falta poner el setID abajo
                    CompraDaoGenSpImpl oCompraDao = new CompraDaoGenSpImpl("compra", connection);
                    ProductoBeanGenSpImpl oProducto = new ProductoBeanGenSpImpl();
                    ProductoDaoGenSpImpl oProductoDao = new ProductoDaoGenSpImpl("producto", connection);
                    
                    //SACAMOS LAS ID's
                    oCliente.setApe1(ape1);
                    oCliente = oClienteDao.getFromLogin(oCliente);//pillamos el id de cliente
                    oProducto.setDescripcion(descripcion);
                    oProducto= oProductoDao.getFromProducto(oProducto);//pillamos la id de producto
                    
                    //RELLENAMOS COMPRA
                    oCompra.setId_cliente(oCliente.getId());
                    oCompra.setId_producto(oProducto.getId());
                    oCompra.setCantidad(cantidad);
                    
                    oCompra = oCompraDao.set(oCompra);
                    
                    if(oCompra.getId()!=0){
                        out.println("La compra se ha añadido satisfactoriamente");
                    }else{
                        out.println("mierda pa tu boca");
                    }
                    
                    
                    
                    
                }

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
            Logger.getLogger(exa2b.class
                    .getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(exa2b.class
                    .getName()).log(Level.SEVERE, null, ex);
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
