package decaf.error;

import decaf.Location;

/**
 * example: unterminated string constant: "this is str"<br>
 * PA2
 */
public class LvalueRequiredError extends DecafError {

    private String op;

    public LvalueRequiredError(Location location, String op) {
        super(location);
        this.op = op;
    }

    @Override
    protected String getErrMsg() {
        return "lvalue required as " + op + " operand";
    }

}
