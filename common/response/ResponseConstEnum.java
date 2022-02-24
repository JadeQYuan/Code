
import lombok.Getter;

@Getter
public enum ResponseConstEnum implements ResponseInstance {

    SUCCESS(200, "成功"),
    FAILED(400, "失败"),
    ERROR(500, "未知错误"),
    ;

    ResponseConstEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    private Integer code;

    private String message;
}
