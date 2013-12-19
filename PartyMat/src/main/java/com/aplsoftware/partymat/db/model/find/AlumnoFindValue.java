package com.aplsoftware.partymat.db.model.find;

import com.apl.base.model.FindValue;
import com.apl.base.tool.MiscBase;

import com.aplsoftware.partymat.db.misc.UserData;


public class AlumnoFindValue
    extends FindValue {
    private static final long serialVersionUID = 3287142658715750143L;

    private String nombre;
    private String apellidos;
    private String dni;
    private String asignatura;
    private String curso;
    private String correo;

    private Integer identificador;

    private Long alumnoAsignaturaId;

    public AlumnoFindValue regAlumnos(final Object[] reg) {
        this.setId((Long) reg[0]);
        this.setNombre((String) reg[1]);
        this.setApellidos((String) reg[2]);
        this.setDni((String) reg[3]);
        this.setIdentificador((Integer) reg[4]);
        this.setAsignatura((String) reg[5]);
        this.setCurso((String) reg[6]);
        return this;
    }

    public AlumnoFindValue regAlumno(final Object[] reg) {
        int i = 0;
        this.setId((Long) reg[i++]);
        this.setNombre((String) reg[i++]);
        this.setApellidos((String) reg[i++]);
        this.setAlumnoAsignaturaId((Long) reg[i++]);
        this.setDni((String) reg[i++]);

        if (MiscBase.equals(true, reg[i++])) {
            this.setCorreo((String) reg[i++]);
        }

        return this;
    }

    public String getCorreo() {
        return this.correo;
    }

    public void setCorreo(final String correo) {
        this.correo = correo;
    }

    public Long getAlumnoAsignaturaId() {
        return this.alumnoAsignaturaId;
    }

    public void setAlumnoAsignaturaId(final Long alumnoAsignaturaId) {
        this.alumnoAsignaturaId = alumnoAsignaturaId;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(final String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return this.apellidos;
    }

    public void setApellidos(final String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDni() {
        return this.dni;
    }

    public void setDni(final String dni) {
        this.dni = dni;
    }

    public Integer getIdentificador() {
        return this.identificador;
    }

    public void setIdentificador(final Integer identificador) {
        this.identificador = identificador;
    }

    public String getAsignatura() {
        return this.asignatura;
    }

    public void setAsignatura(final String asignatura) {
        this.asignatura = asignatura;
    }

    public String getCurso() {
        return this.curso;
    }

    public void setCurso(final String curso) {
        this.curso = curso;
    }

    @Override
    public String toString() {
        return UserData.getInstance()
            .getTextoAlumno(this.apellidos, this.nombre);
    }
}
