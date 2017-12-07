package mercadoNavarro.enums;


public enum PaymentMethod {
    CREDIT {
        @Override
        public String toString() {
            return "Credit";
        }
    }, TRANSFER {
        @Override
        public String toString() {
            return "Transfer";
        }
    }
}
