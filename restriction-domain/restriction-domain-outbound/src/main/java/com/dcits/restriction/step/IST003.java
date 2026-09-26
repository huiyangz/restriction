package com.dcits.restriction.step;

import com.dcits.restriction.facade.bo.ST003InputBO;
import com.dcits.restriction.facade.bo.ST003OutputBO;

/**
 * ST003 检查增加限制起始日期
 *
 * <p>检查增加限制起始日期：若上送的{开始日期}小于{系统日期}或者大于{结束日期}，
 * 则返回检查结果为“不通过”，否则返回检查结果为“通过”。通过/不通过均为步骤正常完成，
 * 本步骤不涉及本地数据库写入，无事务要求。</p>
 */
public interface IST003 {
	/**
	 * 执行检查增加限制起始日期步骤
	 *
	 * @param input 输入BO（runDate 核心运行日期、startDate 开始日期、endDate 结束日期，均必填）
	 * @return 输出BO，checkResult 为“通过”或“不通过”，成功标志为 true
	 */
	ST003OutputBO execute(ST003InputBO input);
}
