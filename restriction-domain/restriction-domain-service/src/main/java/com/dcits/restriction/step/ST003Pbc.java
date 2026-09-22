package com.dcits.restriction.step;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * ST003 检查增加限制起始日期 - 步骤实现类
 * <p>步骤逻辑：若上送的开始日期小于系统日期或者大于结束日期，则检查不通过（succeed=false），
 * 否则检查通过（succeed=true）。本步骤直接返回 boolean 检查结果，不定义错误码。</p>
 */
@Service
public class ST003Pbc implements IST003 {

	private static final Logger logger = LoggerFactory.getLogger(ST003Pbc.class);

	/**
	 * 执行检查增加限制起始日期步骤，统一设置检查结果
	 *
	 * @param input 步骤输入（系统日期、开始日期、结束日期）
	 * @return 步骤输出，检查结果通过 succeed=true，不通过 succeed=false
	 */
	@Override
	public ST003OutputBO execute(ST003InputBO input) {
		ST003OutputBO output = new ST003OutputBO();
		boolean passed = checkRestrictionStartDate(input.getStartDate(), input.getRunDate(), input.getEndDate());
		output.setSucceed(passed);
		logger.info("ST003 检查增加限制起始日期完成，startDate={}, runDate={}, endDate={}, 检查结果={}",
				input.getStartDate(), input.getRunDate(), input.getEndDate(), passed);
		return output;
	}

	/**
	 * 子步骤1：检查增加限制起始日期
	 * <p>开始日期早于系统日期（startDate &lt; runDate）或晚于结束日期（startDate &gt; endDate）时不通过。</p>
	 *
	 * @param startDate 开始日期
	 * @param runDate   系统日期（核心运行日期）
	 * @param endDate   结束日期
	 * @return true-检查通过；false-检查不通过
	 */
	private boolean checkRestrictionStartDate(Date startDate, Date runDate, Date endDate) {
		if (startDate.before(runDate)) {
			logger.info("ST003 开始日期早于系统日期，检查不通过");
			return false;
		}
		if (startDate.after(endDate)) {
			logger.info("ST003 开始日期晚于结束日期，检查不通过");
			return false;
		}
		return true;
	}
}
