package lib.server.wrapper.exceptions;

public class ServerException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private int errorCode = -1;
	
	public ServerException(String errorMessage) {
		this(-1, errorMessage);
	}
	
	public ServerException(int errorCode, String errorMessage)
	{
		super(errorMessage);
		this.errorCode = errorCode;
	}
	
	public ServerException(Throwable e) {
		super(e);
	}

	public int getErrorCode()
	{
		return errorCode;
	}

}
