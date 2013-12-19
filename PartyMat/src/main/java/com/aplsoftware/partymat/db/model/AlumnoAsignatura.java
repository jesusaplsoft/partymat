package com.aplsoftware.partymat.db.model;

import com.apl.base.model.AbstractPersistent;
import com.apl.base.model.FindValue;
import com.apl.base.tool.MiscBase;

import com.aplsoftware.partymat.cfg.Campo;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.OptimisticLocking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
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

    @ForeignKey(name = "ALA_FK_ASIGNATURA_CURSO")
    // @JoinColumn(name = "ASIGNATURA_CURSO_ID",
    // columnDefinition = ColumnDefinition.ALA_ASIGNATURA)
    @JoinColumn(name = "ASIGNATURA_CURSO_ID", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Curso curso;

    // Se ordena por el orden de creación de la tarea
// @NotAudited
    @OneToMany(mappedBy = "alumnoAsignatura", cascade = CascadeType.PERSIST,
        fetch = FetchType.EAGER)
    @OrderColumn(name = "TAREA_ID")
    private Set<Nota> notas;

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

    public AlumnoAsignatura(final Alumno alu, final Curso asc) {
        this.setAlumno(alu);
        this.setCurso(asc);
    }

    public Set<Nota> getNotas() {

        if (this.notas == null) {
            return null;
        } else {
            final Set<Nota> aux = new HashSet<Nota>();
            aux.addAll(this.notas);
            return aux;
        }
    }

    public List<Nota> getNotasOrdenadas() {

        final List<Nota> notasOrdenadas = new ArrayList<Nota>(this.notas);

        Collections.sort(notasOrdenadas, new Comparator<Nota>() {

                @Override
                public int compare(final Nota o1, final Nota o2) {
                    int aux;

                    if (o1 == o2) {
                        return 0;
                    } else if ((o1.getTarea() != null)
                            && (o2.getTarea() != null)) {

                        if ((o1.getTarea().getExamen() != null)
                                && (o2.getTarea().getExamen() != null)) {
                            aux = MiscBase.compare(
                                    o1.getTarea().getExamen().getFecha(),
                                    o2.getTarea().getExamen().getFecha());

                            if (aux == 0) {
                                return MiscBase.compare(o1.getTarea().getId(),
                                        o2.getTarea().getId());
                            } else {
                                return aux;
                            }
                        } else {
                            return MiscBase.compareObject(
                                    o1.getTarea().getExamen(),
                                    o2.getTarea().getExamen());
                        }
                    } else {
                        return MiscBase.compareObject(o1.getTarea(),
                                o2.getTarea());
                    }
                }

            });
        return notasOrdenadas;
    }

    /*
     * Este método existe por cuestiones de compatibilidad con Spring. Para que
     * la definición del atributo notas sea correcta,debe poseer un método
     * getter y uno setter. @param notas
     */
    public void setNotas(final Set<Nota> notas) {

        if ((this.notas == null) || this.notas.isEmpty()) {

            if (notas != null) {
                this.notas = new HashSet<Nota>(notas);
            }
        } else {
            this.copyObjects(notas, this.notas);
        }
    }

    /**
     * @see  Asignatura.copyObjects(Set<Tema>, Set<Tema>)
     */
    public void copyObjects(final Set<Nota> froms, final Set<Nota> tos) {

        for (final Nota fromTema : froms) {
            fromTema.setAlumnoAsignatura(this);

            if (tos.contains(fromTema)) {

                for (Nota toTema : tos) {

                    if (toTema.equals(fromTema)) {
                        toTema = (Nota) AbstractPersistent.copyObject(fromTema,
                                toTema,
                                "correccion", "id", "nota", "tarea");
                        break;
                    }
                }
            }
        }

        tos.addAll(froms);
        tos.retainAll(froms);
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

    public Curso getCurso() {
        return this.curso;
    }

    public Long getCursoId() {
        return this.getEntityId(this.curso);
    }

    public final void setCurso(final Curso curso) {
        this.curso = curso;
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
