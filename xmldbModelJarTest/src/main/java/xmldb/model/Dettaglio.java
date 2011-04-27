package xmldb.model;

import org.xmldb.annotation.Attribute;
import org.xmldb.annotation.Entity;
import org.xmldb.annotation.ID;

/**
 *
 * @author giacomo
 */
@Entity(name = "dettaglio")
public class Dettaglio {

    public static final String TYPE_CELLULARE = "cellulare";
    public static final String TYPE_TELEFONO = "telefono";
    public static final String TYPE_MAIL = "email";
    @ID
    private int id;
    @Attribute
    private String dettaglio;

    public String getDettaglio() {
        return dettaglio;
    }

    public void setDettaglio(String dettaglio) {
        this.dettaglio = dettaglio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
