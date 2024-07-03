package com.li;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayDeque;
import java.util.Deque;

@SpringBootApplication
public class MainApp {
    public static void main(String[] args) {
        Deque<Integer> q = new ArrayDeque<>();
        q.push();
        SpringApplication.run(MainApp.class);
    }
}

class Solution {
    public int rangeBitwiseAnd(int left, int right) {
        /*
         * 因为是位与运算，那么只要某一位出现了一次0，那么该位的位与结果就会是0
         *       所以从 left 到 right 过程中，最右侧的若干位会一直出现 01 变换，也就不会给结果带来增益
         * 因此，只要考虑在从 left 到 right 的过程中，不会出现 01 变换的部分即可
         * 又因为 left <= right ，那么就可以得到，如果 left 的左侧部分和 right 的左侧部分相同，那么这部分就会给结果带来增益
         *       因为从第一个不相同的位开始，那一位一定是 left 为 0 ，right 为 1 ，否则left>right，出现悖论
         * 因此只要找到left 和 right 左侧一直相同的部分（最长公共前缀），就能得到结果值
         * */
        int mask = 1 << 30; // 从最高位开始
        int res = 0;
        while (mask > 0 && ((left & mask) == (right & mask))) {
            res |= left & mask;
            mask >>= 1;
        }
        return res;
    }
}