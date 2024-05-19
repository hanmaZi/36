package agenceimobb;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;


public class ApplicationGUI extends JFrame {
    private GestionUtilisateurs gestionUtilisateurs;
    private JTextField textFieldNomUtilisateur;
    private JPasswordField passwordField;
    private JTextArea textAreaAffichage;
    private JList<String> agentList;
    private DefaultListModel<String> agentListModel;
    private JButton selectAgentButton;


    public ApplicationGUI() {
        super("Agence Immobilière");
        

        gestionUtilisateurs = new GestionUtilisateurs();
        creerAgentsImmobiliersPreEnregistres();

        // Création des composants
        textFieldNomUtilisateur = new JTextField(20);
        passwordField = new JPasswordField(20);
        textAreaAffichage = new JTextArea(15, 50);
        textAreaAffichage.setEditable(false);

        // Création des panneaux
        JPanel panelConnexion = new JPanel(new GridLayout(0, 2, 5, 5)); // GridLayout avec 2 colonnes et espacement de 5 pixels
        JPanel panelBouton = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Layout pour le bouton au centre

        // Ajout des composants au panneau de connexion
        panelConnexion.add(new JLabel("Nom d'utilisateur :"));
        panelConnexion.add(textFieldNomUtilisateur);
        panelConnexion.add(new JLabel("Mot de passe :"));
        panelConnexion.add(passwordField);

        // Ajout des panneaux à la fenêtre principale
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(panelConnexion, BorderLayout.NORTH);
        getContentPane().add(new JScrollPane(textAreaAffichage), BorderLayout.CENTER);
        getContentPane().add(panelBouton, BorderLayout.SOUTH);

        // Ajout du bouton de connexion au panneau de bouton
        JButton btnConnexion = new JButton("Se connecter");
        panelBouton.add(btnConnexion);

        // Ajout d'un écouteur d'événements au bouton de connexion
        btnConnexion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                seConnecter();
            }
        });
        JButton btnConnexionClient = new JButton("Se connecter en tant que client");
        panelBouton.add(btnConnexionClient);
        btnConnexionClient.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                seConnecterClient();

            }
        });
        // Configuration de la fenêtre
        pack(); // Redimensionne la fenêtre pour s'adapter à son contenu
        setLocationRelativeTo(null); // Centre la fenêtre sur l'écran
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);}
        // Initialisation des composants graphiques
        

    private int tentativesEchouees = 0;

    private void seConnecter() {
        String nomUtilisateur = textFieldNomUtilisateur.getText();
        String motDePasse = new String(passwordField.getPassword());

        if (nomUtilisateur.isEmpty() || motDePasse.isEmpty()) {
            afficherMessage("Veuillez entrer un nom d'utilisateur et un mot de passe.");
            return;
        }

        Utilisateur utilisateur = gestionUtilisateurs.authentifierAgent(nomUtilisateur, motDePasse);
        if (utilisateur != null) {
            afficherMessage("Connexion réussie en tant qu'agent immobilier : " + utilisateur.getNom());
            afficherOptionsAgent();
            afficherListeRendezVous(); // Appel de la méthode pour afficher les rendez-vous
        } else {
            // Incrémenter le compteur de tentatives infructueuses
            tentativesEchouees++;

            // Vérifier si le nombre de tentatives infructueuses atteint trois
            if (tentativesEchouees == 3) {
                afficherMessage("Nombre maximal de tentatives atteint. Fermeture de l'application.");
                // Fermer l'application
                System.exit(0);
            } else {
                afficherMessage("Nom d'utilisateur ou mot de passe incorrect. Tentatives restantes : " + (3 - tentativesEchouees));
            }
        }
    }

    private void afficherOptionsAgent() {
        textAreaAffichage.setText(""); // Effacer le contenu précédent
        textAreaAffichage.append("Options pour l'agent immobilier :\n");
        textAreaAffichage.append("1. Ajouter un bien immobilier\n");
        textAreaAffichage.append("2. Modifier un bien immobilier\n");
        textAreaAffichage.append("3. Supprimer un bien immobilier\n");
        textAreaAffichage.append("4. Afficher tous les biens immobiliers\n");
        textAreaAffichage.append("5. Afficher la liste des rendez-vous\n");

        // L'utilisateur choisit une option
        int choix = obtenirChoixUtilisateur();

        // Exécuter l'action associée au choix de l'utilisateur
        switch (choix) {
            case 1:
                ajouterBienImmobilier();
                afficherOptionsApresAction();
                break;
            case 2:
                modifierBienImmobilier();
                afficherOptionsApresAction();
                break;
            case 3:
                supprimerBienImmobilier();
                afficherOptionsApresAction();
                break;
            case 4:
                afficherTousBiensImmobilier();
                afficherOptionsApresAction();
                break;
            case 5:
                afficherListeRendezVous();
                afficherOptionsApresAction();
                break;
            default:
                System.out.println("Choix invalide !");
        }
    }

    private int obtenirChoixUtilisateur() {
        Object[] options = {"Ajouter un bien immobilier", "Modifier un bien immobilier", "Supprimer un bien immobilier", "Afficher tous les biens immobiliers", "Afficher la liste des rendez-vous"};
        int choix = JOptionPane.showOptionDialog(this, "Choisissez une option :", "Options pour l'agent immobilier", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        
        // Ajouter 1 à l'index du choix car les indices des options commencent à partir de zéro
        return choix + 1;
    }


    private void ajouterBienImmobilier() {
        // Demander à l'utilisateur de saisir les détails du bien immobilier
        String type = JOptionPane.showInputDialog(this, "Entrez le type de bien immobilier :");
        double prix = Double.parseDouble(JOptionPane.showInputDialog(this, "Entrez le prix du bien :"));
        int superficie = Integer.parseInt(JOptionPane.showInputDialog(this, "Entrez la superficie du bien :"));
        String ville = JOptionPane.showInputDialog(this, "Entrez la ville du bien :");
        String description = JOptionPane.showInputDialog(this, "Entrez la description du bien :");
        int nbChambres = Integer.parseInt(JOptionPane.showInputDialog(this, "Entrez le nombre de chambres du bien :"));
        int nbSallesDeBain = Integer.parseInt(JOptionPane.showInputDialog(this, "Entrez le nombre de salles de bain du bien :"));
        int anneeConstruction = Integer.parseInt(JOptionPane.showInputDialog(this, "Entrez l'année de construction du bien :"));
        int nbEtages = Integer.parseInt(JOptionPane.showInputDialog(this, "Entrez le nombre d'étages du bien :"));
        
        // Créer le nouveau bien immobilier
        BienImmobilier bien = new BienImmobilier(type, prix, superficie, ville, description, nbChambres, nbSallesDeBain, anneeConstruction, nbEtages);
        
        // Ajouter le bien immobilier à l'agent connecté
        Utilisateur agentConnecte = gestionUtilisateurs.getAgentConnecte();
        if (agentConnecte != null) {
            agentConnecte.ajouterBienAVendre(bien);
            afficherMessage("Le bien immobilier a été ajouté avec succès.");
            
        } else {
            afficherMessage("Aucun agent connecté.");
        }
    }


    private void modifierBienImmobilier() {
        if (gestionUtilisateurs.getAgentConnecte() != null) {
            Utilisateur agentConnecte = gestionUtilisateurs.getAgentConnecte();
            List<BienImmobilier> listeBiens = agentConnecte.getBiensAVendre();

            if (!listeBiens.isEmpty()) {
                // Création d'une boîte de dialogue pour choisir le bien à modifier
                JFrame frame = new JFrame("Choisir le bien à modifier");
                JPanel panel = new JPanel();
                panel.setLayout(new GridLayout(0, 1));

                // Ajout d'un bouton radio pour chaque bien immobilier
                ButtonGroup buttonGroup = new ButtonGroup();
                for (BienImmobilier bien : listeBiens) {
                    JRadioButton radioButton = new JRadioButton(bien.getDescription());
                    radioButton.setActionCommand(bien.getDescription());
                    panel.add(radioButton);
                    buttonGroup.add(radioButton);
                }

                JButton btnChoisir = new JButton("Choisir");
                btnChoisir.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        // Récupération du bien sélectionné
                        String descriptionBienSelectionne = buttonGroup.getSelection().getActionCommand();
                        BienImmobilier bienAModifier = null;
                        for (BienImmobilier bien : listeBiens) {
                            if (bien.getDescription().equals(descriptionBienSelectionne)) {
                                bienAModifier = bien;
                                break;
                            }
                        }
                        if (bienAModifier != null) {
                            modifierBienImmobilierDialogue(bienAModifier);
                            frame.dispose();
                        } else {
                            afficherMessage("Sélectionnez un bien immobilier.");
                        }
                    }
                });

                panel.add(btnChoisir);
                frame.add(panel);
                frame.setSize(400, 300);
                frame.setVisible(true);
            } else {
                afficherMessage("Aucun bien immobilier à modifier.");
            }
        } else {
            afficherMessage("Aucun agent n'est actuellement connecté.");
        }
    }
    private void modifierBienImmobilierDialogue(BienImmobilier bien) {
        // Création d'une boîte de dialogue pour la modification du bien
        JFrame frame = new JFrame("Modifier le bien immobilier");
        JPanel panel = new JPanel(new GridLayout(0, 2));
        
        // Ajout de champs de texte pour chaque attribut du bien immobilier
        panel.add(new JLabel("Description :"));
        JTextField descriptionField = new JTextField(bien.getDescription());
        panel.add(descriptionField);
        
        panel.add(new JLabel("Prix :"));
        JTextField prixField = new JTextField(String.valueOf(bien.getPrix()));
        panel.add(prixField);
        
        panel.add(new JLabel("Surface :"));
        JTextField surfaceField = new JTextField(String.valueOf(bien.getSurface()));
        panel.add(surfaceField);
        
        panel.add(new JLabel("Localisation :"));
        JTextField localisationField = new JTextField(bien.getLocalisation());
        panel.add(localisationField);
        
  

        
        panel.add(new JLabel("Année de construction :"));
        JTextField anneeConstructionField = new JTextField(String.valueOf(bien.getAnneeConstruction()));
        panel.add(anneeConstructionField);
        
        panel.add(new JLabel("Étage :"));
        JTextField etageField = new JTextField(String.valueOf(bien.getEtage()));
        panel.add(etageField);
        
        // Ajout d'un bouton pour valider la modification
        JButton btnValider = new JButton("Valider");
        btnValider.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Mettre à jour les attributs du bien immobilier avec les nouvelles valeurs
                bien.setDescription(descriptionField.getText());
                bien.setPrix(Double.parseDouble(prixField.getText()));
                bien.setSurface(Double.parseDouble(surfaceField.getText()));
                bien.setLocalisation(localisationField.getText());
               
                bien.setAnneeConstruction(Integer.parseInt(anneeConstructionField.getText()));
                bien.setEtage(Integer.parseInt(etageField.getText()));
                
                // Afficher un message de confirmation
                afficherMessage("Le bien immobilier a été modifié avec succès.");
                frame.dispose(); // Fermer la boîte de dialogue
                
            }
        });
        panel.add(btnValider);
        
        // Ajout d'un bouton pour annuler la modification
        JButton btnAnnuler = new JButton("Annuler");
        btnAnnuler.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Fermer la boîte de dialogue sans effectuer de modification
            }
        });
        panel.add(btnAnnuler);
        
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }

    private void supprimerBienImmobilier() {
        // Vérifiez si un agent immobilier est connecté
        Utilisateur agentConnecte = gestionUtilisateurs.getAgentConnecte();
        if (agentConnecte != null) {
            // Récupérez la liste des biens immobiliers de l'agent connecté
            List<BienImmobilier> listeBiens = agentConnecte.getBiensAVendre();
            
            // Vérifiez s'il y a des biens à supprimer
            if (!listeBiens.isEmpty()) {
                // Créez un tableau d'objets pour stocker les noms des biens immobiliers
                Object[] options = listeBiens.stream().map(BienImmobilier::getDescription).toArray();
                
                // Affichez une boîte de dialogue pour que l'utilisateur sélectionne le bien à supprimer
                Object choix = JOptionPane.showInputDialog(this, "Choisissez le bien immobilier à supprimer :",
                        "Supprimer un bien immobilier", JOptionPane.PLAIN_MESSAGE, null, options, null);
                
                // Vérifiez si l'utilisateur a fait un choix
                if (choix != null) {
                    // Parcourez la liste des biens pour trouver celui à supprimer
                    for (BienImmobilier bien : listeBiens) {
                        if (bien.getDescription().equals(choix.toString())) {
                            // Supprimez le bien de la liste des biens immobiliers de l'agent
                            agentConnecte.supprimerBienAVendre(bien);
                            afficherMessage("Le bien immobilier a été supprimé avec succès.");
                            afficherOptionsAgent();
                            return; // Sortez de la méthode après la suppression
                        }
                    }
                }
            }
            // Si la liste des biens est vide ou si aucun bien n'a été sélectionné
            afficherMessage("Aucun bien immobilier à supprimer ou aucun choix n'a été effectué.");
           
        } else {
            afficherMessage("Aucun agent n'est actuellement connecté.");
        }
    }

    private void afficherTousBiensImmobilier() {
        // Vérifiez si un agent immobilier est connecté
        Utilisateur agentConnecte = gestionUtilisateurs.getAgentConnecte();
        if (agentConnecte != null) {
            // Récupérez la liste des biens immobiliers de l'agent connecté
            List<BienImmobilier> listeBiens = agentConnecte.getBiensAVendre();
            
            // Vérifiez s'il y a des biens à afficher
            if (!listeBiens.isEmpty()) {
                // Affichez chaque bien immobilier dans la zone de texte
                textAreaAffichage.setText(""); // Effacez le contenu précédent
                textAreaAffichage.append("Liste des biens immobiliers de l'agent " + agentConnecte.getNom() + " :\n\n");
                for (BienImmobilier bien : listeBiens) {
                    textAreaAffichage.append("Type: " + bien.getType() + "\n");
                    textAreaAffichage.append("Prix: " + bien.getPrix() + "\n");
                    textAreaAffichage.append("Surface: " + bien.getSurface() + "\n");
                    textAreaAffichage.append("Localisation: " + bien.getLocalisation() + "\n");
                    textAreaAffichage.append("Description: " + bien.getDescription() + "\n");
                    textAreaAffichage.append("Nombre de chambres: " + bien.getNombreChambres() + "\n");
                    
                    textAreaAffichage.append("Année de construction: " + bien.getAnneeConstruction() + "\n");
                    
                }
            } else {
                afficherMessage("Aucun bien immobilier à afficher.");
            }
        } else {
            afficherMessage("Aucun agent n'est actuellement connecté.");
        }
    }

 // Ajoutez cette méthode dans la classe ApplicationGUI
    private void afficherListeRendezVous() {
        Utilisateur agentConnecte = gestionUtilisateurs.getAgentConnecte();
        if (agentConnecte != null) {
            List<BienImmobilier> biens = agentConnecte.getBiensAVendre();
            if (biens.isEmpty()) {
                textAreaAffichage.setText("Aucun bien immobilier disponible.");
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                Random random = new Random();

                for (BienImmobilier bien : biens) {
                    stringBuilder.append("Pour le bien : ").append(bien.getDescription()).append("\n");
                    if (bien.getRendezVous().isEmpty()) {
                        stringBuilder.append("Vous avez rendez-vous :\n");
                        for (int i = 0; i < 3; i++) { // Générer 3 rendez-vous aléatoires
                            LocalDate dateRendezVous = LocalDate.now().plusDays(random.nextInt(30)); // Date aléatoire dans les 30 prochains jours
                            String[] noms = {"Ahmed Bouzid", "Hamza Belkacem", "Youssef Benamara", "Amina Khelifi", "Rachid Kaddour"};
                            String nom = noms[random.nextInt(noms.length)]; // Nom aléatoire
                            stringBuilder.append("- ").append(dateRendezVous).append(" avec monsieur ").append(nom).append("\n");
                        }
                    } else {
                        stringBuilder.append("Rendez-vous prévus :\n");
                        for (RendezVous rendezVous : bien.getRendezVous()) {
                            stringBuilder.append("- ").append(rendezVous).append("\n");
                        }
                    }
                    stringBuilder.append("\n");
                }
                textAreaAffichage.setText(stringBuilder.toString());
            }
        } else {
            afficherMessage("Aucun agent n'est actuellement connecté.");
        }
    }
    private void afficherOptionsApresAction() {
        // Demander à l'utilisateur s'il souhaite afficher à nouveau les options de l'agent immobilier
        int choix = JOptionPane.showConfirmDialog(this, "Voulez-vous afficher à nouveau les options de l'agent immobilier ?", "Options de l'agent immobilier", JOptionPane.YES_NO_OPTION);
        if (choix == JOptionPane.YES_OPTION) {
            afficherOptionsAgent(); // Afficher à nouveau les options de l'agent immobilier
        } 
    }

    private void afficherMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    private void afficherMessage1(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
    
    
    //client 
    private void seConnecterClient() {
        // Demander les informations du client de manière plus stylée
        String nom = demanderInformation("Nom :");
        String prenom = demanderInformation("Prénom :");
        String choixLocation = demanderInformation("Achat ou location ? (acheter/louer)");
        double budget = saisirBudgetMaximum();

        // Afficher les options dans une boîte de dialogue avec une présentation plus élégante
        String[] optionsMenu = {
        	    "Afficher les agents immobiliers disponibles",
        	    "Afficher les biens immobiliers disponibles selon le budget",
        	    "Afficher les biens immobiliers disponibles selon le type (villa/appartement)",
        	    "Afficher les biens immobiliers disponibles selon la localisation"
        	};
        	JComboBox<String> menuComboBox = new JComboBox<>(optionsMenu);

        	// Afficher la liste déroulante dans une boîte de dialogue avec un message explicatif
        	JPanel panel = new JPanel();
        	panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        	panel.add(new JLabel("Choisissez une option :"));
        	panel.add(menuComboBox);

        	int choix = JOptionPane.showConfirmDialog(null, panel, "Menu Client", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        	// En fonction de l'option sélectionnée dans la liste déroulante, exécuter l'action correspondante
        	if (choix == JOptionPane.OK_OPTION) {
        	    int selectedIndex = menuComboBox.getSelectedIndex();
        	    switch (selectedIndex) {
        	        case 0:
        	            afficherAgentsImmobiliers();
        	            break;
        	        case 1:
       	             afficherBiensImmobiliersSelonBudget(gestionUtilisateurs, budget, choixLocation);

        	            break;
        	        case 2:
        	        	afficherBiensImmobiliersSelonType(gestionUtilisateurs); 
        	            break;
        	        case 3:
        	            afficherBiensImmobiliersSelonLocalisation(gestionUtilisateurs);

        	            break;
        	        default:
        	            afficherMessage("Choix invalide. Veuillez choisir une option valide.");
        	    }
        	}}

    // Méthodes utilitaires pour demander des informations, saisir un budget et afficher des messages
    private String demanderInformation(String message) {
        // Implémenter une méthode pour demander des informations avec une présentation plus élégante
        return JOptionPane.showInputDialog(null, message);
    }

    private double saisirBudgetMaximum() {
        // Implémenter une méthode pour saisir un budget avec une présentation plus élégante et une gestion des erreurs
        String input = demanderInformation("Please enter your maximum budget:");
        while (!isValidNumber(input)) {
            input = demanderInformation("Please enter a valid number:");
        }
        return Double.parseDouble(input);
    }

    private boolean isValidNumber(String input) {
        // Implémenter une méthode pour vérifier si l'entrée est un nombre valide
        try {
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

 
    private int presenterOptionsDansBoiteDialogue(String message, String[] options) {
        // Implémenter une méthode pour afficher des options dans une boîte de dialogue de manière plus élégante et obtenir le choix de l'utilisateur
        return JOptionPane.showOptionDialog(null, message, "Options", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);
    }

    private void afficherAgentsImmobiliers() {
        StringBuilder sb = new StringBuilder();
        sb.append("Liste des agents immobiliers disponibles :\n");
        List<Utilisateur> agents = gestionUtilisateurs.getUtilisateurs();
        for (int i = 0; i < agents.size(); i++) {
            Utilisateur agent = agents.get(i);
            sb.append(i + 1).append(". ").append(agent.getNom()).append(" ").append(agent.getPrenom()).append("\n");
        }
        textAreaAffichage.setText(sb.toString());

        // Prompt the user to select an agent
        String agentChoice = JOptionPane.showInputDialog(this, "Choisissez un agent par son numéro :");

        try {
            int agentIndex = Integer.parseInt(agentChoice) - 1;
            if (agentIndex >= 0 && agentIndex < agents.size()) {
                Utilisateur selectedAgent = agents.get(agentIndex);
                afficherBiensImmobiliers(selectedAgent);
            } else {
                JOptionPane.showMessageDialog(this, "Numéro d'agent invalide. Veuillez choisir un numéro valide.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Veuillez entrer un numéro valide.");
        }
    }

    private void afficherBiensImmobiliers(Utilisateur agent) {
        List<BienImmobilier> biens = agent.getBiensAVendre();
        StringBuilder sb = new StringBuilder();
        sb.append("Biens immobiliers de ").append(agent.getNom()).append(" ").append(agent.getPrenom()).append(" :\n");
        if (!biens.isEmpty()) {
            for (BienImmobilier bien : biens) {
                sb.append("- ").append(bien.getDescription()).append("\n");
                // Append other details of the property if needed
            }
            sb.append("\n");
            sb.append("Êtes-vous intéressé par l'un de ces biens ? (Oui/Non)");
            String interesse = JOptionPane.showInputDialog(this, sb.toString());
            if ("Oui".equalsIgnoreCase(interesse)) {
                choisirBienEtRendezVous(biens, agent);
            }
        } else {
            sb.append("Aucun bien immobilier disponible pour cet agent.");
        }
        textAreaAffichage.setText(sb.toString());
    }

    private void choisirBienEtRendezVous(List<BienImmobilier> biens, Utilisateur agent) {
        String[] options = new String[biens.size()];
        for (int i = 0; i < biens.size(); i++) {
            options[i] = biens.get(i).getDescription();
        }

        String bienChoisi = (String) JOptionPane.showInputDialog(this,
                "Choisissez le bien qui vous intéresse :", "Choix du bien",
                JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

        if (bienChoisi != null) {
            Random random = new Random();
            LocalDate randomDate = LocalDate.now().plusDays(random.nextInt(7)); // Random date within the next week
            String rendezVousMessage = "Rendez-vous pris avec monsieur " + agent.getNom() + " " + agent.getPrenom() + " le " + randomDate;
            
            // Add agent's contact information
            String contactInfo = "\nSi vous voulez contacter monsieur " + agent.getNom() + ", voici son numéro : " + agent.getTelephone() +
                " et son email : " + agent.getEmail();
            
            // Show the message with contact information
            afficherMessage(rendezVousMessage + contactInfo);
        }
    }

    private static void afficherBiensImmobiliersSelonBudget(GestionUtilisateurs gestionUtilisateurs, double budget, String choixLocation) {
        List<BienImmobilier> biensInteresses = new ArrayList<>();

        StringBuilder message = new StringBuilder("Biens immobiliers disponibles avec un prix inférieur ou égal à votre budget de " + budget + " :\n");

        JComboBox<String> bienComboBox = new JComboBox<>();
        for (Utilisateur agent : gestionUtilisateurs.getUtilisateurs()) {
            for (BienImmobilier bien : agent.getBiensAVendre()) {
                double prix = bien.getPrix();
                if (choixLocation.equalsIgnoreCase("louer")) {
                    prix /= 100; // Si c'est pour la location, diviser le prix par 1000
                }
                if (prix <= budget) {
                    String descriptionPrix = bien.getDescription() + " : " + prix;
                    message.append("- ").append(descriptionPrix).append("\n");
                    biensInteresses.add(bien);
                    bienComboBox.addItem(descriptionPrix);
                }
            }
        }

        JPanel panel = new JPanel(new GridLayout(0, 1));
        JTextArea textArea = new JTextArea(10, 30);
        textArea.setEditable(false);
        textArea.setText(message.toString());
        JScrollPane scrollPane = new JScrollPane(textArea);
        panel.add(scrollPane);
        panel.add(new JLabel("Sélectionnez un bien immobilier :"));
        panel.add(bienComboBox);

        int option = JOptionPane.showConfirmDialog(null, panel, "Biens Immobiliers",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            int selectedIndex = bienComboBox.getSelectedIndex();
            if (selectedIndex >= 0 && selectedIndex < biensInteresses.size()) {
                BienImmobilier bienChoisi = biensInteresses.get(selectedIndex);
                Utilisateur agentProprietaire = null;
                for (Utilisateur agent : gestionUtilisateurs.getUtilisateurs()) {
                    if (agent.getBiensAVendre().contains(bienChoisi)) {
                        agentProprietaire = agent;
                        break;
                    }
                }

                int confirmationRdv = JOptionPane.showConfirmDialog(null, "Le bien immobilier sélectionné appartient à : " + agentProprietaire.getPrenom() + " " + agentProprietaire.getNom() +
                        "\nVoulez-vous prendre rendez-vous avec " + agentProprietaire.getPrenom() + " " + agentProprietaire.getNom() + " ?", "Confirmation Rendez-vous", JOptionPane.YES_NO_OPTION);

                if (confirmationRdv == JOptionPane.YES_OPTION) {
                    // Générer une date de rendez-vous aléatoire
                    LocalDate dateRendezVous = LocalDate.now().plusDays(new Random().nextInt(30)); // Ajoute un nombre aléatoire de jours entre 0 et 29 à la date actuelle

                    JOptionPane.showMessageDialog(null, "Rendez-vous pris avec " + agentProprietaire.getPrenom() + " " + agentProprietaire.getNom() + " le " + dateRendezVous + "!");
                    JOptionPane.showMessageDialog(null, "Si vous souhaitez contacter Monsieur " + agentProprietaire.getNom() + ", voici ses informations de contact :\n" +
                            "Numéro de téléphone : " + agentProprietaire.getTelephone() + "\nAdresse e-mail : " + agentProprietaire.getEmail());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Choix invalide.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    private static void afficherBiensImmobiliersSelonType(GestionUtilisateurs gestionUtilisateurs) {
        String typeBien = JOptionPane.showInputDialog(null, "Veuillez entrer le type de bien immobilier (villa/appartement) :");
        if (typeBien != null) { // Vérifier si l'utilisateur a annulé la saisie
            StringBuilder message = new StringBuilder("Biens immobiliers disponibles de type " + typeBien + " :\n");
            boolean aucunBienTrouve = true;
            List<BienImmobilier> biensInteresses = new ArrayList<>();

            // Recherche des biens immobiliers selon le type spécifié
            for (Utilisateur agent : gestionUtilisateurs.getUtilisateurs()) {
                for (BienImmobilier bien : agent.getBiensAVendre()) {
                    if (bien.getType().equalsIgnoreCase(typeBien)) {
                        message.append("- ").append(bien.getDescription()).append(" : ").append(bien.getPrix()).append("\n");
                        biensInteresses.add(bien);
                        aucunBienTrouve = false;
                    }
                }
            }

            if (aucunBienTrouve) {
                JOptionPane.showMessageDialog(null, "Aucun bien immobilier de type " + typeBien + " trouvé.");
            } else {
                Object[] options = biensInteresses.toArray();
                BienImmobilier choixBien = (BienImmobilier) JOptionPane.showInputDialog(null, message.toString() + "\nChoisissez un bien immobilier :", "Choix du bien immobilier",
                        JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

                if (choixBien != null) {
                    int choixRendezVous = JOptionPane.showConfirmDialog(null, "Voulez-vous prendre rendez-vous avec le propriétaire de ce bien ?", "Prendre rendez-vous",
                            JOptionPane.YES_NO_OPTION);
                    if (choixRendezVous == JOptionPane.YES_OPTION) {
                        // Génération d'une date de rendez-vous aléatoire
                        LocalDate dateRendezVous = LocalDate.now().plusDays(new Random().nextInt(30));
                        JOptionPane.showMessageDialog(null, "Rendez-vous pris pour le bien immobilier : " + choixBien.getDescription() + " le " + dateRendezVous + " !");
                    } else {
                        JOptionPane.showMessageDialog(null, "Aucun rendez-vous pris.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Aucun bien immobilier sélectionné.");
                }
            }
        }
    }

    public static void afficherBiensImmobiliersSelonLocalisation(GestionUtilisateurs gestionUtilisateurs) {
        // Création de la fenêtre
        JFrame frame = new JFrame("Biens immobiliers par localisation");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Panneau principal
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Champ de texte pour la localisation
        JLabel labelLocalisation = new JLabel("Localisation :");
        JTextField textFieldLocalisation = new JTextField(20);
        panel.add(labelLocalisation);
        panel.add(textFieldLocalisation);

        // Bouton pour afficher les biens immobiliers
        JButton buttonAfficherBiens = new JButton("Afficher biens immobiliers");
        panel.add(buttonAfficherBiens);

        // Zone de texte pour afficher les biens immobiliers
        JTextArea textAreaBiens = new JTextArea(10, 30);
        JScrollPane scrollPane = new JScrollPane(textAreaBiens);
        panel.add(scrollPane);

        // Action du bouton
        buttonAfficherBiens.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String localisation = textFieldLocalisation.getText();

                List<BienImmobilier> biensInteresses = new ArrayList<>();
                for (Utilisateur agent : gestionUtilisateurs.getUtilisateurs()) {
                    for (BienImmobilier bien : agent.getBiensAVendre()) {
                        if (bien.getLocalisation().equalsIgnoreCase(localisation)) {
                            biensInteresses.add(bien);
                        }
                    }
                }

                if (biensInteresses.isEmpty()) {
                    textAreaBiens.setText("Aucun bien immobilier disponible à " + localisation + ".");
                } else {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Biens immobiliers disponibles à ").append(localisation).append(" :\n");
                    for (int i = 0; i < biensInteresses.size(); i++) {
                        BienImmobilier bien = biensInteresses.get(i);
                        stringBuilder.append(i + 1).append(". ").append(bien.getDescription()).append(" : ").append(bien.getPrix()).append("\n");
                    }
                    textAreaBiens.setText(stringBuilder.toString());

                    // Demander si l'utilisateur est intéressé par un bien
                    int choix = JOptionPane.showConfirmDialog(null, "Êtes-vous intéressé par l'un de ces biens ?", "Intérêt pour un bien", JOptionPane.YES_NO_OPTION);
                    if (choix == JOptionPane.YES_OPTION) {
                        // Demander à l'utilisateur de choisir un bien
                        String input = JOptionPane.showInputDialog("Veuillez entrer le numéro du bien que vous souhaitez :");
                        try {
                            int choixBien = Integer.parseInt(input);
                            if (choixBien >= 1 && choixBien <= biensInteresses.size()) {
                                BienImmobilier bienChoisi = biensInteresses.get(choixBien - 1);

                                // Générer une date de rendez-vous aléatoire
                                JOptionPane.showMessageDialog(null, "Vous avez choisi le bien : " + bienChoisi.getDescription() + ". Nous allons organiser un rendez-vous avec l'agent immobilier.");

                                // Simulation d'une date de rendez-vous aléatoire
                                LocalDate dateRendezVous = LocalDate.now().plusDays(new Random().nextInt(30)); // Ajoute un nombre aléatoire de jours entre 0 et 29 à la date actuelle
                                JOptionPane.showMessageDialog(null, "Un rendez-vous est prévu le " + dateRendezVous.toString() + ". L'agent immobilier vous contactera pour confirmer.");
                                System.exit(0);
                            } else {
                                JOptionPane.showMessageDialog(null, "Numéro de bien invalide.");
                            }
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "Veuillez entrer un numéro valide.");
                        }
                    }
                }
            }
        });

        // Ajout du panneau au cadre
        frame.add(panel);
        frame.setVisible(true);
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    private void creerAgentsImmobiliersPreEnregistres() {
        // Création des agents immobiliers avec leurs informations et leurs biens
        Utilisateur agent1 = new Utilisateur("agent1", "mdp1", GestionUtilisateurs.getRoleAgent(), "Hammaz", "Ramzi", "Adresse1", "agent1@example.com", "0123456789");
        agent1.ajouterBienAVendre(new BienImmobilier("Maison", 2000000, 150, "Alger", "Belle maison avec vue sur mer", 4, 2, 2000, 2));
        agent1.ajouterBienAVendre(new BienImmobilier("Appartement", 1000000, 80, "Oran", "Appartement lumineux en centre-ville", 2, 1, 2010, 4));
        gestionUtilisateurs.ajouterUtilisateur(agent1);

        Utilisateur agent2 = new Utilisateur("agent2", "mdp2", GestionUtilisateurs.getRoleAgent(), "Reggaz", "fazil", "Adresse2", "agent2@example.com", "0123456789");
        agent2.ajouterBienAVendre(new BienImmobilier("Villa", 3000000, 200, "Alger", "Magnifique villa avec jardin", 5, 3, 2500, 3));
        agent2.ajouterBienAVendre(new BienImmobilier("Appartement", 1500000, 100, "Oran", "Appartement moderne près de la plage", 3, 2, 2015, 5));
        gestionUtilisateurs.ajouterUtilisateur(agent2);

        Utilisateur agent3 = new Utilisateur("agent3", "mdp3", GestionUtilisateurs.getRoleAgent(), "Heddadi", "Melissa", "Adresse3", "agent3@example.com", "0123456789");
        agent3.ajouterBienAVendre(new BienImmobilier("Maison", 2500000, 180, "Tizi Ouzou", "Grande maison avec piscine", 6, 4, 2800, 4));
        agent3.ajouterBienAVendre(new BienImmobilier("Appartement", 1200000, 90, "Constantine", "Appartement bien situé près des écoles", 2, 1, 2008, 3));
        gestionUtilisateurs.ajouterUtilisateur(agent3);

        Utilisateur agent4 = new Utilisateur("agent4", "mdp4", GestionUtilisateurs.getRoleAgent(), "Mahfoufi", "Seryne", "Adresse4", "agent4@example.com", "0123456789");
        agent4.ajouterBienAVendre(new BienImmobilier("Maison", 1800000, 160, "Béjaïa", "Maison avec vue imprenable sur la mer", 5, 3, 2200, 2));
        agent4.ajouterBienAVendre(new BienImmobilier("Appartement", 900000, 70, "Annaba", "Appartement cosy dans un quartier calme", 2, 1, 2012, 3));
        gestionUtilisateurs.ajouterUtilisateur(agent4);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ApplicationGUI();
                
            }
        });
    }
}
