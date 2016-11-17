package com.quanta.aj.yunanjian.util;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * 字符串工具类
 * </p>
 * <p>
 * create: 2010-12-21 上午10:25:28
 * </p>
 * 
 * @author 许德建[xudejian@126.com]
 * @version 1.0
 */
public class StringUtils {

	/**
	 * <p>
	 * 截断字节数,一个中文字包含两个字节数
	 * </p>
	 * 
	 * @param str
	 * @param size
	 * @return String
	 */
	public static String toByteSizeString(String str, int size) {
		return toByteSizeString(str, size, 0);
	}

	/**
	 * <p>
	 * 截断字节数,一个中文字包含两个字节数
	 * </p>
	 * 
	 * @param str
	 * @param size
	 * @param index
	 * @return String
	 */
	public static String toByteSizeString(String str, int size, int index) {
		if (str == null || size <= 0) {
			return "";
		}
		byte[] bs = str.getBytes();
		if (bs.length <= size) {
			return str;
		}
		if (index < 0) {
			index = 0;
		}
		char[] cs = str.toCharArray();
		if (cs.length <= index) {
			return "";
		}
		StringBuilder buf = new StringBuilder();
		for (int i = index; i < cs.length; i++) {
			if (buf.toString().getBytes().length > size) {
				break;
			}
			buf.append(cs[i]);
		}
		if (buf.toString().getBytes().length > size) {
			buf.deleteCharAt(buf.length() - 1);
		}
		return buf.toString();
	}

	/**
	 * <p>
	 * 查找字符串中符合正则的子串
	 * </p>
	 * 
	 * @param input
	 * @param regex
	 * @return List<String>
	 */
	public static List<String> find(String input, String regex) {
		if (input == null || input.length() == 0 || regex == null
				|| regex.length() == 0) {
			return new ArrayList<String>();
		}
		Matcher matcher = Pattern.compile(regex,
				Pattern.CASE_INSENSITIVE | Pattern.DOTALL).matcher(input);
		List<String> groups = new ArrayList<String>();
		while (matcher.find()) {
			groups.add(matcher.group());
		}
		return groups;
	}

	/**
	 * <p>
	 * 是否匹配id 123,456,789,...
	 * </p>
	 * 
	 * @param input
	 * @param id
	 * @return Boolean
	 */
	public static Boolean matcheId(String input, String id) {
		if (input == null || id == null)
			return false;
		return Pattern.matches("^(|.+,)" + id + "(,.*+|)$", input);
	}

	/**
	 * <p>
	 * 字符串拆分成Long集合
	 * </p>
	 * 
	 * @param str
	 * @param regex
	 * @return List<Long>
	 */
	public static List<Long> split(String str, String regex) {
		List<Long> list = new ArrayList<Long>();
		if (str != null && (str = str.trim()).length() > 0) {
			if (regex == null || regex.length() == 0) {
				regex = ",";
			}
			String[] arr = str.split(regex);
			for (String a : arr) {
				try {
					list.add(Long.valueOf(a));
				} catch (NumberFormatException e) {
				}
			}
		}
		return list;
	}

	/**
	 * <p>
	 * 格式化集合为字符串，默认逗号分割
	 * </p>
	 * 
	 * @param <T>
	 * @param coll
	 * @param seperator
	 * @return String
	 */
	public static <T> String join(Collection<T> coll, String seperator) {
		if (coll == null || coll.isEmpty()) {
			return "";
		}
		if (seperator == null || seperator.length() == 0) {
			seperator = ",";
		}
		StringBuilder buf = new StringBuilder();
		Iterator<T> it = coll.iterator();
		buf.append(it.next());
		while (it.hasNext()) {
			buf.append(seperator).append(it.next());
		}
		return buf.toString();
	}

	/**
	 * <p>
	 * 格式化数值为字符串，默认逗号分割
	 * </p>
	 * 
	 * @param <T>
	 * @param arr
	 * @param seperator
	 * @return String
	 */
	public static <T> String join(T[] arr, String seperator) {
		if (arr == null || arr.length == 0) {
			return "";
		}
		if (seperator == null || seperator.length() == 0) {
			seperator = ",";
		}
		StringBuilder sb = new StringBuilder();
		sb.append(arr[0]);
		for (int i = 1; i < arr.length; i++) {
			sb.append(seperator).append(arr[i]);
		}
		return sb.toString();
	}

	/**
	 * <p>
	 * 格式化指定个数的字符串为合并字符串，默认逗号分割
	 * </p>
	 * 
	 * @param str
	 * @param length
	 * @param seperator
	 * @return String
	 */
	public static String join(String str, int length, String seperator) {
		if (str == null || length <= 0) {
			return "";
		}
		if (seperator == null || seperator.length() == 0) {
			seperator = ",";
		}
		StringBuilder sb = new StringBuilder(str);
		for (int i = 1; i < length; i++) {
			sb.append(seperator).append(str);
		}
		return sb.toString();
	}

	/**
	 * <p>
	 * 截取指定宽度字数串，非ascii码或大写字符作为2个字
	 * </p>
	 * 
	 * @param str
	 * @param length
	 * @return String
	 */
	public static String charLength(String str, int length) {
		if (str == null || length <= 0) {
			return "";
		}
		char[] chs = str.toCharArray();
		if (chs.length * 2 <= length) {
			return str;
		}
		StringBuilder targetBuffer = new StringBuilder();
		int len = 0;
		int chValue;
		for (char ch : chs) {
			chValue = (int) ch;
			if (chValue > 127 || chValue >= 65 && chValue <= 90) {
				len += 2;
			} else {
				len++;
			}
			if (len > length) {
				break;
			}
			targetBuffer.append(ch);
		}
		return targetBuffer.toString();
	}

	/**
	 * <p>
	 * 前面补指定字符，若串超过指定长度或者指定长度小于等于零，则返回原串
	 * </p>
	 * 
	 * @param str
	 * @param length
	 * @param chr
	 * @return String
	 */
	public static String fillChar(String str, int length, char chr) {
		if (length <= 0) {
			return str;
		}
		if (str == null) {
			str = "";
		}
		int l = length - str.length();
		if (l <= 0) {
			return str;
		}
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < l; i++) {
			buf.append(chr);
		}
		return buf + str;
	}

	/**
	 * <p>
	 * 转换成输出的js字符串，替换双引号（\"），换行（\n），转义符（\）
	 * </p>
	 * 
	 * @param str
	 * @return String
	 */
	public static String toJsString(String str) {
		if (str == null) {
			return "";
		}
		return str.replaceAll("\\\\", "\\\\\\\\").replaceAll("\"", "\\\\\"")
				.replaceAll("\n", "\\\\n");
	}

	/**
	 * 字符串拼接
	 * 
	 * @param objects
	 * @return String
	 */
	public static String concat(Object... objects) {
		if (objects == null || objects.length == 0) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for (Object object : objects) {
			if (object != null) {
				sb.append(object);
			}
		}
		return sb.toString();
	}

	/**
	 * 是否为中文字符
	 * 
	 * @param c
	 * @return boolean
	 */
	public static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		return ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS;
	}

	/**
	 * 是否含有中文字符
	 * 
	 * @param str
	 * @return boolean
	 */
	public static final boolean isChinese(String str) {
		char[] ch = str.toCharArray();
		for (int i = 0; i < ch.length; i++) {
			if (isChinese(ch[i])) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 中文数值表示转换成整型
	 * 
	 * @param chinese
	 * @return int
	 */
	public static final int toInt(String chinese) {
		int result = 0;
		int yi = 1;// 记录高级单位
		int wan = 1;// 记录高级单位
		int ge = 1;// 记录单位
		char[] chs = chinese.toCharArray();
		for (int i = chs.length - 1; i >= 0; i--) {
			char c = chs[i];
			int temp = 0;// 记录数值
			switch (c) {
			// 数值
			case '〇':
			case '零':
				temp = 0;
				break;
			case '一':
				temp = 1 * ge * wan * yi;
				ge = 1;
				break;
			case '二':
				temp = 2 * ge * wan * yi;
				ge = 1;
				break;
			case '三':
				temp = 3 * ge * wan * yi;
				ge = 1;
				break;
			case '四':
				temp = 4 * ge * wan * yi;
				ge = 1;
				break;
			case '五':
				temp = 5 * ge * wan * yi;
				ge = 1;
				break;
			case '六':
				temp = 6 * ge * wan * yi;
				ge = 1;
				break;
			case '七':
				temp = 7 * ge * wan * yi;
				ge = 1;
				break;
			case '八':
				temp = 8 * ge * wan * yi;
				ge = 1;
				break;
			case '九':
				temp = 9 * ge * wan * yi;
				ge = 1;
				break;
			// 单位，前缀是单数字
			case '十':
				ge = 10;
				break;
			case '百':
				ge = 100;
				break;
			case '千':
				ge = 1000;
				break;
			// 高级单位，前缀可以是多个数字
			case '万':
				wan = 10000;
				ge = 1;
				break;
			case '亿':
				yi = 100000000;
				wan = 1;
				ge = 1;
				break;
			default:
				return -1;
			}
			result += temp;
		}
		if (ge > 1) {
			result += 1 * ge * wan * yi;
		}
		return result;
	}

	/**
	 * 把阿拉伯数字转为中文数字
	 * 
	 * @param num
	 * @return String
	 */
	public static String toChinese(int num) {
		StringBuilder sb = new StringBuilder();
		final String CN_NUM = "〇一二三四五六七八九";
		final String CN_W = "十百千";
		int n = num;
		for (int i = 0; n > 0; i++) {
			int g = n % 10;
			if ((i >= 1) && (i <= 3)) {
				if (g != 0) {
					sb.insert(0, CN_W.charAt(i - 1));
				}
			} else if (i == 4) {
				sb.insert(0, '万');
			} else if ((i >= 5) && (i <= 7)) {
				if (g != 0) {
					sb.insert(0, CN_W.charAt(i - 4 - 1));
				}
			} else if (i == 8) {
				sb.insert(0, '亿');
			} else if ((i >= 9) && (i <= 10)) {
				if (g != 0) {
					sb.insert(0, CN_W.charAt(i - 8 - 1));
				}
			}
			if ((g != 0) || ((i > 0) && (sb.charAt(0) != '〇'))) {
				sb.insert(0, CN_NUM.charAt(g));
			}
			n = n / 10;
		}
		if ((sb.length() > 1) && (sb.charAt(sb.length() - 1) == '〇')) {
			sb.setLength(sb.length() - 1);
		}
		return sb.toString();
	}

	/**
	 * 屏蔽字符串
	 * 
	 * @param str
	 * @param prefix
	 *            前缀位数
	 * @param suffix
	 *            后缀位数
	 * @return String
	 */
	public static String hide(String str, int prefix, int suffix) {
		if (prefix < 0) {
			prefix = 0;
		}
		if (suffix < 0) {
			suffix = 0;
		}
		int len = 0;
		if (str == null || (len = str.length() - (prefix + suffix)) <= 0) {
			return str;
		}
		StringBuilder sb = new StringBuilder();
		if (prefix > 0) {
			sb.append(str.substring(0, prefix));
		}
		for (int i = 0; i < len; i++) {
			sb.append('*');
		}
		if (suffix > 0) {
			sb.append(str.substring(prefix + len));
		}
		return sb.toString();
	}

	/**
	 * 转换字符串
	 * 
	 * @param str
	 * @param charset
	 * @param charsetFrom
	 * @return String
	 */
	public static String trans(String str, String charset, String charsetFrom) {
		if (str == null) {
			return null;
		}
		try {
			byte[] bs = charsetFrom != null ? str.getBytes(charsetFrom) : str
					.getBytes();
			str = new String(bs, charset);
		} catch (UnsupportedEncodingException e) {
		}
		return str;
	}

	/***
	 * <p>
	 * 字节数据转16进制
	 * </p>
	 * <p>
	 * author 许德建【xudejian_dev@126.com，QQ:66018777】
	 * </p>
	 * 
	 * @param src
	 * @return String
	 */
	public static String bytesToHex(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder();
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

	/**
	 * <p>
	 * 16进制转字节数组
	 * </p>
	 * <p>
	 * author 许德建【xudejian_dev@126.com，QQ:66018777】
	 * </p>
	 * 
	 * @param hex
	 * @return byte[]
	 */
	public static byte[] hexToBytes(String hex) {
		if (hex == null || hex.isEmpty()) {
			return null;
		}
		hex = hex.toUpperCase();
		int length = hex.length() / 2;
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			d[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * (i + 1)),
					16);
		}
		return d;
	}

    public static String str2hex(String str) {
        char[] chars = "0123456789ABCDEF".toCharArray();
        StringBuilder sb = new StringBuilder();
        try {
            byte[] bs = str.getBytes("GBK");
            for (byte b : bs) {
                sb.append(chars[(b & 0x0f0) >> 4]);
                sb.append(chars[b & 0x0f]);
            }
        }
        catch (UnsupportedEncodingException e) {
        }
        return sb.toString();
    }

    public static String hex2str(String hex) {
        byte[] bs = new byte[hex.length() / 2];
        for (int i = 0; i < bs.length; i++) {
            try {
                bs[i] = (byte) (0xff & Integer.parseInt(hex.substring(i * 2, i * 2 + 2), 16));
            }
            catch (Exception e) {
                return "";
            }
        }
        try {
            return new String(bs, "GBK");
        }
        catch (UnsupportedEncodingException e) {
        }
        return "";
    }

    public static String fetch(String str, int offset, int length) {
        if(str == null || str.length() < offset) return "";
        int end = Math.min(str.length(),offset + length);
        return str.substring(offset, end).trim();
    }
    /**
     * 字符串转为日期
     * @param style 日期格式
     * @param date 对应的字符串
     * @return
     */
    public static Date strToDate(String style, String date) {
        SimpleDateFormat formatter = new SimpleDateFormat(style);
        Date dateT = new Date();
    	try {
			dateT = formatter.parse(date);
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
        return dateT;
    } 
    /**
     * 日期转为字符串
     * @param style 日期格式
     * @param date 对应的日期
     * @return
     */
    public static String dateToStr(String style, Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(style);
        return formatter.format(date);  
    }

}
