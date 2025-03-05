package com.cda.here.entidades;

import java.util.Date;

public class Asistencia {
    private String fecha;
    private int idAs;
    int idDocente;
    private String estado;
    private int numlista;
    private long estudiante;

    public int getIdDocente() {
        return idDocente;
    }

    public void setIdDocente(int idDocente) {
        this.idDocente = idDocente;
    }

    public int getIdAs() {
        return idAs;
    }

    public void setIdAs(int idAs) {
        this.idAs = idAs;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getNumlista() {
        return numlista;
    }

    public void setNumlista(int numlista) {
        this.numlista = numlista;
    }

    public long getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(long estudiante) {
        this.estudiante = estudiante;
    }
}
