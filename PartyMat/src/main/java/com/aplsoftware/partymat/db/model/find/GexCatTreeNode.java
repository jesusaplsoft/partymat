package com.gexcat.gex.model.find;

import com.apl.base.tool.MiscBase;

import com.apl.components.tree.AbstractIconTreeNode;

import com.gexcat.gex.model.type.Nodo;


public class GexCatTreeNode
    extends AbstractIconTreeNode<Nodo> {

    private static final long serialVersionUID = -5404582671047085268L;

    private String tipo;
    private String descripcion;
    private Object orden;
    private Long id;
    private Long parentId;

    private boolean ajustando;

    public GexCatTreeNode() {
        this(null, null, null, null, null, null);
    }

    public GexCatTreeNode(final Nodo tipo, final Long id) {
        this(null, null, tipo, id, null, null);
    }

    public GexCatTreeNode(final String nombre,
            final String descripcion,
            final Nodo tipo,
            final Long id,
            final Long parentId) {
        this(nombre, descripcion, tipo, id, parentId, null);
    }

    public GexCatTreeNode(final String nombre,
            final String descripcion,
            final Nodo tipo,
            final Long id,
            final Long parentId,
            final Object orden) {
        setText(nombre);
        setDescripcion(descripcion);
        setType(tipo);
        setId(id);
        setParentId(parentId);
        setOrden(orden);
    }

    @Override
    public GexCatTreeNode clone() {
        final GexCatTreeNode nodo = new GexCatTreeNode();
        nodo.setText(getText());
        nodo.setDescripcion(getDescripcion());
        nodo.setType(getType());
        nodo.setId(getId());
        nodo.setParentId(getParentId());
        nodo.setOrden(getOrden());
        return nodo;
    }

    public Object getOrden() {
        return orden;
    }

    public void setOrden(final Object orden) {
        this.orden = orden;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(final String tipo) {
        this.tipo = tipo;
    }

    public Long getId() {
        return id;
    }

    public final void setId(final Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public final void setParentId(final Long parentId) {
        this.parentId = parentId;
    }

    public boolean isAjustando() {
        return ajustando;
    }

    public void setAjustando(final boolean ajustando) {
        this.ajustando = ajustando;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public final void setDescripcion(final String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public GexCatTreeNode getParent() {
        return (GexCatTreeNode) super.getParent();
    }

    @Override
    public Object[] getNodeId() {
        return new Object[] { getType(), getId() };
    }

    @Override
    public Object[] getParentNodeId() {
        return new Object[] { getType().getPadre(), getParentId() };
    }

    @Override
    public int compareTo(final AbstractIconTreeNode<Nodo> obj) {
        final int resultado;

        if (orden != null) {
            resultado = MiscBase.compareObject(orden,
                    ((GexCatTreeNode) obj).orden);

            if (resultado != 0) {
                return resultado;
            }
        }

        return super.compareTo(obj);
    }
}
