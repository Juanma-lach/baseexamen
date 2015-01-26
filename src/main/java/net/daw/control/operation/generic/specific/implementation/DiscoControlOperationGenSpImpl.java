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
package net.daw.control.operation.generic.specific.implementation;

import java.lang.reflect.InvocationTargetException;
import javax.servlet.http.HttpServletRequest;
import net.daw.control.operation.generic.implementation.ControlOperationGenImpl;
import net.daw.helper.ParameterCooker;
import net.daw.service.generic.specific.implementation.DiscoServiceGenSpImpl;

/**
 *
 * @author Armando
 */
public class DiscoControlOperationGenSpImpl extends ControlOperationGenImpl {

    private DiscoServiceGenSpImpl oDiscoService = null;

    public DiscoControlOperationGenSpImpl(HttpServletRequest request) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, Exception {
        super(request);
        oDiscoService = new DiscoServiceGenSpImpl(ParameterCooker.prepareObject(request), connection);
    }

    public String populate(HttpServletRequest request) throws Exception {
        Integer cantidad = Integer.parseInt(request.getParameter("cantidad"));
        if (cantidad <= 30 && cantidad >= 1) {
            return oDiscoService.populate(cantidad);
        } else {
            return "La cantidad debe estar entre 1 y 30";
        }
    }

    public String changeforeign(HttpServletRequest request) throws Exception {
        Integer id_genero = Integer.parseInt(request.getParameter("id_genero"));
        Integer id_genero_new = Integer.parseInt(request.getParameter("id_genero_new"));
        
        return oDiscoService.changeforeign(id_genero,id_genero_new);
    }
}
