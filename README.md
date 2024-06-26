HomeDoc
---

HomeDoc은 만성질환 환자들이 자신의 건강 측정 정보를 입력하고 측정 기록 및 통계를 열람할 수 있는 건강관리 서비스입니다.  
병원 측에서도 자신에게 배정된 환자의 건강정보를 열람하고 실제 진료에 참고할 수 있습니다.
  
Java/Spring으로 기술 스택을 전향한 뒤의 첫 프로젝트인 만큼, 이전 회사에서의 근무 경험을 살릴 수 있는 헬스케어 도메인을 선택하게 되었습니다.  
사용된 기술은 모두 독학을 통해 습득하였으며, 학습과 고민의 과정이 모두 프로젝트에 그대로 녹아 있습니다.  
또한 이전에 비슷한 프로젝트를 개발할 때 아쉬웠던 점이나 배웠던 점 등도 반영할 수 있도록 많은 노력을 기울였습니다.  

프로젝트 개발 회고
---
[https://bomlee427.github.io/categories/#homedoc-개발-회고록](https://bomlee427.github.io/categories/#homedoc-%EA%B0%9C%EB%B0%9C-%ED%9A%8C%EA%B3%A0%EB%A1%9D)  
개발하며 느꼈던 점이나 배운 것들을 완전히 제 것으로 체득하고자, 코드 리뷰 겸 회고록의 형식으로 개발 회고를 기고 중입니다.

프로젝트 요약
---
- 프로젝트 이름 : HomeDoc
- 프로젝트 요약 : 만성질환 환자 건강관리 서비스 백엔드용 API
- 주요 기능
    - 환자 : 회원가입 기능, 건강 측정 정보 CRUD, 조회 및 페이징, 통계 기능
    - 병원 : 환자 등록 및 삭제 기능, 환자 및 환자의 건강정보 열람 및 코멘트 기능
    - 관리자 : 회원(환자) 관리 기능(환자의 개인정보 및 건강정보는 열람할 수 없음), 제휴 병원 등록 및 관리 기능

개발 환경
---
- 개발 언어 : JAVA 11
- 개발 환경 : Spring boot 2.7.x, gradle 8, Git
- ORM : JPA(Spring Data Jpa), Querydsl
- 인증 방식 : JWT
