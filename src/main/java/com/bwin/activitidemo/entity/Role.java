package com.bwin.activitidemo.entity;

import lombok.Data;
import java.io.Serializable;

@Data
public class Role implements Serializable {

    private String id;
    private String enName;
    private String cnName;

}
