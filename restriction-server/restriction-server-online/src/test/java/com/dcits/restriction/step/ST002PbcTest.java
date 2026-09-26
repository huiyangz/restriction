package com.dcits.restriction.step;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.dcits.restriction.enums.TranBranch;
import com.dcits.restriction.facade.bo.ST002InputBO;
import com.dcits.restriction.facade.bo.ST002OutputBO;
import com.dcits.restriction.facade.components.IFmBranchBcc;
import com.dcits.restriction.facade.components.IRbBusAcctBcc;
import com.dcits.restriction.facade.eo.FmBranchEO;
import com.dcits.restriction.facade.eo.RbBusAcctEO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class ST002PbcTest {
    @Mock
    IRbBusAcctBcc rbBusAcctBcc;
    @Mock
    IFmBranchBcc fmBranchBcc;
    @InjectMocks
    ST002Pbc st002Pbc;

    /** 固定输入：账号 200001000000881，交易机构 VALUE_351156 */
    private ST002InputBO buildInput() {
        ST002InputBO input = new ST002InputBO();
        input.setBaseAcctNo("200001000000881");
        input.setBranch(TranBranch.VALUE_351156);
        return input;
    }

    /** 账户记录：账号 200001000000881，开户机构由入参指定 */
    private RbBusAcctEO buildAcctEo(TranBranch acctBranch) {
        RbBusAcctEO eo = new RbBusAcctEO();
        eo.setBaseAcctNo("200001000000881");
        eo.setAcctBranch(acctBranch);
        return eo;
    }

    /** 机构记录：归属机构号与法人由入参指定 */
    private FmBranchEO buildBranchEo(TranBranch branch, String company) {
        FmBranchEO eo = new FmBranchEO();
        eo.setBranch(branch);
        eo.setCompany(company);
        return eo;
    }

    // ST002-TC001 账户唯一、开户机构与交易机构均有记录且法人相同，完整执行子步骤1→2→3，检查通过
    @Test
    public void testST002T01() {
        Mockito.lenient().when(rbBusAcctBcc.findByEo(Mockito.argThat(
                        eo -> eo != null && "200001000000881".equals(eo.getBaseAcctNo()))))
                .thenReturn(List.of(buildAcctEo(TranBranch.VALUE_351157)));
        Mockito.lenient().when(fmBranchBcc.findByBranch(TranBranch.VALUE_351157))
                .thenReturn(buildBranchEo(TranBranch.VALUE_351157, "8880001"));
        Mockito.lenient().when(fmBranchBcc.findByBranch(TranBranch.VALUE_351156))
                .thenReturn(buildBranchEo(TranBranch.VALUE_351156, "8880001"));

        ST002OutputBO output = st002Pbc.execute(buildInput());

        assertTrue(output.isSucceed());
        assertNull(output.getErrorCode());
        assertNull(output.getErrorMessage());
        assertEquals(Boolean.TRUE, output.getCheckResult());
    }

    // ST002-TC002 按账号查询账户信息无记录，子步骤1提前返回不通过，不触达机构信息查询
    @Test
    public void testST002T02() {
        List<RbBusAcctEO> receivedAcctQuery = new ArrayList<>();
        Mockito.lenient().when(rbBusAcctBcc.findByEo(Mockito.argThat(
                        eo -> eo != null && "200001000000881".equals(eo.getBaseAcctNo()))))
                .thenAnswer(invocation -> {
                    receivedAcctQuery.add(invocation.getArgument(0));
                    return Collections.emptyList();
                });

        ST002OutputBO output = st002Pbc.execute(buildInput());

        assertEquals(Boolean.FALSE, output.getCheckResult());
        assertTrue(output.isSucceed());
        assertNull(output.getErrorCode());
        assertNull(output.getErrorMessage());
        assertEquals(1, receivedAcctQuery.size());
        assertEquals("200001000000881", receivedAcctQuery.get(0).getBaseAcctNo());
    }

    // ST002-TC003 按账号查询到多条账户记录，子步骤1提前返回不通过
    @Test
    public void testST002T03() {
        List<RbBusAcctEO> receivedAcctQuery = new ArrayList<>();
        RbBusAcctEO recordOne = buildAcctEo(TranBranch.VALUE_351157);
        recordOne.setInternalKey(1);
        RbBusAcctEO recordTwo = buildAcctEo(TranBranch.VALUE_351158);
        recordTwo.setInternalKey(2);
        Mockito.lenient().when(rbBusAcctBcc.findByEo(Mockito.argThat(
                        eo -> eo != null && "200001000000881".equals(eo.getBaseAcctNo()))))
                .thenAnswer(invocation -> {
                    receivedAcctQuery.add(invocation.getArgument(0));
                    return List.of(recordOne, recordTwo);
                });

        ST002OutputBO output = st002Pbc.execute(buildInput());

        assertEquals(Boolean.FALSE, output.getCheckResult());
        assertTrue(output.isSucceed());
        assertNull(output.getErrorCode());
        assertNull(output.getErrorMessage());
        assertEquals(1, receivedAcctQuery.size());
        assertEquals("200001000000881", receivedAcctQuery.get(0).getBaseAcctNo());
    }

    // ST002-TC004 账户唯一，开户机构在机构信息表无记录，子步骤1后半提前返回不通过，交易机构查询未触达
    @Test
    public void testST002T04() {
        List<TranBranch> receivedBranchQuery = new ArrayList<>();
        Mockito.lenient().when(rbBusAcctBcc.findByEo(Mockito.argThat(
                        eo -> eo != null && "200001000000881".equals(eo.getBaseAcctNo()))))
                .thenReturn(List.of(buildAcctEo(TranBranch.VALUE_351157)));
        Mockito.lenient().when(fmBranchBcc.findByBranch(TranBranch.VALUE_351157))
                .thenAnswer(invocation -> {
                    receivedBranchQuery.add(invocation.getArgument(0));
                    return null;
                });

        ST002OutputBO output = st002Pbc.execute(buildInput());

        assertEquals(Boolean.FALSE, output.getCheckResult());
        assertTrue(output.isSucceed());
        assertNull(output.getErrorCode());
        assertNull(output.getErrorMessage());
        assertEquals(1, receivedBranchQuery.size());
        assertEquals(TranBranch.VALUE_351157, receivedBranchQuery.get(0));
    }

    // ST002-TC005 账户与开户机构均有记录，交易机构在机构信息表无记录，子步骤2提前返回不通过，子步骤3未触达
    @Test
    public void testST002T05() {
        List<TranBranch> receivedBranchQuery = new ArrayList<>();
        Mockito.lenient().when(rbBusAcctBcc.findByEo(Mockito.argThat(
                        eo -> eo != null && "200001000000881".equals(eo.getBaseAcctNo()))))
                .thenReturn(List.of(buildAcctEo(TranBranch.VALUE_351157)));
        Mockito.lenient().when(fmBranchBcc.findByBranch(TranBranch.VALUE_351157))
                .thenAnswer(invocation -> {
                    receivedBranchQuery.add(invocation.getArgument(0));
                    return buildBranchEo(TranBranch.VALUE_351157, "8880001");
                });
        Mockito.lenient().when(fmBranchBcc.findByBranch(TranBranch.VALUE_351156))
                .thenAnswer(invocation -> {
                    receivedBranchQuery.add(invocation.getArgument(0));
                    return null;
                });

        ST002OutputBO output = st002Pbc.execute(buildInput());

        assertEquals(Boolean.FALSE, output.getCheckResult());
        assertTrue(output.isSucceed());
        assertNull(output.getErrorCode());
        assertNull(output.getErrorMessage());
        assertEquals(2, receivedBranchQuery.size());
        assertEquals(TranBranch.VALUE_351157, receivedBranchQuery.get(0));
        assertEquals(TranBranch.VALUE_351156, receivedBranchQuery.get(1));
    }

    // ST002-TC006 账户法人 8880001 与交易机构法人 8880002 不一致，子步骤3判定不通过
    @Test
    public void testST002T06() {
        Mockito.lenient().when(rbBusAcctBcc.findByEo(Mockito.argThat(
                        eo -> eo != null && "200001000000881".equals(eo.getBaseAcctNo()))))
                .thenReturn(List.of(buildAcctEo(TranBranch.VALUE_351157)));
        Mockito.lenient().when(fmBranchBcc.findByBranch(TranBranch.VALUE_351157))
                .thenReturn(buildBranchEo(TranBranch.VALUE_351157, "8880001"));
        Mockito.lenient().when(fmBranchBcc.findByBranch(TranBranch.VALUE_351156))
                .thenReturn(buildBranchEo(TranBranch.VALUE_351156, "8880002"));

        ST002OutputBO output = st002Pbc.execute(buildInput());

        assertEquals(Boolean.FALSE, output.getCheckResult());
        assertTrue(output.isSucceed());
        assertNull(output.getErrorCode());
        assertNull(output.getErrorMessage());
    }
}
