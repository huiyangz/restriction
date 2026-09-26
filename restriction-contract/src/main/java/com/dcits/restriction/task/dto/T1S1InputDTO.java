package com.dcits.restriction.task.dto;

import java.util.Date;

import jakarta.validation.constraints.NotNull;

/** T1S1 增加账户限制 输入DTO */
public class T1S1InputDTO {
    /** 交易机构号 */
    @NotNull
    private String tranBranch;
    /** 开始日期 */
    @NotNull
    private Date startDate;
    /** 结束日期 */
    @NotNull
    private Date endDate;
    /** 账号 */
    @NotNull
    private String baseAcctNo;
    /** 账户限制类型（代码[限制类型]，编码对应枚举 com.dcits.restriction.enums.RestraintType） */
    @NotNull
    private String restraintType;
    /** 核心运行日期（来源：系统日期表 FM_DATE，同时作为 ST004 交易日期 tranDate 的取值来源） */
    @NotNull
    private Date runDate;
    /** 限制期限 */
    @NotNull
    private String term;
    /** 周期类型（代码[期限类型]，编码对应枚举 com.dcits.restriction.enums.TermType） */
    @NotNull
    private String termType;

    public String getTranBranch() {
        return tranBranch;
    }

    public void setTranBranch(String tranBranch) {
        this.tranBranch = tranBranch;
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

    public String getBaseAcctNo() {
        return baseAcctNo;
    }

    public void setBaseAcctNo(String baseAcctNo) {
        this.baseAcctNo = baseAcctNo;
    }

    public String getRestraintType() {
        return restraintType;
    }

    public void setRestraintType(String restraintType) {
        this.restraintType = restraintType;
    }

    public Date getRunDate() {
        return runDate;
    }

    public void setRunDate(Date runDate) {
        this.runDate = runDate;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getTermType() {
        return termType;
    }

    public void setTermType(String termType) {
        this.termType = termType;
    }
}
