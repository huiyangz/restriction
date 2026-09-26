package com.dcits.restriction.step;

import com.dcits.restriction.facade.bo.ST002InputBO;
import com.dcits.restriction.facade.bo.ST002OutputBO;

/** ST002 检查是否跨法人 步骤接口 */
public interface IST002 {

    /**
     * 根据账号查询账户的开户机构及法人、根据交易机构查询法人，比较两法人是否一致。
     * 通过与否由输出的 checkResult 表达；本步骤仅做读查询，无本地事务要求。
     *
     * @param input 账号、交易机构（归属机构号）
     * @return checkResult=true 通过，false 不通过
     */
    ST002OutputBO execute(ST002InputBO input);
}
