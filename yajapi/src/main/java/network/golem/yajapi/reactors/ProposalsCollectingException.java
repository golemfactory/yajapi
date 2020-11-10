package network.golem.yajapi.reactors;

/**
 * Collecting proposals for given demand failed.
 * Further proposals are not collected for this demand - collecting is stopped as you called stopCollecting(demandId)
 */
public class ProposalsCollectingException extends Exception {
    public ProposalsCollectingException() {
    }

    public ProposalsCollectingException(String message) {
        super(message);
    }

    public ProposalsCollectingException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProposalsCollectingException(Throwable cause) {
        super(cause);
    }

    public ProposalsCollectingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
