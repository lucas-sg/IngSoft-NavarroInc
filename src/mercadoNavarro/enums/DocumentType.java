package mercadoNavarro.enums;

public enum DocumentType {
    DNI {
        @Override
        public String toString() {
            return "DNI";
        }
    }, PASSPORT {
        @Override
        public String toString() {
            return "Passport";
        }
    }
}
