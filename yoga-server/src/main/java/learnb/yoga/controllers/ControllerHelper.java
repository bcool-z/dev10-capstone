package learnb.yoga.controllers;

import learnb.yoga.validation.Result;
import org.springframework.http.HttpStatus;

public class ControllerHelper {


    public static HttpStatus getStatus(Result<?> result, HttpStatus statusDefault) {
        switch (result.getStatus()) {
            case INVALID:
                return HttpStatus.PRECONDITION_FAILED;
            case DUPLICATE:
                return HttpStatus.FORBIDDEN;
            case NOT_FOUND:
                return HttpStatus.NOT_FOUND;
        }
        return statusDefault;
    }

}
