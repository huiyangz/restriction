package com.dcits.restriction.step;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * ST003 检查增加限制起始日期 - 步骤实现单元测试
 */
@ExtendWith(MockitoExtension.class)
class ST003PbcTest {

	@InjectMocks
	private ST003Pbc st003Pbc;

	// 正常路径：开始日期晚于系统日期且早于结束日期（runDate < startDate < endDate），检查通过
	@Test
	void testST003T01() throws Exception {
		ST003InputBO input = new ST003InputBO();
		input.setRunDate(parseDate("2026-09-22 00:00:00"));
		input.setStartDate(parseDate("2026-09-25 00:00:00"));
		input.setEndDate(parseDate("2026-12-31 00:00:00"));

		ST003OutputBO result = st003Pbc.execute(input);

		assertTrue(result.isSucceed());
	}

	// 边界：开始日期等于系统日期（startDate == runDate），不构成"小于系统日期"，检查通过
	@Test
	void testST003T02() throws Exception {
		ST003InputBO input = new ST003InputBO();
		input.setRunDate(parseDate("2026-09-22 00:00:00"));
		input.setStartDate(parseDate("2026-09-22 00:00:00"));
		input.setEndDate(parseDate("2026-12-31 00:00:00"));

		ST003OutputBO result = st003Pbc.execute(input);

		assertTrue(result.isSucceed());
	}

	// 边界：开始日期等于结束日期（startDate == endDate），不构成"大于结束日期"，检查通过
	@Test
	void testST003T03() throws Exception {
		ST003InputBO input = new ST003InputBO();
		input.setRunDate(parseDate("2026-09-22 00:00:00"));
		input.setStartDate(parseDate("2026-12-31 00:00:00"));
		input.setEndDate(parseDate("2026-12-31 00:00:00"));

		ST003OutputBO result = st003Pbc.execute(input);

		assertTrue(result.isSucceed());
	}

	// 否定路径：开始日期小于系统日期（startDate < runDate），检查不通过
	@Test
	void testST003T04() throws Exception {
		ST003InputBO input = new ST003InputBO();
		input.setRunDate(parseDate("2026-09-22 00:00:00"));
		input.setStartDate(parseDate("2026-09-21 00:00:00"));
		input.setEndDate(parseDate("2026-12-31 00:00:00"));

		ST003OutputBO result = st003Pbc.execute(input);

		assertFalse(result.isSucceed());
	}

	// 否定路径：开始日期大于结束日期（startDate > endDate），检查不通过
	@Test
	void testST003T05() throws Exception {
		ST003InputBO input = new ST003InputBO();
		input.setRunDate(parseDate("2026-09-22 00:00:00"));
		input.setStartDate(parseDate("2027-01-01 00:00:00"));
		input.setEndDate(parseDate("2026-12-31 00:00:00"));

		ST003OutputBO result = st003Pbc.execute(input);

		assertFalse(result.isSucceed());
	}

	/**
	 * 解析 yyyy-MM-dd HH:mm:ss 格式日期字符串，构造确定性测试数据
	 *
	 * @param text 日期字符串
	 * @return 对应 Date 对象
	 */
	private static Date parseDate(String text) throws ParseException {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(text);
	}
}
