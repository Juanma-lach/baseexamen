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


function fEstadoRoute(op1, source) {
    switch (op1) {
        case "getpage":
            var longitud = source.list.length;
            var tabla = "<table border='1'><tr><th>Id</th><th>Tipo</th></tr>";
            for (i = 0; i < longitud; i++) {
                tabla += "<tr>"
                tabla += "<td>" + source.list[i].id + "</td>";
                tabla += "<td>" + source.list[i].tipo + "</td>";
                tabla += "</tr>"
            }
            tabla += "</table>";
            $("#tabla1").html(tabla);
            break;
        default:
            break;
    }
}