## API Overview

챗봇은 현재 다양한 메신저 플랫폼에서 활용되고 있습니다. 챗봇 API는 다양한 메신저 플랫폼에서 활용되는 챗봇에 대한 정보를 일관적으로 관리하기 위한 리소스를 제공하고 있습니다. 

## Environments

애플리케이션을 실행하기 위한 기본적인 환경은 아래와 같습니다.

- JDK 8 (Required)
- 빌드 도구는 Gradle(Optional)을 사용하지만 Gradle Wrapper를 통해 운영체제에 별도로 설치하지 않아도 테스트 및 실행이 가능합니다.

#### Download & Run

```
$ git clone https://github.com/stunstunstun/chatbot.git
$ cd chatbot
$ ./gradlew bootRun
```
> 애플리케이션은 Embeded Tomcat를 통해 서버 프로세스를 생성하며 서버의 포트는 `8080` 입니다. 

#### Test

```
$ ./gradlew test
```

#### CI & Test coverage

애플리케이션은 jacoco를 통해 테스트 결과에 대한 테스트 커버리지 리포트를 생산하고 `Travis CI`를 통해 master 브랜치에 커밋이 일어날 때마다 지속적으로 빌드 및 테스트를 하고 있습니다.

## APIs

```
http://localhost:8080/chatbot/v1/bots
```

Http Method | HTTP Request | Description
--|--|--
GET | /chatbot/v1/bots | 챗봇 리스트를 조회합니다 
GET | /chatbot/v1/bots/{id} | 고유 ID에 해당하는 챗봇을 조회합니다.
POST | /chatbot/v1/bots | 챗봇을 생성합니다
PUT | /chatbot/v1/bots/{id} | 고유 ID에 해당하는 챗봇을 갱신합니다.
DELETE | /chatbot/v1/bots/{id} | 고유 ID에 해당하는 챗봇을 삭제합니다.

## Resource Representation

챗봇 API의 리소스를 표현하는 JSON 객체의 형태는 아래와 같습니다.

```javascript
{
  "id": Integer,
  "name": String,
  "messengerType": String,
  "messengerToken": String,
  "webhookUrl": String,
  "enabled": Boolean,
}
```

#### Request Header

`Content-Type: application/json`

#### Resource 상세 정보

Property Name | Value | Description 
--|--|--
id | Integer | 챗봇을 구분하는 고유 ID | 
name | String | 챗봇의 이름 | 
messengerType | String | 챗봇의 플랫폼 메신저의 종류
messengerToken | String | 챗봇의 플랫폼 메신저에 접근 권한을 가진 토큰
webhookUrl | String | 챗봇에서 발생하는 이벤트를 전송 받는 웹훅 URL
enabled | Boolean | 챗봇의 서비스 활성여부 

#### Examples

```
GET /chatbot/v1/bots/1 HTTP/1.1
Host: localhost:8080
User-Agent: curl/7.54.0
Accept: */*
 
HTTP/1.1 200 
Content-Type: application/json;charset=UTF-8

{
    "id": 1,
    "name": "Bitcoin Bot",
    "messengerType": "FACEBOOK",
    "messengerToken": "EAAR4t31aREoBACDzWk5luFqjfJZB4wIH4WdbS4p64KZBf7iPfex44TnXTRFws4LFAwilkkytW9K1v1ADOgZAB6hbtnPMVmAUfmRNGfmFBRFLz5ABzi8ZBPtonrAhFo7yxzDoIdNs1QwnXrvlDkgm8IXLTbjbsKfMUFW0Uq6yvgZDZD",
    "webhookUrl": "http://localhost/webhook",
    "enabled": true
}
```

## Errors Representation

API의 요청에 대한 결과가 정상적이지 않다면 아래의 에러 상태를 확인하시면 됩니다.

```javascript
{
    "timestamp": Long,
    "status": Integer,
    "error": String,
    "exception": String,
    "message": String,
    "path": String
}
```

Property Name | Value | Description 
--|--|--
timestamp | Long | API가 호출된 타임스탬프 
status | Integer | HTTP 응답 코드
error | String | 에러 타입
exception | String | 에러의 명세 클래스
message | String | 에러에 대한 상세 메세지
path | String | 에러가 발생한 API URI 

#### Handing Errors

Http Status | Description
--|--
400 | BAD_REQUEST
404 | NOT_FOUND 
405 | METHOD_NOT_ALLOWED
415 | UNSUPPORTED_MEDIA_TYPE
500 | INTERNAL_SERVER_ERROR

#### Examples

```
POST /chatbot/v1/bots/1 HTTP/1.1
Host: localhost:8080
User-Agent: curl/7.54.0
Accept: */*
 
HTTP/1.1 405 
Content-Type: application/json;charset=UTF-8

{
    "timestamp": 1503560424028,
    "status": 405,
    "error": "Method Not Allowed",
    "exception": "org.springframework.web.HttpRequestMethodNotSupportedException",
    "message": "Request method 'POST' not supported",
    "path": "/chatbot/v1/bots/1"
}
```