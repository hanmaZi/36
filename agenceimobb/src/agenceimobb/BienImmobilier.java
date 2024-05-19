package agenceimobb;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BienImmobilier {
    private String id;
    private String type;
    private double prix;
    private double surface;
    private String localisation;
    private String description;
    private int nombreChambres;
    private int nombreSallesBains;
    private int anneeConstruction;
    private int etage;
    private LocalDate[] rendezVousAleatoires;


    public BienImmobilier(String id, String type, double prix, double surface, String localisation, String description,
			int nombreChambres, int nombreSallesBains, int anneeConstruction, int etage,
			LocalDate[] rendezVousAleatoires, List<agenceimobb.RendezVous> rendezVous) {
		super();
		this.id = id;
		this.type = type;
		this.prix = prix;
		this.surface = surface;
		this.localisation = localisation;
		this.description = description;
		this.nombreChambres = nombreChambres;
		this.nombreSallesBains = nombreSallesBains;
		this.anneeConstruction = anneeConstruction;
		this.etage = etage;
		this.rendezVousAleatoires = rendezVousAleatoires;
		RendezVous = rendezVous;
	}
    public void setRendezVousAleatoires(LocalDate[] rendezVousAleatoires) {
        this.rendezVousAleatoires = rendezVousAleatoires;
    }

	// Getters et setters
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public double getPrix() {
		return prix;
	}
	public void setPrix(double prix) {
		this.prix = prix;
	}
	public double getSurface() {
		return surface;
	}
	public void setSurface(double surface) {
		this.surface = surface;
	}
	public String getLocalisation() {
		return localisation;
	}
	public void setLocalisation(String localisation) {
		this.localisation = localisation;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getNombreChambres() {
		return nombreChambres;
	}
	public void setNombreChambres(int nombreChambres) {
		this.nombreChambres = nombreChambres;
	}
	public int getNombreSallesBains() {
		return nombreSallesBains;
	}
	public void setNombreSallesBains(int nombreSallesBains) {
		this.nombreSallesBains = nombreSallesBains;
	}
	public int getAnneeConstruction() {
		return anneeConstruction;
	}
	public void setAnneeConstruction(int anneeConstruction) {
		this.anneeConstruction = anneeConstruction;
	}
	public int getEtage() {
		return etage;
	}
	public void setEtage(int etage) {
		this.etage = etage;
	}
	
public BienImmobilier(String type, double prix, double surface, String localisation,String description, int nombreChambres, int nombreSallesBains,
 int anneeConstruction, int etage) {
this.id = UUID.randomUUID().toString(); 
this.type = type;
this.prix = prix;
this.surface = surface;
this.localisation = localisation;
this.description = description;
this.nombreChambres = nombreChambres;
this.nombreSallesBains = nombreSallesBains;
this.anneeConstruction = anneeConstruction;
this.etage = etage;
}
private List<RendezVous> RendezVous = new ArrayList<>();

public void ajouterRendezVous(RendezVous rdv) {
    RendezVous.add(rdv);
}

public List<RendezVous> getRendezVous() {
    return RendezVous;
}
public String toString() {
    return description + " : " + prix; // Utilisez les attributs pertinents pour obtenir une représentation significative
}


}





