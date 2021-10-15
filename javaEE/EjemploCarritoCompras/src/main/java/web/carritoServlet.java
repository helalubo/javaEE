package web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/carritoServlet")
public class carritoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        String articulo = request.getParameter("articulo");

        HttpSession sesion = request.getSession();
        // recuperamos la lista si existe y si no la crea
        List<String> articulos = (List<String>) sesion.getAttribute("articulos");
        if (articulos == null) {

            articulos = new ArrayList<>();

            sesion.setAttribute("articulos", articulos);

        }

        // revisamos artivulo nuevo y lo agregamos

        if (articulo != null && !articulo.trim().equals("")) {
            articulos.add(articulo);

        }
        try (

                PrintWriter out = response.getWriter()) {

            out.println("<h1>lista de articulos</h1>");
            out.println("<ul>");
            articulos.forEach(a -> out.println("<li>" + a + "</li>"));
            articulos.forEach(System.out::println);
            out.println("</ul>");
            out.println("</br>");
            out.println("</ul>");
            out.println("<a href='/EjemploCarritoCompras'>Volver </a>");
        }

    }

}
