package xmldb.model;

import xmldb.annotation.Attribute;

/**
 *
 * @author giacomo
 */
public class Cellulare extends Dettaglio {

    @Attribute
    private String tipo = TYPE_CELLULARE;

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
