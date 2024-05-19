package agenceimobb;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.time.LocalDate;

class Client {
    private static List<Client> clients = new ArrayList<>();

    private String id;
    private String nom;
    private String prenom;
    private String adresse;
    private String email;
    private String telephone;
    private String typeClient;
    private double budget;
    private LocalDate dateNaissance;
    private String profession;

    // Getters et setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getTypeClient() {
        return typeClient;
    }

    public void setTypeClient(String typeClient) {
        this.typeClient = typeClient;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public Client(String nom, String prenom, String adresse, String email, String telephone,
                  String typeClient, double budget, LocalDate dateNaissance, String profession) {
        this.id = UUID.randomUUID().toString(); // Générer un identifiant unique
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.email = email;
        this.telephone = telephone;
        this.typeClient = typeClient;
        this.budget = budget;
        this.dateNaissance = dateNaissance;
        this.profession = profession;

        // Ajouter le nouveau client à la liste des clients
        clients.add(this);
    }

    // Méthode pour obtenir la liste des clients
    public static List<Client> getClients() {
        return clients;
    }
}
