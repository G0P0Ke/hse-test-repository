package andreev.group.test_repository.configuration

import andreev.group.test_repository.input.rest.filter.TraceIdWebFilter
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class ServletFilterConfiguration {

    @Bean
    fun traceIdFilterRegistrationBean(): FilterRegistrationBean<TraceIdWebFilter> {
        val registrationBean = FilterRegistrationBean<TraceIdWebFilter>()
        val traceIdWebFilter = TraceIdWebFilter()
        registrationBean.filter = traceIdWebFilter
        registrationBean.order = Integer.MAX_VALUE
        return registrationBean
    }

}

