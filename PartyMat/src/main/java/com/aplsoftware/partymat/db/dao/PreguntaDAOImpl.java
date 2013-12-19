package com.aplsoftware.partymat.db.dao;

import com.apl.base.dao.Generic;

import com.gexcat.gex.model.ModeloExamen;
import com.gexcat.gex.model.Pregunta;
import com.gexcat.gex.model.Tema;
import com.gexcat.gex.model.find.PreguntaFindValue;
import com.gexcat.gex.model.find.PreguntaImporter;
import com.gexcat.gex.model.type.TipoPregunta;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;


public interface PreguntaDAOImpl
    extends Generic<Pregunta> {

    String PREGUNTA = "Q";
    String RES_CORRECTA = "A+";
    String RES_INCORRECTA = "A";

    void importarPregunta(Collection<String[]> lista,
            TipoPregunta tipo,
            Tema tema);

    void importarPregunta(Collection<String[]> lista,
            TipoPregunta tipo,
            Set<Tema> tema);

    Pregunta findFirstRow(Pregunta entity, Tema tem);

    Pregunta findNextRow(Pregunta entity, Tema tem);

    Pregunta findPreviousRow(Pregunta entity, Tema tem);

    Pregunta findLastRow(Pregunta entity, Tema tem);

    Integer findTotalPreguntas(Tema tem);

    Integer findNumeroPregunta(Pregunta pre, Tema tem);

    List<PreguntaFindValue> findPreguntas(Tema tema);

    List<PreguntaFindValue> findPreguntas(Set<Tema> temas);

    List<String[]> preparar(List<PreguntaFindValue> lista);

    void deletePreguntas(Tema tem, List<PreguntaFindValue> preguntas);

    void rebuildIndex();

    void rebuildMassIndex();

    List<Pregunta> search(final String queryString);

    List<Pregunta> searchPhrase(final String phraseString, final int slop);

    void importarPregunta(List<PreguntaImporter> lista,
            TipoPregunta tipo,
            Set<Tema> temas);

    void importarPregunta(List<PreguntaImporter> lista,
            TipoPregunta tipo,
            Tema tema);

    List<String[]> prepararExamen(ModeloExamen mod);

    boolean existsPregunta();

    List<PreguntaFindValue> previsualizar(
            Map<Long, PreguntaFindValue> preguntas);

    List<PreguntaFindValue> findFechasExamen(Long preguntaId);

    List<PreguntaFindValue> previsualizar(Pregunta pre);
}
