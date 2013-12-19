package com.aplsoftware.partymat.db.model;

import com.apl.base.annotation.ByDefault;
import com.apl.base.annotation.Default;
import com.apl.base.model.AbstractPersistent;
import com.apl.base.model.FindValue;
import com.apl.base.type.EnumType;

import com.aplsoftware.partymat.cfg.Campo;
import com.aplsoftware.partymat.db.model.image.PreguntaImagen;
import com.aplsoftware.partymat.db.model.type.TipoPregunta;

import org.apache.solr.analysis.ASCIIFoldingFilterFactory;
import org.apache.solr.analysis.LowerCaseFilterFactory;
import org.apache.solr.analysis.PhoneticFilterFactory;
import org.apache.solr.analysis.SnowballPorterFilterFactory;
import org.apache.solr.analysis.StandardTokenizerFactory;
import org.apache.solr.analysis.StopFilterFactory;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.OptimisticLocking;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.AnalyzerDef;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;
import org.hibernate.search.annotations.TokenFilterDef;
import org.hibernate.search.annotations.TokenizerDef;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


// @Audited
@Analyzer(definition = "gexcatAnalyzer")
@AnalyzerDef(name = "gexcatAnalyzer", // @TokenFilterDef(factory =
// LowerCaseFilterFactory.class),
// @TokenFilterDef(factory =
// SnowballPorterFilterFactory.class,
// params = { @Parameter(name = "language",
// value = "English") }) })
    tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class),
    filters = {
        @TokenFilterDef(factory = ASCIIFoldingFilterFactory.class),
        @TokenFilterDef(factory = LowerCaseFilterFactory.class),
        @TokenFilterDef(factory = StopFilterFactory.class,
            params = {
                @org.hibernate.search.annotations.Parameter(name = "words",
                    value = "search/stop_es.txt"),
                @org.hibernate.search.annotations.Parameter(name = "ignoreCase",
                    value = "true"),
                @org.hibernate.search.annotations.Parameter(name =
                        "enablePositionIncrements", value = "true")
            }),
        @TokenFilterDef(
            factory = PhoneticFilterFactory.class,
            params = {
                @org.hibernate.search.annotations.Parameter(name = "encoder",
                    value = "DoubleMetaphone")
            }
        ),
        @TokenFilterDef(factory = SnowballPorterFilterFactory.class,
            params = {
                @org.hibernate.search.annotations.Parameter(name = "language",
                    value = "Spanish"),
                @org.hibernate.search.annotations.Parameter(name = "ignoreCase",
                    value = "true")
            })
    })
@DynamicInsert
@DynamicUpdate
@Entity
@Indexed
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@OptimisticLocking
@Table(name = "PREGUNTA", uniqueConstraints = {
        @UniqueConstraint(name = "PRE_IDX_IMPORTACION", columnNames = {
                "PROFESOR",
                "ID_EXTERNO"
            })
    })
@org.hibernate.annotations.Table(appliesTo = "PREGUNTA",
    comment = "Banco de Datos de Preguntas",
    indexes = {
        @org.hibernate.annotations.Index(name = "PRE_IDX_DIFICULTAD",
            columnNames = { "DIFICULTAD" })
    })
public class Pregunta
    extends AbstractPersistent<Pregunta> {

    @Transient
    public static final String SOLO_IMAGEN = "#onlyImage";

    @Transient
    private static final long serialVersionUID = -1556520611763127408L;
    private static final String[] EQUAL_FIELDS = {
        "profesor",
        "identificador"
    };

    @Column(name = "ID", nullable = false)
    @DocumentId
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "genPreguntaId")
    @Id
    @javax.persistence.SequenceGenerator(name = "genPreguntaId",
        sequenceName = "PRE_GEN_ID")
    private Long id;

    @Column(name = "CUESTION", length = Campo.CUESTION, nullable = false)
    @Field(index = Index.YES, store = Store.NO)
    @NotNull
    @Size(max = Campo.CUESTION)
    private String cuestion;

    @Column(name = "DIFICULTAD",
        precision = Campo.DIFICULTAD_INT + Campo.DIFICULTAD_DEC,
        scale = Campo.DIFICULTAD_DEC, nullable = false)
    @Digits(integer = Campo.DIFICULTAD_INT, fraction = Campo.DIFICULTAD_DEC)
    @NotNull
    private Integer dificultad;

    @OneToMany(mappedBy = "pregunta", fetch = FetchType.EAGER,
        cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PreguntaImagen> imagenes;

    /* Nota: No se puede utilizar la anotación @OrderBy tiene un conflicto con
     * la búsqueda de preguntas (BusquedaPreguntasDAO.findPaginas()) A su vez
     * esta consulta tenía un problema con el cálculo de página y se tuvo que
     * recurrir a una método "manual" @OrderBy(value = "orden")
     */
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(orphanRemoval = true, mappedBy = "pregunta",
        cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Respuesta> respuestas;

    @ForeignKey(name = "TEP_FK_PREGUNTA", inverseName = "TEP_FK_TEMA")
    @JoinTable(name = "TEMA_PREGUNTA", joinColumns = {
            @JoinColumn(name = "PREGUNTA_ID", nullable = false)
        }, inverseJoinColumns = @JoinColumn(name = "TEMA_ID", nullable = false))
    @ManyToMany(cascade = { CascadeType.DETACH }, fetch = FetchType.EAGER)
    private Set<Tema> temas;

    @Column(name = "OBSERVACIONES", length = Campo.OBSERVACIONES)
// @Field(index = Index.NO, store = Store.NO)
    @Size(max = Campo.OBSERVACIONES)
    private String observaciones;

    @Column(name = "PROFESOR", length = Campo.NOMBRE)
    @Field(index = Index.NO, store = Store.NO)
    @Size(max = Campo.NOMBRE)
    private String profesor;

    /**
     * <p>Se utiliza para combinar preguntas junto con el código del
     * profesor</p>
     */
    @Column(name = "ID_EXTERNO")
    private Long identificador;

    @Column(name = "TIPO", nullable = false)
    @NotNull
    @Type(type = EnumType.TYPE,
        parameters = {
            @Parameter(name = EnumType.CLASS,
                value = "com.gexcat.gex.model.type.TipoPregunta")
        })
    private TipoPregunta tipo;

    @Column(name = "EST_ACIERTO", precision = 3, scale = 0, nullable = false)
    @Digits(integer = 3, fraction = 0)
    @NotNull
    private Integer estadisticaAcierto;

    @Column(name = "EST_TOTAL", precision = 3, scale = 0, nullable = false)
    @Digits(integer = 3, fraction = 0)
    @NotNull
    private Integer estadisticaTotal;

    @Column(name = "BASICA")
    @Default(value = AbstractPersistent.BT_FALSE, groups = ByDefault.class)
    @NotNull
    @Type(type = AbstractPersistent.BOOLEAN_TYPE)
    private Boolean basica;

    @Column(name = "ES_BARAJAR")
    @Default(value = AbstractPersistent.BT_TRUE, groups = ByDefault.class)
    @NotNull
    @Type(type = AbstractPersistent.BOOLEAN_TYPE)
    private Boolean barajar;

    @Column(name = "ES_LATEX")
    @Default(value = AbstractPersistent.BT_FALSE, groups = ByDefault.class)
    @NotNull
    @Type(type = AbstractPersistent.BOOLEAN_TYPE)
    private Boolean latex;

    public Pregunta() {
        super();
    }

    public Pregunta(final Pregunta concepto) {
        super(concepto);
    }

    public Pregunta(final FindValue gfv) {
        super(gfv);
    }

    public Pregunta(final Long id) {
        super(id);
    }

    public String getProfesor() {
        return this.profesor;
    }

    public void setProfesor(final String profesor) {
        this.profesor = profesor;
    }

    public Long getIdentificador() {
        return this.identificador;
    }

    public void setIdentificador(final Long identificador) {
        this.identificador = identificador;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(final Long id) {
        this.id = id;
    }

    public String getCuestion() {
        return this.cuestion;
    }

    public void setCuestion(final String cuestion) {
        this.cuestion = cuestion;
    }

    public boolean isLatex() {
        return this.is(this.latex, false);
    }

    public Boolean getLatex() {
        return this.latex;
    }

    public void setLatex(final Boolean latex) {
        this.latex = latex;
    }

    public void setImagen(final PreguntaImagen imagen) {

        if (this.imagenes == null) {
            this.imagenes = new ArrayList<PreguntaImagen>();
        }

        if (imagen == null) {
            this.imagenes.clear();
        } else {
            imagen.setPregunta(this);
            this.imagenes.clear();
            this.imagenes.add(imagen);
        }
    }

    public PreguntaImagen getImagen() {

        if (this.imagenes == null) {
            this.imagenes = new ArrayList<PreguntaImagen>();
        }

        if (this.imagenes.size() == 0) {
            return null;
        } else {
            return this.imagenes.get(0);
        }
    }

    public Set<Respuesta> getRespuestas() {

        if (this.respuestas == null) {
            return null;
        } else {
            final Set<Respuesta> aux = new TreeSet<Respuesta>(
                    Respuesta.COMPARATOR);
            aux.addAll(this.respuestas);
            return aux;
        }
    }

    public void setRespuestas(final Set<Respuesta> respuestas) {

        if ((this.respuestas == null) || this.respuestas.isEmpty()) {

            if (respuestas != null) {
                this.respuestas = new HashSet<Respuesta>(respuestas);
            }
        } else {
            this.copyObjects(respuestas, this.respuestas);
        }
    }

    /**
     * @param  froms  conjunto de respuestas a copiar
     * @param  tos    conjunto de respuestas destino
     */
    public void copyObjects(final Set<Respuesta> froms,
            final Set<Respuesta> tos) {

        for (final Respuesta fromRespuesta : froms) {
            fromRespuesta.setPregunta(this);

            if (tos.contains(fromRespuesta)) {

                for (Respuesta toRespuesta : tos) {

                    if (toRespuesta.equals(fromRespuesta)) {

                        toRespuesta = (Respuesta) AbstractPersistent.copyObject(
                                fromRespuesta,
                                toRespuesta,
                                "texto",
                                "imagenes",
                                "correcta",
                                "orden");
                        toRespuesta.setPregunta(this);
                        break;
                    }
                }
            }
        }

        tos.addAll(froms);
        tos.retainAll(froms);
    }

    public String getObservaciones() {
        return this.observaciones;
    }

    public void setObservaciones(final String observaciones) {
        this.observaciones = observaciones;
    }

    public Integer getDificultad() {
        return this.dificultad;
    }

    public void setDificultad(final Integer dificultad) {
        this.dificultad = dificultad;
    }

    public Set<Tema> getTemas() {

        if (this.temas == null) {
            return null;
        } else {
            return new HashSet<Tema>(this.temas);
        }
    }

    public void setTemas(final Set<Tema> temas) {

        if (this.temas == null) {

            if (temas != null) {
                this.temas = new HashSet<Tema>(temas);
            }
        } else if (temas != null) {
            this.temas.addAll(temas);
            this.temas.retainAll(temas);
        } else {
            this.temas = null;
        }
    }

    public TipoPregunta getTipo() {
        return this.tipo;
    }

    public void setTipo(final TipoPregunta tipo) {
        this.tipo = tipo;
    }

    public Integer getEstadisticaAcierto() {
        return this.estadisticaAcierto;
    }

    public void setEstadisticaAcierto(final Integer estadisticaAcierto) {
        this.estadisticaAcierto = estadisticaAcierto;
    }

    public Integer getEstadisticaTotal() {
        return this.estadisticaTotal;
    }

    public void setEstadisticaTotal(final Integer estadisticaTotal) {
        this.estadisticaTotal = estadisticaTotal;
    }

    public Boolean getBasica() {
        return this.basica;
    }

    public void setBasica(final Boolean basica) {
        this.basica = basica;
    }

    public Boolean getBarajar() {
        return this.barajar;
    }

    public void setBarajar(final Boolean barajar) {
        this.barajar = barajar;
    }

    @Override
    public String getParent() {
        return null;
    }

    @Override
    protected String[] equalizer() {
        return Pregunta.EQUAL_FIELDS;
    }

    @Override
    public boolean canEqual(final Object other) {
        return (other instanceof Pregunta);
    }
}
