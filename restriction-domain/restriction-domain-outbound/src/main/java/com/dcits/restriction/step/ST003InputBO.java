package com.dcits.restriction.step;

import java.util.Date;

/**
 * ST003 检查增加限制起始日期 - 步骤输入BO
 */
public class ST003InputBO {

	/** 系统日期（核心运行日期） */
	private Date runDate;

	/** 开始日期 */
	private Date startDate;

	/** 结束日期 */
	private Date endDate;

	public Date getRunDate() {
		return runDate;
	}

	public void setRunDate(Date runDate) {
		this.runDate = runDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}
