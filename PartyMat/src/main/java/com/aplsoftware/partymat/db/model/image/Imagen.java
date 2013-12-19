package com.aplsoftware.partymat.db.model.image;

import com.apl.base.exception.InfrastructureException;
import com.apl.base.model.AbstractPersistent;
import com.apl.base.tool.MiscBase;

import java.io.IOException;

import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;

import javax.swing.ImageIcon;


@MappedSuperclass
public abstract class Imagen<I extends Imagen<I>>
    extends AbstractPersistent<I> {

    private static final long serialVersionUID = 855052768561469236L;

    private static final String[] EQUAL_FIELDS = { "parentId" };

    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "genImagenId")
    @Id
    @javax.persistence.SequenceGenerator(name = "genImagenId",
        sequenceName = "IMG_GEN_ID")
    private Long id;

    @Column(name = "IMAGEN", nullable = false)
    @Lob
    private byte[] imagen;

    @Column(name = "ORIGINAL", nullable = false)
    @Lob
    private byte[] original;

    public Imagen() {
        super();
    }

    public Imagen(final I img) {
        super(img);
    }

    public Imagen(final ImageIcon imagen) {
        this.setImagen(imagen);
    }

    public Imagen(final ImageIcon imagen, final ImageIcon original) {
        this.setImagen(imagen);
        this.setOriginal(original);
    }

    public Imagen(final byte[] img) {
        this.setImagenBinario(img);
    }

    public Imagen(final Long id) {
        super(id);
    }

    public byte[] getOriginalBinario() {
        return this.original;
    }

    public void setOriginalBinario(final byte[] original) {
        this.original = original;
    }

    public void guardarCopia() {

        if (this.imagen == null) {
            this.original = null;
        } else {
            this.original = Arrays.copyOf(this.imagen, this.imagen.length);
        }
    }

    public void restaurar() {

        if (this.original == null) {
            this.imagen = null;
        } else {
            this.imagen = Arrays.copyOf(this.original, this.original.length);
        }
    }

    // /////////////////////////////
    // campos Persistentes
    // /////////////////////////////

    /**
     * Obtiene el id.
     *
     * @return  el id
     */
    @Override
    public Long getId() {
        return this.id;
    }

    /**
     * Pone el id.
     *
     * @param  id  el nuevo id
     */
    @Override
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Obtiene el id.
     *
     * @return  el id
     */
    protected Long setId() {
        return this.id;
    }

    // ***************************************************************************

    /**
     * Obtiene el logo del Centro.
     *
     * @return  imagen
     */
    public ImageIcon getImagen() {

        if (this.imagen == null) {
            return null;
        } else {
            return new ImageIcon(this.imagen);
        }
    }

    public final void setImagen(final ImageIcon imagen) {
        final String descripcion;

        if (imagen == null) {
            this.imagen = null;
        } else {
            descripcion = imagen.getDescription();

            /*Si existe descripción entronces la imagen es nueva, si no la
             * tiene, es la misma imagen que *estaba guardada previamente
             */
            if (descripcion != null) {

                try {
                    this.imagen = MiscBase.image2Array(imagen.getImage(),
                            descripcion.substring(
                                descripcion.lastIndexOf(".") + 1));
                } catch (final IOException e) {
                    this.imagen = null;
                    throw new InfrastructureException(e);
                }
            }
        }
    }

    public ImageIcon getOriginal() {

        if (this.original == null) {
            return null;
        } else {
            return new ImageIcon(this.original);
        }
    }

    public final void setOriginal(final ImageIcon imagen) {

        if (imagen == null) {
            this.original = null;
        } else {
            imagen.getDescription();

            try {
                this.original = MiscBase.image2Array(imagen.getImage(),
                        "");
            } catch (final IOException e) {
                this.original = null;
                throw new InfrastructureException(e);
            }
        }
    }

    public void setImagenBinario(final byte[] imagen) {
        this.imagen = imagen;
    }

    public byte[] getImagenBinario() {
        return this.imagen;
    }

    protected abstract Long getParentId();

    @Override
    protected String[] equalizer() {
        return Imagen.EQUAL_FIELDS;
    }

    @Override
    public boolean canEqual(final Object other) {
        return (other instanceof Imagen);
    }

}
