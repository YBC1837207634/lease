package com.gong.lease.web.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gong.lease.model.entity.RoomInfo;
import com.gong.lease.web.admin.vo.room.RoomItemVo;
import com.gong.lease.web.admin.vo.room.RoomQueryVo;

/**
* @author liubo
* @description 针对表【room_info(房间信息表)】的数据库操作Mapper
* @createDate 2023-07-24 15:48:00
* @Entity com.gong.lease.model.RoomInfo
*/
public interface RoomInfoMapper extends BaseMapper<RoomInfo> {

    IPage<RoomItemVo> pageItem(Page<RoomItemVo> page, RoomQueryVo queryVo);

}




