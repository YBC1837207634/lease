package com.gong.lease.web.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gong.lease.model.entity.RoomInfo;
import com.gong.lease.web.admin.vo.room.RoomDetailVo;
import com.gong.lease.web.admin.vo.room.RoomItemVo;
import com.gong.lease.web.admin.vo.room.RoomQueryVo;
import com.gong.lease.web.admin.vo.room.RoomSubmitVo;

/**
* @author liubo
* @description 针对表【room_info(房间信息表)】的数据库操作Service
* @createDate 2023-07-24 15:48:00
*/
public interface RoomInfoService extends IService<RoomInfo> {

    IPage<RoomItemVo> pageItem(Page<RoomItemVo> page, RoomQueryVo queryVo);

    void saveOrUpdateRoom(RoomSubmitVo roomSubmitVo);

    RoomDetailVo getRoomDetailById(Long id);

    void removeRoomById(Long id);
}
