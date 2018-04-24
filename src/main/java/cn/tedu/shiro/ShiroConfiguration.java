package cn.tedu.shiro;

import java.util.LinkedHashMap;
import java.util.Map;

//手动导入SecurityManager，否则默认是java.lang.SecurityManager
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.filter.DelegatingFilterProxy;

import cn.tedu.shiro.AuthRealm;

//@Configuration该注解表示这个类是配置文件类
//@Configuration 需要时开启，否则会拦截
public class ShiroConfiguration {

	 /**
     * 注册DelegatingFilterProxy（Shiro）
     *
     * @param dispatcherServlet
     * @return
     * @author SHANHY
     * @create  2016年1月13日
     */
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter(new DelegatingFilterProxy("shiroFilter"));
        //  该值缺省为false,表示生命周期由SpringApplicationContext管理,设置为true则表示由ServletContainer管理  
        filterRegistration.addInitParameter("targetFilterLifecycle", "true");
        filterRegistration.setEnabled(true);
        filterRegistration.addUrlPatterns("/*");
        return filterRegistration;
    }
		
	/**
	 * ShiroFilterFactoryBean 处理拦截资源文件问题。
	 * 注意：单独一个ShiroFilterFactoryBean配置是或报错的，以为在
	 * 初始化ShiroFilterFactoryBean的时候需要注入：SecurityManager
	 *
	 * Filter Chain定义说明 1、一个URL可以配置多个Filter，使用逗号分隔 2、当设置多个过滤器时，全部验证通过，才视为通过
	 * 3、部分过滤器可指定参数，如perms，roles
	 *
	 */
	//Bean注解表示该方法是用来生成Bean的方法，默认bean的id与方法名相同
	@Bean
	public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

		// 必须设置 SecurityManager
		shiroFilterFactoryBean.setSecurityManager(securityManager);

		// 拦截器.
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();

		// 配置退出过滤器,其中的具体的退出代码Shiro已经替我们实现了
		//filterChainDefinitionMap.put("/logout", "logout");

		// <!-- 过滤链定义，从上向下顺序执行，一般将 /**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
		// <!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
		
		filterChainDefinitionMap.put("/tologin.action", "anon");
		filterChainDefinitionMap.put("/login.action", "anon");
		filterChainDefinitionMap.put("/staticfile/**", "anon");
		//filterChainDefinitionMap.put("/", "anon");
		filterChainDefinitionMap.put("/**", "authc");
		
		// 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
		shiroFilterFactoryBean.setLoginUrl("/index.jsp");
		// 登录成功后要跳转的链接
		//shiroFilterFactoryBean.setSuccessUrl("/home.action");
		// 未授权界面;
		//shiroFilterFactoryBean.setUnauthorizedUrl("/403");

		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return shiroFilterFactoryBean;
	}
	
	/**
     *  开启shiro aop注解支持.
     *  使用代理方式;所以需要开启代码支持;
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
       AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
       authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
       return authorizationAttributeSourceAdvisor;
    }
	
	/**引入自定义的凭证匹配器
	 * @return
	 */
	/*@Bean
	public AuthCredential authCredential(){
		AuthCredential authCredential = new AuthCredential();
		return authCredential;
	}*/

	/**创建自定义的Realm，并注入自定义的加密mather
	 * @return
	 */
	/*@Bean
    public AuthRealm authRealm(){
		AuthRealm authRealm = new AuthRealm();
		authRealm.setCredentialsMatcher(authCredential());
       return authRealm;
    }*/
	
	@Bean
	public SecurityManager securityManager(AuthRealm authRealm) {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(authRealm);
		return securityManager;
	}

	@Bean  
	   public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {  
	       return new LifecycleBeanPostProcessor();  
	   }  
	  
	   @Bean  
	   @DependsOn("lifecycleBeanPostProcessor")  
	   public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {  
	       DefaultAdvisorAutoProxyCreator proxyCreator = new DefaultAdvisorAutoProxyCreator();  
	       proxyCreator.setProxyTargetClass(true); // this SETTING  
	       return proxyCreator;  
	   }  
	  
/*	   @Bean  
	   public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {  
	       AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();  
	       advisor.setSecurityManager(securityManager());  
	       return advisor;  
	   }*/ 
}

