package com.luo.springbootinit.datasource;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luo.springbootinit.common.ErrorCode;
import com.luo.springbootinit.constant.CommonConstant;
import com.luo.springbootinit.constant.UserConstant;
import com.luo.springbootinit.exception.BusinessException;
import com.luo.springbootinit.mapper.UserMapper;
import com.luo.springbootinit.model.dto.user.UserQueryRequest;
import com.luo.springbootinit.model.entity.User;
import com.luo.springbootinit.model.enums.UserRoleEnum;
import com.luo.springbootinit.model.vo.LoginUserVO;
import com.luo.springbootinit.model.vo.UserVO;
import com.luo.springbootinit.service.UserService;
import com.luo.springbootinit.utils.SqlUtils;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户服务实现
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@Service
@Slf4j
public class UserDataSource  implements DataSource<UserVO> {
    @Resource
    private UserService userService;

    @Override
    public Page<UserVO> doSearch(String searchText, long pageNum, long pageSize) {
        UserQueryRequest userQueryRequest = new UserQueryRequest();
        userQueryRequest.setUserName(searchText);
        userQueryRequest.setCurrent(pageNum);
        userQueryRequest.setPageSize(pageSize);
        return userService.listUserVoByPage(userQueryRequest);
    }
}
