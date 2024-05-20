package application;

public class medicament {
	private int id;
    private String nom;
    private double quantite;
    private double prix;

     public medicament() {}
    public medicament(int id, String nom, double quantite, double prix) {
        this.id = id;
        this.nom = nom;
        this.quantite = quantite;
        this.prix = prix;
    }

    // Getter pour id
    public int getId() {
        return id;
    }

    // Setter pour id
    public void setId(int id) {
        this.id = id;
    }

    // Getter pour nom
    public String getNom() {
        return nom;
    }

    // Setter pour nom
    public void setNom(String nom) {
        this.nom = nom;
    }

    // Getter pour quantite
    public double getQuantite() {
        return quantite;
    }

    // Setter pour quantite
    public void setQuantite(double quantite) {
        this.quantite = quantite;
    }

    // Getter pour prix
    public double getPrix() {
        return prix;
    }

    // Setter pour prix
    public void setPrix(double prix) {
        this.prix = prix;
    }

    @Override
    public String toString() {
        return "Medicament{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", quantite=" + quantite +
                ", prix=" + prix +
                '}';
    }
}


