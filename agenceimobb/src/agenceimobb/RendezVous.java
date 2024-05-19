package agenceimobb;

import java.time.LocalDateTime;


public class RendezVous {
    private String bienImmobilier;
    private LocalDateTime dateTime;
    private String id;
    // Other attributes and methods

    // Getter method for retrieving the id
    public String getId() {
        return this.id;
    }

    // Setter method for setting the id
    public void setId(String id) {
        this.id = id;
    }


    public RendezVous(String bienImmobilier, LocalDateTime dateTime) {
        this.bienImmobilier = bienImmobilier;
        this.dateTime = dateTime;
    }

    public String getBienImmobilier() {
        return bienImmobilier;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}

