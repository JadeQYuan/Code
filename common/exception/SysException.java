
import lombok.Getter;

@Getter
public class SysException extends BaseException {

    SysException(ResponseInstance responseInstance) {
        super(responseInstance);
    }

    SysException(ResponseInstance responseInstance, String errMessage) {
        super(responseInstance, errMessage);
    }

    SysException(ResponseInstance responseInstance, Throwable cause) {
        super(responseInstance, cause);
    }
}
