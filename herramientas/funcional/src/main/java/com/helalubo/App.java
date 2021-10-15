package com.helalubo;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.helalubo.mook.Person.domain.Person;
import com.helalubo.mook.Product.doamin.Product;

/**
 * Hello world!
 */
public final class App {
    private App() {

    }

    /**
     * Says hello to the world.
     * 
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {

        Person p1 = new Person(1, "Helalubo", LocalDate.of(1995, 9, 2));
        Person p2 = new Person(1, "Maite", LocalDate.of(2003, 9, 7));
        Person p3 = new Person(1, "Jaime", LocalDate.of(1989, 6, 23));
        Person p4 = new Person(1, "Duke", LocalDate.of(2019, 5, 15));
        Person p5 = new Person(1, "James", LocalDate.of(2010, 1, 4));
        Product pr1 = new Product(1, "Ceviche", 15.0);
        Product pr2 = new Product(2, "Chilaquilles", 25.50);
        Product pr3 = new Product(3, "Bandeja Paisa", 35.50);
        Product pr4 = new Product(4, "Ceviche", 16.0);

        List<Person> persons = Arrays.asList(p1, p2, p3, p4, p5);
        List<Product> products = Arrays.asList(pr1, pr2, pr2, pr3, pr4);

        // ************************************
        // nostrar lista rapidamente
        // persons.forEach(System.out::println); // mostrar lista con foreach

        // ************************************
        // filer con funcional

        // List<Person> personasMayoresA18 = persons.stream()
        // .filter(p ->
        // UtilDate.getAge(p.getFecha()) >= 18)
        // .collect(Collectors.toList());

        // personasMayoresA18.forEach(System.out::println); // mostrar lista con foreach

        // ************************************
        // map con funcional //transformar de un tipo A a B
        // List<Integer> FilterList12 = persons.stream().map(p ->
        // UtilDate.getAge(p.getFecha()))
        // .collect(Collectors.toList());

        // FilterList12.forEach(System.out::println); // mostrar lista con foreach

        // ************************************
        // map con filter //transformar de un tipo
        // map con funcional //transformar de un tipo A a B

        // List<Integer> edadesMenoresA18 = persons.stream().filter(p ->
        // UtilDate.getAge(p.getFecha()) < 18)
        // .map(p -> UtilDate.getAge(p.getFecha())).collect(Collectors.toList());

        // edadesMenoresA18.forEach(System.out::println); // mostrar lista con foreach

        // ************************************
        // modificar nombres de persons

        // map con filter //transformar de un tipo
        // map con funcional //transformar de un tipo A a B

        // uso mapeo para obtener el nombre antes de pasarcele la funcion helafuncion

        // Function<String, String> helaFuncion = name -> "hela" + name;
        // List<String> helaNombre =
        // persons.stream().map(Person::getNombre).map(helaFuncion).collect(Collectors.toList());
        // helaNombre.forEach(System.out::println); // mostrar lista con foreach

        // ************************************
        // sorted con java funcional usamos comparador para ordenar por diferentes
        // atributos de objeto en la lista

        // Comparator<Person> byNameAsc = (Person o1, Person o2) ->
        // o1.getNombre().compareTo(o2.getNombre());
        // Comparator<Person> byNameDesc = (Person o1, Person o2) ->
        // o2.getNombre().compareTo(o1.getNombre());
        // Comparator<Person> byDateAsc = (Person o1, Person o2) ->
        // o1.getFecha().compareTo(o2.getFecha());

        // List<Person> listaPersonasAscName =
        // persons.stream().sorted(byDateAsc).collect(Collectors.toList());

        // listaPersonasAscName.forEach(System.out::println); // mostrar lista

        // ************************************
        // Match (param Predicate)
        // ************************************
        // anyMatch no evalua todo el stream, termina en la coincidencia
        // // pregunta si alguno de la lista cumple con la condicion
        // boolean cumpleCondicion = persons.stream().anyMatch(p ->
        // p.getNombre().startsWith("h"));

        // System.out.println(cumpleCondicion);

        // ************************************
        // allmach evalua todo el stream, termina en la coincidencia
        // pregunta si alguno de la lista cumple con la condicion
        // boolean cumpleCondicion = persons.stream().allMatch(p ->
        // p.getNombre().startsWith("H"));

        // System.out.println(cumpleCondicion);

        // ************************************
        // noneMach evalua que ningun valor en el stream consida, en ese caso retorna
        // true
        // pregunta si alguno de la lista cumple con la condicion

        // String letraAVerificar = "x";

        // Predicate<Person> startsWithPredicate = person ->
        // person.getNombre().startsWith(letraAVerificar);

        // boolean cumpleCondicion = persons.stream().noneMatch(startsWithPredicate);

        // System.out.println(cumpleCondicion);

        // ************************************
        // Limint/Skip //EJEMPLO PAGINACION // skip salte los primeros 2 limit marca el
        // limite segun el int ingresado

        // List<Person> filteredListSkip =
        // persons.stream().skip(2).collect(Collectors.toList());

        // filteredList.forEach(System.out::println);

        // List<Person> filteredListLimit =
        // persons.stream().limit(2).collect(Collectors.toList());

        // filteredListLimit.forEach(System.out::println);

        // ************************************
        // Logica de pagiancio en funciona con skit and limit

        // int pageNumber = 0; // cambiando el pageNumber ira arrojandome la page con el
        // size
        // int pageSize = 2;

        // List<Person> filteredListLimit = persons.stream().skip(pageNumber *
        // pageSize).limit(pageSize)
        // .collect(Collectors.toList());

        // filteredListLimit.forEach(System.out::println);

        // ************************************
        // collectors // mas avanzado //{35.5=[{ id='3', nombre='Bandeja Paisa',
        // precio='35.5'}], 25.5=[{ id='2', nombre='Chilaquilles', precio='25.5'}, {
        // id='2', nombre='Chilaquilles', precio='25.5'}]}
        // GroupBy
        // Map<Double, List<Product>> collect1 = products.stream().filter(p ->
        // p.getPrecio() > 20)
        // .collect(Collectors.groupingBy(Product::getPrecio));

        // System.out.println(collect1);
        // System.out.println(collect1.get(35.5)); // el precio quedo como llave

        // ************************************
        // buscar coincidencias con ceviche

        // Map<String, List<Product>> ceviches = products.stream().filter(p ->
        // p.getNombre().startsWith("Ceviche"))
        // .collect(Collectors.groupingBy(Product::getNombre));

        // System.out.println(ceviches);

        // ************************************
        // contar cantidad de elementos con un criterio que agrego, en este caso cuenta
        // la cantidad de elementos con el nombre (los productos mas populares)

        // Map<String, Long> contadoresSegunProductosRepetidos = products.stream()
        // .collect(Collectors.groupingBy(Product::getNombre, Collectors.counting()));

        // System.out.println(contadoresSegunProductosRepetidos);

        // ************************************
        // agrupando por nombre producto y sumando

        // Map<String, Double> agrupoPorNombreYSumaDePrecio = products.stream()
        // .collect(Collectors.groupingBy(Product::getNombre,
        // Collectors.summingDouble(Product::getPrecio)));

        // System.out.println(agrupoPorNombreYSumaDePrecio);

        // ************************************
        // Obteniendo suma y resumen (promedio)

        // DoubleSummaryStatistics statistics = products.stream()
        // .collect(Collectors.summarizingDouble(Product::getPrecio));

        // System.out.println(statistics);
        // System.out.println("\n");
        // System.out.println(statistics.getMax()); // usamos el operador punto para
        // poder obtener valores predeterminados

        // ************************************
        // reduce ejemplo suma de precios como un numero.

        // Optional<Double> suma =
        // products.stream().map(Product::getPrecio).reduce(Double::sum); //
        // .reduce((a,b) -> a+b)

        // System.out.println(suma.get());

        // ************************************

    }
}
