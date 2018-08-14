package com.gxyj.test.mapper;

import com.gxyj.test.entity.B2cImage;

public interface B2cImageMapper {

    /**
     * 获取用户的头像，如果没有返回null
     * @param memberId
     * @return
     */
    B2cImage selectUserAvatar(String memberId);

	String selectProdIdByImageId(String imageId);
}