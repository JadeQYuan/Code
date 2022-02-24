
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
public class CommonJsonResponse<T> implements ResponseInstance {

    private Integer code;

    private String message;

    private T data;

    /**
     * 构造一个没有数据的消息
     */
    private CommonJsonResponse(ResponseConstEnum respConst) {
        this.code = respConst.getCode();
        this.message = respConst.getMessage();
    }

    /**
     * 构造成功返回数据对象
     */
    private CommonJsonResponse(T data) {
        this.code = ResponseConstEnum.SUCCESS.getCode();
        this.message = ResponseConstEnum.SUCCESS.getMessage();
        this.data = data;
    }

    public CommonJsonResponse(int code, String msg) {
        this.code = code;
        this.message = msg;
    }

    /**
     * 返回通用成功对象
     */
    public static <T> CommonJsonResponse<T> newSuccessResponse() {
        return new CommonJsonResponse<>(ResponseConstEnum.SUCCESS);
    }

    /**
     * 成功返回数据对象
     */
    public static <T> CommonJsonResponse<T> newSuccessResponse(T data) {
        return new CommonJsonResponse<>(data);
    }

    /**
     * 返回失败对象
     */
    public static <T> CommonJsonResponse<T> newFailedResponse(String message) {
        return new CommonJsonResponse<>(ResponseConstEnum.FAILED.getCode(), message);
    }

    /**
     * 返回未知异常对象
     */
    public static <T> CommonJsonResponse<T> newErrorResponse() {
        return new CommonJsonResponse<>(ResponseConstEnum.ERROR);
    }

    /**
     * 返回服务端异常对象
     */
    public static <T> CommonJsonResponse<T> newErrorResponse(BaseException baseException) {
        return new CommonJsonResponse<>(baseException.getErrCode(), baseException.getErrMessage());
    }
}
