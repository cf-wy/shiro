package com.shiro.demo.config;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootConfiguration
public class FilterConfig {
    /*@Value("${sso.loginUrl}")
    private String loginUrl;
    @Value("${sso.successUrl}")
    private String successUrl;
    @Value("${sso.unauthorizedUrl")
    private String unauthorizedUrl;
    @Value("${sso.filterChainDefinitions}")
    private List<String> filterChainDefinitions;*/

    /*@Bean
    public FilterRegistrationBean proxyFilterRegistrationBean(){
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new DelegatingFilterProxy("shiroFilter"));
        registration.addUrlPatterns("/*");
        registration.addInitParameter("targetFilterLifecycle","true");
        return registration;
    }*/

    /**
     * 为了解决自定义过滤器加入到servlet过滤器链
     * @param formAuthenticationFilter
     * @return
     */
    @Bean
    public FilterRegistrationBean cancelMyFilterRegistration(FormAuthenticationFilter formAuthenticationFilter) {
        FilterRegistrationBean registration = new FilterRegistrationBean(formAuthenticationFilter);
        registration.setEnabled(false);
        return registration;
    }
    /*@Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilter=new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);
        shiroFilter.setLoginUrl(loginUrl);
        shiroFilter.setSuccessUrl(successUrl);
        shiroFilter.setUnauthorizedUrl(unauthorizedUrl);
        FormAuthenticationFilter authc=new FormAuthenticationFilter();
        authc.setPasswordParam("password");
        authc.setFailureKeyAttribute("shiroLoginFailure");
        authc.setUsernameParam("username");
        Map<String, Filter> filters=new LinkedHashMap<>();
        filters.put("authc",authc);
        shiroFilter.setFilters(filters);
        if(!CollectionUtils.isEmpty(filterChainDefinitions)){

            Map<String,String> map=new LinkedHashMap<>();
            map
        }
        shiroFilter.setFilterChainDefinitionMap();
        return shiroFilter;
    }*/
}
