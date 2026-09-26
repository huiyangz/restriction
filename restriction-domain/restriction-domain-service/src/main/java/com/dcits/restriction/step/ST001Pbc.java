package com.dcits.restriction.step;

import com.dcits.restriction.enums.Status;
import com.dcits.restriction.facade.bo.ST001InputBO;
import com.dcits.restriction.facade.bo.ST001OutputBO;
import com.dcits.restriction.facade.components.IRbRestraintTypeBcc;
import com.dcits.restriction.facade.eo.RbRestraintTypeEO;
import org.springframework.stereotype.Service;

/** ST001 检查限制类型 */
@Service
public class ST001Pbc implements IST001 {

    /** 错误码：无法增加限制，限制类型不存在或无效（来源：restriction-application/src/main/resources/errorcodes.properties ER0039） */
    private static final String ERROR_CODE = "ER0039";
    /** 错误信息，格式：错误码::业务说明 */
    private static final String ERROR_MESSAGE = ERROR_CODE + "::无法增加限制，限制类型不存在或无效";
    /** 检查结果：通过 */
    private static final String CHECK_RESULT_PASSED = "通过";
    /** 检查结果：不通过 */
    private static final String CHECK_RESULT_NOT_PASSED = "不通过";

    private final IRbRestraintTypeBcc rbRestraintTypeBcc;

    public ST001Pbc(IRbRestraintTypeBcc rbRestraintTypeBcc) {
        this.rbRestraintTypeBcc = rbRestraintTypeBcc;
    }

    @Override
    public ST001OutputBO execute(ST001InputBO input) {
        ST001OutputBO output = new ST001OutputBO();
        // 子步骤1 获取限制类型：根据{限制类型}查询【存款限制类型表】获取[限制类型信息]
        RbRestraintTypeEO restraintTypeInfo = rbRestraintTypeBcc.findByRestraintType(input.getRestraintType());
        // 子步骤2 检查限制类型存在性：若[限制类型信息]为空，则返回检查结果为"不通过"，否则继续执行
        if (restraintTypeInfo == null) {
            return checkNotPassed(output);
        }
        // 子步骤3 检查限制类型状态：若[限制类型信息]的$状态$为"A-有效"，则返回检查结果为"通过"，否则返回检查结果为"不通过"
        if (Status.A == restraintTypeInfo.getStatus()) {
            output.setCheckResult(CHECK_RESULT_PASSED);
            output.setSucceed(true);
            return output;
        }
        return checkNotPassed(output);
    }

    /** 业务失败出口：检查结果"不通过"，设置已确认错误码 ER0039 及"错误码::业务说明"格式错误信息后终止 */
    private ST001OutputBO checkNotPassed(ST001OutputBO output) {
        output.setCheckResult(CHECK_RESULT_NOT_PASSED);
        output.setSucceed(false);
        output.setErrorCode(ERROR_CODE);
        output.setErrorMessage(ERROR_MESSAGE);
        return output;
    }
}
