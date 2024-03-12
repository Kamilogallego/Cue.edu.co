package customExceptions;

public class ToyStoreException extends Exception {
    public static class ClientNotFoundException extends Exception {
        public ClientNotFoundException (String message) {
            super(message);
        }
    }

    public static class EmployeesNotFoundException extends Exception {
        public EmployeesNotFoundException(String message) {
            super(message);
        }
    }

    public static class ToyNotFoundException extends Exception {
        public ToyNotFoundException(String message) {
            super(message);
        }
    }

    public static class SaleNotFoundException extends Exception {
        public SaleNotFoundException(String message) {
            super(message);
        }
    }
}