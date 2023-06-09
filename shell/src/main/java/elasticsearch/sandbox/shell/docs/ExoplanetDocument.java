package elasticsearch.sandbox.shell.docs;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExoplanetDocument {
    @JsonProperty("pl_name")
    private String planetName;

    @JsonProperty("hostname")
    private String hostName;

    @JsonProperty("pl_letter")
    private String planetLetter;

    @JsonProperty("gaia_id")
    private String gaiaId;

    @JsonProperty("sy_snum")
    private int numberOfStars;

    @JsonProperty("sy_pnum")
    private int numberOfPlanets;

    @JsonProperty("sy_mnum")
    private int numberOfMoons;

    @JsonProperty("discoverymethod")
    private String discoveryMethod;

    @JsonProperty("disc_year")
    private int discoveryYear;

    @JsonProperty("disc_facility")
    private String discoveryFacility;

    @JsonProperty("pl_orbper")
    private double orbitalPeriod;

    @JsonProperty("pl_orbpererr1")
    private double orbitalPeriodUpperUncertainty;

    @JsonProperty("pl_orbpererr2")
    private double orbitalPeriodLowerUncertainty;

    @JsonProperty("pl_orbperlim")
    private int orbitalPeriodLimitFlag;

    @JsonProperty("st_spectype")
    private String spectralType;
}
