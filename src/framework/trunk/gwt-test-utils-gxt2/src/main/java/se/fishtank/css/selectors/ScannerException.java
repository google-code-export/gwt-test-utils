package se.fishtank.css.selectors;

/**
 * An exception thrown on {@link Scanner} errors.
 * 
 * @author Christer Sandberg
 */
class ScannerException extends Exception {

   /** Serial version UID. */
   private static final long serialVersionUID = -1430921277275539691L;

   /**
    * Constructs a new exception with the specified detail message.
    * 
    * @param message The detail message.
    */
   public ScannerException(String message) {
      super(message);
   }

}
