package com.dcits.restriction.task.scenario;

import java.util.ResourceBundle;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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

/**
 * T1S1 增加账户限制。
 * 按业务顺序执行 ST001 检查限制类型、ST002 检查是否跨法人、ST003 检查增加限制起始日期、
 * ST004 登记账户限制信息；任一步骤执行失败或检查不通过即短路返回失败，全部成功后返回成功。
 *
 * <p>事务边界（本地事务）：ST004 登记账户限制信息涉及本地数据库新增（RB_BUS_RESTRAINTS），
 * 实现方法已标注 @Transactional 且要求调用方经 Spring 代理调用，本场景入口同样标注
 * @Transactional；ST001~ST003 为只读检查步骤，无事务要求。</p>
 */
@Component
public class T1S1 {

    /** 检查结果：通过 */
    private static final String CHECK_RESULT_PASS = "通过";
    /** ST001 检查限制类型不通过错误码（无法增加限制，限制类型不存在或无效） */
    private static final String ERROR_CODE_RESTRAINT_TYPE = "ER0039";
    /** ST002 检查是否跨法人不通过错误码（无法增加限制，无法跨法人增加限制） */
    private static final String ERROR_CODE_CROSS_LEGAL = "ER0040";
    /** ST003 检查增加限制起始日期不通过错误码（限制的开始日期小于系统日期或大于结束日期） */
    private static final String ERROR_CODE_START_DATE = "ER0041";

    private final IST001 ist001;
    private final IST002 ist002;
    private final IST003 ist003;
    private final IST004 ist004;

    public T1S1(IST001 ist001, IST002 ist002, IST003 ist003, IST004 ist004) {
        this.ist001 = ist001;
        this.ist002 = ist002;
        this.ist003 = ist003;
        this.ist004 = ist004;
    }

    /**
     * 增加账户限制。
     * 输入的枚举编码经 byValue 转入业务枚举后调用各步骤；ST004 的交易日期取场景输入的核心运行日期。
     * ST001 失败由步骤成功标志携带，ST002/ST003 检查通过与否由 checkResult 表达（步骤本身执行成功），
     * 故两类信号均判定，任一不通过即失败短路。
     */
    @Transactional
    public T1S1OutputDTO execute(RespHeader header, T1S1InputDTO input) {
        T1S1OutputDTO output = new T1S1OutputDTO();
        RestraintType restraintType = RestraintType.byValue(input.getRestraintType());

        // ST001 检查限制类型
        ST001InputBO st001Input = new ST001InputBO();
        st001Input.setRestraintType(restraintType);
        ST001OutputBO st001Output = ist001.execute(st001Input);
        if (!st001Output.isSucceed() || !CHECK_RESULT_PASS.equals(st001Output.getCheckResult())) {
            handleError(header, ERROR_CODE_RESTRAINT_TYPE,
                    ResourceBundle.getBundle("errorcodes").getString(ERROR_CODE_RESTRAINT_TYPE));
            return output;
        }

        // ST002 检查是否跨法人：交易机构号转内部机构编号枚举
        ST002InputBO st002Input = new ST002InputBO();
        st002Input.setBaseAcctNo(input.getBaseAcctNo());
        st002Input.setBranch(TranBranch.byValue(input.getTranBranch()));
        ST002OutputBO st002Output = ist002.execute(st002Input);
        if (!st002Output.isSucceed() || !Boolean.TRUE.equals(st002Output.getCheckResult())) {
            handleError(header, ERROR_CODE_CROSS_LEGAL,
                    ResourceBundle.getBundle("errorcodes").getString(ERROR_CODE_CROSS_LEGAL));
            return output;
        }

        // ST003 检查增加限制起始日期
        ST003InputBO st003Input = new ST003InputBO();
        st003Input.setRunDate(input.getRunDate());
        st003Input.setStartDate(input.getStartDate());
        st003Input.setEndDate(input.getEndDate());
        ST003OutputBO st003Output = ist003.execute(st003Input);
        if (!st003Output.isSucceed() || !CHECK_RESULT_PASS.equals(st003Output.getCheckResult())) {
            handleError(header, ERROR_CODE_START_DATE,
                    ResourceBundle.getBundle("errorcodes").getString(ERROR_CODE_START_DATE));
            return output;
        }

        // ST004 登记账户限制信息：交易日期取核心运行日期
        ST004InputBO st004Input = new ST004InputBO();
        st004Input.setBaseAcctNo(input.getBaseAcctNo());
        st004Input.setRestraintType(restraintType);
        st004Input.setStartDate(input.getStartDate());
        st004Input.setEndDate(input.getEndDate());
        st004Input.setTerm(input.getTerm());
        st004Input.setTermType(TermType.byValue(input.getTermType()));
        st004Input.setTranDate(input.getRunDate());
        st004Input.setRunDate(input.getRunDate());
        ST004OutputBO st004Output = ist004.execute(st004Input);
        if (!st004Output.isSucceed()) {
            handleError(header, st004Output.getErrorCode(),
                    ResourceBundle.getBundle("errorcodes").getString(st004Output.getErrorCode()));
            return output;
        }

        // 全部步骤成功：映射业务输出并清理复用响应头中的旧错误；
        // resSeqNo 待 ST004 正式定义补充输出后映射（当前 ST004OutputBO 无该字段）
        header.setSucceed(true);
        header.setErrorCode(null);
        header.setErrorMessage(null);
        return output;
    }

    /** 失败处理：设置失败响应头，错误文案统一取自 errorcodes 资源 */
    private void handleError(RespHeader header, String errorCode, String errorMessage) {
        header.setSucceed(false);
        header.setErrorCode(errorCode);
        header.setErrorMessage(errorMessage);
    }
}
