package mercadoNavarro.enums;

public enum DocumentType {
    DNI {
        @Override
        public String toString() {
            return "dni";
        }
    }, PASSPORT {
        @Override
        public String toString() {
            return "passport";
        }
    }
}
