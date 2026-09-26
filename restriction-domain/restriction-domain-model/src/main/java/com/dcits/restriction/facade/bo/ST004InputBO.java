package com.dcits.restriction.facade.bo;

import java.util.Date;

import com.dcits.restriction.enums.RestraintType;
import com.dcits.restriction.enums.TermType;

/** ST004 登记账户限制信息 输入BO */
public class ST004InputBO {
    /** 账号 */
    private String baseAcctNo;
    /** 账户限制类型 */
    private RestraintType restraintType;
    /** 开始日期 */
    private Date startDate;
    /** 结束日期 */
    private Date endDate;
    /** 限制期限 */
    private String term;
    /** 周期类型 */
    private TermType termType;
    /** 交易日期 */
    private Date tranDate;
    /** 核心运行日期 */
    private Date runDate;

    public String getBaseAcctNo() {
        return baseAcctNo;
    }

    public void setBaseAcctNo(String baseAcctNo) {
        this.baseAcctNo = baseAcctNo;
    }

    public RestraintType getRestraintType() {
        return restraintType;
    }

    public void setRestraintType(RestraintType restraintType) {
        this.restraintType = restraintType;
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

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public TermType getTermType() {
        return termType;
    }

    public void setTermType(TermType termType) {
        this.termType = termType;
    }

    public Date getTranDate() {
        return tranDate;
    }

    public void setTranDate(Date tranDate) {
        this.tranDate = tranDate;
    }

    public Date getRunDate() {
        return runDate;
    }

    public void setRunDate(Date runDate) {
        this.runDate = runDate;
    }
}
