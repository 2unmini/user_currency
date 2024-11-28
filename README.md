application.properties 내 정보를 본인에 맞게 수정하여 사용해야 합니다.

## 환전 기능 구현 프로젝트

### 프로젝트 소개 
JPA,JPQL등을 활용한 환전 기능 구현 프로그램 입니다

#### 상태 코드 (HttpStatus 외 상태 코드 )
ERR001 : 원하는 값을 찾을 수 없을 때 생기는 오류 . ERR002 : 유효성 검사 실패 시 발생하는 오류

### User API
| Method | EndPoint      | Desc      |
|--------|---------------|-----------|
| POST   | /users/signup | 사용자 생성    |
| GET    | /users        | 사용자 전체 조회 |
| GET    | /users/{id}   | 사용자 단건 조회 |
| DELETE | /users/{id}   | 사용자 삭제    |
| POST   | /users/login  | 사용자 로그인   |
| DELETE | /users/logout | 사용자 로그아웃  |

### Currency API
| Method | EndPoint         | Desc     |
|--------|------------------|----------|
| POST   | /currencies      | 통화 입력    |
| GET    | /currencies      | 통화 전체 조회 |
| GET    | /currencies/{id} | 통화 단건 조회 |

### CurrencyExchange API
| Method | EndPoint                  | Desc        |
|--------|---------------------------|-------------|
| POST   | /currencies/{id}/exchange | 환전 요청       |
| GET    | /currencies/history       | 환전 요청 목록 조회 |
| GET    | /currencies/total         | 환전 성공 목록 조회 |
| PATCH  | /currencies/{id}          | 환전 취소       |

### User API
<details>
<summary>POST 사용자 생성</summary>
<div markdown="1">  
API PATH : /users/signup

- Request

| 파라미터     |타입  |필수여부| 설명  |
|----------|------|------|-----|
| username |String|O     | 이름  |
| email    |String|O     | 이메일 |

```json
{
    "name":"사용자",
    "email":"Abcd1234@gmail.com"
}
```
 - Response

| 파라미터     | 타입     |필수여부| 설명        |
|----------|--------|------|-----------|
| id       | Long   |O     | 작성자 고유 번호 |
| username | String |O     | 이름        |
| email    | String |O     | 이메일       |

```json
{
    "id":1,
    "name":"사용자",
    "email":"Abcd1234@gmail.com"
}
```

</div>
</details>


<details>
<summary>GET 사용자 전체 조회</summary>
<div markdown="1"> 
API PATH : /users

- Response

| 파라미터     | 타입     |필수여부| 설명        |
|----------|--------|------|-----------|
| id       | Long   |O     | 작성자 고유 번호 |
| username | String |O     | 이름        |
| email    | String |O     | 이메일       |

```json
[
    {
        "id": 1,
        "name": "사용자1",
        "email": "Abcd1234@gmail.com"
    },
    {
        "id": 2,
        "name": "사용자2",
        "email": "1234Abcd@gmail.com"
    }
]

```

</div>
</details>

<details>
<summary>GET 사용자 단건 조회</summary>
<div markdown="1"> 
API PATH : /users/{id}

- Response

| 파라미터     | 타입     |필수여부| 설명        |
|----------|--------|------|-----------|
| id       | Long   |O     | 작성자 고유 번호 |
| username | String |O     | 이름        |
| email    | String |O     | 이메일       |

```json
    {
        "id": 1,
        "name": "사용자1",
        "email": "Abcd1234@gmail.com"
    }
```

</div>
</details>

<details>
<summary>DELETE 사용자 삭제</summary>
<div markdown="1"> 
API PATH : /users/{id}

- Response

| 파라미터   | 타입     |필수여부| 설명   |
|--------|--------|------|------|
|        | String |O     | 성공 메시지 |


```text
    정상적으로 삭제 되었습니다.
```

</div>
</details>

<details>
<summary>POST 사용자 로그인</summary>
<div markdown="1"> 
API PATH : /users/login

- Request

| 파라미터     |타입  |필수여부| 설명  |
|----------|------|------|-----|
| username |String|O     | 이름  |
| email    |String|O     | 이메일 |

```json
    {
        "name": "사용자1",
        "email": "Abcd1234@gmail.com"
    }
```

- Response (성공)
```text

```
- Response(실패)

| 파라미터     | 타입     |필수여부| 설명        |
|----------|--------|------|-----------|
| username | String |O     | 이름        |
| email    | String |O     | 이메일       |

```json
    {
        "errorCode": "ERR002",
        "errorMessage": "이메일을 입력하세요"
    }
```

</div>
</details>

<details>
<summary>DELETE 사용자 로그아웃</summary>
<div markdown="1"> 
API PATH : /users/logout

- Response 

```text

```

</div>
</details>

---
### Currency API

<details>
<summary>POST 통화 입력</summary>
<div markdown="1"> 
API PATH : /currencies

- Request

| 파라미터         | 타입         |필수여부| 설명    |
|--------------|------------|------|-------|
| currencyName | String     |O     | 통화 이름 |
| exchangeRate | BigDecimal |O     | 환율    |
| symbol       | String     |O     | 통화 기호 |

- Response

| 파라미터         | 타입         |필수여부| 설명        |
|--------------|------------|------|-----------|
| Id           | Long       |O     | 통화 고유 식별자 |
| currencyName | String     |O     | 통화 이름     |
| exchangeRate | BigDecimal |O     | 환율        |
| symbol       | String     |O     | 통화 기호     |

```json
{
    "id": 1,
    "currencyName": "USD",
    "exchangeRate": 1393,
    "symbol": "$"
}
```

</div>
</details>

<details>
<summary>GET 통화 전체 조회</summary>
<div markdown="1"> 
API PATH : /currencies

- Response

| 파라미터         | 타입         |필수여부| 설명        |
|--------------|------------|------|-----------|
| Id           | Long       |O     | 통화 고유 식별자 |
| currencyName | String     |O     | 통화 이름     |
| exchangeRate | BigDecimal |O     | 환율        |
| symbol       | String     |O     | 통화 기호     |

```json
[
    {
        "id": 1,
        "currencyName": "USD",
        "exchangeRate": 1393.00,
        "symbol": "$"
    },
    {
        "id": 2,
        "currencyName": "JPY",
        "exchangeRate": 9.20,
        "symbol": "円"
    }
]
```
</div>
</details>

<details>
<summary>GET 통화 단건 조회</summary>
<div markdown="1"> 
API PATH : /currencies/{id}

- Response

| 파라미터         | 타입         |필수여부| 설명        |
|--------------|------------|------|-----------|
| Id           | Long       |O     | 통화 고유 식별자 |
| currencyName | String     |O     | 통화 이름     |
| exchangeRate | BigDecimal |O     | 환율        |
| symbol       | String     |O     | 통화 기호     |

```json
{
  "id": 1,
  "currencyName": "USD",
  "exchangeRate": 1393,
  "symbol": "$"
}
```
</div>
</details>

---
### CurrencyExchange API

<details>
<summary>POST 환전 요청</summary>
<div markdown="1"> 
API PATH : /currencies/{id}/exchange

- Request

 | 파라미터             | 타입         |필수여부| 설명        |
 |------------------|------------|------|-----------|
 | id               | Long       |O     | 통화 고유 식별자 |
 | preExchangeAmount | Long       |O     | 환전 전 금액   |

```json
{
  "preExchangeAmount":100
}
```
- Response

| 파라미터         | 타입         |필수여부| 설명       |
|--------------|------------|------|----------|
| preExchangeAmount| Long       |O     | 환전 전 금액  |
| postExchangeAmount | BigDecimal |O     | 환전 후 금액  |
| status | String     |O     | 환전 요청 상태 |

```json
{
  "preExchangeAmount": 100,
  "postExchangeAmount": 11,
  "status": "NORMAL"
}
```
</div>
</details>

<details>
<summary>GET 환전 요청 목록 조회</summary>
<div markdown="1"> 
API PATH : /currencies/history

- Request

```json

```
- Response

| 파라미터               | 타입         |필수여부| 설명        |
|--------------------|------------|------|-----------|
| id                 | Long       |O     | 환전 접수 식별자 |
| preExchangeAmount  | Long       |O     | 환전 전 금액   |
| postExchangeAmount | BigDecimal |O     | 환전 전 금액     |
| status             | String     |O     | 환전 요청 상태  |

```json
[
  {
    "id": 1,
    "preExchangeAmount": 100,
    "postExchangeAmount": 11.00,
    "status": "CANCELED"
  },
  {
    "id": 2,
    "preExchangeAmount": 100,
    "postExchangeAmount": 11.00,
    "status": "NORMAL"
  },
  {
    "id": 3,
    "preExchangeAmount": 100,
    "postExchangeAmount": 11.00,
    "status": "NORMAL"
  }
]
```
</div>
</details>

<details>
<summary>GET 환전 성공 목록 조회</summary>
<div markdown="1"> 
API PATH : /currencies/total

- Response

| 파라미터               | 타입         |필수여부| 설명              |
|--------------------|------------|------|-----------------|
| count              | Long       |O     | 환전 신청 횟수(취소 제외) |
| totalAmountInKrw  | Long       |O     | 환전 신청한 총 금액     |

```json
[
  {
    "count": 3,
    "totalAmountInKrw": 300
  }
]
```
</div>
</details>

<details>
<summary>PATCH 환전 취소</summary>
<div markdown="1"> 
API PATH : /currencies/{id}

- Request

| 파라미터   | 타입         |필수여부| 설명        |
|--------|------------|------|-----------|
|  id    | Long       |O     | 환전 접수 식별자 |

- Response

```json

```
</div>
</details>

---
<details>
<summary>ERD 다이어그램</summary>
<div markdown="1"> 

![image](https://github.com/user-attachments/assets/39709512-eec7-4edc-a6c9-adf08b2e191e)

</div>
</details>


