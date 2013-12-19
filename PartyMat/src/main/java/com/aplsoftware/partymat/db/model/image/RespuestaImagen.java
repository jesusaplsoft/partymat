package com.aplsoftware.partymat.db.model.image;

import com.aplsoftware.partymat.db.model.Respuesta;

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
@Table(name = "RES_IMAGEN")
@org.hibernate.annotations.Table(appliesTo = "RES_IMAGEN", comment = "Imagen")
public class RespuestaImagen
    extends Imagen<RespuestaImagen> {
    private static final long serialVersionUID = 791425992196808673L;

    @ForeignKey(name = "RES_IMG_FK_ALUMNO")
    @JoinColumn(name = "RESPUESTA_ID", nullable = false)
    @NotNull
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToOne
    private Respuesta respuesta;

    public RespuestaImagen() {
        super();
    }

    public RespuestaImagen(final byte[] img) {
        super(img);
    }

    public RespuestaImagen(final ImageIcon imagen, final ImageIcon original) {
        super(imagen, original);
    }

    public RespuestaImagen(final Long id) {
        super(id);
    }

    public RespuestaImagen(final RespuestaImagen img) {
        super(img);
    }

    public Respuesta getRespuesta() {
        return this.respuesta;
    }

    public void setRespuesta(final Respuesta respuesta) {
        this.respuesta = respuesta;
    }

    @Override
    public Long getParentId() {
        return this.getEntityId(this.respuesta);
    }

    @Override
    public String getParent() {
        return "respuesta";
    }
}
