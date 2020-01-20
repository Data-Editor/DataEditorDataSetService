package com.niek125.datasetservice.events;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ProjectDeletedEvent extends DataEditorEvent {
    private String projectid;
}
