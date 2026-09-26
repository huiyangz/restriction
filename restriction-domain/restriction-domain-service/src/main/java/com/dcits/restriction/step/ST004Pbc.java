package com.dcits.restriction.step;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dcits.restriction.facade.bo.ST004InputBO;
import com.dcits.restriction.facade.bo.ST004OutputBO;
import com.dcits.restriction.facade.components.IRbBusRestraintsBcc;
import com.dcits.restriction.facade.eo.RbBusRestraintsEO;

/**
 * ST004 登记账户限制信息。
 * 登记账户的【限制信息】：将账号、账户限制类型、开始日期、结束日期、限制期限、周期类型、交易日期
 * 写入对公存款账户限制表 RB_BUS_RESTRAINTS。
 *
 * <p>实体绑定依据需求豁免记录：输入 7 个字段与 RbBusRestraintsEO 同名字段逐一对应；
 * runDate 及 EO 其余必填字段的值来源 SPEC 未定义，不做映射与赋值。
 */
@Service
public class ST004Pbc implements IST004 {

    private final IRbBusRestraintsBcc rbBusRestraintsBcc;

    public ST004Pbc(IRbBusRestraintsBcc rbBusRestraintsBcc) {
        this.rbBusRestraintsBcc = rbBusRestraintsBcc;
    }

    /**
     * 登记账户限制信息，成功返回 succeed=true，错误字段为 null。
     */
    @Override
    @Transactional
    public ST004OutputBO execute(ST004InputBO input) {
        RbBusRestraintsEO eo = new RbBusRestraintsEO();
        eo.setBaseAcctNo(input.getBaseAcctNo());
        eo.setRestraintType(input.getRestraintType());
        eo.setStartDate(input.getStartDate());
        eo.setEndDate(input.getEndDate());
        eo.setTerm(input.getTerm());
        eo.setTermType(input.getTermType());
        eo.setTranDate(input.getTranDate());
        rbBusRestraintsBcc.createSelective(eo);

        ST004OutputBO output = new ST004OutputBO();
        output.setSucceed(true);
        return output;
    }
}
