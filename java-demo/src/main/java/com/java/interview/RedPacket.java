package com.java.interview;

import java.util.ArrayList;
import java.util.List;

/**
 * Function: 模拟微信红包生成，以分为单位
 */
public class RedPacket {

    //允许最小红包
    private static final int MIN_MONEY = 1;

    //允许最大红包
    private static final int MAX_MONEY = 200 * 100;

    // 最大的红包是平均值的 TIMES 倍，防止某一次分配红包较大
    private static final double TIMES = 2.1F ;

    //小于最小值
    private static final int LESS = -1 ;

    //大于最大值
    private static final int MORE = -2 ;

    //正常值
    private static final int OK = 1 ;

    private int recursiveCount = 0 ;

    public List<Integer> splitRedPacket(int money, int count){
        List<Integer> moneys = new ArrayList<>();

        //计算最大红包数额
        int max = (int)((money / count) * TIMES);
        max = max > MAX_MONEY ? MAX_MONEY : max;

        for (int i = 0; i < count; i++) {
            //随机获取红包
            int redPacket = randomRedPacket(money, MIN_MONEY,max,count - i);
            moneys.add(redPacket);
            money = money - redPacket;
        }
        return moneys;
    }

    private int randomRedPacket(int totalMoney, int minMoney, int maxMoney, int count) {
        if (count == 1){
            System.out.println("last money: " + totalMoney);
            return totalMoney;
        }

        if (minMoney == maxMoney){
            return minMoney ;
        }


        maxMoney = maxMoney > totalMoney ? totalMoney : maxMoney;
        int packet = (int)(Math.random() * (maxMoney - minMoney) + minMoney);

        int lastMoney = totalMoney - packet ;

        int status = checkMoney(lastMoney,count - 1) ;

        System.out.println(status);

        //正常金额
        if (OK == status){
            return packet ;
        }

        //如果生成的金额不合法 则递归重新生成
        if (LESS == status){
            recursiveCount ++ ;
            System.out.println("recursiveCount==" + recursiveCount);
            return randomRedPacket(totalMoney,minMoney,packet,count) ;
        }

        if (MORE == status){
            recursiveCount ++ ;
            System.out.println("recursiveCount===" + recursiveCount);
            return randomRedPacket(totalMoney,packet,maxMoney,count) ;
        }

        return packet;
    }

    private int checkMoney(int lastMoney, int count) {
        double avg = lastMoney / count ;
        if (avg < MIN_MONEY){
            return LESS ;
        }

        if (avg > MAX_MONEY){
            return MORE ;
        }

        return OK ;
    }

    public static void main(String[] args) {
        RedPacket redPacket  = new RedPacket();
        List<Integer> packets = redPacket.splitRedPacket(100, 10);
        System.out.println(packets);
    }


}
