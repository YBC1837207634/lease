package com.gong.lease.web.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gong.lease.model.entity.AttrKey;
import com.gong.lease.web.admin.vo.attr.AttrKeyVo;

import java.util.List;

/**
* @author liubo
* @description 针对表【attr_key(房间基本属性表)】的数据库操作Service
* @createDate 2023-07-24 15:48:00
*/
public interface AttrKeyService extends IService<AttrKey> {

    List<AttrKeyVo> listAttrInfo();

}
