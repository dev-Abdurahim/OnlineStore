# OnlineStore
Java Backend Developer - Strong Junior Test Task
## Vazifa bajarildi: 100%
### Texnologiyalar
- Java 17
- Spring Boot 3.x
- Spring Data JPA + Hibernate
- H2 In-Memory Database
- Lombok
- Spring Validation
- SLF4J + Logback
- SpringDoc OpenAPI (Swagger UI)
- JUnit 5 + Mockito
- JaCoCo → **%+ Test Coverage*
- ### Bajarilgan talablar (barchasi 100%)

| Talab                                 | Holati      | Izoh                                |
|---------------------------------------|-------------|-------------------------------------|
| To‘liq REST API (12/11 endpoint)     | Done         | Pagination + Search                 |
| Business Logic (stock, status, price) | Done        | To‘liq tranzaksiya bilan            |
| Custom Exception + Global Handler     | Done        | Chiroyli JSON xatolar               |
| DTO + Bean Validation                 | Done        | @Valid, @Email, @Positive           |
| Logging (muhim operatsiyalar)         | Done        | INFO + DEBUG level                  |
| data.sql (10 ta sample mahsulot)      | Done        |                                     |
| Swagger UI                            | Done        | http://localhost:8081/swagger-ui.html |
| Unit + Integration Testlar            | Done        | 80%+ coverage

### Business qoidalar (to‘liq amal qilindi)
- Stock yetarli bo‘lmasa → InsufficientStockException
- Bir buyurtmada bir xil mahsulot 2 marta bo‘lmaydi
- Stock 0 bo‘lsa → buyurtma berib bo‘lmaydi
- Buyurtma bekor qilinsa → stock qaytadi
- Status faqat PENDING ruxsat etilgan o‘tishlarda o‘zgaradi
- Soft delete (Product o‘chirilganda isActive = false)

  ### API Endpointlar (Swagger yoki Postman orqali sinash mumkin)

| Method | Endpoint                            | Izoh                              |
|--------|-------------------------------------|-----------------------------------|
| GET    | /api/products                     | Pagination bilan                  |
| GET    | /api/products/search              | Name + Category                   |
| POST   | /api/products                     | Yangi mahsulot                    |
| PUT    | /api/products/{id}                | Yangilash                         |
| DELETE | /api/products/{id}                | Soft delete                       |
| POST   | /api/orders                       | Yangi buyurtma                    |
| GET    | /api/orders                       | Barcha buyurtmalar (admin)        |
| GET    | /api/orders/customer/{email}      | Mijoz buyurtmalari                |
| PUT    | /api/orders/{id}/status           | Status o‘zgartirish               |
| DELETE | /api/orders/{id}                  | Bekor qilish (stock qaytadi)      |

### Ishga tushirish

git clone https://github.com/dev-Abdurahim/OnlineStore.git
cd OnlineStore
