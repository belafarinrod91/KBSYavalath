package tinf11bc.kbs.yavalath.logic;

public class YavalathException extends Exception {

    public YavalathException() {
        super();
    }
    
    public YavalathException(String message) {
        super(message);
    }
    
    public YavalathException(String message, Throwable cause) {
        super(message, cause);
    }

    public YavalathException(Throwable cause) {
        super(cause);
    }
    protected YavalathException(String message, Throwable cause,
                        boolean enableSuppression,
                        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
