package cn.tedu.sp11.filter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import cn.tedu.web.util.JsonResult;

@Component
public class AccessFilter extends ZuulFilter{
	@Override
	public boolean shouldFilter() {
	    //对指定的serviceid过滤，如果要过滤所有服务，直接返回 true

		RequestContext ctx = RequestContext.getCurrentContext();/*当前这次请求的上下文对象*/
		String serviceId = (String) ctx.get(FilterConstants.SERVICE_ID_KEY);
		if(serviceId.equals("item-service")) {
			return true;
		}
		return false;
	}

	@Override
	public Object run() throws ZuulException {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest req = ctx.getRequest();
		String token = req.getParameter("token");
		if (token == null) {
			//此设置会阻止请求被路由到后台微服务
			ctx.setSendZuulResponse(false);
			//向客户端的响应
			/*String json= JsonResult.err()
					.code(JsonResult.NOT_LOGIN)
					.msg("not login")
					.toString();
			ctx.addZuulRequestHeader("Content-Type","application/json;charset=UTF-8");
			ctx.setResponseBody(json);*/
			ctx.setResponseStatusCode(200);
			ctx.setResponseBody(JsonResult.err().code(JsonResult.NOT_LOGIN).toString());
		}
		//zuul过滤器返回的数据设计为以后扩展使用，
		//目前该返回值没有被使用
		return null;
	}

	@Override
	public String filterType() {
		return FilterConstants.PRE_TYPE;
	}/*过滤器的类型
	pre（前置），post（后置） routing（路由）*/

	@Override
	public int filterOrder() {
	    //该过滤器顺序要 > 5，才能得到 serviceid
		/*在第五个过滤器的中在上下文对象添加了人serviceId，在后面过滤器里才能访问serviceId*/
		return FilterConstants.PRE_DECORATION_FILTER_ORDER+1;
	}
}