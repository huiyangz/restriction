package com.dcits.restriction.step;

import java.sql.Date;

import com.dcits.restriction.facade.bo.ST003InputBO;
import com.dcits.restriction.facade.bo.ST003OutputBO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * ST003 检查增加限制起始日期 单元测试
 */
@ExtendWith(MockitoExtension.class)
public class ST003PbcTest {
	@InjectMocks
	private ST003Pbc st003Pbc;

	/**
	 * 构造确定日历值的 java.util.Date（同粒度可比）
	 */
	private Date dateOf(String yyyyDashMmDashDd) {
		return Date.valueOf(yyyyDashMmDashDd);
	}

	private ST003InputBO buildInput(String runDate, String startDate, String endDate) {
		ST003InputBO input = new ST003InputBO();
		input.setRunDate(dateOf(runDate));
		input.setStartDate(dateOf(startDate));
		input.setEndDate(dateOf(endDate));
		return input;
	}

	// 正常路径：runDate=2026-09-26 早于 startDate=2026-10-01，startDate 早于 endDate=2026-12-31，两支条件均不成立，检查结果为“通过”
	@Test
	public void testST003T01() {
		ST003OutputBO output = st003Pbc.execute(buildInput("2026-09-26", "2026-10-01", "2026-12-31"));
		assertTrue(output.isSucceed());
		assertNull(output.getErrorCode());
		assertNull(output.getErrorMessage());
		assertEquals("通过", output.getCheckResult());
	}

	// 开始日期早于系统日期：startDate=2026-09-25 严格小于 runDate=2026-09-26，第一支条件成立，检查结果为“不通过”
	@Test
	public void testST003T02() {
		ST003OutputBO output = st003Pbc.execute(buildInput("2026-09-26", "2026-09-25", "2026-12-31"));
		assertTrue(output.isSucceed());
		assertNull(output.getErrorCode());
		assertNull(output.getErrorMessage());
		assertEquals("不通过", output.getCheckResult());
	}

	// 开始日期晚于结束日期：startDate=2027-01-05 严格大于 endDate=2026-12-31 且不早于 runDate，第二支条件单独成立，检查结果为“不通过”
	@Test
	public void testST003T03() {
		ST003OutputBO output = st003Pbc.execute(buildInput("2026-09-26", "2027-01-05", "2026-12-31"));
		assertTrue(output.isSucceed());
		assertNull(output.getErrorCode());
		assertNull(output.getErrorMessage());
		assertEquals("不通过", output.getCheckResult());
	}

	// 两支条件同时成立：runDate=2027-01-31 晚于 endDate=2026-12-31，startDate=2027-01-15 同时小于系统日期且大于结束日期，检查结果为“不通过”
	@Test
	public void testST003T04() {
		ST003OutputBO output = st003Pbc.execute(buildInput("2027-01-31", "2027-01-15", "2026-12-31"));
		assertTrue(output.isSucceed());
		assertNull(output.getErrorCode());
		assertNull(output.getErrorMessage());
		assertEquals("不通过", output.getCheckResult());
	}

	// 下边界：startDate=2026-09-26 等于 runDate=2026-09-26，“小于”为严格比较不触发不通过，检查结果为“通过”
	@Test
	public void testST003T05() {
		ST003OutputBO output = st003Pbc.execute(buildInput("2026-09-26", "2026-09-26", "2026-12-31"));
		assertTrue(output.isSucceed());
		assertNull(output.getErrorCode());
		assertNull(output.getErrorMessage());
		assertEquals("通过", output.getCheckResult());
	}

	// 上边界：startDate=2026-12-31 等于 endDate=2026-12-31，“大于”为严格比较不触发不通过，检查结果为“通过”
	@Test
	public void testST003T06() {
		ST003OutputBO output = st003Pbc.execute(buildInput("2026-09-26", "2026-12-31", "2026-12-31"));
		assertTrue(output.isSucceed());
		assertNull(output.getErrorCode());
		assertNull(output.getErrorMessage());
		assertEquals("通过", output.getCheckResult());
	}

	// 退化边界：三个日期同为 2026-09-26，同时压在等于系统日期、等于结束日期两个边界上，检查结果为“通过”
	@Test
	public void testST003T07() {
		ST003OutputBO output = st003Pbc.execute(buildInput("2026-09-26", "2026-09-26", "2026-09-26"));
		assertTrue(output.isSucceed());
		assertNull(output.getErrorCode());
		assertNull(output.getErrorMessage());
		assertEquals("通过", output.getCheckResult());
	}
}
