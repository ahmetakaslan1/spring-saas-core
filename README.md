# Order Management System

Bu proje, Spring Boot ve modern Java teknolojileri kullanılarak geliştirilmiş, modüler yapıda bir Sipariş Yönetim Sistemidir. Gelişmiş loglama, doğrulama (validation) ve API dokümantasyonu gibi production-ready özellikler içerir.

## 🚀 Proje Yapısı ve Modüler Yaklaşım

Proje, "Independent Modules" (Bağımsız Modüller) mimarisi ile tasarlanmıştır. Bu sayede her bir özellik (feature) kendi paketi altında izole edilmiştir. Kodları incelerken şu yapıyı göreceksiniz:

- **`auth`**: Kimlik doğrulama, Login/Register işlemleri ve Token yönetimi.
- **`user`**: Kullanıcı profil yönetimi ve kullanıcıya özgü işlemler.
- **`order`**: (Geliştirme aşamasında) Sipariş yönetimi modülü.
- **`config`**: Tüm sisteme etki eden güvenlik (Security) ve uygulama ayarları.
- **`common`**: Tüm modüllerin ortak kullandığı hata yönetimi (exception handling) ve yardımcı sınıflar.

Bu yapı sayesinde, projenin herhangi bir modülü başka bir projeye kolayca taşınabilir veya bağımsız olarak geliştirilebilir.

## 🛠 Kullanılan Teknolojiler ve Kütüphaneler

Projede kullanılan temel ve yardımcı teknolojiler şunlardır:

### Temel Altyapı

- **Java 17**: En güncel LTS Java sürümü.
- **Spring Boot 3.2**: Hızlı ve güvenli web uygulaması geliştirme çatısı.
- **PostgreSQL**: Güçlü ve açık kaynaklı ilişkisel veritabanı.
- **Maven**: Proje bağımlılık ve build yönetimi.

### Güvenlik (Security)

- **Spring Security**: Sektör standardı güvenlik çatısı.
- **JWT (JSON Web Token)**: Stateless kimlik doğrulama mekanizması.
- **BCrypt**: Güvenli şifre hashleme algoritması.

### Veri Yönetimi ve Validasyon

- **Spring Data JPA (Hibernate)**: Veritabanı işlemleri için ORM katmanı.
- **Spring Validation**: API'ye gelen isteklerdeki verilerin doğruluğunu kontrol eder (Pattern, NotBlank, Size vb.).

### İzleme ve Dokümantasyon

- **Spring Boot Actuator**: Uygulamanın sağlık durumunu (Health Check) ve metriklerini izlemek için kullanılır.
- **OpenAPI / Swagger (SpringDoc)**: API uçlarını otomatik olarak dokümante eder ve test arayüzü sunar.
- **SLF4J & Logback**: Spring Boot ile entegre, gelişmiş loglama altyapısı. Sistemdeki tüm önemli olaylar ve hatalar detaylıca loglanır.

### Verimlilik

- **Lombok**: Getter, Setter, Constructor gibi tekrar eden kodları otomatik üreterek kodun temiz kalmasını sağlar.

## ⚙️ Kurulum ve Çalıştırma

Projeyi yerel ortamınızda en kolay şekilde çalıştırmak için **Docker** kullanmanızı öneririz.

### 1. Ön Hazırlık

- Bilgisayarınızda [Docker Desktop](https://www.docker.com/products/docker-desktop/) kurulu ve çalışır durumda olmalıdır.
- Projeyi klonlayın:
  ```bash
  git clone https://github.com/kullaniciadim/order-management.git
  cd order-management
  ```

### 2. Çevresel Değişkenler (.env)

Proje ayarlarını `.env` dosyası üzerinden okur. Örnek dosyayı kopyalayarak kendi `.env` dosyanızı oluşturun:

```bash
# Windows (PowerShell)
copy .env.example .env

# Linux / Mac
cp .env.example .env
```

Oluşan `.env` dosyasını bir metin editörü ile açıp gerekli değişiklikleri yapabilirsiniz (varsayılan ayarlar Docker için uygundur).

### 3. Uygulamayı Başlatma (Docker)

Aşağıdaki komut ile hem veritabanını hem de uygulamayı tek seferde ayağa kaldırın:

```bash
docker-compose up -d --build
```

Bu işlem ilk seferde veritabanı imajını ve uygulama bağımlılıklarını indireceği için biraz zaman alabilir.

- Uygulama Adresi: `http://localhost:8080`
- Swagger UI (Dokümantasyon): `http://localhost:8080/swagger-ui/index.html` (Servis ayaktayken erişilebilir)

### 4. Geliştirme Ortamı (Opsiyonel)

Eğer Docker olmadan, IDE üzerinden (IntelliJ, VS Code vb.) çalıştırmak isterseniz:

1.  Yerelinizde bir PostgreSQL veritabanı oluşturun.
2.  `.env` dosyasındaki veya `application.yaml` içindeki `DB_HOST` bilgisini `localhost` olarak güncelleyin.
3.  Projeyi IDE üzerinden `OrderManagementApplication.java` dosyasını çalıştırarak başlatın.

---

👨‍💻 Geliştirici: Ahmet Akaslan