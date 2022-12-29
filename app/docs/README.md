> 기능 리스트는 화면단으로 기능을 구별하여 설명

## Splash 기능 리스트

1. 기기가 인터넷에 연결되어 있는지 체크한다. `NetworkChecker`를 활용
2. `FireStore`에서 `metaData`콜렉션을 가져와 기기내의 칵테일 버전과 동일한지 체크한다.

```
firestore.collection("metaData")
  .get()
  .addOnSuccessListener { //... }
```

3. 칵테일 버전이 동일하면 `Continue`
    * 다르다면 기기의 칵테일 버전 업데이트 후 `FireStore`에서 칵테일 리스트 및 키워드 종류 재 다운로드

4 유저 정보가 기기 DB에 저장되어 있다면 `메인` 화면으로 이동한다.

5. 유저 정보가 등록되어 있지 않다면
    * 건너뛰기 : 유저 정보를 기본 값으로 설정하고 `DB` 및 `파이어베이스`에 등록 후 `메인`화면으로 이동
    * 유저 정보 등록하기 : `온보딩` 화면으로 이동

> 파이어 베이스에서 가져오는 것 : 전체 칵테일 리스트, 총 키워드 종류

## 온보딩 기능 리스트

1. `OnboardGraph`내에서 같은 `backStackEntry`객체를 활용 `hiltViewModel`를 생성하여 그래프 내에서 뷰모델을 공유한다.
2. 유저의 정보를 받아온다.
    * nickname : 닉네임
        * 기본 값 : "익명의 누군가"
    * sex : 성별
        * 기본 값 : "Unknown"
    * age : 나이
        * 기본 값 : 20살
    * level : 선호 도수
        * 기본 값 : 10도
    * base : 선호 기주
        * 기본 값 : "상관 없음"
    * keyword  : 선호 키워드
        * 기본 값 : 상쾌한, 트로피컬, 가벼운 -> 3개 이상 넣지 않으면 칵테일 추천이 불가능

> 칵테일 추천 중요도(`CocktailWeight`)는 초기 설정 때 기본값 으로 설정한다. (후에 온보딩에서도 바꿀 수 있게 수정)

3. `FireStore`에 유저정보를 저장한다. -> `setFirebaseUserKey()`
    * 이때 `FireStore`저장을 위한 해쉬 키값을 기기 DB에도 저장한다. `Userinfo.userKey` 이 값으로 사용자를 구별한다.
    * 사용자의 북마크 정보를 `Firestore`에 저장하기위한 위한 북마크 `document` 키 값도 불러와서 DB에
      저장한다. `setFirebaseBookmarkKey()`
    * 사용자 정보, 파이어베이스 키 값(유저정보, 북마크 정보)를 기기 DB에 저장한다.

4. 모든 작업이 완료되면 `메인` 화면 이동한다.

## 칵테일 추천 기능 리스트

1. 초기 온보딩에서 유저의 정보를 받아온다.
    * 선호 키워드
    * 선호 기주
    * 선호 알코올 도수
    * 칵테일 추천 중요도 (`CocktailWeight`)

2. `HomeViewModel`에서 해당 유저 정보를 기반으로 점수를 통계낸다.
    * 키워드 점수 = 중복갯수 * 유저가 설정한 키워드 가중치 * 0.8
    * 기주 점수 = 중복갯수 * 유저가 설정한 기주 가중치 * 1.2
    * 도수 점수 = 3 - |도수 차이| * 유저가 설정한 도수 가중치 * 0.1
        * 중복 갯수 : 칵테일과 유저 선호 정보가 중복되는 갯수
        * 유저 가중치의 경우 최소 0 ~ 최대 4까지 존재한다.

> 해당 알고리즘은 후에 추천 시스템 기반으로 교체하기 임시방편 알고리즘

3. DB에 있는 칵테일을 중 점수 순으로 정렬하여 칵테일을 상위 5개의 칵테일을 추천한다.

4. 랜덤 칵테일, 랜덤 키워드, 랜덤 기주에 따른 칵테일을 추천한다.
    * 랜덤 키워드는 다음과 같이 추출한다.
      `BASE_KEYWORD.shuffled().first()`

---

## 검색 기능 리스트

검색화면은 `검색결과`화면과 `검색을 수행하는`화면으로 나누어서 관리한다.

### 검색결과 화면 기능 리스트

1. DB에 저장된 칵테일을 기반으로 `Room DB`내에서 쿼리를 실행하여 결과 값을 뽑아온다.
    * 검색 결과는 키워드 재료, 한글이름, 영어이름을 모두 비교하는 쿼리를 작성하여 모두 가져온다.

```
    @Query(
        "SELECT * FROM cocktail WHERE enName LIKE '%' || :searchStr || '%'" +
                "Or keyword LIKE '%' || :searchStr  || '%' " +
                "OR ingredient LIKE '%' || :searchStr  || '%'" +
                "OR krName LIKE '%' || :searchStr  || '%' ORDER BY idx DESC"
    )
    fun queryCocktail(searchStr: String): Flow<List<Cocktail>>
```

2. 검색하거나, 칵테일 키워드 버튼을 눌르면 해당 검색어가 입력되고 그에 따른 결과를 반환한다.

3. 북마크 버튼을 눌르면 기기 DB에 북마크번호를 저장하고, 파이어베이스의 해당 유저의 문서에 북마크 값을 저장한다.
    * 파이어 베이스에 저장하는 이유는 후에 유저 기반 추천시스템을 구현할 수도 있기에

### 검색 화면 기능 리스트

1. 검색을 수행할 시 최근 검색어를 `Room DB`에 저장하여 이를 보여준다.
2. 초기 메인화면에서 계산한 나에게 맞는 칵테일 리스트를 전부 다 보여준다.
3. 검색어 입력 시 각각의 검색입력마다 간략한 결과를 출력한다.
    * 사용자의 입력은 `200ms`마다 디바운싱되며 출력된 결과는 페이징을 지원한다.

```
    val pagingCocktailList = textFieldValue.debounce(200)
        .distinctUntilChanged()
        .flatMapLatest {
            cocktailRepository.cocktailPaging(textFieldValue.value.text)
        }
```

4. 간략한 결과를 클릭 시 `디테일` 화면으로 넘어가며, 검색을 완료하면 `검색결과`화면으로 이동한다.

## 디테일 화면 기능 리스트

1. 칵테일에 관한 정보는 `RoomDB`에 저장되어 있는 값을 사용하여 화면을 출력한다.
2. 레시피의
   도식화는 [Compose 에서 Canvas를 사용해보자](https://velog.io/@cksgodl/AndroidCompose-Compose%EC%97%90%EC%84%9C-Canvas%EB%A5%BC-%EC%82%AC%EC%9A%A9%ED%95%B4%EB%B3%B4%EC%9E%90)다음을
   참고 하자.
3. 칵테일에 대한 리뷰는 `Firestore`에서 해당 값을 가져온다.
    * `review`콜렉션 내부에서 해당 칵테일의 `idx`와 같은 리뷰들을 가져와서 보여준다.

```
    firestore.collection("review")
        .whereEqualTo("idx", idx)
        .get()
        .addOnSuccessListener { query ->
            query?.let {
                val reviewList = it.toObjects<Review>()
                if (reviewList.isNotEmpty()) {
                    reviewContents.clear()
                    reviewContents.addAll(reviewList)
                }
            }
        }
```

### 리뷰 작성 화면

1. 칵테일의 리뷰에는 다음과 같은 요소가 필요하다.
    * 별점 (1점 ~ 5점)
    * 사진 (1장 ~ 5장)
    * 리뷰 콘텐츠(최대 150자)
2. 리뷰와 콘텐츠는 `Compose`에서 지원하는 UI로 입력받는다.
3. 사진의 경우 이미지 크롭뷰를 활용하여 이미지를 정사각형으로 잘라서 입력받는다.
   [ImageCropView](https://github.com/naver/android-imagecropview)
   `implementation "com.naver.android.helloyako:imagecropview:1.2.3"`
4. 이미지는 기기 내부의 사진을 `Content Provider`의 `query` 활용하여 가져오며
    * 초기 선택 시에는 외부 저장소에 대한 `Permission`체크를 진행한다.
    * 기기의 `Build.VERSION`이 29(Q)초과일 때와 이하일 때 쿼리를 다르게 적용한다.
    * 가져온 이미지에 대한 페이징을 적용하여 메모리 사용량을 줄인다.
    * 폴더별 이미지 검색 구현

```
val pagingCocktailList: Flow<PagingData<GalleryImage>> =
    Pager(
        config = PagingConfig(
            pageSize = PAGING_SIZE,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            GalleryPagingSource(imageRepository)
        }
    ).flow.cachedIn(viewModelScope)
```

5. 리뷰를 `Firestore`저장할 때는 사진의 경우는 바이트 코드로 변환 후 `Firestorage`에 저장 후 `donwloadURL`을 리뷰 페이지에 저장한다.
    * fireStore에 저장하는 로딩이 길기 때문에 `putDataToStorage`에서 저장될 때마다 현재 상태를 반환하여 로딩처리를 구현

## 지도 화면 기능 리스트

1. 초기에 유저 위치정보에 대한 `Permission`체크를 진행하며 권한이 없다면 권한을 교청하는 화면을 출력한다.
2. 위치 정보는 구글라이브러이인 `FusedLocationClient`를 활용하여 초기에 1번만 위치 정보를 가져온다.
    * 초기 Composition될 때 `LocationListener`을 연결하고 Dispose될 때 해당 리스너를 제거한다.
3. 지도는 [NAVER Map Compose](https://github.com/fornewid/naver-map-compose)를 활용한다.
    * 초기 얻어진 위치정보를 통해 카메라의 위치를 변경한다.
4. 사용자가 카메라의 위치가 변경 후 검색을 누를 때 마다 해당 반경 `3Km`이내에서 `query="칵테일"`의`Location Search`를 진행한다.
    * `Location Search`는 [KAKAO_API](https://developers.kakao.com/docs/latest/ko/local/dev-guide)를
      활용했다.
        * 네이버에서는 좌표, 쿼리를 사용한 API를 제공하지 않음
5. `3KM`이내 장소가 존재하지 않으면 `snackbar`를 출력한다.
    * 장소가 존재한다면 카메라 위치로부터 최대 거리의 마커를 선택하고 둘 사이를 반지름으로하는 원을 지도에 그린다.
6. 마커 클릭 시 관련 정보를 출력하고 네이버지도에 `PendingIntent`로 넘긴다.

## 북마크 화면 기능 리스트

1. `RoomDB`에서 북마크 리스트를 가져오며 북마크가 변화할 시 `Firestore`에도 업데이트를 진행한다.

## 마이페이지 기능 리스트

1. 사용자 정보의 수정이 가능하다.
    * 닉네임
    * 선호 기주
    * 선호 키워드
    * 추천 가중치 수정이 가능하며 수정이 되면 `DB` 및 `FireStore`에 업데이트한다.
