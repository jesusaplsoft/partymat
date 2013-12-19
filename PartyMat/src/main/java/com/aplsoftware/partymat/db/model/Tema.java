package com.aplsoftware.partymat.db.model;

import com.apl.base.model.AbstractPersistent;
import com.apl.base.model.FindValue;

import com.gexcat.gex.cfg.Campo;

import java.util.HashSet;
import java.util.Set;

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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.OptimisticLocking;


@DynamicInsert
@DynamicUpdate
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@OptimisticLocking
@Table(name = "TEMA", uniqueConstraints = {
        @UniqueConstraint(name = "TEM_IDX_CLAVE", columnNames = {
                "ASIGNATURA_ID",
                "CAPITULO"
            })
    })
@Entity
@org.hibernate.annotations.Table(appliesTo = "TEMA",
    comment = "Temas de la Asignatura")
public class Tema
    extends AbstractPersistent<Tema> {

    @Transient
    private static final long serialVersionUID = -1556520611763127408L;
	private static final String[] EQUAL_FIELDS = { "id", "asignatura",
			"capitulo" };

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

    @ForeignKey(name = "TET_FK_TEMA", inverseName = "TET_FK_ETIQUETA")
    @JoinTable(name = "TEMA_ETIQUETA", joinColumns = {
            @JoinColumn(name = "TEMA_ID", nullable = false)
        },
        inverseJoinColumns = @JoinColumn(name = "ETIQUETA_ID", nullable = false))
    @ManyToMany(cascade = { CascadeType.MERGE, CascadeType.REMOVE },
        fetch = FetchType.EAGER)
// @NotAudited
    private Set<Etiqueta> etiquetas;

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
        setAsignatura(asi);
        setCapitulo(capitulo);
    }

    public Set<Etiqueta> getEtiquetas() {
        return new HashSet<Etiqueta>(etiquetas);
    }

    public void setEtiquetas(final Set<Etiqueta> etiquetas) {

        if (this.etiquetas == null) {
            this.etiquetas = new HashSet<Etiqueta>(etiquetas);
        } else {
            this.etiquetas.retainAll(etiquetas);
        }
    }

    public boolean addEtiqueta(final Etiqueta etiqueta) {

        if (etiquetas == null) {
            etiquetas = new HashSet<Etiqueta>();
        }

        return etiquetas.add(etiqueta);
    }

    public boolean removeEtiqueta(final Etiqueta etiqueta) {
        boolean retorno;

        if (etiquetas == null) {
            retorno = false;
        } else {
            retorno = etiquetas.remove(etiqueta);
        }

        return retorno;
    }

    public Asignatura getAsignatura() {
        return asignatura;
    }

    public final void setAsignatura(final Asignatura asignatura) {
        this.asignatura = asignatura;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(final String titulo) {
        this.titulo = titulo;
    }

    public Integer getCapitulo() {
        return capitulo;
    }

    public final void setCapitulo(final Integer capitulo) {
        this.capitulo = capitulo;
    }

    @Override
    public Long getId() {
        return id;
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
        return EQUAL_FIELDS;
    }

    @Override
    public boolean canEqual(final Object other) {
        return (other instanceof Tema);
    }
}
