package com.szy.terminal.web.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.szy.terminal.web.entity.system.ResultData;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 同意格式优化
 * ResponseBodyAdvice 设置basePackages避免使swagger的返回数据格式化避免Swagger-ui页面无法打开
 */

@RestControllerAdvice(basePackages = {"com.szy.terminal.web.controller"})
public class ResponseAdvice implements ResponseBodyAdvice<Object> {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {

        return true;
    }

    @SneakyThrows
    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {


        if (o instanceof String || o == null) {
            //这段代码一定要加，如果Controller直接返回String的话，SpringBoot是直接返回，故我们需要手动转换成json
            return objectMapper.writeValueAsString(ResultData.success(o));
        }

        if (o instanceof ResultData) {
            return o;
        }

        return ResultData.success(o);
    }
}
