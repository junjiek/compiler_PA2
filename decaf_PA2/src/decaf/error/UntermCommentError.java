package decaf.error;

import decaf.Location;

public class UntermCommentError extends DecafError {

	public UntermCommentError(Location location) {
		super(location);
	}

	@Override
	protected String getErrMsg() {
		return  "unterminated multi comment";
	}
}
