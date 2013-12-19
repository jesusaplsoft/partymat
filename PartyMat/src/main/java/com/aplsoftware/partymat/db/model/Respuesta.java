package com.aplsoftware.partymat.db.model;

import com.apl.base.annotation.ByDefault;
import com.apl.base.annotation.Default;
import com.apl.base.model.AbstractPersistent;
import com.apl.base.model.FindValue;
import com.apl.base.tool.MiscBase;

import com.aplsoftware.partymat.cfg.Campo;
import com.aplsoftware.partymat.db.model.image.RespuestaImagen;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.OptimisticLocking;
import org.hibernate.annotations.Type;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


// @Audited
@DynamicInsert
@DynamicUpdate
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@OptimisticLocking
@Table(name = "RESPUESTA", uniqueConstraints = {
        @UniqueConstraint(name = "RES_IDX_CLAVE", columnNames = {
                "PREGUNTA_ID",
                "ORDEN"
            })
    })
@org.hibernate.annotations.Table(appliesTo = "RESPUESTA",
    comment = "Posible Respuesta de la Pregunta planteada")
public class Respuesta
    extends AbstractPersistent<Respuesta> {

    @Transient
    private static final long serialVersionUID = -1556520611763127408L;
    private static final String[] EQUAL_FIELDS = { "pregunta", "orden" };

    @Transient
    public static final Comparator<Respuesta> COMPARATOR;

    static {
        COMPARATOR = new RespuestaOrder();
    }

    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO,
        generator = "genRespuestaId")
    @Id
    @javax.persistence.SequenceGenerator(name = "genRespuestaId",
        sequenceName = "RES_GEN_ID")
    private Long id;

    @Column(name = "TEXTO", length = Campo.TEXTO, nullable = false)
    @NotNull
    @Size(max = Campo.TEXTO)
    private String texto;

// @Column(name = "ES_CORRECTA",
// columnDefinition = ColumnDefinition.RES_ES_CORRECTA)
    @Column(name = "ES_CORRECTA", nullable = false)
    @Default(value = AbstractPersistent.BT_FALSE, groups = ByDefault.class)
    @NotNull
    @Type(type = AbstractPersistent.BOOLEAN_TYPE)
    private Boolean correcta;

    @Column(name = "ORDEN",
        precision = Campo.RESPUESTA_INT + Campo.RESPUESTA_DEC,
        scale = Campo.RESPUESTA_DEC, nullable = false)
    @Digits(integer = Campo.RESPUESTA_INT, fraction = Campo.RESPUESTA_DEC)
// @NotAudited
    @NotNull
    private Integer orden;

    @Column(name = "ES_LATEX")
    @Default(value = AbstractPersistent.BT_FALSE, groups = ByDefault.class)
    @NotNull
    @Type(type = AbstractPersistent.BOOLEAN_TYPE)
    private Boolean latex;

    @ForeignKey(name = "RES_FK_PREGUNTA")
    @JoinColumn(name = "PREGUNTA_ID", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Pregunta pregunta;

    @OneToMany(mappedBy = "respuesta", fetch = FetchType.EAGER,
        cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RespuestaImagen> imagenes;

    public Respuesta() {
        super();
    }

    public Respuesta(final Respuesta respuesta) {
        super(respuesta);
    }

    public Respuesta(final FindValue gfv) {
        super(gfv);
    }

    public Respuesta(final Long id) {
        super(id);
    }

    public Respuesta(final Pregunta pre,
            final Integer orden,
            final String texto,
            final Boolean correcto) {
        this.setPregunta(pre);
        this.setOrden(orden);
        this.setTexto(texto);
        this.setCorrecta(correcto);

    }

    public Integer getOrden() {
        return this.orden;
    }

    public final void setOrden(final Integer orden) {
        this.orden = orden;
    }

    public Pregunta getPregunta() {
        return this.pregunta;
    }

    public Long getPreguntaId() {
        return this.getEntityId(this.pregunta);
    }

    public final void setPregunta(final Pregunta pregunta) {
        this.pregunta = pregunta;
    }

    public Boolean getCorrecta() {
        return this.correcta;
    }

    public boolean isCorrecta() {
        return this.correcta;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(final Long id) {
        this.id = id;
    }

    public String getTexto() {
        return this.texto;
    }

    public final void setTexto(final String texto) {
        this.texto = texto;
    }

    public final void setCorrecta(final Boolean correcta) {
        this.correcta = correcta;
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

    public void setImagen(final RespuestaImagen imagen) {

        if (this.imagenes == null) {
            this.imagenes = new ArrayList<RespuestaImagen>();
        }

        if (imagen == null) {
            this.imagenes.clear();
        } else {
            imagen.setRespuesta(this);
            this.imagenes.clear();
            this.imagenes.add(imagen);
        }
    }

    public RespuestaImagen getImagen() {

        if ((this.imagenes == null) || (this.imagenes.size() == 0)) {
            return null;
        } else {
            return this.imagenes.get(0);
        }
    }

    public List<RespuestaImagen> getImagenes() {
        return this.imagenes;
    }

    public void setImagenes(final List<RespuestaImagen> imagenes) {
        this.imagenes = imagenes;
    }

    @Override
    public String getParent() {
        return "pregunta";
    }

    @Override
    protected String[] equalizer() {
        return Respuesta.EQUAL_FIELDS;
    }

    @Override
    public boolean canEqual(final Object other) {
        return (other instanceof Respuesta);
    }

    public static class RespuestaOrder
        implements Comparator<Respuesta>, Serializable {

        private static final long serialVersionUID = -4581567773218270132L;

        @Override
        public int compare(final Respuesta o1, final Respuesta o2) {

            if (MiscBase.compare(o1.getPreguntaId(), o2.getPreguntaId()) == 0) {
                return MiscBase.compare(o1.getOrden(), o2.getOrden());
            } else {
                return MiscBase.compare(o1.getPreguntaId(), o2.getPreguntaId());
            }
        }
    }
}
