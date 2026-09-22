package com.dcits.restriction.facade.eo;

import com.dcits.restriction.enums.AllowRepeatFlag;
import com.dcits.restriction.enums.DrCrCtlFlag;
import com.dcits.restriction.enums.ResBranchRange;
import com.dcits.restriction.enums.RestraintAmtFlag;
import com.dcits.restriction.enums.RestraintType;
import com.dcits.restriction.enums.Status;
import com.dcits.restriction.enums.UnMaintBranchRange;
import jakarta.validation.constraints.NotNull;

public class RbRestraintTypeEO {
    /** 质押标志 */
    private String pledgedFlag;
    /** 状态 */
    private Status status;
    /** 解限或维护机构代码范围 */
    private UnMaintBranchRange unMaintBranchRange;
    /** 有权机关冻结标志 */
    private String ahBuFlag;
    /** 账户限制类型 */
    @NotNull
    private RestraintType restraintType;
    /** 手工解冻标志 */
    private String manualUnresFlag;
    /** 是否系统专用标志 */
    private String systemUseFlag;
    /** 理财冻结标志 */
    private String finFrozenFlag;
    /** 创建时间戳 */
    @NotNull
    private String createTimestamp;
    /** 止付标志 */
    private String stopFlag;
    /** 手工冻结标志 */
    private String manualResFlag;
    /** 现金标志 */
    private String cashFlag;
    /** 冻结级别 */
    private String resPriority;
    /** 限制机构范围 */
    private ResBranchRange resBranchRange;
    /** 销户标志 */
    private String closeAcctFlag;
    /** 账户限制类型描述 */
    private String restraintTypeDesc;
    /** 借方贷方控制标志 */
    private DrCrCtlFlag drCrCtlFlag;
    /** 转账标志 */
    private String transferFlag;
    /** 允许重复标志 */
    private AllowRepeatFlag allowRepeatFlag;
    /** 最后修改时间戳 */
    @NotNull
    private String lastUpdTimestamp;
    /** 限制金额标志 */
    private RestraintAmtFlag restraintAmtFlag;

    public String getPledgedFlag() {
        return pledgedFlag;
    }

    public void setPledgedFlag(String pledgedFlag) {
        this.pledgedFlag = pledgedFlag;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public UnMaintBranchRange getUnMaintBranchRange() {
        return unMaintBranchRange;
    }

    public void setUnMaintBranchRange(UnMaintBranchRange unMaintBranchRange) {
        this.unMaintBranchRange = unMaintBranchRange;
    }

    public String getAhBuFlag() {
        return ahBuFlag;
    }

    public void setAhBuFlag(String ahBuFlag) {
        this.ahBuFlag = ahBuFlag;
    }

    public RestraintType getRestraintType() {
        return restraintType;
    }

    public void setRestraintType(RestraintType restraintType) {
        this.restraintType = restraintType;
    }

    public String getManualUnresFlag() {
        return manualUnresFlag;
    }

    public void setManualUnresFlag(String manualUnresFlag) {
        this.manualUnresFlag = manualUnresFlag;
    }

    public String getSystemUseFlag() {
        return systemUseFlag;
    }

    public void setSystemUseFlag(String systemUseFlag) {
        this.systemUseFlag = systemUseFlag;
    }

    public String getFinFrozenFlag() {
        return finFrozenFlag;
    }

    public void setFinFrozenFlag(String finFrozenFlag) {
        this.finFrozenFlag = finFrozenFlag;
    }

    public String getCreateTimestamp() {
        return createTimestamp;
    }

    public void setCreateTimestamp(String createTimestamp) {
        this.createTimestamp = createTimestamp;
    }

    public String getStopFlag() {
        return stopFlag;
    }

    public void setStopFlag(String stopFlag) {
        this.stopFlag = stopFlag;
    }

    public String getManualResFlag() {
        return manualResFlag;
    }

    public void setManualResFlag(String manualResFlag) {
        this.manualResFlag = manualResFlag;
    }

    public String getCashFlag() {
        return cashFlag;
    }

    public void setCashFlag(String cashFlag) {
        this.cashFlag = cashFlag;
    }

    public String getResPriority() {
        return resPriority;
    }

    public void setResPriority(String resPriority) {
        this.resPriority = resPriority;
    }

    public ResBranchRange getResBranchRange() {
        return resBranchRange;
    }

    public void setResBranchRange(ResBranchRange resBranchRange) {
        this.resBranchRange = resBranchRange;
    }

    public String getCloseAcctFlag() {
        return closeAcctFlag;
    }

    public void setCloseAcctFlag(String closeAcctFlag) {
        this.closeAcctFlag = closeAcctFlag;
    }

    public String getRestraintTypeDesc() {
        return restraintTypeDesc;
    }

    public void setRestraintTypeDesc(String restraintTypeDesc) {
        this.restraintTypeDesc = restraintTypeDesc;
    }

    public DrCrCtlFlag getDrCrCtlFlag() {
        return drCrCtlFlag;
    }

    public void setDrCrCtlFlag(DrCrCtlFlag drCrCtlFlag) {
        this.drCrCtlFlag = drCrCtlFlag;
    }

    public String getTransferFlag() {
        return transferFlag;
    }

    public void setTransferFlag(String transferFlag) {
        this.transferFlag = transferFlag;
    }

    public AllowRepeatFlag getAllowRepeatFlag() {
        return allowRepeatFlag;
    }

    public void setAllowRepeatFlag(AllowRepeatFlag allowRepeatFlag) {
        this.allowRepeatFlag = allowRepeatFlag;
    }

    public String getLastUpdTimestamp() {
        return lastUpdTimestamp;
    }

    public void setLastUpdTimestamp(String lastUpdTimestamp) {
        this.lastUpdTimestamp = lastUpdTimestamp;
    }

    public RestraintAmtFlag getRestraintAmtFlag() {
        return restraintAmtFlag;
    }

    public void setRestraintAmtFlag(RestraintAmtFlag restraintAmtFlag) {
        this.restraintAmtFlag = restraintAmtFlag;
    }
}