# SpringMVC
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
    - Continue







      
