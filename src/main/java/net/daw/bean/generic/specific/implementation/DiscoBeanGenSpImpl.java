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
public class DiscoBeanGenSpImpl extends BeanGenImpl implements BeanInterface {

    @Expose
    private String nombre;

    @Expose(serialize = false)
    private Integer id_genero = 0; //importante inicializar a 0 las claves ajenas
    @Expose(deserialize = false)
    private GeneroBeanGenSpImpl obj_genero = null;

    @Expose(serialize = false)
    private Integer id_autor = 0; //importante inicializar a 0 las claves ajenas
    @Expose(deserialize = false)
    private AutorBeanGenSpImpl obj_autor = null;

    public DiscoBeanGenSpImpl() {
    }

    public DiscoBeanGenSpImpl(Integer id) {
        super(id);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getId_genero() {
        return id_genero;
    }

    public void setId_genero(Integer id_genero) {
        this.id_genero = id_genero;
    }

    public GeneroBeanGenSpImpl getObj_genero() {
        return obj_genero;
    }

    public void setObj_genero(GeneroBeanGenSpImpl obj_genero) {
        this.obj_genero = obj_genero;
    }

    public Integer getId_autor() {
        return id_autor;
    }

    public void setId_autor(Integer id_autor) {
        this.id_autor = id_autor;
    }

    public AutorBeanGenSpImpl getObj_autor() {
        return obj_autor;
    }

    public void setObj_autor(AutorBeanGenSpImpl obj_autor) {
        this.obj_autor = obj_autor;
    }
    
    

}
