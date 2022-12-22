public enum PartOfTheWorld {
    north("север"),
    northeast("северо-восток"),
    east("восток"),
    southeast("юго-восток"),
    south("юг"),
    southwest("юго-запад"),
    west("запад"),
    northwest("северо-запад");
    public String translate;
    private PartOfTheWorld(String translate)
    {
        this.translate=translate;
    }
}
