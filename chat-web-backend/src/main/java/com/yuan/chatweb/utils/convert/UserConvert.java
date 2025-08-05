package com.yuan.chatweb.utils.convert;

import com.alibaba.fastjson2.JSON;
import com.yuan.chatweb.model.dto.ExtraInfo;
import com.yuan.chatweb.model.dto.UserDTO;
import com.yuan.chatweb.model.entity.UserDO;
import com.yuan.chatweb.model.vo.UserVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

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
//    UserDTO convertToUserDTO(UserDO userDO);
    
    /**
     * UserDTO 转 UserVO
     *
     * @param userDTO 用户DTO对象
     * @return 用户VO对象
     */
    UserVO convertToUserVO(UserDTO userDTO);

    default UserDTO convertToUserDTO(UserDO userDO) {
        if (userDO == null) {
            return null;
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setId(userDO.getId());
        userDTO.setUsername(userDO.getUsername());
        userDTO.setEmail(userDO.getEmail());
        userDTO.setNickname(userDO.getNickname());
        userDTO.setAvatar(userDO.getAvatar());
        userDTO.setExtraInfo(JSON.parseObject(userDO.getExtraInfo(), ExtraInfo.class));
        userDTO.setNewPassword(userDO.getPassword());
        userDTO.setCreatedAt(userDO.getCreatedAt());
        userDTO.setUpdatedAt(userDO.getUpdatedAt());

        return userDTO;
    }
}