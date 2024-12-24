package com.example_a10;

import java.util.Map;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
    }

    public static boolean darCambio(int importeDevolver, Map<Integer, Integer> mapCanti,
            Map<Integer, Integer> mapSol) {
        // Bandera para indicar si se logró devolver el importe solicitado
        boolean objetivo = false;

        // Iterador para recorrer las denominaciones de los billetes disponibles
        Iterator<Integer> billetes = mapCanti.getClaves();

        // Variable para almacenar la denominación del billete actual
        int billete;

        // Mientras haya billetes por revisar y no se haya alcanzado el objetivo
        while (billetes.hasNext() && !objetivo) {
            billete = billetes.next(); // Obtiene la siguiente denominación de billete

            // Verifica si el billete actual puede usarse para devolver parte del importe
            if (billete <= importeDevolver) {
                // Resta el valor del billete al importe que queda por devolver
                importeDevolver -= billete;

                // Agrega el billete al mapa de solución (mapSol)
                if (mapSol.get(billete) == null) {
                    // Si el billete no estaba en mapSol, lo agrega con una cantidad de 1
                    mapSol.insertar(billete, 1);
                } else {
                    // Si ya estaba, incrementa la cantidad usada de ese billete
                    mapSol.insertar(billete, mapSol.get(billete) + 1);
                }

                // Actualiza la disponibilidad de billetes en mapCanti
                if (mapCanti.get(billete) == 1) {
                    // Si este era el último billete de esta denominación, lo elimina
                    mapCanti.eliminar(billete);
                } else {
                    // Si aún quedan más billetes de esta denominación, decrementa su cantidad
                    mapCanti.insertar(billete, mapCanti.get(billete) - 1);
                }

                // Verifica si ya se logró devolver el importe solicitado
                if (importeDevolver == 0) {
                    objetivo = true; // Se logró el objetivo
                } else {
                    // Llama recursivamente para intentar resolver el nuevo problema reducido
                    objetivo = darCambio(importeDevolver, mapCanti, mapSol);

                    // Si la recursión falla, deshace los cambios realizados
                    if (!objetivo) {
                        // Reincorpora el billete usado al importe que queda por devolver
                        importeDevolver += billete;

                        // Reversa los cambios en mapSol
                        if (mapSol.get(billete) == 1) {
                            // Si solo había un billete en mapSol, lo elimina
                            mapSol.eliminar(billete);
                        } else {
                            // Si había más de uno, decrementa la cantidad en mapSol
                            mapSol.insertar(billete, mapSol.get(billete) - 1);
                        }

                        // Reincorpora el billete a mapCanti
                        if (mapCanti.get(billete) == null) {
                            // Si la denominación no estaba en mapCanti, la agrega con 1
                            mapCanti.insertar(billete, 1);
                        } else {
                            // Si ya estaba, incrementa la cantidad disponible en mapCanti
                            mapCanti.insertar(billete, mapCanti.get(billete) + 1);
                        }
                    }
                }
            }
        }

        // Retorna si fue posible devolver el importe solicitado
        return objetivo;
    }

    public static boolean llenarPendriveVueltaAtras(int capacidadMax, Map<String, Integer> espacioProgramas,
            Lista<String> pendrive) {

        // Bandera para indicar si se logró llenar exactamente el pendrive
        boolean objetivo = false;

        // Iterador para recorrer las claves del mapa de programas y sus tamaños
        Iterator<String> it = espacioProgramas.getClaves();

        // Mientras haya programas por probar, haya espacio disponible en el pendrive,
        // y aún no se haya logrado el objetivo
        while (it.hasNext() && capacidadMax > 0 && !objetivo) {

            // Obtiene el siguiente programa a considerar
            String programa = it.next();

            // Elimina el programa del mapa (lo "toma" para usarlo)
            int tamaño = espacioProgramas.eliminar(programa);

            // Agrega el programa al final del pendrive
            pendrive.insertarFinal(programa);

            // Resta el tamaño del programa a la capacidad restante
            capacidadMax -= tamaño;

            // Verifica si se logró llenar exactamente el pendrive
            if (capacidadMax == 0) {
                objetivo = true; // Se alcanzó el objetivo
            } else {
                // Llama recursivamente para intentar llenar el pendrive con el espacio restante
                objetivo = llenarPendriveVueltaAtras(capacidadMax, espacioProgramas, pendrive);

                // Si la recursión no logra una solución, deshace los cambios realizados
                if (!objetivo) {
                    // Recupera el espacio utilizado por el programa
                    capacidadMax += tamaño;

                    // Elimina el programa del pendrive (retroceso)
                    pendrive.suprimir(programa);

                    // Vuelve a insertar el programa en el mapa
                    espacioProgramas.insertar(programa, tamaño);
                }
            }
        }

        // Devuelve si se logró llenar exactamente el pendrive
        return objetivo;
    }

    public static boolean subconjunto(int[] valores, int[] solucion, int resultado, int indice) {

        // Bandera para indicar si se ha encontrado un subconjunto que sume el resultado
        boolean objetivo = false;

        // Mientras haya elementos en el array, el resultado no sea cero, y aún no se
        // haya encontrado solución
        while (indice < valores.length && resultado > 0 && !objetivo) {

            // Marca el elemento actual como parte de la solución
            solucion[indice] = 1;

            // Resta el valor del elemento actual del resultado buscado
            resultado -= valores[indice];

            // Verifica si se alcanzó exactamente el resultado deseado
            if (resultado == 0) {
                objetivo = true; // Se encontró una solución
            } else {
                // Llama recursivamente al método con el mismo índice para considerar el
                // siguiente elemento
                objetivo = subconjunto(valores, solucion, resultado, indice);

                // Si la recursión no encuentra una solución, deshace los cambios
                if (!objetivo) {
                    // Devuelve el valor al resultado
                    resultado += valores[indice];

                    // Marca el elemento actual como no seleccionado en la solución
                    solucion[indice] = 0;
                }
            }

            // Avanza al siguiente índice para probar otros elementos
            indice++;
        }

        // Devuelve si fue posible encontrar un subconjunto que sume al resultado
        return objetivo;
    }

}