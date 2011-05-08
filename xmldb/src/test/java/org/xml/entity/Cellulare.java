package org.xml.entity;



/**
 *
 * @author giacomo
 */
public class Cellulare extends Dettaglio {


    private String tipo = TYPE_CELLULARE;


    private Contatto contatto;

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Contatto getContatto() {
        return contatto;
    }

    public void setContatto(Contatto contatto) {
        this.contatto = contatto;
    }
}
