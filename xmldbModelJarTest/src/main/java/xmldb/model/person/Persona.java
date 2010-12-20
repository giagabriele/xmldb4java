package xmldb.model.person;

import xmldb.annotation.Attribute;
import xmldb.annotation.Entity;
import xmldb.annotation.ID;
import xmldb.annotation.ManyToOne;
import xmldb.model.Dettaglio;

/**
 *
 * @author GGabriele
 */
@Entity(name = "person")
public class Persona {

    @ID
    private int id;
    @Attribute
    private int anni;
    @Attribute(tipo = Attribute.TIPO.ELEMENT)
    private String nome;
    @Attribute(tipo = Attribute.TIPO.ELEMENT)
    private String cognome;
    @ManyToOne
    private Dettaglio dettaglio;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Dettaglio getDettaglio() {
        return dettaglio;
    }

    public void setDettaglio(Dettaglio dettaglio) {
        this.dettaglio = dettaglio;
    }

    public int getAnni() {
        return anni;
    }

    public void setAnni(int anni) {
        this.anni = anni;
    }
}
