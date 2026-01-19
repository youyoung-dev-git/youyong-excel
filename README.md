# YouYoung Excel

Spring Boot 애플리케이션을 위한 Excel 처리 라이브러리입니다. [Alibaba EasyExcel](https://github.com/alibaba/easyexcel) 기반으로 구축되었습니다.

## 주요 기능

- **어노테이션 기반 설정** - 시트명, 헤더, 스타일, 포맷을 어노테이션으로 간편하게 설정
- **양방향 매핑** - Excel ↔ Java Class 자동 변환
- **Spring Boot 통합** - MultipartFile 업로드, HttpServletResponse 다운로드 지원
- **한글 파일명 지원** - 브라우저 호환 한글 파일명 인코딩 자동 처리
- **빈 열 자동 숨김** - 데이터가 없는 컬럼 자동 숨김 기능
- **Spring Boot 3.x / 4.x 호환**

## 요구사항

- Java 21+
- Spring Boot 3.x 또는 4.x (선택사항)

## 설치

[![](https://jitpack.io/v/youyoung-excel/youyoung-excel.svg)](https://jitpack.io/#youyoung-excel/youyoung-excel)

### Gradle (Kotlin DSL)

```kotlin
repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    implementation("com.github.{GitHub사용자명}:youyoung-excel:{버전}")
    // 예: implementation("com.github.youyoung:youyoung-excel:1.0.0")
    // 또는 최신 커밋: implementation("com.github.youyoung:youyoung-excel:main-SNAPSHOT")
}
```

### Gradle (Groovy)

```groovy
repositories {
    mavenCentral()
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.{GitHub사용자명}:youyoung-excel:{버전}'
}
```

### Maven

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependency>
    <groupId>com.github.{GitHub사용자명}</groupId>
    <artifactId>youyoung-excel</artifactId>
    <version>{버전}</version>
</dependency>
```

### 버전 지정 방법

| 버전 형식 | 예시 | 설명 |
|----------|------|------|
| 태그(릴리스) | `1.0.0` | 특정 릴리스 버전 |
| 브랜치-SNAPSHOT | `main-SNAPSHOT` | 브랜치 최신 커밋 |
| 커밋 해시 | `a1b2c3d` | 특정 커밋 |

> **참고**: GitHub에 푸시 후 [JitPack](https://jitpack.io/#youyoung-excel/youyoung-excel)에서 빌드 상태를 확인할 수 있습니다.

---

## 빠른 시작

### 1단계: Excel 모델 클래스 정의

어노테이션을 사용하여 Excel 시트와 컬럼을 정의합니다.

```java
import net.youyoung.excel.annotation.*;
import net.youyoung.excel.style.*;
import java.time.LocalDate;

@ExcelSheet(name = "사용자 목록")
@ExcelHeaderStyle(backgroundColor = ExcelColor.GREY_25_PERCENT, bold = true)
public class UserExcel {

    @ExcelColumn(header = "이름", order = 1)
    @ExcelStyle(bold = true, fontColor = ExcelColor.BLUE)
    private String name;

    @ExcelColumn(header = "이메일", order = 2, width = 30)
    private String email;

    @ExcelColumn(header = "가입일", order = 3)
    @ExcelDateFormat("yyyy-MM-dd")
    private LocalDate joinDate;

    @ExcelColumn(header = "포인트", order = 4, hideIfEmpty = true)
    @ExcelNumberFormat("#,###")
    private Integer points;

    // 기본 생성자 필수
    public UserExcel() {}

    // Getter/Setter
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    // ... 나머지 getter/setter
}
```

### 2단계: Excel 다운로드 (Controller)

```java
import net.youyoung.excel.spring.ExcelHttpResponse;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @GetMapping("/excel")
    public void downloadExcel(HttpServletResponse response) throws IOException {
        List<UserExcel> users = userService.getUsers();

        // 한글 파일명도 자동으로 인코딩 처리됨
        ExcelHttpResponse.write(response, users, UserExcel.class, "사용자목록.xlsx");
    }
}
```

### 3단계: Excel 업로드 (Controller)

```java
import net.youyoung.excel.spring.ExcelMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @PostMapping("/excel")
    public ResponseEntity<?> uploadExcel(@RequestParam MultipartFile file) throws IOException {
        // Excel 파일을 Java 객체 리스트로 변환
        List<UserExcel> users = ExcelMultipartFile.read(file, UserExcel.class);

        userService.saveAll(users);
        return ResponseEntity.ok().build();
    }
}
```

---

## 어노테이션 상세 설명

### @ExcelSheet

클래스에 적용하여 시트 속성을 설정합니다.

```java
@ExcelSheet(name = "주문 내역")
public class OrderExcel { }
```

| 속성 | 타입 | 기본값 | 설명 |
|------|------|--------|------|
| name | String | "Sheet1" | 시트 이름 |

### @ExcelColumn

필드에 적용하여 컬럼 속성을 설정합니다.

```java
@ExcelColumn(header = "상품명", order = 1, width = 25, hideIfEmpty = true)
private String productName;
```

| 속성 | 타입 | 기본값 | 설명 |
|------|------|--------|------|
| header | String | (필수) | 헤더에 표시될 컬럼명 |
| order | int | 0 | 컬럼 순서 (낮을수록 왼쪽) |
| width | int | -1 | 컬럼 너비 (-1: 자동) |
| hideIfEmpty | boolean | false | 모든 값이 비어있으면 컬럼 숨김 |

### @ExcelStyle

필드에 적용하여 데이터 셀 스타일을 설정합니다.

```java
@ExcelColumn(header = "금액", order = 2)
@ExcelStyle(
    backgroundColor = ExcelColor.LIGHT_YELLOW,
    fontColor = ExcelColor.DARK_BLUE,
    align = ExcelAlign.RIGHT,
    bold = true,
    fontSize = 12
)
private Integer amount;
```

| 속성 | 타입 | 기본값 | 설명 |
|------|------|--------|------|
| backgroundColor | ExcelColor | NONE | 배경색 |
| fontColor | ExcelColor | BLACK | 글자색 |
| align | ExcelAlign | LEFT | 정렬 (LEFT, CENTER, RIGHT) |
| bold | boolean | false | 굵은 글씨 |
| fontSize | int | 11 | 글자 크기 |

### @ExcelHeaderStyle

클래스에 적용하여 헤더 행 스타일을 설정합니다.

```java
@ExcelHeaderStyle(
    backgroundColor = ExcelColor.GREY_25_PERCENT,
    fontColor = ExcelColor.BLACK,
    align = ExcelAlign.CENTER,
    bold = true,
    fontSize = 11,
    height = 25
)
public class OrderExcel { }
```

| 속성 | 타입 | 기본값 | 설명 |
|------|------|--------|------|
| backgroundColor | ExcelColor | GREY_25_PERCENT | 헤더 배경색 |
| fontColor | ExcelColor | BLACK | 헤더 글자색 |
| align | ExcelAlign | CENTER | 헤더 정렬 |
| bold | boolean | true | 굵은 글씨 |
| fontSize | int | 11 | 글자 크기 |
| height | int | -1 | 행 높이 (-1: 자동) |

### @ExcelDateFormat

날짜/시간 필드의 포맷을 설정합니다.

```java
@ExcelColumn(header = "주문일시", order = 3)
@ExcelDateFormat("yyyy-MM-dd HH:mm:ss")
private LocalDateTime orderDateTime;

@ExcelColumn(header = "생년월일", order = 4)
@ExcelDateFormat("yyyy년 MM월 dd일")
private LocalDate birthDate;
```

### @ExcelNumberFormat

숫자 필드의 포맷을 설정합니다.

```java
@ExcelColumn(header = "가격", order = 1)
@ExcelNumberFormat("#,###원")
private Integer price;

@ExcelColumn(header = "할인율", order = 2)
@ExcelNumberFormat("0.00%")
private Double discountRate;

@ExcelColumn(header = "정산금액", order = 3)
@ExcelNumberFormat("#,##0.00")
private BigDecimal settlementAmount;
```

---

## 색상 및 정렬 옵션

### ExcelColor (사용 가능한 색상)

```
기본: NONE, BLACK, WHITE
원색: RED, GREEN, BLUE, YELLOW, PINK, TURQUOISE
어두운 색: DARK_RED, DARK_BLUE, DARK_YELLOW, VIOLET, TEAL
회색 계열: GREY_25_PERCENT, GREY_40_PERCENT, GREY_50_PERCENT, GREY_80_PERCENT
밝은 색: LIGHT_BLUE, LIGHT_GREEN, LIGHT_YELLOW, LIGHT_ORANGE, LIGHT_TURQUOISE
기타: SKY_BLUE, GOLD, ORANGE, CORAL, LAVENDER, TAN, LIME, AQUA, ROSE
```

### ExcelAlign (정렬 옵션)

| 값 | 설명 |
|----|------|
| LEFT | 왼쪽 정렬 |
| CENTER | 가운데 정렬 |
| RIGHT | 오른쪽 정렬 |
| JUSTIFY | 양쪽 정렬 |
| GENERAL | 기본 정렬 |

---

## API 레퍼런스

### ExcelReader - Excel 읽기

다양한 소스에서 Excel 파일을 Java 객체로 변환합니다.

```java
import net.youyoung.excel.core.ExcelReader;

// InputStream에서 읽기
List<UserExcel> users = ExcelReader.read(inputStream, UserExcel.class);

// 특정 시트 지정 (0부터 시작)
List<UserExcel> users = ExcelReader.read(inputStream, UserExcel.class, 0);

// 헤더 행 번호 지정 (1부터 시작)
List<UserExcel> users = ExcelReader.read(inputStream, UserExcel.class, 0, 1);

// File 객체에서 읽기
List<UserExcel> users = ExcelReader.read(new File("users.xlsx"), UserExcel.class);

// 파일 경로에서 읽기
List<UserExcel> users = ExcelReader.read("/path/to/users.xlsx", UserExcel.class);
```

### ExcelWriter - Excel 쓰기

Java 객체를 Excel 파일로 변환합니다.

```java
import net.youyoung.excel.core.ExcelWriter;
import net.youyoung.excel.core.ExcelFile;

// byte 배열로 변환
byte[] bytes = ExcelWriter.write(users, UserExcel.class);

// 파일명과 함께 ExcelFile 객체로 반환
ExcelFile file = ExcelWriter.writeWithFileName(users, UserExcel.class, "사용자목록");
// file.getContent() - byte 배열
// file.getFileName() - "사용자목록.xlsx"

// OutputStream으로 출력
ExcelWriter.writeTo(outputStream, users, UserExcel.class);

// File로 저장
ExcelWriter.writeTo(new File("output.xlsx"), users, UserExcel.class);

// 파일 경로로 저장
ExcelWriter.writeTo("/path/to/output.xlsx", users, UserExcel.class);
```

### ExcelHttpResponse - Spring HTTP 응답 (다운로드)

HttpServletResponse를 통해 Excel 파일을 다운로드합니다.

```java
import net.youyoung.excel.spring.ExcelHttpResponse;

// 기본 방식 (메모리에 먼저 생성 후 응답)
ExcelHttpResponse.write(response, users, UserExcel.class, "사용자목록.xlsx");

// 스트리밍 방식 (대용량 데이터에 적합)
ExcelHttpResponse.writeStream(response, users, UserExcel.class, "사용자목록.xlsx");
```

### ExcelMultipartFile - Spring 파일 업로드

MultipartFile에서 Excel 데이터를 읽습니다.

```java
import net.youyoung.excel.spring.ExcelMultipartFile;

// 기본 읽기
List<UserExcel> users = ExcelMultipartFile.read(multipartFile, UserExcel.class);

// 특정 시트 지정
List<UserExcel> users = ExcelMultipartFile.read(multipartFile, UserExcel.class, 0);

// 시트와 헤더 행 번호 지정
List<UserExcel> users = ExcelMultipartFile.read(multipartFile, UserExcel.class, 0, 1);
```

---

## 전체 예제

### 주문 내역 Excel 모델

```java
import net.youyoung.excel.annotation.*;
import net.youyoung.excel.style.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@ExcelSheet(name = "주문 내역")
@ExcelHeaderStyle(
    backgroundColor = ExcelColor.DARK_BLUE,
    fontColor = ExcelColor.WHITE,
    bold = true,
    align = ExcelAlign.CENTER
)
public class OrderExcel {

    @ExcelColumn(header = "주문번호", order = 1, width = 15)
    @ExcelStyle(fontColor = ExcelColor.BLUE, bold = true)
    private String orderId;

    @ExcelColumn(header = "고객명", order = 2, width = 12)
    private String customerName;

    @ExcelColumn(header = "상품명", order = 3, width = 25)
    private String productName;

    @ExcelColumn(header = "수량", order = 4, width = 8)
    @ExcelStyle(align = ExcelAlign.CENTER)
    private Integer quantity;

    @ExcelColumn(header = "단가", order = 5, width = 12)
    @ExcelStyle(align = ExcelAlign.RIGHT)
    @ExcelNumberFormat("#,###원")
    private Integer unitPrice;

    @ExcelColumn(header = "총액", order = 6, width = 15)
    @ExcelStyle(align = ExcelAlign.RIGHT, bold = true, backgroundColor = ExcelColor.LIGHT_YELLOW)
    @ExcelNumberFormat("#,###원")
    private Integer totalAmount;

    @ExcelColumn(header = "주문일시", order = 7, width = 20)
    @ExcelDateFormat("yyyy-MM-dd HH:mm")
    private LocalDateTime orderDateTime;

    @ExcelColumn(header = "비고", order = 8, width = 20, hideIfEmpty = true)
    private String note;

    // 생성자, Getter, Setter 생략
}
```

### Controller 예제

```java
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/excel")
    public void downloadOrders(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            HttpServletResponse response) throws IOException {

        List<OrderExcel> orders = orderService.getOrdersForExcel(startDate, endDate);

        String fileName = String.format("주문내역_%s_%s.xlsx", startDate, endDate);
        ExcelHttpResponse.write(response, orders, OrderExcel.class, fileName);
    }

    @PostMapping("/excel")
    public ResponseEntity<Map<String, Object>> uploadOrders(
            @RequestParam MultipartFile file) throws IOException {

        List<OrderExcel> orders = ExcelMultipartFile.read(file, OrderExcel.class);
        int savedCount = orderService.saveOrders(orders);

        return ResponseEntity.ok(Map.of(
            "message", "업로드 완료",
            "count", savedCount
        ));
    }
}
```

---

## 라이선스

Apache License 2.0
