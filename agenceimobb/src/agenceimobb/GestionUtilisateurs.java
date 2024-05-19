package agenceimobb;

import java.util.ArrayList;
import java.util.List;

public class GestionUtilisateurs {
    private List<Utilisateur> utilisateurs;
    private Utilisateur agentConnecte;
    public GestionUtilisateurs() {
        utilisateurs = new ArrayList<>();
    }

    public List<Utilisateur> getUtilisateurs() {
        return utilisateurs;
    }

    public void ajouterUtilisateur(Utilisateur utilisateur) {
        utilisateurs.add(utilisateur);
    }

    // Add method to authenticate agent
    public Utilisateur authentifierAgent(String nomUtilisateur, String motDePasse) {
        for (Utilisateur utilisateur : utilisateurs) {
            if (utilisateur.getNomUtilisateur().equals(nomUtilisateur) && utilisateur.getMotDePasse().equals(motDePasse) && utilisateur.getRole().equals("agent_immobilier")) {
            	agentConnecte = utilisateur;
            	return utilisateur;
            }
        }
        return null;
    }

    // Add method to get role agent
    public static String getRoleAgent() {
        return "agent_immobilier";
    }
    public static String getRoleClient() {
        return "Client";
    }
    public void ajouterClient(Utilisateur utilisateur) {
        utilisateurs.add(utilisateur);
    }
    public Utilisateur getAgentConnecte() {
        return agentConnecte;
    }
   
 
}