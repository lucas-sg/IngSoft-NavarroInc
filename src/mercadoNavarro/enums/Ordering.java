package mercadoNavarro.enums;


public enum Ordering {
	DEFAULT {
        @Override
        public String toString() {
            return "";
        }
    },
    LOWEST_PRICE {
        @Override
        public String toString() {
            return "Lowest Price";
        }
    }, PICKUP {
        @Override
        public String toString() {
            return "Pickup";
        }
    }, STARS {
        @Override
        public String toString() {
            return "Stars";
        }
    }
}