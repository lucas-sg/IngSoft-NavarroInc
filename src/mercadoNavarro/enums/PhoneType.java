package mercadoNavarro.enums;

public enum PhoneType {
    HOME {
        @Override
        public String toString() {
            return "home";
        }
    }, WORK {
        @Override
        public String toString() {
            return "work";
        }
    }, MOBILE {
        @Override
        public String toString() {
            return "mobile";
        }
    }
}
