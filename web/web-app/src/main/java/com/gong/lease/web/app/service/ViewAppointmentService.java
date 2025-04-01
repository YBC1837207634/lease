package com.gong.lease.web.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gong.lease.model.entity.ViewAppointment;
import com.gong.lease.web.app.vo.appointment.AppointmentDetailVo;
import com.gong.lease.web.app.vo.appointment.AppointmentItemVo;

import java.util.List;

/**
* @author liubo
* @description 针对表【view_appointment(预约看房信息表)】的数据库操作Service
* @createDate 2023-07-26 11:12:39
*/
public interface ViewAppointmentService extends IService<ViewAppointment> {
    List<AppointmentItemVo> listItem(Long userId);

    AppointmentDetailVo getDetailById(Long id);
}
