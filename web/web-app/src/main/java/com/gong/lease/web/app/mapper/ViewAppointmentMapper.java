package com.gong.lease.web.app.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gong.lease.model.entity.ViewAppointment;
import com.gong.lease.web.app.vo.appointment.AppointmentItemVo;

import java.util.List;

/**
* @author liubo
* @description 针对表【view_appointment(预约看房信息表)】的数据库操作Mapper
* @createDate 2023-07-26 11:12:39
* @Entity com.gong.lease.model.entity.ViewAppointment
*/
public interface ViewAppointmentMapper extends BaseMapper<ViewAppointment> {

    List<AppointmentItemVo> listItem(Long userId);
}




