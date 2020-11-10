package network.golem.yajapi.gftp;

public class GftpServiceException extends Exception {
    public GftpServiceException() {
    }

    public GftpServiceException(String message) {
        super(message);
    }

    public GftpServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public GftpServiceException(Throwable cause) {
        super(cause);
    }

    public GftpServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
