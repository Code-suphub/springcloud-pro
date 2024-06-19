package com.li;

public class Test {
    public static void main(String[] args) {
        System.out.println(new Solution().discountPrices("there are $1 $2 and 5$ candies in the shop", 50));
    }
}

class Solution {
    public String discountPrices(String sentence, int discount) {
        // 用一个sb纪录生成的数据，因为可能打折之后可能会出现价格位数的变化
        char[] ch = sentence.toCharArray();
        StringBuilder sb = new StringBuilder();
        // 计算价格的时候直接乘上discount，因为是保留两位小数，等于除以 100
        discount = 100 - discount;
        for (int i = 0; i < ch.length; i++) {
            if (ch[i] == '$') {
                int r = i + 1;
                long price = 0;
                if (r == ch.length || ch[r] > '9' || ch[r] < '0' || (i > 0 && ch[i - 1] != ' ')) {
                    sb.append(ch[i]);
                    // $ 不是价格
                    continue;
                }
                boolean sign = false;
                // 找到当前单词的结束位置
                for (; r < ch.length && ch[r] != ' '; r++) {
                    if (ch[r] > '9' || ch[r] < '0') {
                        sign = true;
                    }
                }
                // 如果超过单词长度或者出现过不是数字的，不是价格
                if (r - i >= 10 || sign) {
                    sb.append(sentence.substring(i, r));
                    i = r;
                    continue;
                }
                price = Long.parseLong(sentence.substring(i + 1, r));
                i = r;

                price = price * discount;
                //价格剩余0，直接拼接0.00
                if (price == 0) {
                    sb.append("$0.00");
                } else if (price < 10) {
                    // 价格剩余小于10，即0.0几，那么凭借0.0+price
                    sb.append("$" + 0 + ".0" + price);
                } else {
                    // 否则大于100的部分除上100就是整数部分，余数就是小数部分
                    long rr = price % 100;
                    sb.append("$" + (price / 100) + "." + (rr == 0 ? "00" : rr));
                }
                if (r < ch.length) {
                    sb.append(" ");
                }
            } else {
                sb.append(ch[i]);
            }
        }
        return sb.toString();
    }
}
