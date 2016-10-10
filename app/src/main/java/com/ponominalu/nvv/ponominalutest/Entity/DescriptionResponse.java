package com.ponominalu.nvv.ponominalutest.Entity;

import com.ponominalu.nvv.ponominalutest.dao.*;
import com.ponominalu.nvv.ponominalutest.dao.Category;

import java.util.List;


public class DescriptionResponse {

    private Description message;

    private Long code;

    private Long ts;

    public Description getMessage() {
        return message;
    }

    public void setMessage(Description message) {
        this.message = message;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public Long getTs() {
        return ts;
    }

    public void setTs(Long ts) {
        this.ts = ts;
    }
}
