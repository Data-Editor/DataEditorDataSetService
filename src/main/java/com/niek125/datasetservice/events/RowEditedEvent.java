package com.niek125.datasetservice.events;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@NoArgsConstructor
@Getter
@Setter
public class RowEditedEvent extends DataEditorEvent {
    private String projectid;
    private int rownumber;
    private Map row;
}
