//VISTA
var vista = function(clase) {
//contexto privado
    var link = "#";
    var neighborhood = 2;
    var urlJson = path + '/json?ob=' + clase;
    var urlJsp = path + '/jsp?ob=' + clase;
    return {
        printValue: function(value, valor) {
            var thisObject = this;
            var strResult = "";
            if (/obj_/.test(valor)) {
                if (value[valor].id > 0) {
                    strResult = '<a href="jsp#/' + valor.substring(4) + '/view/' + value[valor].id + '">' + value[valor].id + ":" + util().getForeign(value[valor]) + '</a>';
                } else {
                    strResult = '???';
                }
            } else {
                switch (value[valor]) {
                    case true:
                        strResult = '<i class="glyphicon glyphicon-ok"></i>';
                        break;
                    case false:
                        strResult = '<i class="glyphicon glyphicon-remove"></i>';
                        break;
                    default:
                        strResult = decodeURIComponent(value[valor]);
                        //if (typeof fieldContent == "string") {
                        if (strResult.length > 50) //don't show too long fields
                            strResult = strResult.substr(0, 20) + " ...";
                        //}
                }
            }
            return strResult;
        },
        //contexto público (interface)
//        getName: function() {
//            return objeto.getName();
//        },
//        getObject: function() {
//            return objeto;
//        },
        getLoading: function() {
            return '<img src="fonts/ajax-loading.gif" alt="cargando..." />';
        },
        cargaModalBuscarClaveAjenaNuevo: function(objControl, objParams, place_id, place_desc, descObjAjena) {
            //var objConsulta = objeto(strObjetoForeign, path);
            //var consultaView = vista(objConsulta, path);
            cabecera = '<button id="full-width" type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>' + '<h3 id="myModalLabel">Elección</h3>';
            pie = '<button class="btn btn-primary" data-dismiss="modal" aria-hidden="true">Cerrar</button>';
            //listado = vista('documento').getEmptyList();
            util().loadForm('#modal01', cabecera, "", pie, true);
            //var consultaControl = control(path);
            //consultaControl.inicia(consultaView, 1, null, null, 10, null, null, null, functionCallback, null, null, null);
            objControl.list('#modal-body', objParams, true);
            objControl.modalListEventsLoading('#modal-body', objParams, function(id) {
                //fulfill id
                place_id.val(id).change();
                //fulfill description
                place_desc.text(decodeURIComponent(objeto(descObjAjena).getMeAsAForeignKey(id)));
                //hide form
                $('#modal01').modal('hide');
            });
        },
//        getRegisterTableView: function(place, id) {
//            $(place).empty().append("<h1>Vista de " + this.getName() + "</h1>");
//            $(place).append((this.getObjectTable(id)));
//            $(place).append('<a class="btn btn-primary" href="jsp#/' + this.getName() + '/edit/' + id + '">Editar</a>');
//            $(place).append('<a class="btn btn-primary" href="jsp#/' + this.getName() + '/remove/"' + id + '">Borrar</a>');
//            $(place).append('<a class="btn btn-primary" href="jsp#/' + this.getName() + '/list/"' + id + '">Volver</a>');
//        },
        getPageLinks: function(url, page_number, total_pages, neighborhood) {
            vector = "<ul class=\"pagination\">";
            if (page_number > 1)
                vector += ('<li><a class="pagination_link" id="' + (page_number - 1) + '" href="' + url + '&page=' + (page_number - 1) + '">prev</a></li>');
            if (page_number > neighborhood + 1)
                vector += ('<li><a class="pagination_link" id="1" href="' + url + '&page=1">1</a></li>');
            if (page_number > neighborhood + 2)
                vector += ('<li>' + '<a href="#">...</a>' + '</li>');
            for (i = (page_number - neighborhood); i <= (page_number + neighborhood); i++) {
                if (i >= 1 && i <= total_pages) {
                    if (page_number === i) {
                        vector += ('<li class="active"><a class="pagination_link" id="' + i + '" href="' + url + '&page=' + i + '">' + i + '</a></li>');
                    }
                    else
                        vector += ('<li><a class="pagination_link" id="' + i + '" href="' + url + '&page=' + i + '">' + i + '</a></li>');
                }
            }
            if (page_number < total_pages - (neighborhood + 1))
                vector += ('<li>' + '<a href="#">...</a>' + '</li>');
            if (page_number < total_pages - (neighborhood))
                vector += ('<li><a class="pagination_link" id="' + total_pages + '" href="' + url + '&page=' + total_pages + '">' + total_pages + '</a></li>');
            if (page_number < total_pages)
                vector += ('<li><a class="pagination_link"  id="' + (page_number + 1) + '" href="' + url + '&page=' + (page_number + 1) + '">next</a></li>');
            vector += "</ul>";
            return vector;
        },
        getTemplate: function() {
            $.when(ajax().ajaxCallSync(objeto.getUrlJsp() + '&op=' + template, 'GET', '')).done(function(data) {
                form = data;
            });
            return form;
        },
        getPanel: function(titulo, contenido) {
            return '<div class="panel panel-default"><div class="panel-heading"><h1>' + titulo + '</h1></div><div class="panel-body">' + contenido + '</div></div>';
        },
        getEmptyForm: function() {
            $.when(ajax().ajaxCallSync(urlJsp + '&op=form&mode=1', 'GET', '')).done(function(data) {
                form = data;
            });
            return form;
        },
//        getEmptyList: function() {
//            $.when(ajax().ajaxCallSync(urlJsp + '&op=list&mode=1', 'GET', '')).done(function(data) {
//                form = data;
//            });
//            return form;
//        },
        getEmptyHList: function() {
            $.when(ajax().ajaxCallSync(urlJsp + '&op=hlist&mode=1', 'GET', '')).done(function(data) {
                form = data;
            });
            return form;
        },
        getEmptyVList: function() {
            $.when(ajax().ajaxCallSync(urlJsp + '&op=vlist&mode=1', 'GET', '')).done(function(data) {
                form = data;
            });
            return form;
        },
        getEmptyDiv: function() {
            return '<div id="content"></div>';
        },
        getEmptyModal: function() {
            var modal = '<div id="modal01" class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">';
            modal += '<div class="modal-dialog modal-lg">';
            modal += '<div class="modal-content">';
            modal += '<div class="modal-header" id="modal-header"></div>';
            modal += '<div class="modal-body" id="modal-body"></div>';
            modal += '<div class="modal-footer" id="modal-footer"></div>';
            modal += '</div>';
            modal += '</div>';
            modal += '</div>';
            return modal;
        },
        getObjectTable: function(nombresCamposBonitos, valoresRegistro, nombresCampos) {
            var thisObject = this;
            var tabla = "<table class=\"table table table-bordered table-condensed\">";
            $.each(nombresCampos, function(index, nombreDeCampo) {
                tabla += '<tr><td><strong>' + nombresCamposBonitos[index] + '</strong></td>';
                tabla += '<td>' + thisObject.printValue(valoresRegistro, nombreDeCampo) + '</td>';
            });
            tabla += '</table>';
            return tabla;
        },
        doFillForm: function(campos, datos) {
            var thisObject = this;
            $.each(campos, function(index, valor) {
                if (/obj_/.test(valor)) {
                    $('#' + campos[index] + "_id").val(decodeURIComponent(datos[campos[index]].id));
                    $('#' + campos[index] + "_desc").text(decodeURIComponent(util().getForeign(datos[campos[index]])));
                    //$('#' + campos[index] + "_desc").text(decodeURIComponent(thisObject.getForeign(datos[campos[index]])));
                } else {
                    switch (datos[campos[index]]) {
                        case true:
                            $('#' + campos[index]).attr("checked", "checked");
                            break;
                        case false:
                            $('#' + campos[index]).attr("checked", "");
                            break;
                        default:
                            //$('#' + campos[index]).val(decodeURIComponent(datos[campos[index]]));
                            $('#' + campos[index]).val(decodeURIComponent(thisObject.printValue(datos, valor)));
                    }
                }

            });
        },
        getRegistersInfo: function(regs) {
            return "<p><small>Mostrando una consulta de " + regs + " registros.</small></p>";
        },
        getOrderInfo: function(objParams) {
            if (objParams['order']) {
                strOrder = "<p><small>Contenido ordenado por " + objParams["order"] + " (" + objParams["ordervalue"] + ') <a href="jsp#/' + clase + '/list/' + param().getUrlStringFromParamsObject(param().getUrlObjectFromParamsWithoutParamArray(objParams, ["order", "ordervalue"])) + '" id="linkQuitarOrden">(Quitar orden)</a></small></p>';
            } else {
                strOrder = "<p>Contenido no ordenado</p>";
            }
            return strOrder;
        },
        getFilterInfo: function(objParams) {
            if (objParams['filter']) {
                strFilter = "<p><small>Contenido filtrado (" + objParams ['filter'] + " " + objParams['filteroperator'] + " " + objParams['filtervalue'] + ') <a href="jsp#/' + clase + '/list/' + param().getUrlStringFromParamsObject(param().getUrlObjectFromParamsWithoutParamArray(objParams, ["filter", "filteroperator", "filtervalue"])) + '" id="linkQuitarFiltro">(Quitar filtro)</small></a></p>';
            } else {
                strFilter = "<p>Contenido no filtrado</p>";
            }
            return strFilter;
        },
//        getUrlFromParamsWithoutOrder: function(pag, order, ordervalue, rpp, filter, filteroperator, filtervalue, systemfilter, systemfilteroperator, systemfiltervalue) {
//            var url = '';
//            if (pag)
//                url += "page=" + pag;
//            if (rpp)
//                url += "&rpp=" + rpp;
//            if (filter)
//                url += "&filter=" + filter;
//            if (filteroperator)
//                url += "&filteroperator=" + filteroperator;
//            if (filtervalue)
//                url += "&filtervalue=" + filtervalue;
//            if (systemfilter)
//                url += "&systemfilter=" + systemfilter;
//            if (systemfilteroperator)
//                url += "&systemfilteroperator=" + systemfilteroperator;
//            if (systemfiltervalue)
//                url += "&systemfiltervalue=" + systemfiltervalue;
//            return url;
//        },
//        getUrlFromParamsWithoutPage: function(pag, order, ordervalue, rpp, filter, filteroperator, filtervalue, systemfilter, systemfilteroperator, systemfiltervalue) {
//            var url = '';
//            if (rpp)
//                url += "rpp=" + rpp;
//            if (order)
//                url += "&order=" + order;
//            if (ordervalue)
//                url += "&ordervalue=" + ordervalue;
//            if (filter)
//                url += "&filter=" + filter;
//            if (filteroperator)
//                url += "&filteroperator=" + filteroperator;
//            if (filtervalue)
//                url += "&filtervalue=" + filtervalue;
//            if (systemfilter)
//                url += "&systemfilter=" + systemfilter;
//            if (systemfilteroperator)
//                url += "&systemfilteroperator=" + systemfilteroperator;
//            if (systemfiltervalue)
//                url += "&systemfiltervalue=" + systemfiltervalue;
//            return url;
//        },
//        getUrlFromParamsWithoutFilter: function(pag, order, ordervalue, rpp, filter, filteroperator, filtervalue, systemfilter, systemfilteroperator, systemfiltervalue) {
//            var url = '';
//            if (pag)
//                url += "page=" + pag;
//            if (rpp)
//                url += "&rpp=" + rpp;
//            if (order)
//                url += "&order=" + order;
//            if (ordervalue)
//                url += "&ordervalue=" + ordervalue;
//            if (systemfilter)
//                url += "&systemfilter=" + systemfilter;
//            if (systemfilteroperator)
//                url += "&systemfilteroperator=" + systemfilteroperator;
//            if (systemfiltervalue)
//                url += "&systemfiltervalue=" + systemfiltervalue;
//            return url;
//        },
//        getUrlFromParamsWithoutRpp: function(pag, order, ordervalue, rpp, filter, filteroperator, filtervalue, systemfilter, systemfilteroperator, systemfiltervalue) {
//            var url = '';
//            if (pag)
//                url += "page=" + pag;
//            if (order)
//                url += "&order=" + order;
//            if (ordervalue)
//                url += "&ordervalue=" + ordervalue;
//            if (filter)
//                url += "&filter=" + filter;
//            if (filteroperator)
//                url += "&filteroperator=" + filteroperator;
//            if (filtervalue)
//                url += "&filtervalue=" + filtervalue;
//            if (systemfilter)
//                url += "&systemfilter=" + systemfilter;
//            if (systemfilteroperator)
//                url += "&systemfilteroperator=" + systemfilteroperator;
//            if (systemfiltervalue)
//                url += "&systemfiltervalue=" + systemfiltervalue;
//            return url;
//        },
        getRppLinks: function(objParams) {
            var UrlFromParamsWithoutRpp = param().getUrlStringFromParamsObject(param().getUrlObjectFromParamsWithoutParamArray(objParams, ["rpp"]));
            var botonera = '<div id="pagination"><ul class="pagination">';
            if (objParams['rpp'] == 5)
                botonera += '<li class="active">';
            else
                botonera += '<li>';
            botonera += '<a class="rpp_link" id="5" href="jsp#/' + clase + '/list/' + UrlFromParamsWithoutRpp + '&rpp=5">5</a></li>';
            if (objParams['rpp'] == 10)
                botonera += '<li class="active">';
            else
                botonera += '<li>';
            botonera += '<a class="rpp_link" id="10" href="jsp#/' + clase + '/list/' + UrlFromParamsWithoutRpp + '&rpp=10">10</a></li>';
            if (objParams['rpp'] == 20)
                botonera += '<li class="active">';
            else
                botonera += '<li>';
            botonera += '<a class="rpp_link" id="20" href="jsp#/' + clase + '/list/' + UrlFromParamsWithoutRpp + '&rpp=20">20</a></li>';
            if (objParams['rpp'] == 50)
                botonera += '<li class="active">';
            else
                botonera += '<li>';
            botonera += '<a class="rpp_link" id="50" href="jsp#/' + clase + '/list/' + UrlFromParamsWithoutRpp + '&rpp=50">50</a></li>';
            botonera += '</ul></div>';
            return botonera;
        },
        getHeaderPageTable: function(prettyFieldNames, fieldNames, visibleFields, UrlFromParamsWithoutOrder) {
            var numField = 0; //visible field counter
            var tabla = "";
            if (prettyFieldNames !== null) {
                tabla += '<tr>';
                $.each(prettyFieldNames, function(index, value) {
                    numField++; //field counter
                    if (numField <= visibleFields) {
//                        if (value === "acciones") {
//                            tabla += '<th class="col-md-2">' + value;
//                            tabla += '</th>';
//                        } else {
                        if (fieldNames[numField - 1] === "id") {
                            tabla += '<th class="col-md-1">' + value;
                        } else {
                            tabla += '<th>' + value;
                        }
                        if (fieldNames[numField - 1].substr(0, 4) == "obj_") {
                            fieldName = fieldNames[numField - 1].substring(4);
                            fieldName = "id_" + fieldName;
                        } else {
                            fieldName = fieldNames[numField - 1];
                        }
                        tabla += '<br />';
                        tabla += '<a class="orderAsc" id="' + fieldName + '" href="jsp#/' + clase + '/list/' + UrlFromParamsWithoutOrder + '&order=' + fieldName + '&ordervalue=asc"><i class="glyphicon glyphicon-arrow-up"></i></a>';
                        tabla += '<a class="orderDesc" id="' + fieldName + '" href="jsp#/' + clase + '/list/' + UrlFromParamsWithoutOrder + '&order=' + fieldName + '&ordervalue=desc"><i class="glyphicon glyphicon-arrow-down"></i></a>';
                        tabla += '</th>';
//                        }
                    }
//                    if (numField == visibleFields + 1) {
//                        tabla += '<th class="col-md-2">acciones</th>';
//                    }

                });
                tabla += '<th class="col-md-2">acciones</th>';
                tabla += '</tr>';
            }
            return tabla;
        },
        getBodyPageTable: function(page, fieldNames, visibleFields, tdbuttons) {
            var thisObject = this;
//            var tabla = "";
//            $.each(page, function(index, value) {
//                tabla += '<tr>';
//                var numField = 0;
//                var id;
//                $.each(fieldNames, function(index, valor) {
//                    if ("id" == valor) {
//                        id = value[valor];
//                    }
//                    //numField++;
//                    //if (numField <= visibleFields) {
//                        if (/id_/.test(valor)) {
//                            //falta codificar un método dame clave ajena en el modelo ...
//
//                            //falta sacar esto como un callback
//
//                            foreignRegister = objeto.getForeignKey(value[valor], valor);
//                            tabla += '<td>' + foreignRegister["id"] + '</td>';
//
//
//
////                            $.when(ajax().ajaxCallSync(path + '/json?ob=' + valor.split("_")[1].replace(/[0-9]*$/, "") + '&op=get&id=' + value[valor], 'GET', '')).done(function(data) {
////                                var contador = 0;
////                                var add_tabla = "";
////                                for (key in data) {
////                                    if (contador == 0)
////                                        add_tabla = '<td>id=' + data[key] + '(no existe)</td>';
////                                    if (contador == 1)
////                                        add_tabla = '<td>' + data[key] + '</td>';
////                                    contador++;
////                                }
////                                if (contador == 0) {
////                                    add_tabla = '<td>' + value[valor] + ' #error</td>';
////                                }
////                                tabla += add_tabla;
////                            });
//                        } else {
//                            switch (value[valor]) {
//                                case true:
//                                    tabla += '<td><i class="glyphicon glyphicon-ok"></i></td>';
//                                    break;
//                                case false:
//                                    tabla += '<td><i class="glyphicon glyphicon-remove"></i></td>';
//                                    break;
//                                default:
//                                    var fieldContent = decodeURIComponent(value[valor]);
//                                    if (typeof fieldContent == "string") {
//                                        if (value[valor].length > 50) //don't show too long fields
//                                            fieldContent = decodeURIComponent(value[valor]).substr(0, 20) + " ...";
//                                    }
//                                    tabla += '<td>' + fieldContent + '</td>';
//                            }
//                        }
//                    //}
//                });
//                tabla += '<td>';
//                tabla += tdbuttons(id);
////                tabla += '<div class="btn-toolbar" role="toolbar"><div class="btn-group btn-group-xs">';
////                tabla += '<a class="btn btn-default" href="jsp#/' + operationName + '/view/' + id + '"><i class="glyphicon glyphicon-eye-open"></i></a>';
////                tabla += '<a class="btn btn-default" href="jsp#/' + operationName + '/edit/' + id + '"><i class="glyphicon glyphicon-pencil"></i></a>';
////                tabla += '<a class="btn btn-default" href="jsp#/' + operationName + '/remove/' + id + '"><i class="glyphicon glyphicon-remove"></i></a>';
////                tabla += '</div></div>';
//                tabla += '</td>';
//                tabla += '</tr>';
//            });
//            return tabla;


            var tabla = "";
            $.each(page, function(index, value) {
                tabla += '<tr>';
                var numField = 0;
                var id;
                var strClaveAjena;
                $.each(fieldNames, function(index, valor) {
                    if ("id" == valor) {
                        id = value[valor];
                    }
                    numField++;
                    if (numField <= visibleFields) {

                        tabla += '<td>' + thisObject.printValue(value, valor) + '</td>';
//                        if (/obj_tipodocumento/.test(valor)) {
//                            if (value[valor].id > 0) {
//                                strClaveAjena = value[valor].id + ": " + value[valor].descripcion;
//                                strClaveAjena = '<a href="jsp#/tipodocumento/view/' + value[valor].id + '">' + strClaveAjena + '</a>';
//                                tabla += '<td>' + strClaveAjena + '</td>';
//                            } else {
//                                tabla += '<td>sin tipo</td>';
//                            }
//                        }
//                        if (/obj_usuario/.test(valor)) {
//                            if (value[valor].id > 0) {
//                                strClaveAjena = value[valor].id + ": " + value[valor].login;
//                                strClaveAjena = '<a href="jsp#/usuario/view/' + value[valor].id + '">' + strClaveAjena + '</a>';
//                                tabla += '<td>' + strClaveAjena + '</td>';
//                            } else {
//                                tabla += '<td>sin usuario</td>';
//                            }
//                        }
//                        if (!(/obj_/.test(valor))) {
//                            switch (value[valor]) {
//                                case true:
//                                    tabla += '<td><i class="glyphicon glyphicon-ok"></i></td>';
//                                    break;
//                                case false:
//                                    tabla += '<td><i class="glyphicon glyphicon-remove"></i></td>';
//                                    break;
//                                default:
//                                    var fieldContent = decodeURIComponent(value[valor]);
//                                    if (typeof fieldContent == "string") {
//                                        if (value[valor].length > 50) //don't show too long fields
//                                            fieldContent = decodeURIComponent(value[valor]).substr(0, 20) + " ...";
//                                    }
//                                    tabla += '<td>' + fieldContent + '</td>';
//                            }
//                        }
                    }
                });
                tabla += '<td>';
                tabla += tdbuttons(id);
                tabla += '</td>';
                tabla += '</tr>';
            });
            return tabla;
        },
//        getPageTable: function(pag, order, ordervalue, rpp, filter, filteroperator, filtervalue, systemfilter, systemfilteroperator, systemfiltervalue, botonera) {
//            var tabla = '';
//            var visibleFields = 7;
//            var fieldNames = objeto.getFieldNames();
//            var prettyFieldNames = objeto.getPrettyFieldNamesAcciones();
//            var UrlFromParamsWithoutOrder = this.getUrlFromParamsWithoutOrder(pag, order, ordervalue, rpp, filter, filteroperator, filtervalue, systemfilter, systemfilteroperator, systemfiltervalue);
//
//            tabla += "<table class=\"table table-responsive table-hover table-striped table-condensed\">";
//
//            tabla += this.getHeaderPageTable(prettyFieldNames, visibleFields, objeto.getName(), UrlFromParamsWithoutOrder);
//
//            page = objeto.getPage(pag, order, ordervalue, rpp, filter, filteroperator, filtervalue, systemfilter, systemfilteroperator, systemfiltervalue)['list'];
//            if (page != 0) {
//                tabla += this.getBodyPageTable(page, fieldNames, visibleFields, objeto.getName(), objeto.getPath());
//                tabla += "</table>";
//            } else {
//                tabla = "<div class=\"alert alert-info\"><h4>Ha habido un problema con la base de datos</h4><br/>El probema puede ser:<ul><li>La tabla está vacia.</li><li>Tu busqueda no tubo resultados.</li></ul></div>";
//            }
//            return tabla;
//        }
//        getPageTable: function(pag, order, ordervalue, rpp, filter, filteroperator, filtervalue, systemfilter, systemfilteroperator, systemfiltervalue, botonera) {
//            urlWithoutOrder = this.getUrlFromParamsWithoutOrder(pag,rpp, filter, filteroperator, filtervalue, systemfilter, systemfilteroperator, systemfiltervalue, botonera);
//            var tabla = "<table class=\"table table-responsive table-hover table-striped table-condensed\">";
//            var visibleFields = 5;
//            var numField = 0; //visible field counter
//            if (objeto.getPrettyFieldNamesAcciones() !== null) {
//                tabla += '<tr>';
//                $.each(objeto.getPrettyFieldNamesAcciones(), function(index, value) {
//                    numField++; //field counter
//                    if (numField <= visibleFields) {
//                        if (value === "acciones") {
//                            tabla += '<th class="col-md-2">' + value;
//                            tabla += '</th>';
//                        } else {
//                            if (value === "id") {
//                                tabla += '<th class="col-md-1">' + value;
//                                tabla += '<br /><a class="orderAsc' + index + '" href="jsp#/' + this.getName() + '/list/' + url + '&order=id&ordervalue=asc"><i class="glyphicon glyphicon-arrow-up"></i></a>';
//                                tabla += '<a class="orderDesc' + index + '" href="#"><i class="glyphicon glyphicon-arrow-down"></i></a>';
//                                tabla += '</th>';
//                            } else {
//                                tabla += '<th>' + value;
//                                tabla += '<br /><a class="orderAsc' + index + '" href="#"><i class="glyphicon glyphicon-arrow-up"></i></a>';
//                                tabla += '<a class="orderDesc' + index + '" href="#"><i class="glyphicon glyphicon-arrow-down"></i></a>';
//                                tabla += '</th>';
//                            }
//                        }
//                    }
//                    if (numField == visibleFields + 1) {
//                        tabla += '<th class="col-md-2">acciones</th>';
//                    }
//
//                });
//                tabla += '</tr>';
//            }
//
//            page = objeto.getPage(pag, order, ordervalue, rpp, filter, filteroperator, filtervalue, systemfilter, systemfilteroperator, systemfiltervalue)['list'];
//            if (page != 0) {
//                $.each(page, function(index, value) {
//                    tabla += '<tr>';
//                    numField = 0;
//                    $.each(objeto.getFieldNames(), function(index, valor) {
//                        if ("id" == valor) {
//                            id = valor;
//                        }
//                        numField++;
//                        if (numField <= visibleFields) {
//                            if (/id_/.test(valor)) {
//                                $.when(ajax().ajaxCallSync(objeto.getPath() + '/json?ob=' + valor.split("_")[1].replace(/[0-9]*$/, "") + '&op=get&id=' + value[valor], 'GET', '')).done(function(data) {
//                                    contador = 0;
//                                    add_tabla = "";
//                                    for (key in data) {
//                                        if (contador == 0)
//                                            add_tabla = '<td>id=' + data[key] + '(no existe)</td>';
//                                        if (contador == 1)
//                                            add_tabla = '<td>' + data[key] + '</td>';
//                                        contador++;
//                                    }
//                                    if (contador == 0) {
//                                        add_tabla = '<td>' + value[valor] + ' #error</td>';
//                                    }
//                                    tabla += add_tabla;
//                                });
//                            } else {
//                                switch (value[valor]) {
//                                    case true:
//                                        tabla += '<td><i class="glyphicon glyphicon-ok"></i></td>';
//                                        break;
//                                    case false:
//                                        tabla += '<td><i class="glyphicon glyphicon-remove"></i></td>';
//                                        break;
//                                    default:
//                                        var fieldContent = decodeURIComponent(value[valor]);
//                                        if (typeof fieldContent == "string") {
//
//                                            if (value[valor].length > 50) //don't show too long fields
//                                                fieldContent = decodeURIComponent(value[valor]).substr(0, 20) + " ...";
//
//                                        }
//                                        tabla += '<td>' + fieldContent + '</td>';
//                                }
//                            }
//                        }
//                    });
//
//                    tabla += '<td><div class="btn-toolbar" role="toolbar"><div class="btn-group btn-group-xs">';
//                    if (callback) {
//
//                    } else {
//                        tabla += '<a class="btn btn-default" href="jsp#/' + vista.getName() + '/view/' + id + '"><i class="glyphicon glyphicon-eye-open"></i> ' + valor.text + '</a>';
//                        tabla += '<a class="btn btn-default" href="jsp#/' + vista.getName() + '/edit/' + id + '"><i class="glyphicon glyphicon-pencil"></i> ' + valor.text + '</a>';
//                        tabla += '<a class="btn btn-default" href="jsp#/' + vista.getName() + '/remove/' + id + '"><i class="glyphicon glyphicon-remove"></i> ' + valor.text + '</a>';
//                    }
//                    tabla += '</div></div></td>';
//                    tabla += '</tr>';
//                });
//                tabla += "</table>";
//            } else {
//                tabla = "<div class=\"alert alert-info\"><h4>Ha habido un problema con la base de datos</h4><br/>El probema puede ser:<ul><li>La tabla está vacia.</li><li>Tu busqueda no tubo resultados.</li></ul></div>";
//            }
//
//            return tabla;
//
//        }
    };
};



