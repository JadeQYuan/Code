
public interface BizAssert extends Assert, ResponseInstance {

    @Override
    default BaseException newException() {
        return new BizException(this);
    }

    @Override
    default BaseException newException(String message) {
        return new BizException(this, message);
    }

    @Override
    default BaseException newException(Throwable cause) {
        return new BizException(this, cause);
    }
}
