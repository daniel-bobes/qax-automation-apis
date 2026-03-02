package com.danielbobes.api.gorest.models.common;

import java.util.Arrays;
import java.util.List;

public class ErrorListResponse {

    private List<SingleError> errorList;

    public ErrorListResponse() {

    }

    public ErrorListResponse(List<SingleError> errorList) {
        this.errorList = errorList;
    }

    public List<SingleError> getErrorList() {
        return errorList;
    }

    public void setErrorList(List<SingleError> errorList) {
        this.errorList = errorList;
    }

    public boolean isEmpty(){
        return getErrorList().isEmpty();
    }

    public static ErrorListResponse valueOf(List<SingleError> errorList){
        return new ErrorListResponse(errorList);
    }

    public static ErrorListResponse valueOf(SingleError[] errorList){
        return new ErrorListResponse(Arrays.asList(errorList));
    }

}
