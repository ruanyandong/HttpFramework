package com.example.httpframework.model;

public class ResponseClass {
    private int resultCode;
    private String reason;

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }


    @Override
    public String toString() {
        return "resultCode = "+resultCode+"\n"+"reason = "+reason;
    }
}
