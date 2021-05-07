package com.demo.order.config;

import com.demo.common.ApiResponseCode;
import com.demo.common.ApiResponseWrapper;
import com.demo.common.BizException;
import com.demo.common.Result;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.Objects;

/**
 * 统一异常处理
 */
@RestControllerAdvice
public class OrderExceptionHandler {

    private static final Logger LOGGER = LogManager.getLogger(OrderExceptionHandler.class);

//    //参数异常处理
//    @OrderExceptionHandler(BindException.class)
//    public Object bindException(BindException e) {
//        Result result = new Result();
//        result.setResultCode(ApiResponseCode.PARA_ERR.getCode());
//        BindingResult bindingResult = e.getBindingResult();
//        result.setMessage(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
//        return result;
//    }
//
//    //参数异常处理
//    @OrderExceptionHandler(MethodArgumentNotValidException.class)
//    public Object bindException(MethodArgumentNotValidException e) {
//        Result result = new Result();
//        result.setResultCode(ApiResponseCode.PARA_ERR.getCode());
//        BindingResult bindingResult = e.getBindingResult();
//        result.setMessage(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
//        return result;
//    }
//
//    @OrderExceptionHandler(Exception.class)
//    public Result handleException(Exception e, HttpServletRequest req) {
//        LOGGER.error(e.getMessage(), e);
//        return wrapException(e);
//    }

    //自定义异常处理
    private Result wrapException(Exception exception) {
        Result result = null;
        if (exception instanceof BizException) {
            result = ApiResponseWrapper.wrapException((BizException) exception);
        }
        else if (exception instanceof RuntimeException) {
            LOGGER.error(exception.getMessage(), exception);
            result = ApiResponseWrapper.wrap(ApiResponseCode.FAILURE,"失败"); //exception.getMessage()
        }
        else if (exception instanceof ParseException) {
            result = ApiResponseWrapper.wrap(ApiResponseCode.PARSEFAIL);
        } else {
            result = ApiResponseWrapper.wrap(ApiResponseCode.FAILURE);
        }
        return result;
    }
}
