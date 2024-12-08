package org.example.DTO;

import org.example.Entidades.Entrenador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class CombateDTO implements Serializable {
    private Date fecha;
    private long id;
    private ArrayList<Entrenador> entrenadores = new ArrayList<>();

    //Constructores

    public CombateDTO() {
    }

    /**
     *
     * @param fecha
     * @param id
     * @param entrenadores
     */
    public CombateDTO(Date fecha, long id, ArrayList<Entrenador> entrenadores) {
        this.fecha = fecha;
        this.id = id;
        this.entrenadores = entrenadores;
    }
}
