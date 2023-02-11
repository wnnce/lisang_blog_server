package com.zeroxn.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tag implements Serializable {
    private Integer id;
    private String name;
    private LocalDateTime createTime;
    private Integer status;
    private Integer articleNum;

    public Tag(Integer id) {
        this.id = id;
    }
}
