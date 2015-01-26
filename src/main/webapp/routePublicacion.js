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

function fPublicacionRoute(op1, source) {
    switch (op1) {
        case "getaggregateviewsome":
            var longitud = source.data.page.list.length;
            var longitud2 = source.data.prettyColumns.length;
            var tabla = "<table border='1'>";
            for (i = 0; i < longitud; i++) {
                if (i == 0) {
                    tabla += "<tr>";
                    for (j = 0; j < longitud2; j++) {
                        tabla += "<th>" + source.data.prettyColumns[j] + "</th>";
                    }
                    tabla += "</tr>"
                }
                tabla += "<tr>"
                tabla += "<td>" + source.data.page.list[i].id + "</td>";
                tabla += "<td>" + source.data.page.list[i].contenido + "</td>";
                tabla += "<td>" + source.data.page.list[i].obj_usuario.login + "</td>";
                tabla += "</tr>"
            }
            tabla += "</table>";
            $("#tabla1").html(tabla);
            break;
    }
}