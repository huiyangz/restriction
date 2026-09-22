package com.dcits.restriction.step;

/**
 * ST003 检查增加限制起始日期 - 步骤接口
 * <p>若上送的开始日期小于系统日期或者大于结束日期，则检查不通过（succeed=false），否则检查通过（succeed=true）。</p>
 */
public interface IST003 {

	/**
	 * 执行检查增加限制起始日期步骤
	 *
	 * @param input 步骤输入（系统日期、开始日期、结束日期）
	 * @return 步骤输出，检查结果通过 succeed=true，不通过 succeed=false
	 */
	ST003OutputBO execute(ST003InputBO input);
}
