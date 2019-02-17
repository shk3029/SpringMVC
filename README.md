# SpringMVC 강의[백기선] (2019/2/3 ~)
[문서자료](https://docs.google.com/document/d/1V05o1_ZxhHIuN2-zkwxcwIcKpJ7V_XrLaJTAXl9VA54/edit#heading=h.27bngx7yn4d7)
### 1부. 스프링 MVC 동작 원리
- 1강 스프링 MVC 소개
  - MVC
    - M(모델), V(뷰), C(컨트롤러)
    - 모델 : 평범한 자바 객체 POJO
  - MVC 패턴 장점
    - 동시 다발적 개발이 가능함
    - 높은 결합도(논리적으로 관련있는 기능을 그룹화)와 낮은 의존도(뷰,모델, 컨트롤러가 독립적)
    - 개발 용이성 (책임이 구분)
    - 한 모델에 대한 여러 형태의 뷰를 가질 수 있음
  - MVC 패턴의 단점
    - 코드 네비게이션이 복잡함
    - 코드 일관성 유지 노력이 필요
    - 높은 학습 곡선
  - 기타
    - RequestMapping으로 url 매핑
    - 스프링 4.3부터 GetMapping, PostMapping 가능

- 2강 서블릿 소개
  - 서블릿
    - 자바 엔터프라이즈 에디션은 웹 애플리케이션 개발용 스팩과 API 제공
    - 요청 당 쓰레드 (만들거나, 풀에서 가져다가) 사용
    - 그 중에 가장 중요한 클래스중 하나가 HttpServlet
  - 서블릿 등장 이전에 사용하던 기술인 CGI (Common Gateway Interface)
    - 요청 당 프로세스를 만들어 사용했음
  - 서블릿 생명주기
    - 서블릿 컨테이너가 서블릿 인스턴스의 init() 메소드를 호출하여 초기화
      - 최초 요청 이후에는 이 과정을 생략
    - 서블릿이 초기화 된 다음부터 클라이언트의 요청을 처리할 수 있다. 각 요청은 별도의 쓰레드로 처리하고 이때 서블릿 인스턴스의 service() 메소드를 호출한다.
      - 이 안에서 HTTP 요청을 받고 클라이언트로 보낼 HTTP 응답을 만든다.
      - service()는 보통 HTTP Method에 따라 doGet(), doPost() 등으로 처리를 위임한다.
      - 따라서 보통 doGet() 또는 doPost()를 구현한다.
    - 서블릿 컨테이너 판단에 따라 해당 서블릿을 메모리에서 내려야 할 시점에 destroy()를 호출한다.
- 3강 서블릿 실습
   - HttpServlet 상속받아 HelloServlet 만들기
   - 깃 예제 참고

 - 4강 서블릿 리스너와 서블릿 필터
      - 웹 애플리케이션에서 발생하는 주요 이벤트를 감지하고 각 이벤트에 특별한 작업이 필요한 경우에 사용할 수 있음
        - 서블릿 컨텍스트 수준의 이벤트
          - 컨텍스트 라이프사이클 이벤트
          - 컨텍스트 애트리뷰트 변경 이벤트
        - 세션 수준의 이벤트
          - 세션 라이플사이클 이벤트
          - 세션 애트리뷰트 변경 이벤트
      - 서블릿 필터
        - 들어온 요청을 서블릿으로 보내고, 또 서블릿이 작성한 응답을 클라이언트로 보내기 전에 특별한 처리가 필요한 경우에 사용할 수 있음
        - 체인 형태의 구조
          - Servlet Container --> Servlet (request)
          - Servlet --> Servlet Container (response)
          - 이 사이에 FilterA, FilterB...
  - 5강 스프링 IoC 컨테이너 연동
    - [웹 어플리케이션 동작원리 먼저 공부하기](https://minwan1.github.io/2017/10/08/2017-10-08-Spring-Container,Servlet-Container/)
    - ContextLoaderListener
      - 서블릿 리스너 구현체
        - ApplicationContext를 만들어 준다.
        - ApplicationContext를 서블릿 컨텍스트 라이프사이클에 따라 등록하고 소멸시켜준다.
        - 서블릿에서 IoC 컨테이너를 ServletContext를 통해 꺼내 사용할 수 있다.
  - 6강 스프링 MVC 연동
    - DispatcherServlet
      - 스프링 MVC의 핵심
      - Front Controller 역할을 한다
  - 7강 DispatcherServlet 동작 원리 - 1
    - DispatcherServlet 초기화
      - 다음의 특별한 타입의 빈들을 찾거나, 기본 전략에 해당하는 빈들을 등록한다.
      - HandlerMapping: 핸들러를 찾아주는 인터페이스
      - HandlerAdapter: 핸들러를 실행하는 인터페이스
      - HandlerExceptionResolver
      - ViewResolver
    - DispatcherServlet 동작 순서
      - 요청을 분석한다. (로케일, 테마, 멀티파트 등)
      - (핸들러 맵핑에게 위임하여) 요청을 처리할 핸들러를 찾는다. 
      - (등록되어 있는 핸들러 어댑터 중에) 해당 핸들러를 실행할 수 있는 “핸들러 어댑터”를 찾는다.
      - 찾아낸 “핸들러 어댑터”를 사용해서 핸들러의 응답을 처리한다.
      - 핸들러의 리턴값을 보고 어떻게 처리할지 판단한다.
      - 뷰 이름에 해당하는 뷰를 찾아서 모델 데이터를 랜더링한다.
      - @ResponseEntity가 있다면 Converter를 사용해서 응답 본문을 만들고.
      - (부가적으로) 예외가 발생했다면, 예외 처리 핸들러에 요청 처리를 위임한다.
      - 최종적으로 응답을 보낸다.
    - @RestController는 각 메소드에 @Responsebody를 써주는 기능
    
   - 8강 DispatcherServlet 동작 원리 - 2
      - [핸들러 설명](https://springsource.tistory.com/3)
      - [핸들러 종류](http://www.egovframe.go.kr/wiki/doku.php?id=egovframework:rte2:ptl:handlermapping)
      - HandlerMapping
          - BeanNameUrlHandlerMapping
      - HandlerAdapter
          - SimpleControllerHandlerAdapter    
        ~~~
          @org.springframework.stereotype.Controller("/simple")
          public class SimpleController implements Controller {
             @Override
             public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
                 return new ModelAndView("/WEB-INF/simple.jsp");
             }
          }
        ~~~  
  - 9강 DispatcherServlet 동작 원리 - 3
    - ViewResolver
      - InternalResourceViewResolver
        - Prefix
        - Suffix
        ~~~
        @Configuration
        @ComponentScan
        public class WebConfig {
           @Bean
           public InternalResourceViewResolver viewResolver() {
               InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
               viewResolver.setPrefix("/WEB-INF/");
               viewResolver.setSuffix(".jsp");
               return viewResolver;
           }
        }
        ~~~
      - InternalResourceViewResolver가 기본 뷰리졸버이지만, 보통 prefix, suffix를 등록해서 재등록해서 사용
      - [ViewResolver 종류](https://logical-code.tistory.com/95)
      - [ViewResolver](https://milkye.tistory.com/267)

  - 10강 스프링 MVC 구성 요소
    - DispatcherSerlvet의 기본 전략
      - DispatcherServlet.properties
    - MultipartResolver
      - 파일 업로드 요청 처리에 필요한 인터페이스
      - HttpServletRequest를 MultipartHttpServletRequest로 변환해주어 요청이 담고 있는 File을 꺼낼 수 있는 API 제공
      - 스프링부트에서는 default = StandardServletMultipartResolver를 사용 / 그냥 스프링 MVC에서는 default = null
    - LocaleResolver
      - 클라이언트의 위치(Locale) 정보를 파악하는 인터페이스
      - 기본 전략은 요청의 accept-language를 보고 판단 (default = AcceptHeaderLocaleResolver)
      - [LocaleResolver 종류](https://moonscode.tistory.com/126)
      - [LocaleResolver로 다국어처리 비교(SpringMVC vs SpringBoot)](https://dymn.tistory.com/44)
    - ThemeResolver
      - 애플리케이션에 설정된 테마를 파악하고 변경할 수 있는 인터페이스
      - [참고자료](https://memorynotfound.com/spring-mvc-theme-switcher-example/)
    - HandlerMapping
      - 요청을 처리할 핸들러를 찾는 인터페이스
      - default = RequestMappingHandler
    - HandlerAdapter
      - HandlerMapping이 찾아낸 “핸들러”를 처리하는 인터페이스
      - 스프링 MVC 확장력의 핵심
    - HandlerExceptionResolver
      - 요청 처리 중에 발생한 에러 처리하는 인터페이스
    - RequestToViewNameTranslator
      - 핸들러에서 뷰 이름을 명시적으로 리턴하지 않은 경우, 요청을 기반으로 뷰 이름을 판단하는 인터페이스
      - 요청 URL 기준으로 리턴
    - ViewResolver
      - 뷰 이름(string)에 해당하는 뷰를 찾아내는 인터페이스
    - FlashMapManager
      - FlashMap 인스턴스를 가져오고 저장하는 인터페이스
      - FlashMap은 주로 리다이렉션을 사용할 때 요청 매개변수를 사용하지 않고 데이터를 전달하고 정리할 때 사용한다.
      - redirect:/events?id=2020 (x) -> redirect:/events 로 보내면서 원하는 데이터를 전달할 수 있음
      - 세션기반

  - 11강 스프링 MVC 동작 원리 마무리
    - 결국엔 서블릿 = DispatcherSerlvet
    - DispatcherSerlvet 초기화
      1. 특정 타입에 해당하는 빈을 찾음
      2. 없으면 기본 전략을 사용 (DispatcherServlet.properties)
    - 스프링 부트 사용하지 않는 스프링 MVC
      - 서블릿 컨네이너(ex, 톰캣)에 등록한 웹 애플리케이션(WAR)에 DispatcherServlet을 등록한다.
        - web.xml에 서블릿 등록
        - 또는 WebApplicationInitializer에 자바 코드로 서블릿 등록 (스프링 3.1+, 서블릿 3.0+)
          ~~~
          * web.xml을 사용하지 않고 자바 파일로 dispatcherServlet을 등록하는 방법

          public class WebApplication implements WebApplicationInitializer {
              @Override
              public void onStartup(ServletContext servletContext) throws ServletException {
                  AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
                  context.register(WebConfig.class);
                  context.refresh();

                  DispatcherServlet dispatcherServlet = new DispatcherServlet(context);
                  ServletRegistration.Dynamic app = servletContext.addServlet("app", dispatcherServlet);
                  app.addMapping("/app/*");
              }
          }
          ~~~
      - 세부 구성 요소는 빈 설정하기 나름
      - 스프링 부트를 사용하는 스프링 MVC
        - 자바 애플리케이션에 내장 톰캣을 만들고 그 안에 DispatcherServlet을 등록한다
          - 스프링 부트 자동 설정이 자동으로 해줌
        - 스프링 부트의 주관에 따라 여러 인터페이스 구현체를 빈으로 등록한다
### 2부. 스프링 MVC 설정
  - 12강 스프링 MVC 빈 설정
    - 스프링 MVC 구성 요소 직접 빈으로 등록
    - @Configuration을 사용한 자바 설정 파일에 직접 @Bean을 사용해서 등록하기
      ~~~
      * 예제(핸들러)
      @Bean
      public HandlerMapping handlerMapping() {
          RequestMappingHandlerMapping handlerMapping = new RequestMappingHandlerMapping();
          handlerMapping.setInterceptors();// 핸들러 인터셉터를 통해 서블릿보다 좀 더 유연하게 다룰 수 있음
          handlerMapping.setOrder(Ordered.HIGHEST_PRECEDENCE); // 여러 핸들러가 있을 때, 우선순위를 줄 수 있음
          return handlerMapping;
      }
      ~~~
  - 13강 Continue






      
