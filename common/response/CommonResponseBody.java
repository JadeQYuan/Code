
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.validation.ConstraintViolationException;
import java.util.Objects;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class CommonResponseBody implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return Objects.nonNull(returnType.getMethod()) && !returnType.getMethod().getReturnType().getSimpleName().equals("CommonJsonResponse");
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body instanceof CommonJsonResponse || request.getURI().getPath().startsWith("/swagger")) {
            return body;
        }
        CommonJsonResponse<Object> commonJsonResponse = CommonJsonResponse.newSuccessResponse(body);
        if (Objects.nonNull(returnType.getMethod()) && returnType.getMethod().getReturnType().equals(String.class)
                || body instanceof String) {
            return JsonUtils.writeValueAsString(commonJsonResponse);
        }
        return commonJsonResponse;
    }

    @ExceptionHandler({
            AsyncRequestTimeoutException.class,
            BindException.class, // param 参数校验异常
            ConstraintViolationException.class,
//            ConversionNotSupportedException.class, // TypeMismatchException
            HttpMessageConversionException.class,
//            HttpMessageNotReadableException.class, // json转换请i去异常
//            HttpMessageNotWritableException.class,
            HttpMediaTypeException.class,
//            HttpMediaTypeNotSupportedException.class,
//            HttpMediaTypeNotAcceptableException.class,
            HttpRequestMethodNotSupportedException.class, // Request method 'POST' not supported
            MethodArgumentNotValidException.class, // requestBody 参数校验异常
//            MethodArgumentTypeMismatchException.class, // TypeMismatchException
            MissingPathVariableException.class,
            MissingServletRequestParameterException.class,
            MissingServletRequestPartException.class,
//            NoHandlerFoundException.class, // 需要开始spring支持，但会导致静态页面无法访问
            ServletRequestBindingException.class,
            TypeMismatchException.class
    })
    @ResponseBody
    public CommonJsonResponse<Object> handleServletException(Exception e) {
        String errMessage;
        if (e instanceof MethodArgumentNotValidException) {
            errMessage = wrapperBindingResult(((MethodArgumentNotValidException) e).getBindingResult());
        } else if (e instanceof BindException) {
            errMessage = wrapperBindingResult(((BindException) e).getBindingResult());
        } else {
            errMessage = e.getMessage();
        }
        log.error("【请求异常】[{}] {}", e.getClass().getSimpleName(), errMessage);
        return CommonJsonResponse.newFailedResponse(errMessage);
    }

    @ExceptionHandler(BaseException.class)
    @ResponseBody
    public CommonJsonResponse<Object> handleServiceException(Exception e) {
        if (e instanceof BizException) {
            BizException bizException = (BizException) e;
            log.error("【业务异常】{}", bizException.getErrMessage());
            return CommonJsonResponse.newErrorResponse(bizException);
        } else if (e instanceof SysException) {
            SysException sysException = (SysException) e;
            log.error("【系统异常】{}", sysException.getErrMessage(), e);
            return CommonJsonResponse.newErrorResponse(sysException);
        }
        return CommonJsonResponse.newErrorResponse();
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public CommonJsonResponse<Object> handleException(Exception e) {
        log.error("【服务异常】{}", e.getMessage(), e);
        return CommonJsonResponse.newErrorResponse();
    }

    /**
     * 包装绑定异常结果
     *
     * @param bindingResult 绑定结果
     * @return 异常结果
     */
    private String wrapperBindingResult(BindingResult bindingResult) {
        return bindingResult.getAllErrors().parallelStream().map(error -> {
            StringBuilder msg = new StringBuilder();
            if (error instanceof FieldError) {
                msg.append(((FieldError) error).getField()).append(": ");
            }
            return msg.append(error.getDefaultMessage() == null ? "" : error.getDefaultMessage());
        }).collect(Collectors.joining(", "));
    }
}
