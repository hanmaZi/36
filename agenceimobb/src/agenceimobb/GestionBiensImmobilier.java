package agenceimobb;

import java.util.ArrayList;
import java.util.List;

public class GestionBiensImmobilier {
    private List<BienImmobilier> biensImmobilier;

    public GestionBiensImmobilier() {
        this.biensImmobilier = new ArrayList<>();
    }

    // Méthode pour ajouter un bien immobilier
    public void ajouterBienImmobilier(BienImmobilier bien) {
        biensImmobilier.add(bien);
    }

    // Méthode pour modifier un bien immobilier
    public void modifierBienImmobilier(BienImmobilier ancienBien, BienImmobilier nouveauBien) {
        if (biensImmobilier.contains(ancienBien)) {
            biensImmobilier.remove(ancienBien);
            biensImmobilier.add(nouveauBien);
        } else {
            System.out.println("Le bien immobilier à modifier n'existe pas.");
        }
    }

    // Méthode pour supprimer un bien immobilier
    public void supprimerBienImmobilier(BienImmobilier bien) {
        biensImmobilier.remove(bien);
    }

    // Méthode pour rechercher des biens immobiliers par critères
    public List<BienImmobilier> rechercherBiensImmobilier(String critere) {
        List<BienImmobilier> resultatsRecherche = new ArrayList<>();
        for (BienImmobilier bien : biensImmobilier) {
            // Vous pouvez ajouter ici la logique de recherche basée sur les critères spécifiés
            // Par exemple, recherche par type, localisation, prix, etc.
            // Ici, je vais simplement vérifier si la description contient le critère spécifié
            if (bien.getDescription().toLowerCase().contains(critere.toLowerCase())) {
                resultatsRecherche.add(bien);
            }
        }
        return resultatsRecherche;
    }

    // Méthode pour affecter un bien à un agent immobilier
    public void affecterBienAAgentImmobilier(BienImmobilier bien, Utilisateur agent) {
        if (biensImmobilier.contains(bien)) {
            agent.ajouterBienAVendre(bien);
            System.out.println("Le bien immobilier a été affecté à l'agent immobilier avec succès.");
        } else {
            System.out.println("Le bien immobilier à affecter n'existe pas.");
        }
    }
}
