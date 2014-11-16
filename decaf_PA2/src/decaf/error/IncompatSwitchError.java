package decaf.error;

import decaf.Location;

/**
 * example: unterminated string constant: "this is str"<br>
 * PA2
 */
public class IncompatSwitchError extends DecafError {

    private String conditionType;

    public IncompatSwitchError(Location location, String conditionType) {
        super(location);
        this.conditionType = conditionType;
    }

    @Override
    protected String getErrMsg() {
        return "incompatible switch: " + conditionType + " given,  int expected";
    }

}
