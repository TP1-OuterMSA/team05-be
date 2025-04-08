package com.mjmeal.mj_cafeteria_team_feedback_be.common.response.code;

import lombok.Getter;

@Getter
public enum StatusCode {

    COMMON("COMMON"),
    REVIEW("REVIEW"),
    ANSWER("ANSWER"),
    LIKE("LIKE"),
    MENU("MENU"),
    QUESTION("QUESTION"),
    USER("USER");

    private final String prefix;

    StatusCode(String prefix){
        this.prefix = prefix;
    }

    public String getCode(int codeNumber){
        return this.prefix + codeNumber;
    }
}
