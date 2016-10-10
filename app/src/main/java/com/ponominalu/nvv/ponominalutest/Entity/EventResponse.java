package com.ponominalu.nvv.ponominalutest.Entity;


import com.ponominalu.nvv.ponominalutest.dao.Event;

import java.util.ArrayList;
import java.util.List;


public class EventResponse {

    private List<Event> message;

    private Long code;

    private Long ts;

    public List<Event> getMessage() {
        if(message==null){
            message = new ArrayList<>();
        }
        return message;
    }

    public void setMessage(List<Event> message) {
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
