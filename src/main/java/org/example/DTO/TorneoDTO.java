package org.example.DTO;

import org.example.Entidades.Combate;
import org.example.Entidades.Entrenador;

import java.io.Serializable;
import java.util.ArrayList;

public class TorneoDTO implements Serializable {
    private int id;
    private String nombre;
    private char codRegion;
    private float PuntosVictoria;
    private int puntos = (int) (Math.random() * 51) + 50;  // Los ptos que ganas
    private String nombreAdmin;
    private String passAdmin;
    private ArrayList<Entrenador> entrenadores = new ArrayList<>();  //Lo hacemos con arraylist para no tener problema con el numero de entrenadores, ni combates.
    private ArrayList<Combate> combates = new ArrayList<>();

    //Constructores

    public TorneoDTO() {
    }

    /**
     * Constructor por defecto de la clase TORNEODTO
     * @param id
     * @param nombre
     * @param codRegion
     * @param puntosVictoria
     * @param puntos
     * @param passAdmin
     * @param nombreAdmin
     * @param combates
     * @param entrenadores
     */
    public TorneoDTO(int id, String nombre, char codRegion, float puntosVictoria, int puntos, String passAdmin, String nombreAdmin, ArrayList<Combate> combates, ArrayList<Entrenador> entrenadores) {
        super();
        this.id = id;
        this.nombre = nombre;
        this.codRegion = codRegion;
        PuntosVictoria = puntosVictoria;
        this.puntos = puntos;
        this.passAdmin = passAdmin;
        this.nombreAdmin = nombreAdmin;
        this.combates = combates;
        this.entrenadores = entrenadores;
    }
}
