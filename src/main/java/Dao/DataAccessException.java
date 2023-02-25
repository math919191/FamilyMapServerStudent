package Dao;

/**
 * Exception specifically for data access classes
 * */
public class DataAccessException extends Exception {
    /**
     * Creates an exception for DAO class given a message
     * @param message description of error method
     * */
    DataAccessException(String message) {
        super(message);
    }
}
