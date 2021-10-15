package web;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/ContadorVisitasServlet")
public class ContadorVisitasServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");

        // declaramos contador

        int contador = 0;

        // revisamos si existe el cookie contadorVisitas para
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals("contadorVisitas")) {
                    contador = Integer.parseInt(c.getValue());
                }

            }
        }

        // incrementamos el contador el uno

        contador++;

        Cookie c = new Cookie("contadorVisitas", Integer.toString(contador));
        // la cookie se almacenara en el cliente por una hora
        c.setMaxAge(3600);
        response.addCookie(c);

        // mandamos el mensaje al navegador
        PrintWriter out = response.getWriter();
        out.println("la cantidad de veces que se ingreso a la pagina es de " + contador);
    }

}
