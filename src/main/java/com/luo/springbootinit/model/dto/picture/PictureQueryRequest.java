package com.luo.springbootinit.model.dto.picture;

import com.luo.springbootinit.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author lkx
 * @version 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PictureQueryRequest extends PageRequest implements Serializable {

    /**
     * 搜索词
     */
    private String searchText;

    private static final long serialVersionUID = -688952969092604698L;
}
