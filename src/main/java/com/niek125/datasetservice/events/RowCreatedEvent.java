package com.niek125.datasetservice.events;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@NoArgsConstructor
@Getter
@Setter
public class RowCreatedEvent extends DataEditorEvent {
    private String projectid;
    private Map row;
}
