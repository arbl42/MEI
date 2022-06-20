package eu.europa.esig.dss.web.config;

import eu.europa.esig.dss.utils.Utils;
import eu.europa.esig.dss.web.handler.MyLoginSuccessHandler;
import eu.europa.esig.dss.web.handler.MyLogoutSuccessHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.header.HeaderWriter;
import org.springframework.security.web.header.writers.DelegatingRequestMatcherHeaderWriter;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter.XFrameOptionsMode;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.MappedInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import java.util.Collection;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	private DataSource dataSource;

	@Value("${web.security.cookie.samesite}")
	private String samesite;

	@Value("${web.security.csp}")
	private String csp;
	
	/** API urls (REST/SOAP webServices) */
	private static final String[] API_URLS = new String[] {
			"/services/rest/**", "/services/soap/**"
	};

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// javadoc uses frames
		http.headers().addHeaderWriter(javadocHeaderWriter());
		http.headers().addHeaderWriter(svgHeaderWriter());
		http.headers().addHeaderWriter(serverEsigDSS());
		
		http.csrf().ignoringAntMatchers(API_URLS); // disable CSRF for API calls (REST/SOAP webServices)

		if (Utils.isStringNotEmpty(csp)) {
			http.headers().contentSecurityPolicy(csp);
		}

		// the non authenticated user is only allowed to go to the login page
		// (and also acccess the resources in the WebConfig.java file)
		http
			.authorizeRequests()
				.antMatchers("/login","/register","/css/**","/images/**","/scripts/**",
							"/webjars/**","/jar/**","/downloads/**","/doc/**","/apidocs/**").permitAll()
				.anyRequest().authenticated()
				.and()
			.formLogin()
				.loginPage("/login")
				.permitAll()
				.successHandler(loginSuccessHandler())
				.and()
			.logout()
				.permitAll()
				.logoutSuccessHandler(logoutSuccessHandler());
	}

	@Bean
	public MyLoginSuccessHandler loginSuccessHandler() {
		return new MyLoginSuccessHandler();
	}

	@Bean
	public MyLogoutSuccessHandler logoutSuccessHandler() {
		return new MyLogoutSuccessHandler();
	}

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	/* Métodos alternativos a definir uma variável de sessão
	Possivelmente é uma maneira melhor de obter o username...
	@Bean
	public boolean isUserLoggedIn() {
		return  SecurityContextHolder.getContext().getAuthentication() != null &&
		SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
	}

	@Bean
	public String getLoggedInUsername() {
		if(!isUserLoggedIn())
			return null;
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}*/

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		/*
		 * à partida, a datasource devia de ser definida no application.properties e ser autowired
		 * o problema é que já está a ser usada uma base de dados jdbc pela aplicação (em memória)
		 * pelo que somos obrigados a configurar uma nova "por código"
		 * (esta ligação também é definida em tsp-config.xml)
		 */

		DriverManagerDataSource  dataSourceBuilder = new DriverManagerDataSource();
        dataSourceBuilder.setDriverClassName("com.mysql.jdbc.Driver");
        dataSourceBuilder.setUrl("jdbc:mysql://localhost:3306/test?serverTimezone=UTC&useSSL=false");
        dataSourceBuilder.setUsername("root");
        dataSourceBuilder.setPassword("80365.0402");
		this.dataSource = dataSourceBuilder;

		auth.jdbcAuthentication()
		.dataSource(dataSource)
		.passwordEncoder(encoder())
		.usersByUsernameQuery("select username,password,1 from accounts where username = ?")
		.authoritiesByUsernameQuery("select username,'ROLE_USER' from accounts where username = ?");
	}

	@Bean
	public HeaderWriter javadocHeaderWriter() {
		final AntPathRequestMatcher javadocAntPathRequestMatcher = new AntPathRequestMatcher("/apidocs/**");
		final HeaderWriter hw = new XFrameOptionsHeaderWriter(XFrameOptionsMode.SAMEORIGIN);
		return new DelegatingRequestMatcherHeaderWriter(javadocAntPathRequestMatcher, hw);
	}

	@Bean
	public  HeaderWriter svgHeaderWriter() {
		final AntPathRequestMatcher javadocAntPathRequestMatcher = new AntPathRequestMatcher("/validation/diag-data.svg");
		final HeaderWriter hw = new XFrameOptionsHeaderWriter(XFrameOptionsMode.SAMEORIGIN);
		return new DelegatingRequestMatcherHeaderWriter(javadocAntPathRequestMatcher, hw);
	}
	
	@Bean
	public HeaderWriter serverEsigDSS() {
		return new StaticHeadersWriter("Server", "ESIG-DSS");
	}

	@Bean
	public MappedInterceptor cookiesInterceptor() {
		return new MappedInterceptor(null, new CookiesHandlerInterceptor());
	}

	/**
	 * The class is used to enrich "Set-Cookie" header with "SameSite=strict" value
	 *
	 * NOTE: Spring does not provide support of cookies handling out of the box
	 *       and requires a Spring Session dependency for that.
	 *       Here is a manual way of response headers configuration
	 */
	private final class CookiesHandlerInterceptor implements HandlerInterceptor {

		/** The "SameSite" cookie parameter name */
		private static final String SAMESITE_NAME = "SameSite";

		@Override
		public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
							   ModelAndView modelAndView) {
			if (Utils.isStringNotEmpty(samesite)) {
				Collection<String> setCookieHeaders = response.getHeaders(HttpHeaders.SET_COOKIE);
				if (Utils.isCollectionNotEmpty(setCookieHeaders)) {
					for (String header : setCookieHeaders) {
						header = String.format("%s; %s=%s", header, SAMESITE_NAME, samesite);
						response.setHeader(HttpHeaders.SET_COOKIE, header);
					}
				}
			}
		}
	}

}
