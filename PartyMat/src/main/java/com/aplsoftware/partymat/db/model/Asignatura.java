package com.aplsoftware.partymat.db.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

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
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.OptimisticLocking;

import com.apl.base.model.AbstractPersistent;
import com.apl.base.model.FindValue;
import com.apl.base.tool.MiscBase;

import com.gexcat.gex.cfg.Campo;


@DynamicInsert
@DynamicUpdate
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@OptimisticLocking
@Table(name = "ASIGNATURA", uniqueConstraints = {
        @UniqueConstraint(name = "ASI_IDX_CLAVE", columnNames = {
                "CENTRO_ID",
                "SIGLAS"
            })
    })
@Entity
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

// @NotAudited
	@OneToMany(orphanRemoval = true, mappedBy = "asignatura", fetch = FetchType.EAGER)
    @OrderBy(value = "capitulo")
	private List<Tema> temas;

    @ForeignKey(name = "ASI_FK_CENTRO")
    @JoinColumn(name = "CENTRO_ID", nullable = false)
    @ManyToOne
    @NotNull
    private Centro centro;

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

    public Asignatura(final Centro cen, final String codigo) {
        setCodigo(codigo);
        setCentro(cen);

    }

    public Centro getCentro() {
        return centro;
    }

    public Long getCentroId() {
        return getEntityId(centro);
    }

    public final void setCentro(final Centro centro) {
        this.centro = centro;
    }

    public List<Tema> getTemas() {
        return getTemas(true);
    }

    /**
     * <p>Devuelve Temas</p>
     *
     * @param   unmodifiable  si es no modificable
     *
     * @return  colección de Temas
     */
    public List<Tema> getTemas(final boolean unmodifiable) {

        if (temas == null) {
            return Collections.emptyList();
        } else if (unmodifiable) {
            return Collections.unmodifiableList(temas);
        } else {
            return new ArrayList<Tema>(temas);
        }
    }

    public List<Tema> getTemasOrdenados() {

        final List<Tema> temas;

        if (this.temas == null) {
            return new ArrayList<Tema>();
        } else {
            temas = new ArrayList<Tema>(this.temas);
            Collections.sort(temas, new Comparator<Tema>() {
                    @Override
                    public int compare(final Tema o1, final Tema o2) {
                        return MiscBase.compare(o1.getCapitulo(),
                                o2.getCapitulo());
                    }
                });
            return temas;
        }
    }

    public void setTemas(final List<Tema> temas) {

    	if (temas == null){
    		this.temas.clear();
    	} else if (this.temas == null || this.temas.isEmpty()) {

            if (temas != null)
                this.temas = new ArrayList<Tema>(temas);
            
        } else {
        	boolean exists = false;
        	for (final Tema from : temas){
        		exists = false;
        		for (final Tema elm : this.temas)
        			if (elm.equals(from)) {
        				copyObject(from, elm, "titulo", "capitulo");
        				exists = true;
        				break;
        			}
        		if (!exists){
        			this.temas.add(from);
        		}
        	}
        	 
        	this.temas.retainAll(temas);
        }        
    }

    /**
     * Reemplaza el Set TOS por el Set FROMS. Es fundamental que funcionen
     * correctamente los métodos equals y hashcode.
     *
     * <p>Para ello:</p>
     *
     * <ul>
     *   <li>Llama al método copyObject del padre y le pasa los temas iguales y
     *     la relación de campos a cambiar</li>
     *   <li>Pasa los temas que hay en origen y no en destino</li>
     *   <li>Quita en destino los temas que no están en origen</li>
     * </ul>
     *
     * @param  froms  Set emisor
     * @param  tos    Set receptor
     */
    public void copyObjects(final Set<Tema> froms, final Set<Tema> tos) {

        for (final Tema fromTema : froms) {
            fromTema.setAsignatura(this);

            if (tos.contains(fromTema)) {

                for (Tema toTema : tos) {

                    if (toTema.equals(fromTema)) {
                        toTema = (Tema) copyObject(fromTema, toTema, "titulo");
                        break;
                    }
                }
            }
        }

        tos.addAll(froms);
        tos.retainAll(froms);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(final String nombre) {
        this.nombre = nombre;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(final Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
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
        return EQUAL_FIELDS;
    }

    @Override
    public boolean canEqual(final Object other) {
        return (other instanceof Asignatura);
    }
}
