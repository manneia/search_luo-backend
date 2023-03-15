package com.yupi.springbootinit.model.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 图片
 *
 * @author lkx
 * @version 1.0.0
 * @Description
 */
@Data
public class Picture implements Serializable {
    private static final long serialVersionUID = 4473334845302675835L;
    private String title;
    private String url;

}
