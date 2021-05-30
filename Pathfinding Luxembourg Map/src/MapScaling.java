public class MapScaling {

    public static final double screenWidth = 580;
    public static final double screenHeight = 850;

    public static double latX = 5.701153;
    public static double latY = 6.531256;
    public static double longX = 49.441148;
    public static double longY = 50.182918;

    public static double latTotal = latY - latX;
    public static double longTotal = longY - longX;

    public static Integer convertLat(double lat) {
        return (int) (screenWidth / (latTotal / (lat - latX)));
    }

    public static Integer convertLong(double lon) {
        return (int) (screenHeight / (longTotal / (lon - longX)));
    }
}
