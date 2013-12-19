package com.aplsoftware.partymat.db.dao;

import com.apl.base.dao.Generic;

import com.gexcat.gex.model.AlumnoAsignatura;
import com.gexcat.gex.model.Asignatura;
import com.gexcat.gex.model.Curso;
import com.gexcat.gex.model.Nota;
import com.gexcat.gex.model.Tarea;
import com.gexcat.gex.model.find.AsignaturaItem;
import com.gexcat.gex.model.find.CursoItem;
import com.gexcat.gex.model.find.GexCatTreeNode;

import java.util.List;


public interface AsignaturaDAO
    extends Generic<Curso> {

    Asignatura findById(Asignatura asi);

    /**
     * Lista de Asignatura que serán mostrados en el árbol.
     *
     * @return
     */
    List<GexCatTreeNode> findTree();

    List<GexCatTreeNode> findCursosTree();

    List<GexCatTreeNode> findTemasTree();

    Curso findPreviousRow(Curso entity);

    Curso findNextRow(Curso entity);

    Curso findFirstRow(Long centroId, Curso entity);

    Curso findLastRow(Long centroId, Curso entity);

    Boolean existsNotas(Tarea tar);

    List<AsignaturaItem> findItems();

    List<CursoItem> findCursos(Long parentId);

    List<Nota> getNotas(AlumnoAsignatura ala);

    void deleteTarea(Long tareaId);

	Curso updateRow(Curso entity, List<Tarea> tareasBorradas);

}
