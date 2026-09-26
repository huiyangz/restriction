package com.dcits.restriction.step;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.dcits.restriction.enums.RestraintType;
import com.dcits.restriction.enums.TermType;
import com.dcits.restriction.facade.bo.ST004InputBO;
import com.dcits.restriction.facade.bo.ST004OutputBO;
import com.dcits.restriction.facade.components.IRbBusRestraintsBcc;
import com.dcits.restriction.facade.eo.RbBusRestraintsEO;

/** ST004 登记账户限制信息 单元测试 */
@ExtendWith(MockitoExtension.class)
public class ST004PbcTest {

    @Mock
    private IRbBusRestraintsBcc irbBusRestraintsBcc;

    @InjectMocks
    private ST004Pbc st004Pbc;

    // 场景：挂失止付类（数值编码 VALUE_13）限制类型按月周期（TermType.M）登记成功；
    // 预期：execute 返回 succeed=true、错误字段为 null，写入 EO 的 7 个映射字段与输入一致。
    @Test
    public void testST004T01() {
        ST004InputBO input = new ST004InputBO();
        input.setBaseAcctNo("6222000011112222");
        input.setRestraintType(RestraintType.VALUE_13);
        input.setStartDate(new GregorianCalendar(2026, Calendar.SEPTEMBER, 26).getTime());
        input.setEndDate(new GregorianCalendar(2027, Calendar.MARCH, 26).getTime());
        input.setTerm("6");
        input.setTermType(TermType.M);
        input.setTranDate(new GregorianCalendar(2026, Calendar.SEPTEMBER, 26).getTime());
        input.setRunDate(new GregorianCalendar(2026, Calendar.SEPTEMBER, 26).getTime());

        final RbBusRestraintsEO[] captured = new RbBusRestraintsEO[1];
        lenient().when(irbBusRestraintsBcc.createSelective(any(RbBusRestraintsEO.class)))
                .thenAnswer(invocation -> {
                    captured[0] = invocation.getArgument(0);
                    return 1;
                });

        ST004OutputBO result = st004Pbc.execute(input);

        assertTrue(result.isSucceed());
        assertNull(result.getErrorCode());
        assertNull(result.getErrorMessage());
        assertEquals("6222000011112222", captured[0].getBaseAcctNo());
        assertEquals(RestraintType.VALUE_13, captured[0].getRestraintType());
        assertEquals(input.getStartDate(), captured[0].getStartDate());
        assertEquals(input.getEndDate(), captured[0].getEndDate());
        assertEquals("6", captured[0].getTerm());
        assertEquals(TermType.M, captured[0].getTermType());
        assertEquals(input.getTranDate(), captured[0].getTranDate());
    }

    // 场景：字母编码限制类型（司法查控余额冻结 SF1）按日周期（TermType.D）登记成功；
    // 预期：execute 返回 succeed=true、错误字段为 null，写入 EO 的 7 个映射字段与输入一致。
    @Test
    public void testST004T02() {
        ST004InputBO input = new ST004InputBO();
        input.setBaseAcctNo("6222000033334444");
        input.setRestraintType(RestraintType.SF1);
        input.setStartDate(new GregorianCalendar(2026, Calendar.SEPTEMBER, 26).getTime());
        input.setEndDate(new GregorianCalendar(2026, Calendar.OCTOBER, 26).getTime());
        input.setTerm("30");
        input.setTermType(TermType.D);
        input.setTranDate(new GregorianCalendar(2026, Calendar.SEPTEMBER, 26).getTime());
        input.setRunDate(new GregorianCalendar(2026, Calendar.SEPTEMBER, 26).getTime());

        final RbBusRestraintsEO[] captured = new RbBusRestraintsEO[1];
        lenient().when(irbBusRestraintsBcc.createSelective(any(RbBusRestraintsEO.class)))
                .thenAnswer(invocation -> {
                    captured[0] = invocation.getArgument(0);
                    return 1;
                });

        ST004OutputBO result = st004Pbc.execute(input);

        assertTrue(result.isSucceed());
        assertNull(result.getErrorCode());
        assertNull(result.getErrorMessage());
        assertEquals("6222000033334444", captured[0].getBaseAcctNo());
        assertEquals(RestraintType.SF1, captured[0].getRestraintType());
        assertEquals(input.getStartDate(), captured[0].getStartDate());
        assertEquals(input.getEndDate(), captured[0].getEndDate());
        assertEquals("30", captured[0].getTerm());
        assertEquals(TermType.D, captured[0].getTermType());
        assertEquals(input.getTranDate(), captured[0].getTranDate());
    }
}
