package cn.tedu.sp11;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@EnableDiscoveryClient
@SpringBootApplication
public class Sp11ZuulApplication {

	public static void main(String[] args) {
		SpringApplication.run(Sp11ZuulApplication.class, args);
	}
   /*zuul
   作为一个独立的服务，部署在系统前面
   *
   * 不推荐重试，在最前面重试，会造成后台所有服务器压力倍增
   */
	/*feign：业务微服务系统 内部，服务和服务之间调用
	不推荐启用重试hystrix
	会造成混乱，无法得知那个服务出现了熔断或者降级
	* */
}
