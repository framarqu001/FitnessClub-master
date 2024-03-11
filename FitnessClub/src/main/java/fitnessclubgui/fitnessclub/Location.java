package fitnessclubgui.fitnessclub;

/**
 * Location represents the different locations of the available gyms
 * Includes the town, zip code, and county the gym is located
 * Bridgewater, Edison, Franklin, Piscataway, Somerville
 *
 * @author Francisco Marquez
 */
public enum Location {
    BRIDGEWATER("08807", "SOMERSET"),
    EDISON("08837", "MIDDLESEX"),
    FRANKLIN("08873", "SOMERSET"),
    PISCATAWAY("08854", "MIDDLESEX"),
    SOMERVILLE("08876", "SOMERSET");

    private final String zipCode;
    private final String county;

    /**
     * Initalizes a location
     *
     * @param zipCode The zipcode of the location
     * @param county The county of the location
     */
    Location (String zipCode, String county) {
        this.zipCode = zipCode;
        this.county = county;
    }

    /**
     * Returns a string with the name of the town, zipcode, and county of the location
     * @return A string with the name of the town, zipcode, and county of the location
     */
    @Override
    public String toString () {
        return this.name() + ", " + zipCode + ", " + county;

    }

    /**
     * Returns the county and zipcode of the location as a string.
     * @return a string holding the county and zipcode of the location.
     */
    public String getCountyString(){
        return county + " " + zipCode;
    }

    /**
     * Returns the zipcode of the location
     * @return The zipcode of the location
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * Returns the county of the location
     * @return The county of the location
     */
    public String getCounty() {
        return county;
    }
}