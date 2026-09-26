package com.dcits.restriction.step;

import com.dcits.restriction.facade.bo.ST001InputBO;
import com.dcits.restriction.facade.bo.ST001OutputBO;

/** ST001 检查限制类型：根据{限制类型}查询【存款限制类型表】获取[限制类型信息]，检查限制类型存在且状态为"A-有效"。纯只读步骤，无本地写入，无事务要求 */
public interface IST001 {

    /** 执行检查限制类型步骤 */
    ST001OutputBO execute(ST001InputBO input);
}
