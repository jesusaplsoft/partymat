package com.aplsoftware.partymat.db.model;

import com.apl.base.model.AbstractPersistent;
import com.apl.base.model.FindValue;

import com.aplsoftware.partymat.cfg.Campo;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OptimisticLocking;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@DynamicInsert
@DynamicUpdate
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@OptimisticLocking
@Table(name = "ASIGNATURA", uniqueConstraints = {
        @UniqueConstraint(name = "ASI_IDX_CLAVE", columnNames = {
                "CENTRO_ID",
                "SIGLAS"
            })
    })
@org.hibernate.annotations.Table(appliesTo = "ASIGNATURA",
    comment = "Asignaturas que imparte el profesor",
    indexes = {
        @org.hibernate.annotations.Index(name = "ASI_IDX_NOMBRE",
            columnNames = { "NOMBRE" })
    })
public class Asignatura
    extends AbstractPersistent<Asignatura> {

    @Transient
    private static final long serialVersionUID = -1556520611763127408L;

    private static final String[] EQUAL_FIELDS = { "centro", "codigo" };

    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO,
        generator = "genAsignaturaId")
    @Id
    @javax.persistence.SequenceGenerator(name = "genAsignaturaId",
        sequenceName = "ASI_GEN_ID")
    private Long id;

    @Column(name = "NOMBRE", length = Campo.NOMBRE, nullable = false)
    @NotNull
    @Size(max = Campo.NOMBRE)
    private String nombre;

    @Column(name = "SIGLAS", length = Campo.ASI_SIGLAS, nullable = false)
    @NotNull
    @Size(max = Campo.ASI_SIGLAS)
    private String codigo;

    public Asignatura() {
        super();
    }

    public Asignatura(final Asignatura asignatura) {
        super(asignatura);
    }

    public Asignatura(final FindValue gfv) {
        super(gfv);
    }

    public Asignatura(final Long id) {
        super(id);
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(final String nombre) {
        this.nombre = nombre;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(final Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return this.codigo;
    }

    public final void setCodigo(final String codigo) {
        this.codigo = codigo;
    }

    @Override
    public String getParent() {
        return "centro";
    }

    @Override
    protected String[] equalizer() {
        return Asignatura.EQUAL_FIELDS;
    }

    @Override
    public boolean canEqual(final Object other) {
        return (other instanceof Asignatura);
    }
}
