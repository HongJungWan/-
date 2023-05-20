<br/>

<img width="495" alt="moment logo" src="https://user-images.githubusercontent.com/76596316/234157173-e1224d83-3404-419d-a3bc-8877a88e69a9.png">

<br/>

* Spring Batch & Quartz Scheduler Server는 Private Repository로 관리 중...

<br/><br/>

### 💡 프로젝트 목표

> 1️⃣ 섣부른 최적화보다는 OOP 개념 원칙하에 프로젝트를 진행한다. <br/><br/>
> 2️⃣ 객체지향 생활체조 원칙과 테스트 코드를 생활화한다. <br/><br/>
> 3️⃣ 요구사항 추가 및 변경으로 인한 리팩토링을 두려워하지 말자. <br/>

<br/><br/>

### 👬 팀원 소개

* **홍정완(Back-End, AWS Infra)**

<br/><br/>

### 💻 Moment

<br/><br/>

### 📄 CI/CD 구조도

<br/><br/>

### 📄 서버 구조도

<br/><br/>

### 📄 ERD - 계속 업데이트 예정

![04-29 수정된 ERD](https://user-images.githubusercontent.com/76596316/235294049-b8612ecf-c874-4a83-ab8b-a6ce1414abb9.png)

<br/><br/>

### 📄 요구사항 MVP

<br/><br/>

### ♻ 브랜치 전략 - Github flow

* 소규모 팀에서 사용하기 좋은 전략이라고 판단하여 본 프로젝트에 도입

<br/>

<img src="https://user-images.githubusercontent.com/76596316/218664821-90beae88-398b-4159-9469-7c0ddf04e99e.png" width="400">

<br/><br/>

### ♻ 도메인형 디렉터리 구조

* 보다 효율적이고 견고한 패키지 구조에 대한 고민 -> 도메인형 디렉터리 구조로 변경

<br/>

```
  com 
  ㄴ day
      ㄴ dream
          ㄴ component
          |   ㄴ user 
          |   |   ㄴ api 
          |   |   |   ㄴ request
          |   |   |   ㄴ response
          |   |   ㄴ application 
          |   |   ㄴ dto
          |   |   ㄴ repository
          |   |   ㄴ domain
          |   |   ㄴ exception 
          |   ... 
          ㄴ core
              ㄴ auth 
              ㄴ common
              ㄴ config 
              ㄴ error 
              ㄴ infra 
              ㄴ util 
```

<br/><br/>

### 🧐 프로젝트 진행 시, 이슈에 대한 접근 방법과 해결 과정

<br/><br/>

### 프로젝트 개발환경

```
Web 개발 환경

• IDE : IntelliJ IDEA Ultimate, DataGrip IDEA Ultimate
• 언어 : Java 11
• 프레임워크 : SpringBoot 2.7.10
• 데이터베이스 : MySQL 8.0.28
```

<br/><br/>

### ✔ 커밋 컨벤션

---

✅ 기본적으로 커밋 메시지는 제목 / 본문 / 관련 이슈로 구분 <br/>
✅ feat : 새로운 기능 추가 <br/>
✅ fix : 버그 수정 <br/>
✅ docs : 문서 수정 <br/>
✅ style : 코드 포맷팅, 세미콜론 누락, 코드 변경이 없는 경우 <br/>
✅ refactor : 코드 리펙토링 <br/>
✅ test : 테스트 코드, 리펙토링 테스트 코드 추가 <br/>
✅ chore : 기타 변경사항 <br/>
✅ 제목은 50자를 넘기지 않고, 맞침표를 붙이지 않는다. <br/>
✅ 본문은 "어떻게" 보다 "무엇을" 과 "왜"를 설명한다. <br/>
✅ 제목과 구분되기 위해 한 칸 띄워 작성한다. <br/>

<br/><br/>