package states;

public class States {
    enum STATE{
        INITIATED("INITIATED"),
        STARTED("STARTED"),
        PARTIALLY_DONE("PARTIALLY_DONE"),
        DONE("DONE"),
        FAILED("FAILED"),
        UNKNOWN("UNKNOWN");

        private final String state;
        STATE(String state){
            this.state = state;
        }
    }
}
