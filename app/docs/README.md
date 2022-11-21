## Splash 기능 구현

1. 기기가 인터넷에 연결되어 있는지 체크한다. `NetworkChecker`를 활용
2. `FireStore`에서 `metaData`콜렉션을 가져와 기기내의 칵테일 버전과 동일한지 체크한다.

```
firestore.collection("metaData")
  .get()
  .addOnSuccessListener { //... }
```

3-1. 동일하면 메인화면으로 진입 3-2. 다르다면 칵테일 버전 업데이트 후 `FireStore`에서 칵테일 리스트 재 다운로드

## 온보딩 기능 구현

1. `OnboardGraph`내에서 같은 entry객체로 `hiltViewModel`을 생성하여 뷰모델을 공유한다.
2. 유저의 정보를 받아온다.
    * nickname : 닉네임
    * sex : 성별
    * age : 나이
    * level : 선호 도수
    * base : 선호 기주
    * keyword  : 선호 키워드
3. `OnboardViewModel`에 유저의 정보를 저장하고 후에 DB에 저장한다. -> `insertUserinfo()`

## 칵테일 추천 기능 구현

1. 초기 온보딩에서 유저의 정보를 받아온다.
    * 선호 키워드
    * 선호 기주
    * 선호 알코올 도수
    * 나이, 성별

2. `HomeViewModel`에서 해당 유저 정보를 기반으로 점수를 통계낸다.
    * 키워드 점수 = 중복갯수 * 유저가 설정한 키워드 가중치 * 0.8
    * 기주 점수 = 중복갯수 * 유저가 설정한 기주 가중치 * 1.2
    * 도수 점수 = 3 - |도수 차이| * 유저가 설정한 도수 가중치 * 0.1
        * 중복 갯수 : 칵테일과 유저 선호 정보가 중복되는 갯수
        * 유저 가중치의 경우 최소 0 ~ 최대 4까지 존재한다.

3. DB에 있는 칵테일을 중 점수 순으로 정렬하여 칵테일을 추천한다.

---

## Repository 역할

* CocktailRepository
    * DB에 칵테일 정보 CRUD
    * DB에 칵테일 북마크 정보 CRUD
    * DB에 칵테일 키워드 정보 CRUD
    * 칵테일 페이징 데이터 변환 `cocktailPaging()`
    * 칵테일 버전 체크
* UserInfoRepository
    * DB에 유저 정보 CRUD
    * DB에 유저 칵테일 가중치 CRUD
* SearchRepository
    * DB에 최근 검색어 CRUD 
