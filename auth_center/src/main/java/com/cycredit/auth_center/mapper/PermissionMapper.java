package com.cycredit.auth_center.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author helang
 * @date 2021/5/11
 */
@Component
@Mapper
public interface PermissionMapper extends BaseMapper {
    /**
     * 查询用户拥有权限
     *
     * @param id
     * @return
     */
    @Select("select m.perms from menu m left join role_menu rm on m.id = rm.menu_id " +
            "where rm.role_id = #{roleId}")
    List<String> findByUser(@Param("roleId") Long id);
}
