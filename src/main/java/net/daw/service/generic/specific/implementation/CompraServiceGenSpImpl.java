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
package net.daw.service.generic.specific.implementation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.sql.Connection;
import javax.servlet.http.HttpServletRequest;
import net.daw.bean.generic.specific.implementation.ClienteBeanGenSpImpl;
import net.daw.bean.generic.specific.implementation.CompraBeanGenSpImpl;
import net.daw.bean.generic.specific.implementation.ProductoBeanGenSpImpl;
import net.daw.dao.generic.specific.implementation.ClienteDaoGenSpImpl;
import net.daw.dao.generic.specific.implementation.CompraDaoGenSpImpl;
import net.daw.dao.generic.specific.implementation.ProductoDaoGenSpImpl;
import net.daw.service.generic.implementation.TableServiceGenImpl;

/**
 *
 * @author Armando
 */
public class CompraServiceGenSpImpl extends TableServiceGenImpl {

    public CompraServiceGenSpImpl(String strObject, Connection con) {
        super(strObject, con);
    }

    public String insertar(HttpServletRequest request) throws Exception {
        String jason = "";
        // insertar compra, id_usuario, id_producto
        //Daos
        ClienteDaoGenSpImpl oClienteDao = new ClienteDaoGenSpImpl("cliente", oConnection);
        ProductoDaoGenSpImpl oProductoDao = new ProductoDaoGenSpImpl("producto", oConnection);
        CompraDaoGenSpImpl oCompraDao = new CompraDaoGenSpImpl("compra", oConnection);
        // Beans
        ClienteBeanGenSpImpl oClienteBean = new ClienteBeanGenSpImpl(0);
        ProductoBeanGenSpImpl oProductoBean = new ProductoBeanGenSpImpl(0);

        // Obtenemos de la url
        String nombre = request.getParameter("nombre");
        String ape1 = request.getParameter("ape1");
        String ape2 = request.getParameter("ape2");

        String descripcion = request.getParameter("descripcion");

        Integer cantidad = Integer.parseInt(request.getParameter("cantidad"));

        // Insertamos en los beans
        oClienteBean.setNombre(nombre);
        oClienteBean.setApe1(ape1);
        oClienteBean.setApe2(ape2);

        oProductoBean.setDescripcion(descripcion);

        // Obtenemos el pojo relleno
        oClienteBean = oClienteDao.getFromLogin(oClienteBean);
        oProductoBean = oProductoDao.getFromField(oProductoBean);

        // Obtenermos los ids
        Integer idCliente = oClienteBean.getId();
        Integer idProducto = oProductoBean.getId();

        // Creamos el pojo de compra
        CompraBeanGenSpImpl oCompraBean = new CompraBeanGenSpImpl(0);
        oCompraBean.setId_cliente(idCliente);
        oCompraBean.setId_producto(idProducto);
        oCompraBean.setCantidad(cantidad);
        
        oCompraBean = oCompraDao.set(oCompraBean);

        Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
        jason = gson.toJson(oCompraBean);
        return jason;
    }

}
