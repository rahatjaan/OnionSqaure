package com.onionsquare.core.util;

 
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

 

public class Util {

	public static String concate(String[] strings, String seprater) {
		String str = "";
		if (strings != null) {
			for (String string : strings) {
				if (!isNullOrEmptyTrimmed(string)) {
					if (!str.isEmpty()) {
						str += seprater;
					}
					str += string;
				}
			}
		}
		return str;
	}

	public static String convertFirstLowerCase(String sourceStr) {
		char[] stringArray = sourceStr.toCharArray();
		stringArray[0] = Character.toLowerCase(stringArray[0]);
		sourceStr = new String(stringArray);
		return sourceStr;
	}

	public static String createFirstNotEqualQueryClause(String columnName, Integer value) {
		if (value == null) {
			return "";
		}
		return "" + columnName + " <> " + value;
	}

 

	public static String createFirstQueryClause(String columnName, Double value) {
		if (value == null) {
			return " 1=1 ";
		}
		return " " + columnName + " = " + value;
	}

	public static String createFirstQueryClause(String columnName, Integer value) {
		if (value == null) {
			return " 1=1 ";
		}
		return " " + columnName + " = " + value;
	}
    
	public static String createFirstQueryClauseIntvalue(String columnName, String value) {
		if (isNullOrEmptyTrimmed(value)) {
			return " 1=1 ";
		}
		return " " + columnName + " = " + value.trim() + "";
	}
	
	public static String createFirstQueryClause(String columnName, String value) {
		if (isNullOrEmptyTrimmed(value)) {
			return " 1=1 ";
		}
		return " " + columnName + " = '" + value.trim() + "'";
	}

	public static String createFirstRangeQueryClause(String columnName, Double startValue, Double endValue) {
		if (startValue == null || endValue == null) {
			return " 1=1 ";
		}
		return columnName + " between " + startValue + " and " + endValue;
	}

	public static String createFirstRangeQueryClause(String columnName, Timestamp startDate, Timestamp endDate) {
		if (startDate == null || endDate == null) {
			return " 1=1 ";
		}
		return columnName + " between '" + startDate + "' and '" + endDate + "'";
	}

 

	public static String createNotEqualQueryClause(String columnName, Integer value) {
		if (value == null) {
			return " 1=1";
		}
		return " and " + columnName + " <> " + value;
	} 
 

	public static String createQueryClause(String columnName, Double value) {
		if (value == null) {
			return "";
		}
		return " and " + columnName + " = " + value;
	}
	public static String createQueryClause(String columnName, Timestamp timestamp) {
		if (timestamp == null) {
			return "";
		}
		return " and " + columnName + " = '" + timestamp+"'";
	}

	public static String createQueryClause(String columnName, Integer value) {
		if (value == null) {
			return "";
		}
		return " and " + columnName + " = " + value;
	}

	public static String createGTEQQueryClause(String columnName, Integer value) {
		if (value == null) {
			return "";
		}
		return " and " + columnName + " >= " + value;
	}

	public static String createQueryClause(String columnName, String value) {
		if (isNullOrEmptyTrimmed(value)) {
			return "";
		}
		return " and " + columnName + " = '" + value.trim() + "'";
	}

	public static String createRangeQueryClause(String columnName, BigDecimal startValue, BigDecimal endValue) {
		if (startValue == null || endValue == null) {
			return "";
		}
		return " and " + columnName + " between " + startValue + " and " + endValue;
	}

	public static String createRangeQueryClause(String columnName, Integer startValue, Integer endValue) {
		if (startValue == null || endValue == null) {
			return "";
		}
		return " and " + columnName + " between " + startValue + " and " + endValue;
	}
	public static String createRangeQueryClause(String columnName, Date startValue, Date endValue) {
		if (startValue == null || endValue == null) {
			return "";
		}
		return " and " + columnName + " between :startDate and :endDate";
	}

	public static String createRangeQueryClause(String columnName, Double startValue, Double endValue) {
		if (startValue == null || endValue == null) {
			return "";
		}
		return " and " + columnName + " between " + startValue + " and " + endValue;
	}

	public static String createRangeQueryClause(String columnName, Float startValue, Float endValue) {
		if (startValue == null || endValue == null) {
			return "";
		}
		return " and " + columnName + " between " + startValue + " and " + endValue;
	}

	public static String createRangeQueryClause(String columnName, Timestamp startDate, Timestamp endDate) {
		if (startDate == null || endDate == null) {
			return "";
		}
		return " and " + columnName + " between '" + startDate + "' and '" + endDate + "'";
	}

	/**
	 * Descending order
	 * 
	 * @param column
	 * @return
	 */
	public static String createOrderByDesc(String column) {
		if (column == null)
			return "";
		return " order by " + column + " DESC";
	}

	/**
	 * Ascending order
	 * 
	 * @param column
	 * @return
	 */
	public static String createOrderByAsc(String column) {
		if (column == null)
			return "";
		return " order by " + column + " ASC";
	}

	/**
	 * This clause can be used for only character columns
	 * 
	 * @return
	 */
	public static String createlikeFirstQueryClause(String columnName, String value) {
		if (columnName == null || value == null || "".equals(value) || "".equals(columnName)) {
			return " 1=1";
		}
		return " " + columnName + " like '%" + value + "%'";
	}

	/**
	 * This clause can be used for only character columns
	 * 
	 * @return
	 */
	public static String createlikeQueryClause(String columnName, String value) {
		if (columnName == null || value == null) {
			return "";
		}
		return " and " + columnName + " like '%" + value + "%'";
	}
    

	public static boolean isNullOrEmpty(String value) {
		return value == null || "".equals(value);
	}

	public static boolean isNullOrEmptyTrimmed(String value) {
		return value == null || "".equals(value.trim());
	}

	public static Double parseDouble(String value) {
		if (isNullOrEmpty(value)) {
			return null;
		}
		try {
			return Double.valueOf(value);
		} catch (NumberFormatException e) {
		}
		return null;
	}

	public static Integer parseInteger(String value) {
		if (isNullOrEmpty(value)) {
			return null;
		}
		try {
			return Integer.valueOf(value);
		} catch (NumberFormatException e) {
		}
		return null;
	}

	public static Long parseLong(String value) {
		if (isNullOrEmpty(value)) {
			return null;
		}
		try {
			return Long.valueOf(value);
		} catch (NumberFormatException e) {
		}
		return null;
	}
	
	public static float getFloatValue(BigDecimal value){
		return value != null ? value.floatValue() : 0;
	}
	
	public static long getLongValue(Timestamp time){
		return time != null ? time.getTime() : 0;
	}
}
