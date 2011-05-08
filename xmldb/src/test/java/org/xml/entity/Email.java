package org.xml.entity;


/**
 *
 * @author giacomo
 */
public class Email extends Dettaglio {

    private String tipo = TYPE_MAIL;

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
