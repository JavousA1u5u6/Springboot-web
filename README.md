# Spring的发展 #

## Spring1.x 时代 ##
在Spring1.x时代，都是通过xml文件配置bean，随着项目的不断扩大，需要将xml配置分放到不同的配置文件中，需要频繁的在java类和xml配置文件中切换。

## Spring2.x时代 ##
随着JDK 1.5带来的注解支持，Spring2.x可以使用注解对Bean进行申明和注入，大大的减少了xml配置文件，同时也大大简化了项目的开发。

最佳实践：
1、	应用的基本配置用xml，比如：数据源、资源文件等；
2、	业务开发用注解，比如：Service中注入bean等；

## Spring3.x到Spring4.x ##
从Spring3.x开始提供了Java配置方式，使用Java配置方式可以更好的理解你配置的Bean，现在我们就处于这个时代，并且Spring4.x和Spring boot都推荐使用java配置的方式



本演示项目主要意义用于演示Spring的Java配置

	核心注解:@Configuration 和 @Bean

Spring的Java配置方式是通过 @Configuration 和 @Bean 这两个注解实现的：
1、@Configuration 作用于类上，相当于一个xml配置文件；
2、@Bean 作用于方法上，相当于xml配置中的<bean>；


		


