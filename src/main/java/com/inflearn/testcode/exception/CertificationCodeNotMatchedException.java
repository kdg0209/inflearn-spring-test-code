package com.inflearn.testcode.exception;

public class CertificationCodeNotMatchedException extends RuntimeException {

    public CertificationCodeNotMatchedException() {
        super("자격 증명에 실패하였습니다.");
    }
}
