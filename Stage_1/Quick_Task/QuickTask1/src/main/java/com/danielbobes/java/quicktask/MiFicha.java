package com.danielbobes.java.quicktask;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class MiFicha {

    public static void main(String[] args) {
        // Mi información
        String nombre = "Daniel";
        Integer edad = 33;
        Boolean estudiandoAutoAPIs = true;

        ArrayList<String> hobbies = new ArrayList<>();
        hobbies.add("Ver series o películas");
        hobbies.add("Escuchar música");
        hobbies.add("Jugar a juegos de ordenador o de mesa");
        hobbies.add("Salir a pasear");
        hobbies.add("Jugar con mis mascotas");

        // Mostrar información por pantalla
        System.out.println("Mi información:");
        System.out.println("Nombre: " + nombre);
        System.out.println("Edad: " + edad + " años");
        System.out.println("¿Estudiando automatización de APIs? " + (estudiandoAutoAPIs? "Si" : "No"));
        System.out.println("Hobbies: " + String.join(", ", hobbies));

        System.out.println();

        // Imprimir tipos de cada variable
        System.out.println("Tipo de cada variable:");
        System.out.println("'Nombre': " + nombre.getClass().getSimpleName());
        System.out.println("'Edad': " + edad.getClass().getSimpleName());
        System.out.println("'Estudiando automatización de APIs': "
                + estudiandoAutoAPIs.getClass().getSimpleName());
        System.out.println("'Hobbies': " + hobbies.getClass().getSimpleName());

        System.out.println();

        // Mostrar total de hobbies
        System.out.println("Tengo un total de " + hobbies.size() + " hobbies.");

        System.out.println();

        // Cambiar edad
        edad = edad + 1;
        System.out.println("Nueva edad: " + edad + " años");

    }


}
