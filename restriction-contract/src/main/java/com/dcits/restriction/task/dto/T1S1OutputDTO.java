package com.dcits.restriction.task.dto;

/** T1S1 增加账户限制 输出DTO */
public class T1S1OutputDTO {
    /**
     * 限制编号（来源：对公存款账户限制表 RB_BUS_RESTRAINTS，由 ST004 登记账户限制信息时生成并返回）。
     * 当前 ST004 正式定义尚未补充输出 resSeqNo（ST004OutputBO 无该字段），本场景暂无赋值来源，
     * 待 ST004 定义补充并重新生成 BO 后映射。
     */
    private String resSeqNo;

    public String getResSeqNo() {
        return resSeqNo;
    }

    public void setResSeqNo(String resSeqNo) {
        this.resSeqNo = resSeqNo;
    }
}
