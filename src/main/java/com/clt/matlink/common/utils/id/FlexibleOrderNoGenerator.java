package com.clt.matlink.common.utils.id;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;

/**
 * 固定总长度的订单号生成器（支持自定义前缀）
 *
 * 总长度 = prefix.length() + 12（时间）+ suffixLength
 * 通过指定 totalLength 自动计算 suffix 长度
 */
public class FlexibleOrderNoGenerator {

    private static final long MACHINE_ID = 1;
    private static final long DATACENTER_ID = 1;
    private static final cn.hutool.core.lang.Snowflake SNOWFLAKE = IdUtil.createSnowflake(MACHINE_ID, DATACENTER_ID);

    // 默认总长度（可根据业务调整）
    private static final int DEFAULT_TOTAL_LENGTH = 24;

    /**
     * 生成固定总长度的订单号（使用默认总长度）
     *
     * @param prefix 业务前缀，如 "ORD", "PAY"
     * @return 固定长度订单号
     */
    public static String generate(String prefix) {
        return generate(prefix, DEFAULT_TOTAL_LENGTH);
    }

    /**
     * 生成固定总长度的订单号
     *
     * @param prefix       业务前缀（不能为空）
     * @param totalLength 期望的订单号总长度（必须 > prefix.length() + 12）
     * @return 固定长度订单号
     */
    public static String generate(String prefix, int totalLength) {
        if (prefix == null || prefix.isEmpty()) {
            throw new IllegalArgumentException("Prefix must not be null or empty");
        }
        int timePartLength = 12; // yyMMddHHmmss
        int minTotalLength = prefix.length() + timePartLength + 1; // 至少1位后缀
        if (totalLength < minTotalLength) {
            throw new IllegalArgumentException(
                    "totalLength=" + totalLength + " is too small for prefix='" + prefix + "'");
        }

        String timePart = DateUtil.format(new java.util.Date(), "yyMMddHHmmss");
        long id = SNOWFLAKE.nextId();
        long maskedId = id & 0xFFFFFFFFFFFFL; // 48位掩码
        String suffix = Long.toString(maskedId, 36).toLowerCase();

        // 计算需要的后缀长度
        int requiredSuffixLen = totalLength - prefix.length() - timePartLength;

        // 调整后缀长度：补零或截断（理论上不会超）
        if (suffix.length() > requiredSuffixLen) {
            suffix = suffix.substring(suffix.length() - requiredSuffixLen); // 截右边
        } else {
            // 左侧补 '0'
            suffix = String.format("%" + requiredSuffixLen + "s", suffix).replace(' ', '0');
        }

        return prefix + timePart + suffix;
    }

    // ================== 测试 ==================
    public static void main(String[] args) {
        System.out.println(generate("ORD"));      // 默认24位：ORD260124170000xxxxxx
        System.out.println(generate("PAY", 20));  // 总长20位：PAY260124170000xxxx
        System.out.println(generate("RFD", 22));  // RFD260124170000xxxxxxxx
    }
}