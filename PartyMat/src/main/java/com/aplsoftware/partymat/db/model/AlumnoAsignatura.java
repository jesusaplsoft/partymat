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

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


// SQLite
// @AttributeOverrides({
// @AttributeOverride(name = "version",
// column =
// @Column(name = "VERSION", columnDefinition = ColumnDefinition.VERSION))
// })
@DynamicInsert
@DynamicUpdate
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@OptimisticLocking
@Table(name = "ALUMNO_ASIGNATURA", uniqueConstraints = {
        @UniqueConstraint(name = "ALA_IDX_ASIGNATURA", columnNames = {
                "ASIGNATURA_CURSO_ID",
                "ALUMNO_ID"
            })
    })
public class AlumnoAsignatura
    extends AbstractPersistent<AlumnoAsignatura> {

    @Transient
    private static final long serialVersionUID = -1556520611763127408L;

    private static final String[] EQUAL_FIELDS = {
        "alumno",
        "curso"
    };

    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO,
        generator = "genAlumnoAsignaturaId")
    @Id
    @javax.persistence.SequenceGenerator(name = "genAlumnoAsignaturaId",
        sequenceName = "ALA_GEN_ID")
    private Long id;

    @Column(name = "OBSERVACIONES", length = Campo.OBSERVACIONES)
    @Size(max = Campo.OBSERVACIONES)
    private String observaciones;

    @ForeignKey(name = "ALA_FK_ALUMNO")
    // @JoinColumn(name = "ALUMNO_ID", columnDefinition =
    // ColumnDefinition.ALA_ALUMNO)
    @JoinColumn(name = "ALUMNO_ID", nullable = false)
    @ManyToOne
    @NotNull
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Alumno alumno;

    public AlumnoAsignatura() {
        super();
    }

    public AlumnoAsignatura(final AlumnoAsignatura asa) {
        super(asa);
    }

    public AlumnoAsignatura(final FindValue gfv) {
        super(gfv);
    }

    public AlumnoAsignatura(final Long id) {
        super(id);
    }

    public AlumnoAsignatura(final Alumno alu) {
        this.setAlumno(alu);
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(final Long id) {
        this.id = id;
    }

    public String getObservaciones() {
        return this.observaciones;
    }

    public void setObservaciones(final String observaciones) {
        this.observaciones = observaciones;
    }

    public Alumno getAlumno() {
        return this.alumno;
    }

    public Long getAlumnoId() {
        return this.getEntityId(this.alumno);
    }

    public final void setAlumno(final Alumno alumno) {
        this.alumno = alumno;
    }

    @Override
    public String getParent() {
        return "alumno";
    }

    @Override
    protected String[] equalizer() {
        return AlumnoAsignatura.EQUAL_FIELDS;
    }

    @Override
    public boolean canEqual(final Object other) {
        return (other instanceof AlumnoAsignatura);
    }
}
