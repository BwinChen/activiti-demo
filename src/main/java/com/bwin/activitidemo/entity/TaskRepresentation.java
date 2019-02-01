package com.bwin.activitidemo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TaskRepresentation {

    private String id;
    private String name;
    private String processInstanceId;

}
