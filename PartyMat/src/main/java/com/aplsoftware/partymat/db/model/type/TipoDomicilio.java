package com.aplsoftware.partymat.db.model.type;

public enum TipoDomicilio {

    CURSO     ("C"),
    FAMILIAR  ("F"),
    NACIMIENTO("N");

    private String tipo;

    private TipoDomicilio(final String tipo) {
        this.tipo = tipo;
    }

    public String toValue() {
        return this.tipo;
    }

    public static TipoDomicilio fromValue(final String tipo) {

        for (final TipoDomicilio frm : TipoDomicilio.values()) {

            if (frm.toValue().equals(tipo)) {
                return frm;
            }
        }

        return null;
    }
}
