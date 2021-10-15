package com.helalubo;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.helalubo.mook.person.application.services.personService;
import com.helalubo.mook.person.infrastructure.repositories.personRepository;
import com.helalubo.mook.person.model.Person;

/**
 * Hello world!
 */
public final class App {
    private App() {
    }

    public static void main(String[] args) throws JsonMappingException, JsonProcessingException {

        personRepository repo = new personRepository();
        personService service = new personService(repo);

        List<Person> people = service.findAllPerson();

        // people.forEach(System.out::println);

        // System.out.println(people.get(1).getApellido());

        // #1.-Mostrar todos los datos de los usuarios por consola
        // ************************************
        //
        people.forEach(System.out::println);
        // ************************************

        // #2.-Retornar todos los trabajos de los usuarios
        // ************************************

        // List<String> nombres =
        // people.stream().map(Person::getTrabajo).collect(Collectors.toList());
        // nombres.forEach(System.out::println);
        // ************************************

        // #3.-Retornar todos los paises de los usuarios
        // ************************************

        // List<String> paises =
        // people.stream().map(Person::getPais).collect(Collectors.toList());
        // paises.forEach(System.out::println);
        // ************************************

        // #4.-Retornar un array de objetos de aquellos usuarios cuyo pais sea China
        // ************************************

        // List<Person> chinos = people.stream().filter(p ->
        // p.getPais().equals("China")).collect(Collectors.toList());
        // chinos.forEach(System.out::println);
        // ************************************
        // //console.log(chinas);

        // #5.-Retornar una array de objetos de todos los usuarios menores a 21 años
        // ************************************

        // List<Person> menoresA21 = people.stream().filter(p -> p.getEdad() <
        // 21).collect(Collectors.toList());
        // menoresA21.forEach(System.out::println);
        // ************************************

        // #6.-Retornar la cantidad de usuarios con sexo masculino (Male)
        // ************************************

        // Long cantidadMAsculinos = people.stream().filter(p ->
        // p.getSexo().equals("Male")).count();

        // System.out.println(cantidadMAsculinos);
        // ************************************

        // #7.-Retornar una array de strings (el nombre de los usarios de sexo
        // femenino (Female))
        // ************************************

        // List<String> nombreFemeninos = people.stream().filter(p ->
        // p.getSexo().equals("Female")).map(Person::getNombre)
        // .collect(Collectors.toList());

        // System.out.println(nombreFemeninos);
        // ************************************
        // //console.log(nombresFemeninos);

        // #8.-Retornar una array de strings (el email de los usarios de sexo
        // masculino (Male))
        // ************************************

        // List<String> mailsMasculinos = people.stream().filter(p ->
        // p.getSexo().equals("Male")).map(Person::getEmail)
        // .collect(Collectors.toList());

        // System.out.println(mailsMasculinos);
        // ************************************

        // #9.-Retornar un array de objetos que solo contengan los nombres, apellidos
        // y ciudades de todos los usuarios
        // ************************************

        // List<Map<String, String>> datosUser = people.stream().map(p -> {
        // Map<String, String> myMap = new HashMap<>();
        // myMap.put("nombre", p.getNombre());
        // myMap.put("apellido", p.getApellido());
        // myMap.put("ciudad", p.getCiudad());

        // return myMap;
        // }).collect(Collectors.toList());

        // System.out.println(datosUser.get(1).get("ciudad"));
        // ************************************
        // //console.log(datosUsers);

        // #10.-Retornar un array de objetos que solo contengan los nombres, apellidos
        // y ciudades de todos los usuarios
        // masculinos mayores de 35 años

        // ************************************
        //
        // List<Map<String, String>> datosUsersMayoresMasculinos =
        // people.stream().filter(p -> p.getEdad() > 35).map(p -> {
        // Map<String, String> myMap = new HashMap<>();
        // myMap.put("nombre", p.getNombre());
        // myMap.put("apellido", p.getApellido());
        // myMap.put("ciudad", p.getCiudad());

        // return myMap;
        // }).collect(Collectors.toList());

        // System.out.println(datosUsersMayoresMasculinos);
        // ************************************

        // #11.-Retornar el promedio de edad de los usuarios
        // // ************************************
        // //
        // DoubleSummaryStatistics statistics =
        // people.stream().collect(Collectors.summarizingDouble(Person::getEdad));

        // // System.out.println(statistics);
        // // System.out.println("\n");
        // System.out.println(statistics.getAverage()); // usamos el operador punto para
        // // poder obtener valores predeterminados

        // // ************************************

        // #12.-Retornar el promedio de edad de los usuarios masculinos
        // // ************************************
        // //
        // Double promedioEdadMasculino = people.stream().filter(p ->
        // "Male".equals(p.getSexo()))
        // .collect(Collectors.summarizingDouble(Person::getEdad)).getAverage();

        // // System.out.println(statistics);
        // // System.out.println("\n");
        // System.out.println(promedioEdadMasculino); // usamos el operador punto para
        // // poder obtener valores predeterminados

        // // ************************************
        // //console.log(promedioEdadMasculinos());

        // #13.-Retornar el promedio de edad de los usuarios egipcios (Egypt)
        // ************************************
        //
        // Double promedioEdadEgipcios = people.stream().filter(p ->
        // "Egypt".equals(p.getPais()))
        // .collect(Collectors.summarizingDouble(Person::getEdad)).getAverage();

        // // System.out.println(statistics);
        // // System.out.println("\n");
        // System.out.println(promedioEdadEgipcios); // usamos el operador punto para
        // // poder obtener valores predeterminados

        System.out.println("Ejercicio finalizado");

        // ************************************

        // Eliminar de la lista el id 1000
        // people.removeIf(p -> p.getId().equals(1000));

        // people.forEach(System.out::println);

        // ************************************
        // separo array de string por caracter que yo decida
        // ************************************
        // List<String> ch = Arrays.asList("HE", "E", "L", "U", "B", "O");

        // String chString =
        // ch.stream().map(String::valueOf).collect(Collectors.joining("=> "));

        // System.out.println(chString);
        // ************************************
        // Manejo string y determino como empieza y que tiene en medio separo string por
        // lo que sea
        // ************************************
        List<String> ch = Arrays.asList("HE", "E", "L", "U", "B", "O");
        String chString = ch.stream().map(String::valueOf).collect(Collectors.joining("=> ", "[", "]"));

        System.out.println(chString);

        // ************************************
    }
}
