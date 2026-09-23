package com.dcits.restriction.service.components;

import com.dcits.restriction.enums.BranchType;
import com.dcits.restriction.enums.City;
import com.dcits.restriction.enums.Country;
import com.dcits.restriction.enums.District;
import com.dcits.restriction.enums.HierarchyCode;
import com.dcits.restriction.enums.ProfitCenter;
import com.dcits.restriction.enums.TranBranch;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import com.dcits.restriction.entity.FmBranch;
import com.dcits.restriction.entity.FmBranchExample;
import com.dcits.restriction.facade.components.IFmBranchBcc;
import com.dcits.restriction.facade.eo.FmBranchEO;
import com.dcits.restriction.repo.FmBranchMapper;
import com.dcits.restriction.service.utils.FmBranchValueUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FmBranchBasisCpnt implements IFmBranchBcc {
    @Autowired
    FmBranchMapper fmBranchMapper;

    @Override
    public long countByEo(FmBranchEO eo) {
        FmBranchExample example = FmBranchValueUtil.eoToEntityExample(eo);
        return fmBranchMapper.countByExample(example);
    }

    @Override
    public int removeByEo(FmBranchEO eo) {
        FmBranchExample example = FmBranchValueUtil.eoToEntityExample(eo);
        return fmBranchMapper.deleteByExample(example);
    }

    @Override
    public int removeByPrimaryKey(String branch) {
        return fmBranchMapper.deleteByPrimaryKey(branch);
    }

    @Override
    public int create(FmBranchEO eo) {
        FmBranch row = FmBranchValueUtil.eoToEntity(eo);
        return fmBranchMapper.insert(row);
    }

    @Override
    public int createSelective(FmBranchEO eo) {
        FmBranch row = FmBranchValueUtil.eoToEntity(eo);
        return fmBranchMapper.insertSelective(row);
    }

    @Override
    public List<FmBranchEO> findByEo(FmBranchEO eo) {
        FmBranchExample example = FmBranchValueUtil.eoToEntityExample(eo);
        List<FmBranchEO> result = new ArrayList<>();
        List<FmBranch> dbResult = fmBranchMapper.selectByExample(example);
        for (FmBranch item : dbResult) {
            result.add(FmBranchValueUtil.entityToEo(item));
        }
        return result;
    }

    @Override
    public FmBranchEO findByPrimaryKey(String branch) {
        return FmBranchValueUtil.entityToEo(fmBranchMapper.selectByPrimaryKey(branch));
    }

    @Override
    public int modifyByPrimaryKeySelective(FmBranchEO eo) {
        FmBranch row = FmBranchValueUtil.eoToEntity(eo);
        return fmBranchMapper.updateByPrimaryKeySelective(row);
    }

    @Override
    public int modifyByPrimaryKey(FmBranchEO eo) {
        FmBranch row = FmBranchValueUtil.eoToEntity(eo);
        return fmBranchMapper.updateByPrimaryKey(row);
    }

    FmBranchEO byBranch(TranBranch branch) {
        FmBranchEO eo = new FmBranchEO();
        eo.setBranch(branch);
        return eo;
    }

    /**根据归属机构号查询表《机构信息表(FM_BRANCH)》**/
    public FmBranchEO findByBranch(TranBranch branch) {
        List<FmBranchEO> eos = findByEo(byBranch(branch));
        return eos.isEmpty() ? null : eos.get(0);
    }
}