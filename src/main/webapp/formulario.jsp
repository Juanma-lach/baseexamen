<%-- 
    Document   : formulario
    Created on : 21-nov-2014, 10:59:14
    Author     : Armando
--%>

<%@page import="net.daw.helper.Aleatorio"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    int numero1 = Aleatorio.randInt(1, 10);
    int numero2 = Aleatorio.randInt(1, 10);
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Logueate!</h1>
        <h2><%=numero1%></h2>
        <h2><%=numero2%></h2>

        <form class="form-signin" id="loginForm" action="exe" role="form" method="post">                                    
            <input type="hidden" name="numero1" value="<%=numero1%>" />
            <input type="hidden" name="numero2" value="<%=numero2%>" />
            <input type="hidden" name="op" value="logindesdeformulario" />     
            <input type="hidden" name="ob" value="usuario" />  
            <label class="control-label" for="inputLogin" style="margin-top: 15px">Capcha</label>
            <input value="" class="form-control"  id="inputLogin" type="text" placeholder="Introduce el capcha" required="" autofocus="" name="login" />                                                                            
            <button class="btn btn-lg btn-primary btn-block" type="submit"  style="margin-top: 15px">Acceder</button>                           
        </form>

    </body>
</html>
