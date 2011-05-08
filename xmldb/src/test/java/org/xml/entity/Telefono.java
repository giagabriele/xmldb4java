package org.xml.entity;


/**
 *
 * @author giacomo
 */
public class Telefono extends Dettaglio {

    private String tipo = TYPE_TELEFONO;

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
