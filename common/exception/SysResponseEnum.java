
import lombok.Getter;

@Getter
public enum SysResponseEnum implements SysAssert {

    SYS_EXCEPTION(5000, "系统异常"),
    ;

    SysResponseEnum(Integer errCode, String errMessage) {
        this.code = errCode;
        this.message = errMessage;
    }

    private final Integer code;

    private final String message;
}
