package com.example.test.controller.exception;

import com.example.test.dto.response.ResponseDTO;
import com.example.test.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Map;

@Controller
@RequestMapping("${server.error.path:${error.path:/error}}")
@RequiredArgsConstructor
public class CustomErrorController implements ErrorController {

    private final ResponseService responseService;

    @RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
    public String errorHtml(HttpServletRequest request, Model model) {
        HttpStatus status = getStatus(request);

        HttpStatus.Series series = status.series();

        model.addAttribute("status", status);

        return "/" + series.value() + "xx";
    }// errorHtml

    @RequestMapping
    public ResponseEntity<ResponseDTO> error(HttpServletRequest request) {
        HttpStatus status = getStatus(request);

        ResponseDTO responseDTO = responseService.getFailResponseDTO(status);

        return new ResponseEntity<>(responseDTO, status);
    }// error

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        try {
            return HttpStatus.valueOf(statusCode);
        }
        catch (Exception ex) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }// try-catch
    }// getStatus

}// CustomErrorController
