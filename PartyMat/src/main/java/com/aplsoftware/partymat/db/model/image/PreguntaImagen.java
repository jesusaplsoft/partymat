package com.aplsoftware.partymat.db.model.image;

import com.aplsoftware.partymat.db.model.Pregunta;

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
@Table(name = "PRE_IMAGEN")
@org.hibernate.annotations.Table(appliesTo = "PRE_IMAGEN", comment = "Imagen")
public class PreguntaImagen
    extends Imagen<PreguntaImagen> {
    private static final long serialVersionUID = 791425992196808673L;

    @ForeignKey(name = "PRE_IMG_FK_ALUMNO")
    @JoinColumn(name = "PREGUNTA_ID", nullable = false)
    @NotNull
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToOne
    private Pregunta pregunta;

    public PreguntaImagen() {
        super();
    }

    public PreguntaImagen(final byte[] img) {
        super(img);
    }

    public PreguntaImagen(final ImageIcon imagen, final ImageIcon original) {
        super(imagen, original);
    }

    public PreguntaImagen(final Long id) {
        super(id);
    }

    public PreguntaImagen(final PreguntaImagen img) {
        super(img);
    }

    public Pregunta getPregunta() {
        return this.pregunta;
    }

    public void setPregunta(final Pregunta pregunta) {
        this.pregunta = pregunta;
    }

    @Override
    public Long getParentId() {
        return this.getEntityId(this.pregunta);
    }

    @Override
    public String getParent() {
        return "pregunta";
    }
}
