## Git convention

### Github Flow
- 작은 기능 하나 구현 할 때 마다 커밋하기
- issue는 큰 기능이나 한 화면 기준으로 큰 단위의 작업
- PR은 작업 분량으로 정상작동 내용이 있다면 머지하는 식으로 작은 단위의 작업
- 커밋 하나라도 했으면 PR 바로 하기
- 깃헙 플로우의 생명은 빠른 merge (작은 구현 단위로 PR을 끊읍시다!)
- 한 PR에서 모든걸 다 하려고 하지 말아요

### Branch Convention
{작업유형}/#{이슈 번호} ex) feat/#3, fix/#4 ...

브랜치를 새로 생성할 때에는 꼭 최신 버전의 main 브랜치를 기점으로 생성하기

### Commit Message Convention
{작업유형}: {작업내용}  ex) add: login 파일 추가

- 파일 추가 : add
- 버그 수정 : fix
- 리팩터링 : refactoring
- 파일 삭제 : remove
- 기능 추가 : feat
- 문서 수정 : docs
- 주석 추가 : comment
- 작은 기능 하나 구현 할 때 마다 커밋하기

### ISSUE Convention
[{작업유형}] {작업내용} ex) [Feat] 리뷰 목록

### PR Convention
[{작업유형}/#{이슈번호}] 작업내용 ex) [Feat/#3] 리뷰 쓰기 구현 완료


## Android convention

### layout id 규칙
- @+id/loginTextView
- @+id/passwordImageButton
- @+id/LoginTextView

### layout 파일명
snake_case를 적용한다.

- activity_기능: activity_login
- fragment_기능: fragment_home
- item_기능: item_user

### drawable 파일명
역시 xml 이기 때문에 snake_case를 적용한다. 자세한 내용은 아래를 참고한다.

- icon은 “ic_” -> ic_error
- image는 “img_” -> img_default_user
- shape는 “shape_” -> shape_border_radius10
- selector는 “selector_” -> selector_edittext_background
- 아이콘 이름 규칙
- ic_기능_모양

### 클래스 파일명
UpperCamelCase 적용 하기 MainActivity.kt, UserViewModel.kt, WriteFragment.kt, UserInfo.kt

### 함수명
- 동사형태로 작성하고, lowerCamelCase 적용하기
- fun showList(), fun updateContacts()

### 변수명
- 명사형태로 작성하고, lowerCamelCase 적용
- isEnd(Boolean 타입 제외), viewPagerAdapter

### 더미데이터 이름 규칙
recyclerview 이름_숫자 -> post_1, post_2

### 코드 주석 규칙 (kt 파일에 사용)
- 코드 위에 어떤 기능인지 설명 ex) 파이어베이스 연결, 좋아요 기능
- 공통적으로 사용하는 변수를 제외한 애들은 선언 옆에 // 이 주석을 사용해서 설명해주기
- 화면 전환 시 어느 화면에서 어느 화면으로 넘어가는지 설명
- 자세하게 써주기
