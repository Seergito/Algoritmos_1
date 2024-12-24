package com;

import java.util.Iterator;
import java.util.Map;

public class a10 {

    public static boolean darCambio()(int importeDevolver, Map<Integer, Integer>, mapCanti, Map<Integer,Integer> mapSol ){
        
        boolean objetivo=false;

        Iterator<Integer> billetes = mapCanti.getClaves();

        int billete;

        while(billetes.hasNext() && !objetivo) {
            billete = billetes.next();

            if(billete <= importeDevolver){

                importeDevolver -= billete;

                //Agrega el billete al mapSol
                if(mapSol.get(billete) == null){
                    //Si el billete no está en mapSol, lo agrega con cantidad 1
                    mapSol.insertar(billete,1);
                }else{
                    //Si ya estaba, incrementa la cantidad usada de este billete
                    mapSol.insertar(billete,mapSol.get(billete) -1);
                }

                //Actualizamos la cantidad de billetes de mapCanti
                if(mapCanti.get(billete) == 1){
                    mapCanti.eliminar(billete);
                }else{
                    //Si quedan más billetes de esta denominación, decrementamos su cantidad
                    mapCanti.insertar(billete, mapCanti.get(billete) -1 );
                }

                //Verifica si ya se logró devolver el importe solicitado
                if(importeDevolver == 0) {
                    objetivo=true; 
                } else {
                    //Llama recursivamente para intentar resolver el nuevo problema reducido
                    objetivo = darCambio(importeDevolver, mapCanti, mapSol); 

                    //Si la recusión falla, deshace los cambios realizados

                    if(!objetivo){
                        //Reincorpa el billete usado al importe que queda por devolver

                        importeDevolver += billete;

                        //Reversa los cambios en mapaSol VUETLA ATRAS
                        if(mapaSol.get(billete) == 1){
                            //Si solo hay un billete lo elimina
                            mapSol.eliminar(billete);
                        }else {
                            //Si había más de uno, decrementa la cantidad en mapSol
                            mapSol.insertar(billete, mapSol.get(billete) -1);
                        }

                        //Reincorpora el billete a mapCanti
                        if(mapCanti.get(billete) == null){
                            //Si la denominacion no está en mapCanti, lo agrega en 1
                            mapCanti.insertar(billete,1);
                        }else{
                            mapCanti.insertar(billete , mapCanti.get(billete) + 1);
                        }
                    }

                }

                return objetivo;
            }
        }

    }

public static boolean llenarPendriveVueltaAtras(int capacidadMax, Map<String,Integer> espacioProgramas, Lista<String> pendrive ){


    boolean objetivo = false;
    Iterator<String> it = espacioProgramas.getClaves();
    while (it.hasNext() &&  capacidadMax > 0 && !objetivo ) {

        String programa = it.next();
        int tamaño = espacioProgramas.eliminar(programa);
        pendrive.insertarFinal(programa);
        capacidadMax -= tamaño;

        if(capacidadMax == 0){
            objetivo = true;
        }else{
            objetivo = llenarPendriveVueltaAtras(capacidadMax, espacioProgramas, pendrive){
                if(!objetivo){
                    capacidadMax -=tamaño;
                    pendrive.suprimir(programa);
                    espacioProgramas.insertar(programa, tamaño);
                }
            }

        }

    }


}

    public static boolean subconjunto(int[] valores, int[] solucion, int resultado, int indice) {

        boolean objetivo = false;
        while (indice < valores.length && resultado > 0 && !objetivo) {
            solucion[indice] = 1;
            resultado -= valores[indice];
            if (resultado == 0) {
                objetivo = true;
            } else {
                objetivo = subconjunto(valores, solucion, resultado, indice);
                if (!objetivo) {
                    resultado += valores[indice];
                    solucion[indice] = 0;
                }
            }
            indice++;
        }
        return objetivo;

    }

}
