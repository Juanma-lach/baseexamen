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

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;
import net.daw.bean.generic.specific.implementation.AutorBeanGenSpImpl;
import net.daw.bean.generic.specific.implementation.DiscoBeanGenSpImpl;
import net.daw.bean.generic.specific.implementation.GeneroBeanGenSpImpl;
import net.daw.bean.generic.specific.implementation.NombreBeanGenSpImpl;
import net.daw.bean.generic.specific.implementation.TituloBeanGenSpImpl;
import net.daw.dao.generic.specific.implementation.AutorDaoGenSpImpl;
import net.daw.dao.generic.specific.implementation.DiscoDaoGenSpImpl;
import net.daw.dao.generic.specific.implementation.GeneroDaoGenSpImpl;
import net.daw.dao.generic.specific.implementation.TituloDaoGenSpImpl;
import net.daw.service.generic.implementation.TableServiceGenImpl;

/**
 *
 * @author Armando
 */
public class DiscoServiceGenSpImpl extends TableServiceGenImpl {

    public DiscoServiceGenSpImpl(String strObject, Connection con) {
        super(strObject, con);
    }

    public String populate(Integer cantidad) throws Exception {
        DiscoDaoGenSpImpl oDiscoDao = new DiscoDaoGenSpImpl("disco", oConnection);

        TituloDaoGenSpImpl oTituloDao = new TituloDaoGenSpImpl("titulo", oConnection);
        AutorDaoGenSpImpl oAutorDao = new AutorDaoGenSpImpl("autor", oConnection);
        GeneroDaoGenSpImpl oGeneroDao = new GeneroDaoGenSpImpl("genero", oConnection);

        ArrayList<TituloBeanGenSpImpl> pageTitulo = oTituloDao.getPage(1000, 1, null, null);
        ArrayList<AutorBeanGenSpImpl> pageAutor = oAutorDao.getPage(1000, 1, null, null);
        ArrayList<GeneroBeanGenSpImpl> pageGenero = oGeneroDao.getPage(1000, 1, null, null);

        Collections.shuffle(pageTitulo);
        Collections.shuffle(pageAutor);
        Collections.shuffle(pageGenero);

        for (Integer i = 0; i <= cantidad; i++) {
            DiscoBeanGenSpImpl oDiscoBean = new DiscoBeanGenSpImpl(0);
            TituloBeanGenSpImpl oTituloBean = new TituloBeanGenSpImpl();
            AutorBeanGenSpImpl oAutorBean = new AutorBeanGenSpImpl();
            GeneroBeanGenSpImpl oGeneroBean = new GeneroBeanGenSpImpl();

            Random r = new Random();
            int valorTitulo = r.nextInt(pageTitulo.size());
            oTituloBean = pageTitulo.get(valorTitulo);
            oDiscoBean.setNombre(oTituloBean.getDescripcion());

            int valorAutor = r.nextInt(pageAutor.size());
            oAutorBean = pageAutor.get(valorAutor);
            oDiscoBean.setId_autor(oAutorBean.getId());

            int valorGenero = r.nextInt(pageGenero.size());
            oGeneroBean = pageGenero.get(valorGenero);
            oDiscoBean.setId_genero(oGeneroBean.getId());

            oDiscoDao.set(oDiscoBean);
        }

        return "{'data':'Operacion realizada con exito'}";
    }

    public String changeforeign(Integer id_genero, Integer id_genero_new) throws Exception {
        String jsonresult = "";
        DiscoDaoGenSpImpl oDiscoDao = new DiscoDaoGenSpImpl("disco", oConnection);
        GeneroDaoGenSpImpl oGeneroDao = new GeneroDaoGenSpImpl("genero", oConnection);

        ArrayList<GeneroBeanGenSpImpl> pageGenero = oGeneroDao.getPage(1000, 1, null, null);
        ArrayList<DiscoBeanGenSpImpl> pageDisco = oDiscoDao.getPage(1000, 1, null, null);

        for (Integer i = 0; i < pageGenero.size(); i++) {
            GeneroBeanGenSpImpl oGeneroBean = new GeneroBeanGenSpImpl();
            oGeneroBean = pageGenero.get(i);
            if (oGeneroBean.getId() == id_genero_new) {
                jsonresult = "existe";
            }
        }
        if (jsonresult.equalsIgnoreCase("existe")) {
            for (Integer j = 0; j < pageDisco.size(); j++) {
                DiscoBeanGenSpImpl oDiscoBean = new DiscoBeanGenSpImpl();
                oDiscoBean = pageDisco.get(j);
                if (oDiscoBean.getId_genero() == id_genero) {
                    oDiscoBean.setId_genero(id_genero_new);
                    oDiscoDao.set(oDiscoBean);
                    jsonresult = "Operacion Realizada con exito";
                }
            }
        } else {
            jsonresult = "El genero especificado no existe";
        }
        return jsonresult;
    }
}
