package com.example.test.controller.exception;

import com.example.test.dto.response.ResponseDTO;
import com.example.test.exception.RoleApiException;
import com.example.test.exception.role.ChildRoleExistException;
import com.example.test.exception.role.RoleAlreadyExistException;
import com.example.test.service.ResponseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.coyote.Response;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackages = "com.example.test.controller.admin")
@RequiredArgsConstructor
@Log4j2
public class AdminPageExceptionController {

    private final ResponseService responseService;

    private final String BASIC_ERROR_PATH = "/admin/error/basic";

    @ExceptionHandler(value = RoleApiException.class)
    public String roleApiException(RoleApiException exception, Model model) {
        ResponseDTO responseDTO = null;

        if(exception instanceof RoleAlreadyExistException) {
            responseDTO = responseService.getFailResponseDTO("api.role.existalready.code", "api.role.existalready.msg");
        }else if(exception instanceof ChildRoleExistException) {
            responseDTO = responseService.getFailResponseDTO("api.role.childroleexist.code", "api.role.childroleexist.msg");
        }// if-else

        model.addAttribute("responseDTO", responseDTO);

        return BASIC_ERROR_PATH;
    }// roleApiException

    @ExceptionHandler(value = Exception.class)
    public String etcException(Exception e, Model model) {
        e.printStackTrace();

        ResponseDTO responseDTO = responseService.getFailResponseDTO("fail.code", "fail.msg", e);

        model.addAttribute("responseDTO", responseDTO);

        return BASIC_ERROR_PATH;
    }// etcException

}// AdminPageExceptionController














