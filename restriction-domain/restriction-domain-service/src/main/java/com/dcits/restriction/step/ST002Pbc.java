package com.dcits.restriction.step;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dcits.restriction.enums.TranBranch;
import com.dcits.restriction.facade.bo.ST002InputBO;
import com.dcits.restriction.facade.bo.ST002OutputBO;
import com.dcits.restriction.facade.components.IFmBranchBcc;
import com.dcits.restriction.facade.components.IRbBusAcctBcc;
import com.dcits.restriction.facade.eo.FmBranchEO;
import com.dcits.restriction.facade.eo.RbBusAcctEO;

/** ST002 检查是否跨法人 */
@Service
public class ST002Pbc implements IST002 {
    @Autowired
    IRbBusAcctBcc rbBusAcctBcc;
    @Autowired
    IFmBranchBcc fmBranchBcc;

    @Override
    public ST002OutputBO execute(ST002InputBO input) {
        ST002OutputBO output = new ST002OutputBO();

        // 子步骤1 获取账户法人：根据{账号}查询【账户信息】获取账户的开户机构
        RbBusAcctEO acctCondition = new RbBusAcctEO();
        acctCondition.setBaseAcctNo(input.getBaseAcctNo());
        List<RbBusAcctEO> acctList = rbBusAcctBcc.findByEo(acctCondition);
        if (acctList == null || acctList.size() != 1) {
            // 查询无记录或多条记录，检查结果为"不通过"
            return finish(output, Boolean.FALSE);
        }
        TranBranch acctBranch = acctList.get(0).getAcctBranch();
        // 子步骤1 获取账户法人：根据开户机构查询【机构信息】获取账户的法人
        FmBranchEO acctBranchEo = fmBranchBcc.findByBranch(acctBranch);
        if (acctBranchEo == null) {
            // 查询无记录，检查结果为"不通过"
            return finish(output, Boolean.FALSE);
        }
        String acctCompany = acctBranchEo.getCompany();

        // 子步骤2 获取交易机构法人：根据{交易机构}查询【机构信息】获取交易机构的法人
        FmBranchEO tranBranchEo = fmBranchBcc.findByBranch(input.getBranch());
        if (tranBranchEo == null) {
            // 查询无记录，检查结果为"不通过"
            return finish(output, Boolean.FALSE);
        }
        String tranCompany = tranBranchEo.getCompany();

        // 子步骤3 检查账户法人：账户法人与交易机构法人一致时通过，否则不通过
        return finish(output, Objects.equals(acctCompany, tranCompany));
    }

    /** 按检查结论结束步骤：检查通过与否均为步骤执行成功 */
    private ST002OutputBO finish(ST002OutputBO output, Boolean checkResult) {
        output.setCheckResult(checkResult);
        output.setSucceed(true);
        return output;
    }
}
