package com.cda.here.entidades;

public class Estudiantes {
    public long getMatricula() {
        return matricula;
    }

    public void setMatricula(long matricula) {
        this.matricula = matricula;
    }

    public int getNumeroL() {
        return numeroL;
    }

    public void setNumeroL(int numeroL) {
        this.numeroL = numeroL;
    }

    public int getListaAlumno() {
        return listaAlumno;
    }

    public void setListaAlumno(int listaAlumno) {
        this.listaAlumno = listaAlumno;
    }

    public int getDocenteAlumno() {
        return docenteAlumno;
    }

    public void setDocenteAlumno(int docenteAlumno) {
        this.docenteAlumno = docenteAlumno;
    }

    public String getNombreAl() {
        return nombreAl;
    }

    public void setNombreAl(String nombreAl) {
        this.nombreAl = nombreAl;
    }

    private long matricula;
    private int numeroL;
    private int listaAlumno;
    private int docenteAlumno;
    private String nombreAl;
}
