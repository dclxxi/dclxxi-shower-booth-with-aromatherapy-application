# shower-booth-with-aromatherapy-application
지능형 아로마테라피 샤워부스 연동 어플
## 📌 Summary


> 2022 한이음 ICT공모전에서 수행한 프로젝트로,<br>
총 5명의 팀원(3명: 하드웨어(샤워기 제작), 2명: 소프트웨어(앱 개발))과 함께 진행하였습니다.




> **사용자의 기분을 측정**하여 **아로마오일을 맞춤 추천**해주는 **IoT기반의 지능형 샤워기**의<br>동작을 제어하고 사용 정보를 관리할 수 있는 **앱 어플리케이션**입니다.


 <img width="500" alt="시스템구성도.jpg" src="https://s3.us-west-2.amazonaws.com/secure.notion-static.com/96f478da-ffc8-474b-abb1-2504890172ef/%EC%8B%9C%EC%8A%A4%ED%85%9C%EA%B5%AC%EC%84%B1%EB%8F%84.jpg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20221128%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20221128T024142Z&X-Amz-Expires=86400&X-Amz-Signature=3a2cfb618990a6b4aa6f85d2a08bf8f7170189c9c281250d4ea722ca4fc99bf9&X-Amz-SignedHeaders=host&response-content-disposition=filename%3D%22%25EC%258B%259C%25EC%258A%25A4%25ED%2585%259C%25EA%25B5%25AC%25EC%2584%25B1%25EB%258F%2584.jpg%22&x-id=GetObject">


프로젝트 기간 : `2022.07 ~ 2022.09`

<br>

### 🔗 발표 및 데모 영상

[[2022 한이음 공모전] 아로마 테라피를 지원하는 지능형 샤워기](https://www.youtube.com/watch?v=o64UrTMOjCU)

<br>

### 기획 의도

- 사용자의 감정과 체온 등을 측정하여 현대인의 지친 심신을 회복할 수 있는 아로마테라피를 지원하는 샤워 시스템을 개발한다.
- 샤워를 하기 위해 수온 체크, 헤드 높낮이 조절, 필터 교체 등의 번거로움이 있다. 이와 같은 불편함을 해소하기 위해 IoT를 기반으로 여러 가지 기능을 첨가하여 사용자의 편의를 위한 샤워기를 제작한다.
- 수동조작으로 인해 발생할 수 있는 화상 및 낙상사고의 위험을 방지하기 위해 자동화 샤워 시스템을 개발한다.

<br>

### 🛠️ Specification

- Android
- Spring boot, Gradle
- Java, JavaScript
- JPA
- REST API
- Firebase Auth
- MySQL, H2, AWS RDS
- Android Studio, IntelliJ
- Gitlab Runner
- Gitlab, Notion

<br>

### 🏛️ ****Architecture****
 <img width="700" alt="아키텍처구성도.jpg" src="https://s3.us-west-2.amazonaws.com/secure.notion-static.com/00defa09-6754-43d9-ba21-4f7f2b8e1d9e/%EC%95%84%ED%82%A4%ED%85%8D%EC%B2%98%EA%B5%AC%EC%84%B1%EB%8F%84.jpg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20221128%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20221128T024157Z&X-Amz-Expires=86400&X-Amz-Signature=f7a62ba892a7289f1c33e50b1dfbc7d9993657639fdd8416cef8dec7fda83fb0&X-Amz-SignedHeaders=host&response-content-disposition=filename%3D%22%25EC%2595%2584%25ED%2582%25A4%25ED%2585%258D%25EC%25B2%2598%25EA%25B5%25AC%25EC%2584%25B1%25EB%258F%2584.jpg%22&x-id=GetObject">
  <img width="900" alt="UI 구성도.png" src="https://s3.us-west-2.amazonaws.com/secure.notion-static.com/b4910e8f-8406-4a64-855f-a2bdb444a838/UI_%EA%B5%AC%EC%84%B1%EB%8F%84.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20221128%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20221128T024224Z&X-Amz-Expires=86400&X-Amz-Signature=3bd96e28a2d8662bf7873d10f0c6d986c3149235f69256857615859f92fac6cd&X-Amz-SignedHeaders=host&response-content-disposition=filename%3D%22UI%2520%25EA%25B5%25AC%25EC%2584%25B1%25EB%258F%2584.png%22&x-id=GetObject">
 
 <br>
 
### 📋 앱 서비스 기능
|기능|설명|
| --- | --- |
| 로그인 및 회원가입 | 서비스 사용을 위해 사용자는 로그인을 수행하거나 회원가입을 수행해야 한다. |
| 지능형 샤워기 서비스 | 아로마테라피를 위한 지능형 샤워기 서비스를 제공한다. |
| 샤워 기록 관리 | 로그인한 사용자의 샤워기록을 생성, 조회, 수정, 삭제할 수 있다. |
| 만족도 기록 관리 | 로그인한 사용자의 만족도 정보를 등록, 조회, 수정, 삭제할 수 있다. |
| 만족도 통계 관리 | 로그인한 사용자의 만족도 통계와 전체 사용자의 만족도 통계를 조회할 수 있다. |
| 아로마오일 추천 | 지능형 샤워기 서비스 중에 동작되는 알고리즘으로, 사용자의 만족도를 기반으로 <br>아로마오일을 추천한다. |
| 아로마오일 정보 관리 | 로그인한 사용자가 보유한 아로마오일과 시스템이 관리하는 전체 아로마오일에 <br>대한 정보를 생성, 조회, 수정, 삭제할 수 있다. |
