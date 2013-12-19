package com.aplsoftware.partymat.db.dao;

import com.apl.base.dao.Generic;
import com.apl.base.dao.impl.AbstractGenericFind.Busqueda;

import com.gexcat.gex.model.Alumno;
import com.gexcat.gex.model.AlumnoAsignatura;
import com.gexcat.gex.model.Curso;
import com.gexcat.gex.model.Correccion;
import com.gexcat.gex.model.find.AlumnoFindValue;
import com.gexcat.gex.model.find.AlumnoImporter;
import com.gexcat.gex.model.find.GexCatTreeNode;

import java.util.List;


public interface AlumnoDAOImpl
    extends Generic<AlumnoAsignatura> {

    /**
     * Lista de Alumnos que serán mostrados en el árbol. Están organizados por
     * cursos.
     *
     * @return
     */
    List<GexCatTreeNode> findTree();

    /**
     * <p>Devuelve el número dotal de cursos en el que este alumno se encuentra
     * matriculado</p>
     *
     * @return
     */
    boolean existsCursosAlumno(Alumno alu);

    Alumno findById(Alumno alu);

    void deleteRow(AlumnoAsignatura ala, boolean alumno);

    void importarAlumnos(final Curso asc,
            final List<AlumnoImporter> lista);

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

    Correccion getCorreccion(Long id);

	boolean existsIdentificador(Integer identificador, Long id);

	List<AlumnoImporter> comprobarAlumnos(List<AlumnoImporter> alumnos);

	Alumno findIdentificador(Integer id);

	Integer findNumeroAlumno(AlumnoAsignatura ala);

	List<AlumnoFindValue> findAlumnos(Long cursoId);

	void updateAlumnos(Long cursoId, List<AlumnoFindValue> lista);

	void deleteAlumnos(Long cursoId);

}
