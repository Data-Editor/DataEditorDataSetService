package com.niek125.datasetservice.events;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataEditorEvent {
    private String creator;
    private String interest;

    public DataEditorEvent() {
        this.creator = "message-consumer";
    }
}
