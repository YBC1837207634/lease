package com.gong.lease.web.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gong.lease.model.entity.ApartmentFacility;
import com.gong.lease.web.app.mapper.ApartmentFacilityMapper;
import com.gong.lease.web.app.service.ApartmentFacilityService;
import org.springframework.stereotype.Service;

/**
* @author liubo
* @description 针对表【apartment_facility(公寓&配套关联表)】的数据库操作Service实现
* @createDate 2023-07-26 11:12:39
*/
@Service
public class ApartmentFacilityServiceImpl extends ServiceImpl<ApartmentFacilityMapper, ApartmentFacility>
    implements ApartmentFacilityService{
}




