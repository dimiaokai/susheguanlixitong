package cn.tedu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ImportResource;

/*@SpringBootApplication相当于三个注解：@Configuration 标志设置类；
 * @EnableAutoConfiguration 可以自动扫描该包及子孙包的注解配置
 * @ComponentScan 定义包扫描
 *  
 */
//@EnableTransactionManagement
@SpringBootApplication
@ImportResource("classpath:applicationContext-transaction.xml") //引入事务控制
@MapperScan("cn.tedu.mapper")      //开启mapper包扫描
public class Application extends SpringBootServletInitializer {
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application){
		return application.sources(Application.class);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
