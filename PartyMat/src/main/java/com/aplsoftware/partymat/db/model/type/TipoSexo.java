package com.aplsoftware.partymat.db.model.type;

import com.apl.components.misc.Misc;


public enum TipoSexo {

    MASCULINO ("M"),
    FEMENINO  ("F"),
    INDEFINIDO(null);

    private String tipo;

    private TipoSexo(final String tipo) {
        this.tipo = tipo;
    }

    public String toValue() {
        return this.tipo;
    }

    @Override
    public String toString() {

        if (this.tipo == null) {
            return "";
        }

        return Misc.getComponentBundle("TipoSexo." + this.name());
    }

    public static TipoSexo fromValue(final String tipo) {

        if (tipo != null) {

            for (final TipoSexo frm : TipoSexo.values()) {

                if (frm.toValue().equals(tipo)) {
                    return frm;
                }
            }
        }

        return null;
    }

    public static TipoSexo defaultValue() {
        return null;
    }
}
