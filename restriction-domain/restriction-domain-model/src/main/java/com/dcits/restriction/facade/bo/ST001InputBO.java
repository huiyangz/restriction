package com.dcits.restriction.facade.bo;

import com.dcits.restriction.enums.RestraintType;

/** ST001 检查限制类型 输入BO */
public class ST001InputBO {
    /** 账户限制类型（必填），取值来源于代码[限制类型]，对应枚举 com.dcits.restriction.enums.RestraintType */
    private RestraintType restraintType;

    public RestraintType getRestraintType() {
        return restraintType;
    }

    public void setRestraintType(RestraintType restraintType) {
        this.restraintType = restraintType;
    }
}
