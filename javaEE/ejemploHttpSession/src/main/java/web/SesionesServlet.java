package web;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/SesionesServlet")
public class SesionesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");

        // si existe la trae, si no existe la crea.
        HttpSession sesion = request.getSession();

        String titulo = null;

        // vamos a pedir el atributo contador visistas a la sesion

        Integer contadorVisitas = (Integer) sesion.getAttribute("contadorVisitas");
        // si es nulo ees la primera vez que accedemos a la accedemos
        System.out.println(contadorVisitas);
        if (contadorVisitas == null) {
            titulo = "Bienvenido por primera vez ";
            contadorVisitas = 1;

        } else {

            contadorVisitas++;
            titulo = "Bienvenido nuevamente ";
        }
        sesion.setAttribute("contadorVisitas", contadorVisitas);

        // agregamos el nuevo valor a la sesio

        PrintWriter out = response.getWriter();
        out.println(titulo);
        out.println("<br>");
        out.println("Contador de visitas: " + contadorVisitas);
        out.println("<br>");
        out.println("ID de la sesion" + sesion.getId());
        out.close();

        // para manejar el tiempo que queremos la sesion lo establecemos en el web.xml

    }

}
