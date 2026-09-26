package com.dcits.restriction.facade.bo;

import com.dcits.common.step.StepResult;

/** ST001 检查限制类型 输出BO */
public class ST001OutputBO extends StepResult {
    /** 检查结果，取值：通过 / 不通过 */
    private String checkResult;

    public String getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(String checkResult) {
        this.checkResult = checkResult;
    }
}
