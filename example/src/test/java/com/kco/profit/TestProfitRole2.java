package com.kco.profit;

import com.kco.profit.demo2.ProfitType;
import com.kco.profit.demo2.role.ProfitRole;
import com.kco.profit.demo2.role.factory.ProfitRoleFactory;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2017/4/14.
 */
public class TestProfitRole2 {

    private static final List<Double> testDate = Arrays.asList(100.0,200.0,300.0,400.0,700.0,
            1000.0,2000.0,3000.0,7000.0,
            10000.0, 20000.0, 30000.0, 70000.0);

    @Test
    public void test(){
        String profitTypeInfo = ProfitType.getProfitTypeInfo();
        System.out.println(profitTypeInfo);

    }
    @Test
    public void testFixedIncome(){
        for (double data : testDate){
            ProfitRole fixedIncome = ProfitRoleFactory.createProfitRole("FIXED_INCOME", "1.00");
            double profit = fixedIncome.getProfit(data);
            Assert.assertEquals(1.00, profit, 0.00001);
        }
    }

    @Test
    public void testFixedRate(){
        for (double data : testDate){
            ProfitRole fixedRate = ProfitRoleFactory.createProfitRole("FIXED_RATE", "0.1%");
            double profit = fixedRate.getProfit(data);
            Assert.assertEquals(data * 0.1 * 0.01, profit, 0.00001);
        }
    }

    @Test
    public void testFixedRateAndFixedIncome(){
        for (double data : testDate){
            ProfitRole profitRole = ProfitRoleFactory.createProfitRole("FIXED_RATE_AND_FIXED_INCOME", "0.63%+3.00");
            double profit = profitRole.getProfit(data);
            Assert.assertEquals(data * 0.63 * 0.01 + 3.0, profit, 0.00001);
        }
    }

    @Test
    public void testFixedRateAndUpperLimit(){
        for (double data : testDate){
            ProfitRole profitRole = ProfitRoleFactory.createProfitRole("FIXED_RATE_AND_UPPER_LIMIT", "1.00~0.1%~3.00");
            double profit = profitRole.getProfit(data);
            double actual = data * 0.1 * 0.01;
            if (actual < 1.0){
                actual = 1.0;
            }
            if (actual > 3.0){
                actual = 3.0;
            }
            Assert.assertEquals(actual, profit, 0.00001);
        }
    }

    @Test
    public void testGradientRate(){
        for (double data : testDate){
            ProfitRole profitRole = ProfitRoleFactory.createProfitRole("GRADIENT_RATE", "0.1%<1000<0.2%<5000<0.3%<15000<0.5%");
            double profit = profitRole.getProfit(data);
            if (data < 1000){
                Assert.assertEquals(data * 0.01 * 0.1, profit, 0.00001);
            }else if (data < 5000){
                Assert.assertEquals(data * 0.01 * 0.2, profit, 0.00001);
            }else if(data < 15000){
                Assert.assertEquals(data * 0.01 * 0.3, profit, 0.00001);
            }else{
                Assert.assertEquals(data * 0.01 * 0.5, profit, 0.00001);
            }
        }
    }
}
