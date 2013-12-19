package com.aplsoftware.partymat.db.model.image;

import com.apl.base.model.OneToOneRelation;

import com.aplsoftware.partymat.db.model.Alumno;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.OptimisticLocking;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import javax.swing.ImageIcon;

import javax.validation.constraints.NotNull;


/**
 * <b>Imágenes.</b>
 *
 * <p>Datos de cada imagen.</p>
 *
 * @author   $Author: jose $
 * @version  $Revision: 1.11 $ $Date: 2009/07/27 11:06:48 $
 */
@DynamicInsert
@DynamicUpdate
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@OptimisticLocking
@Table(name = "ALU_IMAGEN")
@org.hibernate.annotations.Table(appliesTo = "ALU_IMAGEN",
    comment = "Fotos de alumnos")
public class AlumnoImagen
    extends Imagen<AlumnoImagen>
    implements OneToOneRelation<Alumno> {
    private static final long serialVersionUID = -7665294280850013279L;

    @ForeignKey(name = "ALU_IMG_FK_ALUMNO")
    @JoinColumn(name = "ALUMNO_ID", nullable = false)
    @NotNull
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToOne
    private Alumno alumno;

    public AlumnoImagen() {
        super();
    }

    public AlumnoImagen(final AlumnoImagen img) {
        super(img);
    }

    public AlumnoImagen(final ImageIcon imagen, final ImageIcon original) {
        super(imagen, original);
    }

    public AlumnoImagen(final byte[] img) {
        this.setImagenBinario(img);
    }

    public AlumnoImagen(final Long id) {
        super(id);
    }

    public Alumno getAlumno() {
        return this.alumno;
    }

    public void setAlumno(final Alumno alumno) {
        this.alumno = alumno;
    }

    @Override
    public Long getParentId() {
        return this.getEntityId(this.alumno);
    }

    @Override
    public String getParent() {
        return "alumno";
    }

    @Override
    public void setParent(final Alumno parent) {
        this.setAlumno(parent);
    }
}
