package com.aplsoftware.partymat.db.model.type;

import com.apl.components.misc.Misc;


public enum TipoPregunta {

    NINGUNO   (null),
    TEST      ("T"),
    PAREJAS   ("P"),
    RELLENAR  ("R"),
    DESARROLLO("D");

    private String tipo;

    private TipoPregunta(final String tipo) {
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

        return Misc.getBundle("TipoPregunta." + this.name());
    }

    public static TipoPregunta fromValue(final String tipo) {

        for (final TipoPregunta frm : TipoPregunta.values()) {

            if ((frm.toValue() != null) && frm.toValue().equals(tipo)) {
                return frm;
            }
        }

        return null;
    }

    public static TipoPregunta[] validValues() {
        // return new TipoPregunta[] { TEST, PAREJAS, RELLENAR, DESARROLLO };
        return new TipoPregunta[] { TEST, DESARROLLO };
    }

    public static TipoPregunta defaultValue() {
        return TEST;
    }
}
