package com.aplsoftware.partymat.db.dao;

import com.apl.base.dao.Generic;
import com.apl.base.dao.impl.AbstractGenericFind.Busqueda;

import com.aplsoftware.partymat.db.model.Alumno;
import com.aplsoftware.partymat.db.model.AlumnoAsignatura;
import com.aplsoftware.partymat.db.model.find.AlumnoFindValue;

import java.util.List;


public interface AlumnoDAOImpl
    extends Generic<AlumnoAsignatura> {

    Alumno findById(Alumno alu);

    void deleteRow(AlumnoAsignatura ala, boolean alumno);

    @Override
    AlumnoAsignatura updateRow(AlumnoAsignatura ala);

    AlumnoAsignatura findFirstRow(AlumnoAsignatura ala);

    AlumnoAsignatura findPreviousRow(AlumnoAsignatura ala);

    AlumnoAsignatura findNextRow(AlumnoAsignatura ala);

    AlumnoAsignatura findLastRow(AlumnoAsignatura ala);

    List<AlumnoFindValue> findAlumnos(Long cursoId,
            String dni,
            Integer identificador,
            String nombre,
            Busqueda tipoBusqueda,
            boolean caseSensitive,
            int paginaActual);

    Integer findAlumnosPages(Long cursoId,
            String dni,
            Integer identificador,
            String nombre,
            Busqueda tipoBusqueda,
            boolean caseSensitive);

    boolean existsIdentificador(Integer identificador, Long id);

    Alumno findIdentificador(Integer id);

    Integer findNumeroAlumno(AlumnoAsignatura ala);

    List<AlumnoFindValue> findAlumnos(Long cursoId);

    void updateAlumnos(Long cursoId, List<AlumnoFindValue> lista);

    void deleteAlumnos(Long cursoId);

}
