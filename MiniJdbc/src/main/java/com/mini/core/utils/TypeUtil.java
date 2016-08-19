package com.mini.core.utils;

/**
 * <p>TypeUtil.java
 * <p>JAVA基本类型互相转换
 * 
 * @author sxjun
 * @time 2016/01/26
 * @version 1.0
 */
public class TypeUtil {

    /**
     * 转为字符串
     * @param obj
     * @return 为null时返回空字符串
     */
    public static String object2String(Object obj) {
		String str = "";
		try {
		    str = (String) obj;
		    if (str == null) {
		    	str = "";
		    }
		} catch (ClassCastException ce) {
		    try {
		    	str = String.valueOf(obj);
		    } catch (Exception e) {
		    	str = "";
		    }
		}
		return str.trim();
    }

    /**
     * 转null为空字符
     * 
     * @param str
     * @return
     */
    public static String nullOfString(Object obj) {
		String str = object2String(obj);
		return str;
    }

    /**
     * 转null为Int
     * 
     * @param obj
     * @return
     */
    public static int nullOfInt(Object obj) {
    	return object2Int(obj);
    }

    // *****************Object 转 其他类型*****************

    /**
     * 转字节
     * 
     * @param str
     * @return
     * @throws 如果无法转换
     *             抛出 NumberFormatException
     */
    public static byte object2Byte(Object obj) {
		String str = object2String(obj);
		str = nullOfString(str);
		try {
		    return Byte.parseByte(str);
		} catch (NumberFormatException e) {
		    System.out.println("转换出错" + e);
		    throw e;
		}
    }

    /**
     * 转布尔
     * 
     * @param str
     * @return 为1或"true"返回真 否则假
     */
    public static boolean object2Boolean(Object obj) {
		String str = object2String(obj);
		if (str.equals("1")) {
		    return true;
		} else if (str.equals("0")) {
		    return false;
		} else {
		    try {
			return Boolean.parseBoolean(str);
		    } catch (Exception e) {
			return false;
		    }
		}
    }

    /**
     * 转Int
     * 
     * @param str
     * @return 相应的值 或 零
     */
    public static int object2Int(Object obj) {
		String str = object2String(obj);
		int i = 0;
		try {
		    i = Integer.parseInt(str.trim());
		} catch (NumberFormatException e) {
		    i = 0;
		}
		return i;
    }

    /**
     * 转short
     * 
     * @param obj
     * @return 相应的值 或 零
     */
    public static short object2Short(Object obj) {
		String str = object2String(obj);
		short i = 0;
		try {
		    i = Short.parseShort(str.trim());
		} catch (NumberFormatException e) {
		    i = 0;
		}
		return i;
    }

    /**
     * 转Double
     * 
     * @param obj
     * @return 相应的值 或 零
     */
    public static double object2Double(Object obj) {
		String str = object2String(obj);
		double i = 0;
		try {
		    i = Double.parseDouble(str.trim());
		} catch (NumberFormatException e) {
		    i = 0;
		}
		return i;
    }

    /**
     * 转Long
     * 
     * @param obj
     * @return 相应的值 或 零
     */
    public static long object2Long(Object obj) {
		String str = object2String(obj);
		Long li = new Long(0);
		try {
		    li = Long.valueOf(str);
		} catch (NumberFormatException e) {
		}
		return li.longValue();
    }

    // ***************** 其他类型相互转换 *****************

    /**
     * double转long
     * 
     * @param d
     * @return 只截取前面的整数
     */
    public static long double2Long(double d) {
		long l = 0;
		try {
		    // double转换成long前要过滤掉double类型小数点后数据
		    l = Long.parseLong(String.valueOf(d).substring(0,
			    String.valueOf(d).lastIndexOf(".")));
		} catch (Exception e) {
		    l = 0;
		}
		return l;
    }

    /**
     * double转int
     * 
     * @param d
     * @return 只截取前面的整数
     */
    public static int double2Int(double d) {
		int i = 0;
		try {
		    // double转换成Int前要过滤掉double类型小数点后数据
		    i = Integer.parseInt(String.valueOf(d).substring(0,
			    String.valueOf(d).lastIndexOf(".")));
		} catch (Exception e) {
		    i = 0;
		}
		return i;
    }

    /**
     * double转long(四舍五入)
     * 
     * @param d
     * @return 只截取前面的整数 (四舍五入)
     */
    public static long double2LongWhithRound(double d) {
		long l = 0;
		try {
		    l = Math.round(d);
		} catch (Exception e) {
		    l = 0;
		}
		return l;
    }

    /**
     * double转int(四舍五入)
     * 
     * @param d
     * @return 只截取前面的整数 (四舍五入)
     */
    public static int double2IntWhithRound(double d) {
		int i = 0;
		try {
		    i = (int) Math.round(d);
		} catch (Exception e) {
		    i = 0;
		}
		return i;
    }

    /**
     * long 转double
     * @param d
     * @return
     */
    public static double long2Double(long d) {
		double l = 0;
		try {
		    l = Double.parseDouble(String.valueOf(d));
		} catch (Exception e) {
		    l = 0;
		}
		return l;
    }

    /**
     * long 转int
     * @param d
     * @return
     */
    public static int long2Int(long d) {
		int l = 0;
		try {
		    l = Integer.parseInt(String.valueOf(d));
		} catch (Exception e) {
		    l = 0;
		}
		return l;
    }
    
    
    public static float object2Float(Object obj){
    	String str = object2String(obj);
	    Float f = new Float(0);
		try {
		    f = Float.valueOf(str);
		} catch (NumberFormatException e) {
		}
		return f.floatValue();
    }
}