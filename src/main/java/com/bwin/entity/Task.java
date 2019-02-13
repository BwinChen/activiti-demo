package com.bwin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Task implements Serializable {

    private String id;
    private String name;
    private String processInstanceId;

}
