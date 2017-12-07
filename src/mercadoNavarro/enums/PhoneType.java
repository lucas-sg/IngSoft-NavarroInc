package mercadoNavarro.enums;

public enum PhoneType {
    HOME {
        @Override
        public String toString() {
            return "Home";
        }
    }, WORK {
        @Override
        public String toString() {
            return "Work";
        }
    }, MOBILE {
        @Override
        public String toString() {
            return "Mobile";
        }
    }
}
