package hrakuun.ja.projekt.restaurace;

public class Settings {

    private static final String COOKBOOKFILE = "dataFiles/cookBook.txt";
    private static final String ORDERSFILE = "dataFiles/ordersInProgress.txt";
    private static final String DELIMITER = ";";

    public static String getCookBookFilePath(){
        return COOKBOOKFILE;
    }
    public static String getOrdersInProgressPath() {
        return ORDERSFILE;
    }
    public static String getDelimiter() {
        return DELIMITER;
    }

}
