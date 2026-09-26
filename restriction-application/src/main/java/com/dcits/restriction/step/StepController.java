package com.dcits.restriction.step;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dcits.restriction.facade.bo.ST001InputBO;
import com.dcits.restriction.facade.bo.ST001OutputBO;
import com.dcits.restriction.facade.bo.ST002InputBO;
import com.dcits.restriction.facade.bo.ST002OutputBO;
import com.dcits.restriction.facade.bo.ST003InputBO;
import com.dcits.restriction.facade.bo.ST003OutputBO;
import com.dcits.restriction.facade.bo.ST004InputBO;
import com.dcits.restriction.facade.bo.ST004OutputBO;

/**
 * 步骤控制器
 */
@RestController
@RequestMapping("steps")
public class StepController {
    
    @Autowired
    private IST001 st001;

    @Autowired
    private IST002 st002;

    @Autowired
    private IST003 st003;

    @Autowired
    private IST004 st004;

    /**
     * 执行ST001-检查限制类型步骤
     * @param input 输入参数
     * @return 输出结果
     */
    @PostMapping("/ST001")
    public ST001OutputBO executeST001(@RequestBody ST001InputBO input) {
        return st001.execute(input);
    }

    /**
     * 执行ST002-检查是否跨法人步骤
     * @param input 输入参数
     * @return 输出结果
     */
    @PostMapping("/ST002")
    public ST002OutputBO executeST002(@RequestBody ST002InputBO input) {
        return st002.execute(input);
    }

    /**
     * 执行ST003-检查增加限制起始日期步骤
     * @param input 输入参数
     * @return 输出结果
     */
    @PostMapping("/ST003")
    public ST003OutputBO executeST003(@RequestBody ST003InputBO input) {
        return st003.execute(input);
    }

    /**
     * 执行ST004-登记账户限制信息步骤
     * @param input 输入参数
     * @return 输出结果
     */
    @PostMapping("/ST004")
    public ST004OutputBO executeST004(@RequestBody ST004InputBO input) {
        return st004.execute(input);
    }

}