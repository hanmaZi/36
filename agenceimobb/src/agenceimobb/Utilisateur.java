package agenceimobb;

import java.util.ArrayList;
import java.util.List;


public class Utilisateur {
    private String nomUtilisateur;
    private String motDePasse;
    private String role;
    private String nom;
    private String prenom;
    private String adresse;
    private String email;
    private String telephone;
    private List<BienImmobilier> biensAVendre;
   

    public Utilisateur(String nomUtilisateur, String motDePasse, String role, String nom, String prenom, String adresse, String email, String telephone) {
        this.nomUtilisateur = nomUtilisateur;
        this.motDePasse = motDePasse;
        this.role = role;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.email = email;
        this.telephone = telephone;
        this.biensAVendre = new ArrayList<>(); // Initialisation de la liste de biens à vendre
        
    }
    
    public String getNomUtilisateur() {
        return nomUtilisateur;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public String getRole() {
        return role;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getEmail() {
        return email;
    }

    public String getTelephone() {
        return telephone;
    }

    // Méthode pour ajouter un bien à vendre à la liste des biens de l'utilisateur
    public void ajouterBienAVendre(BienImmobilier bien) {
        biensAVendre.add(bien);
    }

    // Méthode pour récupérer la liste des biens à vendre de l'utilisateur
    public List<BienImmobilier> getBiensAVendre() {
        return biensAVendre;
    }
    public void supprimerBienAVendre(BienImmobilier bien) {
        biensAVendre.remove(bien);
    }
 


}

