package com.dcits.restriction.step;

import com.dcits.restriction.facade.bo.ST003InputBO;
import com.dcits.restriction.facade.bo.ST003OutputBO;
import org.springframework.stereotype.Service;

/**
 * ST003 检查增加限制起始日期
 */
@Service
public class ST003Pbc implements IST003 {
	/** 检查结果：通过 */
	private static final String CHECK_RESULT_PASS = "通过";
	/** 检查结果：不通过 */
	private static final String CHECK_RESULT_FAIL = "不通过";

	@Override
	public ST003OutputBO execute(ST003InputBO input) {
		ST003OutputBO output = new ST003OutputBO();
		// 1.检查增加限制起始日期：开始日期小于系统日期或者大于结束日期，检查结果为“不通过”，否则为“通过”
		if (input.getStartDate().before(input.getRunDate()) || input.getStartDate().after(input.getEndDate())) {
			output.setCheckResult(CHECK_RESULT_FAIL);
		} else {
			output.setCheckResult(CHECK_RESULT_PASS);
		}
		// 通过/不通过均为步骤正常完成，无已定义错误码
		output.setSucceed(true);
		return output;
	}
}
