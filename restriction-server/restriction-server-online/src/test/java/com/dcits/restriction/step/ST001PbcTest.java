package com.dcits.restriction.step;

import com.dcits.restriction.enums.RestraintType;
import com.dcits.restriction.enums.Status;
import com.dcits.restriction.facade.bo.ST001InputBO;
import com.dcits.restriction.facade.bo.ST001OutputBO;
import com.dcits.restriction.facade.components.IRbRestraintTypeBcc;
import com.dcits.restriction.facade.eo.RbRestraintTypeEO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ST001PbcTest {

    @Mock
    private IRbRestraintTypeBcc rbRestraintTypeBcc;

    @InjectMocks
    private ST001Pbc st001Pbc;

    // ST001-TC001：限制类型 VALUE_13 存在于存款限制类型表且状态为"A-有效"，子步骤1→2→3 全部成功，检查结果"通过"
    @Test
    void testST001T01() {
        RbRestraintTypeEO restraintTypeInfo = new RbRestraintTypeEO();
        restraintTypeInfo.setRestraintType(RestraintType.VALUE_13);
        restraintTypeInfo.setStatus(Status.A);
        Mockito.lenient().when(rbRestraintTypeBcc.findByRestraintType(RestraintType.VALUE_13))
                .thenReturn(restraintTypeInfo);

        ST001InputBO input = new ST001InputBO();
        input.setRestraintType(RestraintType.VALUE_13);

        ST001OutputBO output = st001Pbc.execute(input);

        Assertions.assertTrue(output.isSucceed());
        Assertions.assertEquals("通过", output.getCheckResult());
        Assertions.assertNull(output.getErrorCode());
        Assertions.assertNull(output.getErrorMessage());
    }

    // ST001-TC002：限制类型 VALUE_16 在存款限制类型表中无记录，子步骤2 判空提前返回"不通过"，不触达子步骤3
    @Test
    void testST001T02() {
        Mockito.lenient().when(rbRestraintTypeBcc.findByRestraintType(RestraintType.VALUE_16))
                .thenReturn(null);

        ST001InputBO input = new ST001InputBO();
        input.setRestraintType(RestraintType.VALUE_16);

        ST001OutputBO output = st001Pbc.execute(input);

        Assertions.assertFalse(output.isSucceed());
        Assertions.assertEquals("不通过", output.getCheckResult());
        Assertions.assertEquals("ER0039", output.getErrorCode());
    }

    // ST001-TC003：限制类型 VALUE_4 记录存在但状态非"A-有效"（代表值 Status.C 非活动状态），子步骤3 状态判断不通过，检查结果"不通过"
    @Test
    void testST001T03() {
        RbRestraintTypeEO restraintTypeInfo = new RbRestraintTypeEO();
        restraintTypeInfo.setRestraintType(RestraintType.VALUE_4);
        restraintTypeInfo.setStatus(Status.C);
        Mockito.lenient().when(rbRestraintTypeBcc.findByRestraintType(RestraintType.VALUE_4))
                .thenReturn(restraintTypeInfo);

        ST001InputBO input = new ST001InputBO();
        input.setRestraintType(RestraintType.VALUE_4);

        ST001OutputBO output = st001Pbc.execute(input);

        Assertions.assertFalse(output.isSucceed());
        Assertions.assertEquals("不通过", output.getCheckResult());
        Assertions.assertEquals("ER0039", output.getErrorCode());
    }
}
