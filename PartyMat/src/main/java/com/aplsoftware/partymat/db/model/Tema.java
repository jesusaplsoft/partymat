package com.aplsoftware.partymat.db.model;

import com.apl.base.model.AbstractPersistent;
import com.apl.base.model.FindValue;

import com.aplsoftware.partymat.cfg.Campo;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.OptimisticLocking;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@DynamicInsert
@DynamicUpdate
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@OptimisticLocking
@Table(name = "TEMA", uniqueConstraints = {
        @UniqueConstraint(name = "TEM_IDX_CLAVE", columnNames = {
                "ASIGNATURA_ID",
                "CAPITULO"
            })
    })
@org.hibernate.annotations.Table(appliesTo = "TEMA",
    comment = "Temas de la Asignatura")
public class Tema
    extends AbstractPersistent<Tema> {

    @Transient
    private static final long serialVersionUID = -1556520611763127408L;
    private static final String[] EQUAL_FIELDS = {
        "id",
        "asignatura",
        "capitulo"
    };

    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "genTemaId")
    @Id
    @javax.persistence.SequenceGenerator(name = "genTemaId",
        sequenceName = "TEM_GEN_ID")
    private Long id;

    @Column(name = "TITULO", length = Campo.TITULO, nullable = false)
    @NotNull
    @Size(max = Campo.TITULO)
    private String titulo;

    @Column(name = "CAPITULO", precision = Campo.TEMA_INT + Campo.TEMA_DEC,
        scale = Campo.TEMA_DEC, nullable = false)
    @Digits(integer = Campo.TEMA_INT, fraction = Campo.TEMA_DEC)
    @NotNull
    private Integer capitulo;

    @ForeignKey(name = "TEM_FK_ASIGNATURA")
    @JoinColumn(name = "ASIGNATURA_ID", nullable = false)
    @ManyToOne
    @NotNull
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Asignatura asignatura;

    public Tema() {
        super();
    }

    public Tema(final Tema tema) {
        super(tema);
    }

    public Tema(final FindValue gfv) {
        super(gfv);
    }

    public Tema(final Long id) {
        super(id);
    }

    public Tema(final Asignatura asi, final Integer capitulo) {
        this.setAsignatura(asi);
        this.setCapitulo(capitulo);
    }

    public Asignatura getAsignatura() {
        return this.asignatura;
    }

    public final void setAsignatura(final Asignatura asignatura) {
        this.asignatura = asignatura;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public void setTitulo(final String titulo) {
        this.titulo = titulo;
    }

    public Integer getCapitulo() {
        return this.capitulo;
    }

    public final void setCapitulo(final Integer capitulo) {
        this.capitulo = capitulo;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(final Long id) {
        this.id = id;
    }

    @Override
    public String getParent() {
        return "asignatura";
    }

    @Override
    protected String[] equalizer() {
        return Tema.EQUAL_FIELDS;
    }

    @Override
    public boolean canEqual(final Object other) {
        return (other instanceof Tema);
    }
}
