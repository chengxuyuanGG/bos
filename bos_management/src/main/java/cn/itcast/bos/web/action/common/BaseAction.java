package cn.itcast.bos.web.action.common;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@SuppressWarnings("all")
public class BaseAction<T> extends ActionSupport implements ModelDriven<T> {

	protected T model;

	@Override
	public T getModel() {
		return model;
	}

	// 构造方法
	public BaseAction() {
		try {
			// 获取调用当前对象的对象
			ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
			// 通过type获取当前对象的泛型
			Class actualTypeArguments = (Class) type.getActualTypeArguments()[0];
			// 将值付给model
			model = (T) actualTypeArguments.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected int page;
	protected int rows;

	public void setPage(int page) {
		this.page = page;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}
	
	public void pushPageDatakToValueStack(Page<T> page) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", page.getTotalElements());
		result.put("rows", page.getContent());
		ActionContext.getContext().getValueStack().push(result);
	}
	
}
