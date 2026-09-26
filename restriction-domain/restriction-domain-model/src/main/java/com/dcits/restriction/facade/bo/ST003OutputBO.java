package com.dcits.restriction.facade.bo;

import com.dcits.common.step.StepResult;

/** ST003 检查增加限制起始日期 输出BO */
public class ST003OutputBO extends StepResult {
	/** 检查结果：通过 / 不通过 */
	private String checkResult;

	public String getCheckResult() {
		return checkResult;
	}

	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}
}
