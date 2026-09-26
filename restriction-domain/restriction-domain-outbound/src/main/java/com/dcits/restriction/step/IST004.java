package com.dcits.restriction.step;

import com.dcits.restriction.facade.bo.ST004InputBO;
import com.dcits.restriction.facade.bo.ST004OutputBO;

/**
 * ST004 登记账户限制信息。
 * 登记账户的【限制信息】（账号、账户限制类型、开始日期、结束日期、限制期限、周期类型、交易日期）。
 *
 * <p>事务要求：本步骤涉及本地数据库新增（RB_BUS_RESTRAINTS），实现方法已标注
 * {@code @Transactional}，调用方须经 Spring 代理调用以获得事务支持。
 */
public interface IST004 {

    /**
     * 登记账户限制信息。
     *
     * @param input 登记输入（8 个字段均必填）
     * @return 处理结果状态，无额外业务输出字段
     */
    ST004OutputBO execute(ST004InputBO input);
}
