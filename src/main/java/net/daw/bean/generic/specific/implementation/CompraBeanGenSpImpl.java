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
package net.daw.bean.generic.specific.implementation;

import com.google.gson.annotations.Expose;
import net.daw.bean.generic.implementation.BeanGenImpl;
import net.daw.bean.publicinterface.BeanInterface;

/**
 *
 * @author Armando
 */
public class CompraBeanGenSpImpl extends BeanGenImpl implements BeanInterface {

    public CompraBeanGenSpImpl() {
    }

    public CompraBeanGenSpImpl(Integer id) {
        super(id);
    }

    @Expose(serialize = false)
    private Integer id_cliente = 0;
    @Expose(deserialize = false)
    private ClienteBeanGenSpImpl obj_cliente = null;

    @Expose(serialize = false)
    private Integer id_producto = 0;
    @Expose(deserialize = false)
    private ProductoBeanGenSpImpl obj_producto = null;
    
    @Expose
    private Integer cantidad;

    public Integer getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(Integer id_cliente) {
        this.id_cliente = id_cliente;
    }

    public ClienteBeanGenSpImpl getObj_cliente() {
        return obj_cliente;
    }

    public void setObj_cliente(ClienteBeanGenSpImpl obj_cliente) {
        this.obj_cliente = obj_cliente;
    }

    public Integer getId_producto() {
        return id_producto;
    }

    public void setId_producto(Integer id_producto) {
        this.id_producto = id_producto;
    }

    public ProductoBeanGenSpImpl getObj_producto() {
        return obj_producto;
    }

    public void setObj_producto(ProductoBeanGenSpImpl obj_producto) {
        this.obj_producto = obj_producto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
    
}
