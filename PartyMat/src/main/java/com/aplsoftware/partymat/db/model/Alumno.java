package com.aplsoftware.partymat.db.model;

import com.apl.base.annotation.ByDefault;
import com.apl.base.annotation.Default;
import com.apl.base.model.AbstractAuditablePersistent;
import com.apl.base.model.AbstractPersistent;
import com.apl.base.model.Auditable;
import com.apl.base.model.FindValue;
import com.apl.base.type.EnumType;

import com.aplsoftware.partymat.cfg.Campo;
import com.aplsoftware.partymat.db.model.image.AlumnoImagen;
import com.aplsoftware.partymat.db.model.type.TipoDomicilio;
import com.aplsoftware.partymat.db.model.type.TipoSexo;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OptimisticLocking;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import java.util.Date;
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
@NamedQueries(
    value = {
        @NamedQuery(name = Alumno.UPD_CURSO,
            query =
                "UPDATE AlumnoAsignatura SET curso.id = :cursoId WHERE id = :id"),
    }
)
@OptimisticLocking
@Table(name = "ALUMNO", uniqueConstraints = {
        @UniqueConstraint(name = "ALU_IDX_CLAVE", columnNames = { "DNI" }),
        @UniqueConstraint(name = "ALU_IDX_NIU", columnNames = { "NIU" })
    })
@org.hibernate.annotations.Table(appliesTo = "ALUMNO", comment = "Alumno",
    indexes = {
        @org.hibernate.annotations.Index(name = "ALU_IDX_NOMBRE",
            columnNames = { "APELLIDOS", "NOMBRE" })
    })
public class Alumno
    extends AbstractAuditablePersistent<Auditable, Alumno> {

    @Transient
    private static final long serialVersionUID = -1556520611763127408L;

    public static final String UPD_CURSO = "updateCurso";

    private static final String[] EQUAL_FIELDS = { "codigo" };

    enum CampoAlumno {
        NOMBRE
    }

    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "genAlumnoId")
    @Id
    @javax.persistence.SequenceGenerator(name = "genAlumnoId",
        sequenceName = "ALU_GEN_ID")
    private Long id;

    @Column(name = "NOMBRE", length = Campo.ALU_NOMBRE, nullable = false)
    @NotNull
    @Size(max = Campo.ALU_NOMBRE)
    private String nombre;

    @Column(name = "OBSERVACIONES", length = Campo.OBSERVACIONES)
    @Size(max = Campo.OBSERVACIONES)
    private String observaciones;

    @Column(name = "APELLIDOS", length = Campo.APELLIDOS, nullable = false)
    @NotNull
    @Size(max = Campo.APELLIDOS)
    private String apellidos;

    @Column(name = "DNI", length = Campo.DNI, nullable = false)
    @NotNull
    @Size(max = Campo.DNI)
    private String codigo;

    @Column(name = "NIU", length = Campo.NIU)
    @Size(max = Campo.NIU)
    private String niu;

    @Column(name = "SEXO")
    @Type(type = EnumType.TYPE,
        parameters = {
            @Parameter(name = EnumType.CLASS,
                value = "com.gexcat.gex.model.type.TipoSexo")
        })
    private TipoSexo sexo;

    @Column(name = "MOVIL", length = Campo.MOVIL)
    @Size(max = Campo.MOVIL)
    private String movil;

    @Column(name = "ENVIAR_CORREO")
    @Default(value = AbstractPersistent.BT_TRUE, groups = ByDefault.class)
    @NotNull
    @Type(type = AbstractPersistent.BOOLEAN_TYPE)
    private Boolean enviarCorreo;

    @Column(name = "CORREO", length = Campo.CORREO)
    @Size(max = Campo.CORREO)
    private String correo;

    @Column(name = "NACIONALIDAD", length = Campo.NACIONALIDAD)
    @Size(max = Campo.NACIONALIDAD)
    private String nacionalidad;

    @Column(name = "TRABAJA")
    @Default(value = AbstractPersistent.BT_FALSE, groups = ByDefault.class)
    @NotNull
    @Type(type = AbstractPersistent.BOOLEAN_TYPE)
    private Boolean trabaja;

    @Column(name = "EMPRESA", length = Campo.EMPRESA)
    @Size(max = Campo.EMPRESA)
    private String empresa;

    @Column(name = "HORARIO", length = Campo.HORARIO)
    @Size(max = Campo.HORARIO)
    private String horario;

    @Column(name = "INICIO_ESTUDIOS", length = Campo.CARRERA)
    @Size(max = Campo.CARRERA)
    private String inicioEstudios;

    @Column(name = "ESTUDIOS_ANTERIORES", length = Campo.ESTUDIOS)
    @Size(max = Campo.ESTUDIOS)
    private String estudiosAnteriores;

    @Column(name = "FECHA_NACIMIENTO")
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;

    @Column(name = "IDENTIFICADOR", precision = Campo.ID_CTA_INT,
        nullable = false)
    @Digits(integer = Campo.ID_CTA_INT, fraction = 0)
    @NotNull
    private Integer identificador;

    @OneToMany(mappedBy = "alumno", fetch = FetchType.EAGER,
        cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AlumnoImagen> fotos;

    @OneToMany(mappedBy = "alumno", fetch = FetchType.EAGER,
        cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Domicilio> domicilios;

    public Alumno() {
        super();
    }

    public Alumno(final Alumno alumno) {
        super(alumno);
    }

    public Alumno(final FindValue gfv) {
        super(gfv);
    }

    public Alumno(final Long id) {
        super(id);
    }

    public Alumno(final String codigo) {
        this.setCodigo(codigo);
    }

    public String getObservaciones() {
        return this.observaciones;
    }

    public void setObservaciones(final String observaciones) {
        this.observaciones = observaciones;
    }

    public Boolean getTrabaja() {
        return this.trabaja;
    }

    public void setTrabaja(final Boolean trabaja) {
        this.trabaja = trabaja;
    }

    public boolean isTrabaja() {
        return this.trabaja;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(final String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return this.apellidos;
    }

    public void setApellidos(final String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDni() {
        return this.codigo;
    }

    public void setDni(final String codigo) {
        this.codigo = (codigo == null) ? null : codigo.toUpperCase();
    }

    public String getCodigo() {
        return this.codigo;
    }

    public final void setCodigo(final String codigo) {
        this.codigo = codigo;
    }

    public String getNiu() {
        return this.niu;
    }

    public void setNiu(final String niu) {
        this.niu = niu;
    }

    public String getMovil() {
        return this.movil;
    }

    public void setMovil(final String movil) {
        this.movil = movil;
    }

    public String getCorreo() {
        return this.correo;
    }

    public void setCorreo(final String correo) {
        this.correo = correo;
    }

    public void setFoto(final AlumnoImagen foto) {

        if (foto != null) {
            foto.setAlumno(this);
        }

        this.fotos = this.setOne(this.fotos, foto);
    }

    public AlumnoImagen getFoto() {
        return this.getOne(this.fotos);
    }

    public String getNacionalidad() {
        return this.nacionalidad;
    }

    public void setNacionalidad(final String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getEmpresa() {
        return this.empresa;
    }

    public void setEmpresa(final String empresa) {
        this.empresa = empresa;
    }

    public String getHorario() {
        return this.horario;
    }

    public void setHorario(final String horario) {
        this.horario = horario;
    }

    public String getInicioEstudios() {
        return this.inicioEstudios;
    }

    public void setInicioEstudios(final String inicioEstudios) {
        this.inicioEstudios = inicioEstudios;
    }

    public String getEstudiosAnteriores() {
        return this.estudiosAnteriores;
    }

    public void setEstudiosAnteriores(final String estudiosAnteriores) {
        this.estudiosAnteriores = estudiosAnteriores;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(final Long id) {
        this.id = id;
    }

    public TipoSexo getSexo() {
        return this.sexo;
    }

    public void setSexo(final TipoSexo sexo) {
        this.sexo = sexo;
    }

    public Domicilio getDomicilioCurso() {
        return this.getDomicilio(TipoDomicilio.CURSO);
    }

    public void setDomicilioCurso(final Domicilio domicilioCurso) {
        this.setDomicilio(domicilioCurso, TipoDomicilio.CURSO);
    }

    public Domicilio getDomicilioFamiliar() {
        return this.getDomicilio(TipoDomicilio.FAMILIAR);
    }

    public void setDomicilioFamiliar(final Domicilio domicilioFamiliar) {
        this.setDomicilio(domicilioFamiliar, TipoDomicilio.FAMILIAR);
    }

    public Domicilio getDomicilioNacimiento() {
        return this.getDomicilio(TipoDomicilio.NACIMIENTO);
    }

    public void setDomicilioNacimiento(final Domicilio domicilioNacimiento) {
        this.setDomicilio(domicilioNacimiento, TipoDomicilio.NACIMIENTO);
    }

    public Integer getIdentificador() {
        return this.identificador;
    }

    public void setIdentificador(final Integer identificador) {
        this.identificador = identificador;
    }

    public Domicilio getDomicilio(final TipoDomicilio tipo) {

        if ((this.domicilios != null) && (this.domicilios.size() > 0)) {

            for (final Domicilio dom : this.domicilios) {

                if (dom.getTipo() == tipo) {
                    return dom;
                }
            }
        }

        return new Domicilio(this, tipo);
    }

    private void setDomicilio(final Domicilio domicilio,
            final TipoDomicilio tipo) {

        if (this.domicilios == null) {
            this.domicilios = new HashSet<Domicilio>();
        }

        if (domicilio == null) {
            this.domicilios.add(new Domicilio(this, tipo));
        } else {

            if (domicilio.getAlumno() == null) {
                domicilio.setAlumno(this);
                domicilio.setTipo(tipo);
            }

            this.domicilios.add(domicilio);
        }
    }

    public Set<AlumnoImagen> getFotos() {
        return this.fotos;
    }

    public void setFotos(final Set<AlumnoImagen> fotos) {
        this.fotos = fotos;
    }

    public Set<Domicilio> getDomicilios() {
        return this.domicilios;
    }

    public void setDomicilios(final Set<Domicilio> domicilios) {
        this.domicilios = domicilios;
    }

    public Date getFechaNacimiento() {

        if (this.fechaNacimiento == null) {
            return null;
        } else {
            return new Date(this.fechaNacimiento.getTime());
        }
    }

    public void setFechaNacimiento(final Date fechaNacimiento) {

        if (fechaNacimiento == null) {
            this.fechaNacimiento = null;
        } else {
            this.fechaNacimiento = new Date(fechaNacimiento.getTime());
        }
    }

    public Boolean getEnviarCorreo() {
        return this.enviarCorreo;
    }

    public boolean isEnviarCorreo() {
        return this.enviarCorreo;
    }

    public void setEnviarCorreo(final Boolean enviarCorreo) {
        this.enviarCorreo = enviarCorreo;
    }

    @Override
    public String getParent() {
        return null;
    }

    @Override
    protected String[] equalizer() {
        return Alumno.EQUAL_FIELDS;
    }

    @Override
    public boolean canEqual(final Object other) {
        return (other instanceof Alumno);
    }
}
