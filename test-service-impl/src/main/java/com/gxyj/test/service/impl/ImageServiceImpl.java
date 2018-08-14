package com.gxyj.test.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gxyj.test.dto.ImageDTO;
import com.gxyj.test.service.ImageService;

/**
 * 
 * @author xuliangyong
 *
 */
@Service("imageService")
public class ImageServiceImpl implements ImageService {
	
	/**
	 * 【强制】 slf4j
	 */
	private static final Logger log = LoggerFactory.getLogger(ImageServiceImpl.class);

	
	
	
	@Override
	@Transactional(readOnly=true)
	public ImageDTO getImage(String imageId) {
		String prodId = "1";
		
		ImageDTO imageDTO = new ImageDTO();
		imageDTO.setProdId(prodId);
		imageDTO.setImgUrl("this/is/test/image.jpg");
		imageDTO.setImgName("测试图片");
		
		log.info(prodId);
		
		return imageDTO;
	}

}
