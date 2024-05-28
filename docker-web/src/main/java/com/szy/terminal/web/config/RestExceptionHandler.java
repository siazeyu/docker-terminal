package com.szy.terminal.web.config;


import com.szy.terminal.web.entity.system.ResultData;
import com.szy.terminal.web.entity.system.ReturnCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {
    /**
     * 默认全局异常处理。
     *
     * @param e 异常
     * @return ResultData
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultData exception(Exception e) {

        e.printStackTrace();
        return ResultData.fail(ReturnCode.RC500.getCode(), e.getMessage());

    }

}
