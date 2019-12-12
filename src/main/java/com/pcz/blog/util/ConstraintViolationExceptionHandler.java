package com.pcz.blog.util;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author picongzhi
 */
public class ConstraintViolationExceptionHandler {
    /**
     * 获取批量异常信息
     *
     * @param constraintViolationException 约束异常
     * @return 遗产信息
     */
    public static String getMessage(ConstraintViolationException constraintViolationException) {
        List<String> messageList = new ArrayList<>();
        for (ConstraintViolation<?> constraintViolation : constraintViolationException.getConstraintViolations()) {
            messageList.add(constraintViolation.getMessage());
        }

        return StringUtils.join(messageList.toArray());
    }
}
