package decaf.error;

import decaf.Location;

/**
 * example: unterminated string constant: "this is str"<br>
 * PA2
 */
public class IncompatCaseError extends DecafError {
    public IncompatCaseError(Location location) {
        super(location);
    }

    @Override
    protected String getErrMsg() {
        return "incompatible case: int constant is expected";
    }

}
