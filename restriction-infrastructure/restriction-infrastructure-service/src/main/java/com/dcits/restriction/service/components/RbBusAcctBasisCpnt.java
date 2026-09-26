package com.dcits.restriction.service.components;

import com.dcits.restriction.enums.AcctCcy;
import com.dcits.restriction.enums.AcctNatureNo;
import com.dcits.restriction.enums.AcctRiskLevel;
import com.dcits.restriction.enums.AcctStatus;
import com.dcits.restriction.enums.AcctVerifyFlag;
import com.dcits.restriction.enums.AcctVerifyResult;
import com.dcits.restriction.enums.AllDepInd;
import com.dcits.restriction.enums.AllDraInd;
import com.dcits.restriction.enums.AllDraRange;
import com.dcits.restriction.enums.AnnualStatus;
import com.dcits.restriction.enums.AutoRenewInd;
import com.dcits.restriction.enums.BalType;
import com.dcits.restriction.enums.CheckCertificateType;
import com.dcits.restriction.enums.ClientType;
import com.dcits.restriction.enums.DepositNature;
import com.dcits.restriction.enums.FarmerFlag;
import com.dcits.restriction.enums.FixedCall;
import com.dcits.restriction.enums.IntIndFlag;
import com.dcits.restriction.enums.ManageType;
import com.dcits.restriction.enums.OsaFlag;
import com.dcits.restriction.enums.RbAcctType;
import com.dcits.restriction.enums.RbBusAcctPurpose;
import com.dcits.restriction.enums.RenewMethod;
import com.dcits.restriction.enums.SimpleAcct;
import com.dcits.restriction.enums.SourceType;
import com.dcits.restriction.enums.SpecAcctFlag;
import com.dcits.restriction.enums.TermType;
import com.dcits.restriction.enums.TranBranch;
import java.math.BigDecimal;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import com.dcits.restriction.entity.RbBusAcct;
import com.dcits.restriction.entity.RbBusAcctExample;
import com.dcits.restriction.facade.components.IRbBusAcctBcc;
import com.dcits.restriction.facade.eo.RbBusAcctEO;
import com.dcits.restriction.repo.RbBusAcctMapper;
import com.dcits.restriction.service.utils.RbBusAcctValueUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RbBusAcctBasisCpnt implements IRbBusAcctBcc {
    @Autowired
    RbBusAcctMapper rbBusAcctMapper;

    @Override
    public long countByEo(RbBusAcctEO eo) {
        RbBusAcctExample example = RbBusAcctValueUtil.eoToEntityExample(eo);
        return rbBusAcctMapper.countByExample(example);
    }

    @Override
    public int removeByEo(RbBusAcctEO eo) {
        RbBusAcctExample example = RbBusAcctValueUtil.eoToEntityExample(eo);
        return rbBusAcctMapper.deleteByExample(example);
    }

    @Override
    public int removeByPrimaryKey(Integer internalKey) {
        return rbBusAcctMapper.deleteByPrimaryKey(internalKey);
    }

    @Override
    public int create(RbBusAcctEO eo) {
        RbBusAcct row = RbBusAcctValueUtil.eoToEntity(eo);
        return rbBusAcctMapper.insert(row);
    }

    @Override
    public int createSelective(RbBusAcctEO eo) {
        RbBusAcct row = RbBusAcctValueUtil.eoToEntity(eo);
        return rbBusAcctMapper.insertSelective(row);
    }

    @Override
    public List<RbBusAcctEO> findByEo(RbBusAcctEO eo) {
        RbBusAcctExample example = RbBusAcctValueUtil.eoToEntityExample(eo);
        List<RbBusAcctEO> result = new ArrayList<>();
        List<RbBusAcct> dbResult = rbBusAcctMapper.selectByExample(example);
        for (RbBusAcct item : dbResult) {
            result.add(RbBusAcctValueUtil.entityToEo(item));
        }
        return result;
    }

    @Override
    public RbBusAcctEO findByPrimaryKey(Integer internalKey) {
        return RbBusAcctValueUtil.entityToEo(rbBusAcctMapper.selectByPrimaryKey(internalKey));
    }

    @Override
    public int modifyByPrimaryKeySelective(RbBusAcctEO eo) {
        RbBusAcct row = RbBusAcctValueUtil.eoToEntity(eo);
        return rbBusAcctMapper.updateByPrimaryKeySelective(row);
    }

    @Override
    public int modifyByPrimaryKey(RbBusAcctEO eo) {
        RbBusAcct row = RbBusAcctValueUtil.eoToEntity(eo);
        return rbBusAcctMapper.updateByPrimaryKey(row);
    }
}