package agenceimobb;

import java.util.ArrayList;
import java.util.List;

public class AgenceImmobilier {

    private List<BienImmobilier> biensImmobiliers;
    private List<Client> clients;
    
    private List<RendezVous> rendezVous;

    public AgenceImmobilier() {
        this.biensImmobiliers = new ArrayList<>();
        this.clients = new ArrayList<>();
        
        this.rendezVous = new ArrayList<>();
    }

    // Méthodes pour la gestion des biens immobiliers
    public void ajouterBienImmobilier(BienImmobilier bien) {
        biensImmobiliers.add(bien);
    }

    public void modifierBienImmobilier(String id, BienImmobilier nouveauBien) {
        for (BienImmobilier bien : biensImmobiliers) {
            if (bien.getId().equals(id)) {
                // Modifier les attributs du bien immobilier
                bien.setType(nouveauBien.getType());
                bien.setPrix(nouveauBien.getPrix());
                bien.setSurface(nouveauBien.getSurface());
                // Ajouter d'autres attributs à modifier selon les besoins
                break;
            }
        }
    }

    public void supprimerBienImmobilier(String id) {
        biensImmobiliers.removeIf(bien -> bien.getId().equals(id));
    }

    // Méthodes pour la gestion des clients
    public void ajouterClient(Client client) {
        clients.add(client);
    }

    public void modifierClient(String id, Client nouveauClient) {
        for (Client client : clients) {
            if (client.getId().equals(id)) {
                // Modifier les attributs du client
                client.setNom(nouveauClient.getNom());
                client.setPrenom(nouveauClient.getPrenom());
                // Ajouter d'autres attributs à modifier selon les besoins
                break;
            }
        }
    }

    public void supprimerClient(String id) {
        clients.removeIf(client -> client.getId().equals(id));
    }

    // Méthodes pour la gestion des transactions


    // Méthodes pour la gestion des rendez-vous
    public void planifierRendezVous(RendezVous rendezVous) {
        this.rendezVous.add(rendezVous);
    }

    public void annulerRendezVous(String id) {
        rendezVous.removeIf(rdv -> rdv.getId().equals(id));
    }

  
}
