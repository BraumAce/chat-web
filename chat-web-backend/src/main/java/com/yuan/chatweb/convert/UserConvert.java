package com.yuan.chatweb.convert;

import com.yuan.chatweb.model.dto.UserDTO;
import com.yuan.chatweb.model.entity.UserDO;
import com.yuan.chatweb.model.vo.UserVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 用户对象转换接口
 *
 * @author BraumAce
 */
@Mapper
public interface UserConvert {
    
    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);
    
    /**
     * UserDO 转 UserDTO
     *
     * @param userDO 用户DO对象
     * @return 用户DTO对象
     */
    UserDTO convertToUserDTO(UserDO userDO);
    
    /**
     * UserDTO 转 UserVO
     *
     * @param userDTO 用户DTO对象
     * @return 用户VO对象
     */
    UserVO convertToUserVO(UserDTO userDTO);
}