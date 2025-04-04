package com.gong.lease.web.app.vo.appointment;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gong.lease.model.enums.AppointmentStatus;
import com.gong.lease.web.app.vo.graph.GraphVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Schema(description = "APP端预约看房基本信息")
public class AppointmentItemVo {

    @Schema(description = "预约Id")
    private Long id;

    @Schema(description = "预约公寓名称")
    private String apartmentName;

    @Schema(description = "公寓图片列表")
    private List<GraphVo> graphVoList;

    @Schema(description = "预约时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date appointmentTime;

    @Schema(description = "当前预约状态")
    private AppointmentStatus appointmentStatus;
}
