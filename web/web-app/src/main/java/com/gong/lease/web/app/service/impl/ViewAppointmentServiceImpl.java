package com.gong.lease.web.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gong.lease.model.entity.ViewAppointment;
import com.gong.lease.web.app.mapper.ViewAppointmentMapper;
import com.gong.lease.web.app.service.ApartmentInfoService;
import com.gong.lease.web.app.service.ViewAppointmentService;
import com.gong.lease.web.app.vo.apartment.ApartmentItemVo;
import com.gong.lease.web.app.vo.appointment.AppointmentDetailVo;
import com.gong.lease.web.app.vo.appointment.AppointmentItemVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liubo
 * @description 针对表【view_appointment(预约看房信息表)】的数据库操作Service实现
 * @createDate 2023-07-26 11:12:39
 */
@Service
public class ViewAppointmentServiceImpl extends ServiceImpl<ViewAppointmentMapper, ViewAppointment>
        implements ViewAppointmentService {

    @Autowired
    private ViewAppointmentMapper viewAppointmentMapper;

    @Autowired
    private ApartmentInfoService apartmentInfoService;

    @Override
    public List<AppointmentItemVo> listItem(Long userId) {
        return viewAppointmentMapper.listItem(userId);
    }

    @Override
    public AppointmentDetailVo getDetailById(Long id) {
        ViewAppointment viewAppointment = viewAppointmentMapper.selectById(id);
        ApartmentItemVo apartmentItemVo = apartmentInfoService.selectApartmentItemVoById(viewAppointment.getApartmentId());

        AppointmentDetailVo appointmentDetailVo = new AppointmentDetailVo();
        BeanUtils.copyProperties(viewAppointment, appointmentDetailVo);
        appointmentDetailVo.setApartmentItemVo(apartmentItemVo);
        return appointmentDetailVo;
    }
}




