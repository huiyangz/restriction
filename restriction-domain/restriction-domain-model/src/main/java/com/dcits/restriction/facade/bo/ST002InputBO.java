package com.dcits.restriction.facade.bo;

import com.dcits.restriction.enums.TranBranch;

/** ST002 检查是否跨法人 步骤输入 */
public class ST002InputBO {
    /** 账号 */
    private String baseAcctNo;
    /** 归属机构号（交易机构） */
    private TranBranch branch;

    public String getBaseAcctNo() {
        return baseAcctNo;
    }

    public void setBaseAcctNo(String baseAcctNo) {
        this.baseAcctNo = baseAcctNo;
    }

    public TranBranch getBranch() {
        return branch;
    }

    public void setBranch(TranBranch branch) {
        this.branch = branch;
    }
}
