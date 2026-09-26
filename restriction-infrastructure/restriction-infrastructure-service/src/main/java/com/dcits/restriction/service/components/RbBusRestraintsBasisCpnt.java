package com.dcits.restriction.service.components;

import com.dcits.restriction.enums.AcctInternalKeyType;
import com.dcits.restriction.enums.ResAcctRange;
import com.dcits.restriction.enums.RestraintLevel;
import com.dcits.restriction.enums.RestraintSource;
import com.dcits.restriction.enums.RestraintsStatus;
import com.dcits.restriction.enums.RestraintType;
import com.dcits.restriction.enums.SourceModule;
import com.dcits.restriction.enums.TermType;
import com.dcits.restriction.enums.ThawDocumentType2;
import com.dcits.restriction.enums.TranBranch;
import java.math.BigDecimal;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import com.dcits.restriction.entity.RbBusRestraints;
import com.dcits.restriction.entity.RbBusRestraintsExample;
import com.dcits.restriction.facade.components.IRbBusRestraintsBcc;
import com.dcits.restriction.facade.eo.RbBusRestraintsEO;
import com.dcits.restriction.repo.RbBusRestraintsMapper;
import com.dcits.restriction.service.utils.RbBusRestraintsValueUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RbBusRestraintsBasisCpnt implements IRbBusRestraintsBcc {
    @Autowired
    RbBusRestraintsMapper rbBusRestraintsMapper;

    @Override
    public long countByEo(RbBusRestraintsEO eo) {
        RbBusRestraintsExample example = RbBusRestraintsValueUtil.eoToEntityExample(eo);
        return rbBusRestraintsMapper.countByExample(example);
    }

    @Override
    public int removeByEo(RbBusRestraintsEO eo) {
        RbBusRestraintsExample example = RbBusRestraintsValueUtil.eoToEntityExample(eo);
        return rbBusRestraintsMapper.deleteByExample(example);
    }

    @Override
    public int removeByPrimaryKey(String resSeqNo) {
        return rbBusRestraintsMapper.deleteByPrimaryKey(resSeqNo);
    }

    @Override
    public int create(RbBusRestraintsEO eo) {
        RbBusRestraints row = RbBusRestraintsValueUtil.eoToEntity(eo);
        return rbBusRestraintsMapper.insert(row);
    }

    @Override
    public int createSelective(RbBusRestraintsEO eo) {
        RbBusRestraints row = RbBusRestraintsValueUtil.eoToEntity(eo);
        return rbBusRestraintsMapper.insertSelective(row);
    }

    @Override
    public List<RbBusRestraintsEO> findByEo(RbBusRestraintsEO eo) {
        RbBusRestraintsExample example = RbBusRestraintsValueUtil.eoToEntityExample(eo);
        List<RbBusRestraintsEO> result = new ArrayList<>();
        List<RbBusRestraints> dbResult = rbBusRestraintsMapper.selectByExample(example);
        for (RbBusRestraints item : dbResult) {
            result.add(RbBusRestraintsValueUtil.entityToEo(item));
        }
        return result;
    }

    @Override
    public RbBusRestraintsEO findByPrimaryKey(String resSeqNo) {
        return RbBusRestraintsValueUtil.entityToEo(rbBusRestraintsMapper.selectByPrimaryKey(resSeqNo));
    }

    @Override
    public int modifyByPrimaryKeySelective(RbBusRestraintsEO eo) {
        RbBusRestraints row = RbBusRestraintsValueUtil.eoToEntity(eo);
        return rbBusRestraintsMapper.updateByPrimaryKeySelective(row);
    }

    @Override
    public int modifyByPrimaryKey(RbBusRestraintsEO eo) {
        RbBusRestraints row = RbBusRestraintsValueUtil.eoToEntity(eo);
        return rbBusRestraintsMapper.updateByPrimaryKey(row);
    }
}