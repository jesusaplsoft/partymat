package com.gexcat.gex.model.find;

import com.apl.base.model.FindValue;
import com.apl.base.tool.MiscBase;
import com.gexcat.gex.misc.UserData;


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
        setId((Long) reg[0]);
        setNombre((String) reg[1]);
        setApellidos((String) reg[2]);
        setDni((String) reg[3]);
		setIdentificador((Integer) reg[4]);
        setAsignatura((String) reg[5]);
        setCurso((String) reg[6]);
        return this;
    }

    public AlumnoFindValue regAlumno(final Object[] reg) {
    	int i = 0;
        setId((Long) reg[i++]);
        setNombre((String) reg[i++]);
        setApellidos((String) reg[i++]);
        setAlumnoAsignaturaId((Long) reg[i++]);
        setDni((String) reg[i++]);
        if (MiscBase.equals(true, reg[i++])){
        	setCorreo((String) reg[i++]);
        }
        return this;
    }
    
    public String getCorreo() {
		return correo;
	}

	public void setCorreo(final String correo) {
		this.correo = correo;
	}

	public Long getAlumnoAsignaturaId() {
        return alumnoAsignaturaId;
    }

    public void setAlumnoAsignaturaId(final Long alumnoAsignaturaId) {
        this.alumnoAsignaturaId = alumnoAsignaturaId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(final String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(final String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(final String dni) {
        this.dni = dni;
    }

	public Integer getIdentificador() {
		return identificador;
	}

	public void setIdentificador(final Integer identificador) {
		this.identificador = identificador;
	}

	public String getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(final String asignatura) {
        this.asignatura = asignatura;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(final String curso) {
        this.curso = curso;
    }
    
    @Override
    public String toString() {
    	return UserData.getInstance().getTextoAlumno(apellidos, nombre);
    }
}
