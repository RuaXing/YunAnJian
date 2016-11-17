package com.quanta.aj.yunanjian.orm;

import com.ziwu.util.NumberUtils;
import com.ziwu.util.ReflectUtils;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网格数据，带有页脚
 * </p>
 * 
 * @author 许德建【Email：xudejian_dev@126.com；QQ：66018777】
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Page<T> {

	public boolean notPage;

	/** 页码 */
	public int pageNumber;

	/** 页宽 **/
	public int pageSize;

	/** 总记录数 */
	public long total;

	/** 数据行 */
	public List<T> rows;

	/** 页脚行 */
	public List<Map<String, Object>> footer;

	/** 排序字段名 **/
	public String order;

	/** 排序方式 **/
	public String sort;

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	/**
	 * <p>
	 * 为生成页脚行进行统计
	 * </p>
	 * <p>
	 * author 许德建【Email：xudejian_dev@126.com；QQ：66018777】
	 * </p>
	 * 
	 * @return 网格数据
	 */
	public Page<T> countForFooter(Field... fs) {
		if (rows == null || rows.isEmpty()) {
			return this;
		}
		List<FieldCount> fcs = new ArrayList<FieldCount>();
		List<FieldOpt> fos = new ArrayList<FieldOpt>();
		List<FieldText> fts = new ArrayList<FieldText>();
		List<FieldMin> fns = new ArrayList<FieldMin>();
		List<FieldMax> fxs = new ArrayList<FieldMax>();
		for (Field f : fs) {
			if (f instanceof FieldCount) {
				fcs.add((FieldCount) f);
			}
			else if (f instanceof FieldOpt) {
				fos.add((FieldOpt) f);
			}
			else if (f instanceof FieldText) {
				fts.add((FieldText) f);
			}
			else if (f instanceof FieldMin) {
				fns.add((FieldMin) f);
			}
			else if (f instanceof FieldMax) {
				fxs.add((FieldMax) f);
			}
		}
		if (fcs.isEmpty()) {
			return this;
		}
		Double val;
		for (Object row : rows) {
			if (row instanceof Map) {
				for (FieldCount fc : fcs) {
					val = NumberUtils.parseDouble(((Map<?, ?>) row).get(fc.field));
					if (val != null) {
						fc.count += val;
					}
				}
				for (FieldMin fn : fns) {
					val = NumberUtils.parseDouble(((Map<?, ?>) row).get(fn.field));
					if (val != null) {
						if (fn.value == null) {
							fn.value = val;
						}
						else {
							fn.value = Math.min(fn.value.doubleValue(), val.doubleValue());
						}
					}
				}
				for (FieldMax fx : fxs) {
					val = NumberUtils.parseDouble(((Map<?, ?>) row).get(fx.field));
					if (val != null) {
						if (fx.value == null) {
							fx.value = val;
						}
						else {
							fx.value = Math.max(fx.value.doubleValue(), val.doubleValue());
						}
					}
				}
			}
			else {
				for (FieldCount fc : fcs) {
					val = NumberUtils.parseDouble(ReflectUtils.eval(row, fc.field));
					if (val != null) {
						fc.count += val;
					}
				}
				for (FieldMin fn : fns) {
					val = NumberUtils.parseDouble(ReflectUtils.eval(row, fn.field));
					if (val != null) {
						if (fn.value == null) {
							fn.value = val;
						}
						else {
							fn.value = Math.min(fn.value.doubleValue(), val.doubleValue());
						}
					}
				}
				for (FieldMax fx : fxs) {
					val = NumberUtils.parseDouble(ReflectUtils.eval(row, fx.field));
					if (val != null) {
						if (fx.value == null) {
							fx.value = val;
						}
						else {
							fx.value = Math.max(fx.value.doubleValue(), val.doubleValue());
						}
					}
				}
			}
		}
		Map<String, Object> frow = new HashMap<String, Object>();
		List<Map<String, Object>> frows = new ArrayList<Map<String, Object>>();
		frows.add(frow);
		footer = frows;
		// 合计统计项
		for (FieldCount fc : fcs) {
			if (fc.count - (int) fc.count == 0) {
				frow.put(fc.name, (int) fc.count);
			}
			else if (fc.precision != null && fc.precision > 0) {
				frow.put(fc.name, NumberUtils.format(fc.count, fc.precision.intValue()));
			}
			else {
				frow.put(fc.name, fc.count);
			}
		}
		// 求运算
		for (FieldOpt fo : fos) {
			val = 0d;
			Number master = (Number) frow.get(fo.master);
			Number factor = (Number) frow.get(fo.factor);
			if (master != null && factor != null) {
				switch (fo.opt) {
				case plus:
					val = master.doubleValue() + factor.doubleValue();
					break;
				case minus:
					val = master.doubleValue() - factor.doubleValue();
					break;
				case mul:
					val = master.doubleValue() * factor.doubleValue();
					break;
				case div:
					val = factor.doubleValue() == 0 ? 0 : master.doubleValue() / factor.doubleValue();
					break;
				default:
					break;
				}
			}
			// 定义精度位数
			if (fo.precision != null) {
				frow.put(fo.name, NumberUtils.format(val, fo.precision.intValue()));
			}
			// 如果为整数值，则取整
			else if (val.doubleValue() - val.longValue() == 0) {
				frow.put(fo.name, val.intValue());
			}
			else {
				frow.put(fo.name, val);
			}
		}
		// 文本
		for (FieldText ft : fts) {
			frow.put(ft.name, ft.text);
		}
		// 最小值
		for (FieldMin fn : fns) {
			frow.put(fn.name, fn.value != null ? fn.value.doubleValue() : 0);
		}
		// 最大值
		for (FieldMax fx : fxs) {
			frow.put(fx.name, fx.value != null ? fx.value.doubleValue() : 0);
		}
		return this;
	}

	/**
	 * <p>
	 * 页脚统计字段
	 * </p>
	 * 
	 * @author 许德建【Email：xudejian_dev@126.com；QQ：66018777】
	 */
	public abstract static class Field {

		public String name;

		public Field(String name) {
			super();
			this.name = name;
		}
	}

	/**
	 * <p>
	 * 统计页脚行合计
	 * </p>
	 * 
	 * @author 许德建【Email：xudejian_dev@126.com；QQ：66018777】
	 */
	public static class FieldCount extends Field {

		public String field;

		public double count;

		public Integer precision;

		public FieldCount(String name, String field) {
			super(name);
			this.field = field;
		}

		public FieldCount(String name, String field, Integer precision) {
			super(name);
			this.field = field;
			this.precision = precision;
		}
	}

	/**
	 * <p>
	 * 字段运算符
	 * </p>
	 * 
	 * @author 许德建【Email：xudejian_dev@126.com；QQ：66018777】
	 */
	public static enum OPT {
		plus, minus, mul, div
	}

	/**
	 * <p>
	 * 统计页脚行字段运算
	 * </p>
	 * 
	 * @author 许德建【Email：xudejian_dev@126.com；QQ：66018777】
	 */
	public static class FieldOpt extends Field {

		public String master;

		public String factor;

		public Integer precision;

		/**
		 * 运算符
		 * 
		 * @see OPT
		 */
		public OPT opt;

		public FieldOpt(String name, String master, String factor, OPT opt) {
			super(name);
			this.master = master;
			this.factor = factor;
			this.opt = opt;
		}

		public FieldOpt(String name, String master, String factor, OPT opt, Integer precision) {
			super(name);
			this.master = master;
			this.factor = factor;
			this.opt = opt;
			this.precision = precision;
		}

	}

	/**
	 * <p>
	 * 页脚文本
	 * </p>
	 * 
	 * @author 许德建【Email：xudejian_dev@126.com；QQ：66018777】
	 */
	public static class FieldText extends Field {

		public String text;

		public FieldText(String name, String text) {
			super(name);
			this.text = text;
		}
	}

	/**
	 * <p>
	 * 最小值
	 * </p>
	 * 
	 * @author 许德建【Email：xudejian_dev@126.com；QQ：66018777】
	 */
	public static class FieldMin extends Field {

		public String field;

		public Double value;

		public FieldMin(String name, String field) {
			super(name);
			this.field = field;
		}
	}

	/**
	 * <p>
	 * 最大值
	 * </p>
	 * 
	 * @author 许德建【Email：xudejian_dev@126.com；QQ：66018777】
	 */
	public static class FieldMax extends Field {

		public String field;

		public Double value;

		public FieldMax(String name, String field) {
			super(name);
			this.field = field;
		}
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public boolean isNotPage() {
		return notPage;
	}

	public void setNotPage(boolean notPage) {
		this.notPage = notPage;
	}
}
