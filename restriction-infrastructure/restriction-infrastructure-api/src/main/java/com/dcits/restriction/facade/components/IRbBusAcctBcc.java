package com.dcits.restriction.facade.components;

import com.dcits.restriction.enums.AcctCcy;
import com.dcits.restriction.enums.AcctNatureNo;
import com.dcits.restriction.enums.AcctRiskLevel;
import com.dcits.restriction.enums.AcctStatus;
import com.dcits.restriction.enums.AcctVerifyFlag;
import com.dcits.restriction.enums.AcctVerifyResult;
import com.dcits.restriction.enums.AllDepInd;
import com.dcits.restriction.enums.AllDraInd;
import com.dcits.restriction.enums.AllDraRange;
import com.dcits.restriction.enums.AnnualStatus;
import com.dcits.restriction.enums.AutoRenewInd;
import com.dcits.restriction.enums.BalType;
import com.dcits.restriction.enums.CheckCertificateType;
import com.dcits.restriction.enums.ClientType;
import com.dcits.restriction.enums.DepositNature;
import com.dcits.restriction.enums.FarmerFlag;
import com.dcits.restriction.enums.FixedCall;
import com.dcits.restriction.enums.IntIndFlag;
import com.dcits.restriction.enums.ManageType;
import com.dcits.restriction.enums.OsaFlag;
import com.dcits.restriction.enums.RbAcctType;
import com.dcits.restriction.enums.RbBusAcctPurpose;
import com.dcits.restriction.enums.RenewMethod;
import com.dcits.restriction.enums.SimpleAcct;
import com.dcits.restriction.enums.SourceType;
import com.dcits.restriction.enums.SpecAcctFlag;
import com.dcits.restriction.enums.TermType;
import com.dcits.restriction.enums.TranBranch;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.dcits.restriction.facade.eo.RbBusAcctEO;

/*实体表【对公存款账户主表(RB_BUS_ACCT)】数据服务接口*/
public interface IRbBusAcctBcc {
    /** count数据库表记录根据入参com.dcits.restriction.facade.eo.RbBusAcctEO中的属性字段组合 **/
    long countByEo(RbBusAcctEO eo);

    /** remove数据库表记录根据入参com.dcits.restriction.facade.eo.RbBusAcctEO中的属性字段组合 **/
    int removeByEo(RbBusAcctEO eo);

    /** remove 根据主键: 账户内部键值 **/
    int removeByPrimaryKey(Integer internalKey);

    int create(RbBusAcctEO eo);

    /** create数据库表记录，主键和EO对象中不允许为空的字段必填，其它可为空字段可选填，执行时根据com.dcits.restriction.facade.eo.RbBusAcctEO中不为空的属性写入数据库**/
    int createSelective(RbBusAcctEO eo);

    /** find数据库表记录根据入参com.dcits.restriction.facade.eo.RbBusAcctEO中的属性字段组合 **/
    List<RbBusAcctEO> findByEo(RbBusAcctEO eo);

    /** find 根据主键: 账户内部键值 **/
    RbBusAcctEO findByPrimaryKey(Integer internalKey);

    /**  根据主键: 账户内部键值执行更新记录操作，仅更新入参com.dcits.restriction.facade.eo.RbBusAcctEO中不为空的属性字段 **/
    int modifyByPrimaryKeySelective(RbBusAcctEO eo);

    /** modify 根据主键: 账户内部键值 **/
    int modifyByPrimaryKey(RbBusAcctEO eo);
}