package com.dcits.restriction.facade.bo;

import com.dcits.common.step.StepResult;

/** ST002 检查是否跨法人 步骤输出 */
public class ST002OutputBO extends StepResult {
    /** 检查结果：true=通过，false=不通过 */
    private Boolean checkResult;

    public Boolean getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(Boolean checkResult) {
        this.checkResult = checkResult;
    }
}
