package xmldb.model;

import xmldb.annotation.Attribute;
import xmldb.annotation.ManyToOne;

/**
 *
 * @author giacomo
 */
public class Cellulare extends Dettaglio {

    @Attribute
    private String tipo = TYPE_CELLULARE;

    @ManyToOne
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
