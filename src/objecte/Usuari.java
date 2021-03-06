package objecte;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class Usuari {
    //<editor-fold desc="Atributs">
    private SimpleIntegerProperty id;
    private SimpleStringProperty nom;
    private SimpleStringProperty pCognom;
    private SimpleStringProperty sCognom;
    private SimpleStringProperty telefonMobil;
    private SimpleStringProperty telefonFixe;
    private SimpleStringProperty email;
    private String contrasenya;
    private SimpleBooleanProperty actiu;
    private String salt;
    private SimpleBooleanProperty admin;
    private SimpleObjectProperty <Nivell> nivell;
    //</editor-fold>
    //<editor-fold desc="Contructors">
    /**
     * Constructor per defecte
     */

    public Usuari(){
        this.id= new SimpleIntegerProperty(-1);
        this.nom = new SimpleStringProperty("");
        this.pCognom = new SimpleStringProperty("");
        this.sCognom = new SimpleStringProperty("");
        this.telefonMobil = new SimpleStringProperty("");
        this.telefonFixe = new SimpleStringProperty("");
        this.email = new SimpleStringProperty("");
        this.contrasenya = "";
        this.actiu = new SimpleBooleanProperty(true);
        this.salt = "";
        this.admin = new SimpleBooleanProperty(false);
        this.nivell = new SimpleObjectProperty<>(new Nivell());
    }

    /**
     * Contructor amb els atributs
     * @param nom
     * @param pcognom
     * @param scognom
     * @param telefon_mobil
     * @param telefon_fixe
     * @param email
     * @param contrasenya
     * @param estat
     * @param salt
     * @param esAdmin
     * @param nivell 
     * @param id
     */

    public Usuari(int id,String nom, String pcognom, String scognom,
        String telefon_mobil, String telefon_fixe,String email,  
        String contrasenya, boolean estat, String salt, boolean esAdmin,
        Nivell nivell){
        this.id=new SimpleIntegerProperty(id);
        this.nom = new SimpleStringProperty(nom);
        this.pCognom = new SimpleStringProperty(pcognom);
        this.sCognom = new SimpleStringProperty(scognom);
        this.telefonMobil = new SimpleStringProperty(telefon_mobil);
        this.telefonFixe = new SimpleStringProperty(telefon_fixe);
        this.email = new SimpleStringProperty(email);
        this.contrasenya = contrasenya;
        this.actiu = new SimpleBooleanProperty(estat);
        this.salt = salt;
        this.admin = new SimpleBooleanProperty(esAdmin);
        this.nivell = new SimpleObjectProperty<>(nivell);
    }
    //</editor-fold>
    //<editor-fold desc="Getters Attributs">
    
    /**
     * Getter id
     * @return id
     */
    public int getId() { return id.get(); }
    
    /**
     * Getter nom
     * @return nom
     */
    public String getNom() { return nom.get(); }
    
    /**
     * Getter pCognom
     * @return pCognom
     */
    public String getPcognom() { return pCognom.get(); }
    
    /**
     * Getter sCognom
     * @return sCognom
     */
    public String getScognom() { return sCognom.get(); }
    
    /**
     * Getter telefonMobil
     * @return telefonMobil
     */
    public String getTelefonMobil() { return telefonMobil.get(); }
    
    /**
     * Getter telefonFixe
     * @return telefonFixe
     */
    public String getTelefonFixe() { return telefonFixe.get(); }
    
    /**
     * Getter email
     * @return email
     */
    public String getEmail() { return email.get(); }
    
    /**
     * Getter contrasenya
     * @return contrasenya
     */
    public String getContrasenya() { return this.contrasenya; }
    
    /**
     * Getter actiu 
     * @return actiu
     */
    public boolean isActiu() { return actiu.get(); }
    
    /**
     * Getter salt
     * @return salt
     */
    public String getSalt() { return this.salt; }
    
    /**
     * Getter admin
     * @return admin
     */
    public boolean isAdmin() { return admin.get(); }
    
    /**
     * Getter nivell
     * @return nivell
     */
    public Nivell getNivell() { return nivell.get(); }
    //</editor-fold>
    //<editor-fold desc="Setters Attributs">
    
    /**
     * Setter id
     * @param id  
     */
    public void setId(int id) { this.id.set(id); }
    
    /**
     * Setter nom
     * @param nom 
     */
    public void setNom(String nom) { this.nom.set(nom); }
    
    /**
     * Setter pCognom
     * @param pCognom 
     */
    public void setPcognom(String pCognom) { this.pCognom.set(pCognom); }
    
    /**
     * Setter sCognom
     * @param sCognom 
     */
    public void setScognom(String sCognom) { this.sCognom.set(sCognom); }
    
    /**
     * Setter telefonMobil
     * @param telefonMobil 
     */
    public void setTelefonMobil(String telefonMobil) { this.telefonMobil.set(telefonMobil); }
    
    /**
     * Setter telefonFixe
     * @param telefonFixe 
     */
    public void setTelefonFixe(String telefonFixe) { this.telefonFixe.set(telefonFixe); }
    
    /**
     * Setter email
     * @param email 
     */
    public void setEmail(String email) { this.email.set(email); }
    
    /**
     * Setter contrasenya
     * @param contrasenya 
     */
    public void setContrasenya(String contrasenya) { this.contrasenya=contrasenya; }
    
    /**
     * Setter actiu
     * @param actiu 
     */
    public void setActiu(boolean actiu) { this.actiu.set(actiu); }
    
    /**
     * Setter salt
     * @param salt 
     */
    public void setSalt(String salt) { this.salt=salt; }
    
    /**
     * Setter admin
     * @param isAdmin 
     */
    public void setAdmin(boolean isAdmin) { this.admin.set(isAdmin); }
    
    /**
     * Setter nivell
     * @param nivell 
     */
    public void setNivell(Nivell nivell) { this.nivell.set(nivell); }
    //</editor-fold>
    //<editor-fold desc="Getters Properties">

    public SimpleIntegerProperty idProperty() { return id; }
    public SimpleStringProperty nomProperty() { return nom; }
    public SimpleStringProperty pCognomProperty() { return pCognom; }
    public SimpleStringProperty sCognomProperty() { return sCognom; }
    public SimpleStringProperty telefonMobilProperty() { return telefonMobil; }
    public SimpleStringProperty telefonFixeProperty() { return telefonFixe; }
    public SimpleStringProperty emailProperty() { return email; }
    public SimpleBooleanProperty actiuProperty() { return actiu; }
    public SimpleBooleanProperty adminProperty() { return admin; }
    public SimpleObjectProperty<Nivell> nivellProperty() { return nivell; }
    
    //</editor-fold>
    
    /**
     * Funció toString
     * @return Nom PrimerCognom SegonCognom del usuari
     */
    @Override 
    public String toString(){
        return this.getNom()+" "+this.getPcognom()+" "+this.getScognom();
    }
}
