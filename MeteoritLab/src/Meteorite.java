public class Meteorite{
    String name;
    long id;
    MeteoriteType type;
    String aClass;
    long massInGram;
    MeteoriteFallingState fallingState;
    int yearOfDiscovery;
    double longitude;
    double latitude;
    double[] longitudeAndLatitude;

    public Meteorite(
            String name,
            Long id,
            MeteoriteType type,
            String aClass,
            Long massInGram,
            MeteoriteFallingState fallingState,
            Integer yearOfDiscovery,
            Double longitude,
            Double latitude,
            double[] longitudeAndLatitude
    ) {
        this.name                 = name;
        this.id                   = id;
        this.type                 = type;
        this.aClass               = aClass;
        this.massInGram           = massInGram;
        this.fallingState         = fallingState;
        this.yearOfDiscovery      = yearOfDiscovery;
        this.longitude            = longitude; // долгота
        this.latitude             = latitude; // широта
        this.longitudeAndLatitude = longitudeAndLatitude;
    }

    public PartOfTheWorld getPartOfTheWorld() {
        String lt = "", lng = "";
        if (-30 < latitude && latitude < 30)
            if (longitude <= 0)
                lng = PartOfTheWorld.west.name();
            else lng = PartOfTheWorld.east.name();
        if (30 <= latitude)
            lt = PartOfTheWorld.north.name();
        else lt = PartOfTheWorld.south.name();
        return PartOfTheWorld.valueOf(lt + lng);
    }

}
