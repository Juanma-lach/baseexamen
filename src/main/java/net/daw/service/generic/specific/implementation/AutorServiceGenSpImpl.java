/*
 * Copyright (C) 2014 Armando
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
import java.util.ArrayList;
import java.util.Collections;
import net.daw.bean.generic.specific.implementation.ApellidoBeanGenSpImpl;
import net.daw.bean.generic.specific.implementation.AutorBeanGenSpImpl;
import net.daw.bean.generic.specific.implementation.NombreBeanGenSpImpl;
import net.daw.bean.generic.specific.implementation.PublicacionBeanGenSpImpl;
import net.daw.bean.generic.specific.implementation.UsuarioBeanGenSpImpl;
import net.daw.dao.generic.specific.implementation.ApellidoDaoGenSpImpl;
import net.daw.dao.generic.specific.implementation.AutorDaoGenSpImpl;
import net.daw.dao.generic.specific.implementation.NombreDaoGenSpImpl;
import net.daw.helper.ExceptionBooster;
import net.daw.service.generic.implementation.TableServiceGenImpl;

/**
 *
 * @author Armando
 */
public class AutorServiceGenSpImpl extends TableServiceGenImpl {

    public AutorServiceGenSpImpl(String strObject, Connection con) {
        super(strObject, con);
    }

    public String populate() throws Exception {
        NombreDaoGenSpImpl oNombreDao = new NombreDaoGenSpImpl("nombre", oConnection);
        ApellidoDaoGenSpImpl oApellidoDao = new ApellidoDaoGenSpImpl("apellido", oConnection);
        AutorDaoGenSpImpl oAutorDao = new AutorDaoGenSpImpl("autor", oConnection);

        try {
            oConnection.setAutoCommit(false);

            ArrayList<NombreBeanGenSpImpl> pageNombre = oNombreDao.getPage(10, 1, null, null);
            ArrayList<ApellidoBeanGenSpImpl> pageApellido = oApellidoDao.getPage(10, 1, null, null);
            
            Collections.shuffle(pageNombre);
            Collections.shuffle(pageApellido);

            Integer longitud = pageNombre.size();
            for (int i = 0; i < longitud; i++) {
                AutorBeanGenSpImpl oAutorBean = new AutorBeanGenSpImpl();
                
                NombreBeanGenSpImpl oNombreBean = pageNombre.get(i);
                ApellidoBeanGenSpImpl oApellidoBean = pageApellido.get(i);

                oAutorBean.setId(0);
                oAutorBean.setNombre(oNombreBean.getNombre());
                oAutorBean.setApellido(oApellidoBean.getApellido());

                oAutorDao.set(oAutorBean);

            }

            oConnection.commit();
        } catch (Exception ex) {
            oConnection.rollback();
            ExceptionBooster.boost(new Exception(this.getClass().getName() + ":set ERROR: " + ex.getMessage()));
        }
        return "Populate con exito";
    }
}
