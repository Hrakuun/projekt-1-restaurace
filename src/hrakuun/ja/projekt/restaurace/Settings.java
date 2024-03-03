package hrakuun.ja.projekt.restaurace;

public class Settings {

    private static final String COOKBOOKFILE = "dataFiles/cookBook.txt";
    private static final String ORDERSINPROGRESS = "dataFiles/ordersInProgress.txt";
    private static final String COMPLETEDORDERS = "dataFiles/completedOrders.txt";
    private static final String DELIMITER = ";";

    public static String getCookBookFilePath(){
        return COOKBOOKFILE;
    }
    public static String getOrdersInProgressPath() {
        return ORDERSINPROGRESS;
    }
    public static String getCompletedOrdersPath(){
        return COMPLETEDORDERS;
    }
    public static String getDelimiter() {
        return DELIMITER;
    }

}
