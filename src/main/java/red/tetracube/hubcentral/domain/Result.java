package red.tetracube.hubcentral.domain;

import red.tetracube.hubcentral.domain.exceptions.HubCentralException;

public class Result<T> {
    
    private T content;
    private HubCentralException exception;
    private boolean success;

    public static <T> Result<T> success(T content) {
        var result = new Result<T>();
        result.content = content;
        result.success = true;
        result.exception = null;
        return result;
    }

    public static <T, E extends HubCentralException> Result<T> failed(E exception) {
        var result = new Result<T>();
        result.content = null;
        result.success = false;
        result.exception = exception;
        return result;
    }

    public T getContent() {
        return content;
    }

    public HubCentralException getException() {
        return exception;
    }

    public boolean isSuccess() {
        return success;
    }

}
