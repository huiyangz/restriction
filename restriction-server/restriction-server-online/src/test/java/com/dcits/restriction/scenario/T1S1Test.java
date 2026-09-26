package com.dcits.restriction.scenario;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.dcits.common.task.RespHeader;
import com.dcits.restriction.enums.RestraintType;
import com.dcits.restriction.enums.TermType;
import com.dcits.restriction.enums.TranBranch;
import com.dcits.restriction.facade.bo.ST001InputBO;
import com.dcits.restriction.facade.bo.ST001OutputBO;
import com.dcits.restriction.facade.bo.ST002InputBO;
import com.dcits.restriction.facade.bo.ST002OutputBO;
import com.dcits.restriction.facade.bo.ST003InputBO;
import com.dcits.restriction.facade.bo.ST003OutputBO;
import com.dcits.restriction.facade.bo.ST004InputBO;
import com.dcits.restriction.facade.bo.ST004OutputBO;
import com.dcits.restriction.step.IST001;
import com.dcits.restriction.step.IST002;
import com.dcits.restriction.step.IST003;
import com.dcits.restriction.step.IST004;
import com.dcits.restriction.task.dto.T1S1InputDTO;
import com.dcits.restriction.task.dto.T1S1OutputDTO;
import com.dcits.restriction.task.scenario.T1S1;

/** T1S1 增加账户限制 场景单元测试：四个步骤接口 mock，RespHeader、输入 DTO 与各步骤 OutputBO 真实构造 */
@ExtendWith(MockitoExtension.class)
class T1S1Test {

    @Mock
    private IST001 ist001;
    @Mock
    private IST002 ist002;
    @Mock
    private IST003 ist003;
    @Mock
    private IST004 ist004;

    @InjectMocks
    private T1S1 t1s1;

    // T1S1-TC001：四个步骤全部成功，核对场景输入到各步骤入参的映射与枚举转换（restraintType→VALUE_13、tranBranch→VALUE_351001、termType→M、ST004.tranDate 取 runDate），成功返回前清理复用响应头旧错误
    @Test
    void testT1S1T01() throws ParseException {
        ST001InputBO[] st001Captured = new ST001InputBO[1];
        Mockito.lenient().when(ist001.execute(Mockito.any(ST001InputBO.class))).thenAnswer(invocation -> {
            st001Captured[0] = invocation.getArgument(0);
            ST001OutputBO bo = new ST001OutputBO();
            bo.setSucceed(true);
            bo.setCheckResult("通过");
            return bo;
        });
        ST002InputBO[] st002Captured = new ST002InputBO[1];
        Mockito.lenient().when(ist002.execute(Mockito.any(ST002InputBO.class))).thenAnswer(invocation -> {
            st002Captured[0] = invocation.getArgument(0);
            ST002OutputBO bo = new ST002OutputBO();
            bo.setSucceed(true);
            bo.setCheckResult(Boolean.TRUE);
            return bo;
        });
        ST003InputBO[] st003Captured = new ST003InputBO[1];
        Mockito.lenient().when(ist003.execute(Mockito.any(ST003InputBO.class))).thenAnswer(invocation -> {
            st003Captured[0] = invocation.getArgument(0);
            ST003OutputBO bo = new ST003OutputBO();
            bo.setSucceed(true);
            bo.setCheckResult("通过");
            return bo;
        });
        ST004InputBO[] st004Captured = new ST004InputBO[1];
        Mockito.lenient().when(ist004.execute(Mockito.any(ST004InputBO.class))).thenAnswer(invocation -> {
            st004Captured[0] = invocation.getArgument(0);
            ST004OutputBO bo = new ST004OutputBO();
            bo.setSucceed(true);
            return bo;
        });

        RespHeader header = new RespHeader();
        header.setErrorCode("ER0001");
        header.setErrorMessage("旧错误");

        T1S1InputDTO input = buildInput();

        T1S1OutputDTO output = t1s1.execute(header, input);

        Assertions.assertNotNull(output);
        Assertions.assertTrue(header.isSucceed());
        Assertions.assertNull(header.getErrorCode());
        Assertions.assertNull(header.getErrorMessage());

        Assertions.assertEquals(RestraintType.VALUE_13, st001Captured[0].getRestraintType());
        Assertions.assertEquals("2000010000123456", st002Captured[0].getBaseAcctNo());
        Assertions.assertEquals(TranBranch.VALUE_351001, st002Captured[0].getBranch());
        Assertions.assertEquals(input.getRunDate(), st003Captured[0].getRunDate());
        Assertions.assertEquals(input.getStartDate(), st003Captured[0].getStartDate());
        Assertions.assertEquals(input.getEndDate(), st003Captured[0].getEndDate());
        Assertions.assertEquals("2000010000123456", st004Captured[0].getBaseAcctNo());
        Assertions.assertEquals(RestraintType.VALUE_13, st004Captured[0].getRestraintType());
        Assertions.assertEquals(input.getStartDate(), st004Captured[0].getStartDate());
        Assertions.assertEquals(input.getEndDate(), st004Captured[0].getEndDate());
        Assertions.assertEquals("6", st004Captured[0].getTerm());
        Assertions.assertEquals(TermType.M, st004Captured[0].getTermType());
        Assertions.assertEquals(input.getRunDate(), st004Captured[0].getTranDate());
        Assertions.assertEquals(input.getRunDate(), st004Captured[0].getRunDate());
    }

    // T1S1-TC002：ST001 检查限制类型不通过（限制类型不存在或无效），首步失败短路，场景以 ER0039 拒绝，后续步骤不设桩
    @Test
    void testT1S1T02() throws ParseException {
        ST001OutputBO st001Bo = new ST001OutputBO();
        st001Bo.setSucceed(false);
        st001Bo.setCheckResult("不通过");
        st001Bo.setErrorCode("ER0039");
        st001Bo.setErrorMessage(ResourceBundle.getBundle("errorcodes").getString("ER0039"));
        Mockito.lenient().when(ist001.execute(Mockito.any(ST001InputBO.class))).thenReturn(st001Bo);

        RespHeader header = new RespHeader();
        T1S1InputDTO input = buildInput();

        t1s1.execute(header, input);

        Assertions.assertFalse(header.isSucceed());
        Assertions.assertEquals("ER0039", header.getErrorCode());
    }

    // T1S1-TC003：ST001 通过后 ST002 检查是否跨法人不通过（checkResult=false），中间步骤失败短路，场景以 ER0040 拒绝
    @Test
    void testT1S1T03() throws ParseException {
        ST001OutputBO st001Bo = new ST001OutputBO();
        st001Bo.setSucceed(true);
        st001Bo.setCheckResult("通过");
        Mockito.lenient().when(ist001.execute(Mockito.any(ST001InputBO.class))).thenReturn(st001Bo);

        ST002OutputBO st002Bo = new ST002OutputBO();
        st002Bo.setSucceed(false);
        st002Bo.setCheckResult(Boolean.FALSE);
        st002Bo.setErrorCode("ER0040");
        st002Bo.setErrorMessage(ResourceBundle.getBundle("errorcodes").getString("ER0040"));
        Mockito.lenient().when(ist002.execute(Mockito.any(ST002InputBO.class))).thenReturn(st002Bo);

        RespHeader header = new RespHeader();
        T1S1InputDTO input = buildInput();

        t1s1.execute(header, input);

        Assertions.assertFalse(header.isSucceed());
        Assertions.assertEquals("ER0040", header.getErrorCode());
    }

    // T1S1-TC004：ST003 检查增加限制起始日期不通过：开始日期 2026-09-25 小于系统日期 2026-09-26，末个检查步失败短路，场景以 ER0041 拒绝
    @Test
    void testT1S1T04() throws ParseException {
        ST001OutputBO st001Bo = new ST001OutputBO();
        st001Bo.setSucceed(true);
        st001Bo.setCheckResult("通过");
        Mockito.lenient().when(ist001.execute(Mockito.any(ST001InputBO.class))).thenReturn(st001Bo);

        ST002OutputBO st002Bo = new ST002OutputBO();
        st002Bo.setSucceed(true);
        st002Bo.setCheckResult(Boolean.TRUE);
        Mockito.lenient().when(ist002.execute(Mockito.any(ST002InputBO.class))).thenReturn(st002Bo);

        ST003OutputBO st003Bo = new ST003OutputBO();
        st003Bo.setSucceed(false);
        st003Bo.setCheckResult("不通过");
        st003Bo.setErrorCode("ER0041");
        st003Bo.setErrorMessage(ResourceBundle.getBundle("errorcodes").getString("ER0041"));
        Mockito.lenient().when(ist003.execute(Mockito.any(ST003InputBO.class))).thenReturn(st003Bo);

        RespHeader header = new RespHeader();
        T1S1InputDTO input = buildInput();
        input.setStartDate(new SimpleDateFormat("yyyy-MM-dd").parse("2026-09-25"));

        t1s1.execute(header, input);

        Assertions.assertFalse(header.isSucceed());
        Assertions.assertEquals("ER0041", header.getErrorCode());
    }

    /** 构造标准成功输入：限制类型 13、期限 6 个月、机构 351001，开始日期等于系统日期、结束日期半年后 */
    private T1S1InputDTO buildInput() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        T1S1InputDTO input = new T1S1InputDTO();
        input.setTranBranch("351001");
        input.setBaseAcctNo("2000010000123456");
        input.setRestraintType("13");
        input.setTerm("6");
        input.setTermType("M");
        input.setRunDate(dateFormat.parse("2026-09-26"));
        input.setStartDate(dateFormat.parse("2026-09-26"));
        input.setEndDate(dateFormat.parse("2027-03-26"));
        return input;
    }
}
