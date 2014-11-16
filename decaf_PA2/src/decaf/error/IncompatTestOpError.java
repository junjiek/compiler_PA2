package decaf.error;

import decaf.Location;

/**
 * example: unterminated string constant: "this is str"<br>
 * PA2
 */
public class IncompatTestOpError extends DecafError {

    private String type1;
    private String type2;

    public IncompatTestOpError(Location location, String type1, String type2) {
        super(location);
        this.type1 = type1;
        this.type2 = type2;
    }

    @Override
    protected String getErrMsg() {
        return "operands to ?:  have different types " + type1 + " and " + type2;
    }

}
