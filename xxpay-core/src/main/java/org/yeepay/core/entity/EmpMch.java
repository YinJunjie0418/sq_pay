package org.yeepay.core.entity;

import java.io.Serializable;

public class EmpMch implements Serializable {
    /**
     * 员工ID
     *
     * @mbggenerated
     */
    private Long empId;
    /**
     * 商户ID
     *
     * @mbggenerated
     */
    private Long mchId;

    public Long getMchId() {
        return mchId;
    }
    public void setMchId(Long mchId) {
        this.mchId = mchId;
    }
    public Long getEmpId() {
        return empId;
    }
    public void setEmpId(Long mchId) {
        this.empId = empId;
    }
}
