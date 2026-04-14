# -_-_APLICACION_WEB_Sistema_de_Noticias_Integracon_API_Externa_- :. 
ICACIÓN WEB: Sistema de Noticias (Integración API Externa):

<img width="1536" height="1024" alt="image" src="https://github.com/user-attachments/assets/e70e974b-091f-45e9-a718-578e3b557c01" />    

<img width="2551" height="1030" alt="image" src="https://github.com/user-attachments/assets/7fa9b6f0-0fb6-4f6d-99f8-d50bb41af7e2" />    
    
```

🎯 OBJETIVO

Desarrollar una aplicación que:

✔ Consuma noticias desde una API externa
✔ Muestre resultados en interfaz web
✔ Permita filtrar por categoría / país
✔ Guarde historial en base de datos (Oracle 19c opcional)
✔ Arquitectura profesional (Controller + Service + DAO).

🧩 🏗️ ARQUITECTURA
com.noticias.app
│
├── controller
│   └── NoticiaController.java
│
├── service
│   └── NoticiaService.java
│
├── client
│   └── NewsApiClient.java
│
├── model
│   └── Noticia.java
│
├── repository (opcional - Oracle)
│   └── NoticiaRepository.java
│
└── NoticiaApplication.java

🔌 🌍 API EXTERNA (EJEMPLO)

Puedes usar:

NewsAPI.org
GNews
Mediastack

Ejemplo endpoint:

https://newsapi.org/v2/top-headlines?country=us&apiKey=TU_API_KEY

📦 🧩 DEPENDENCIAS (Maven)
<dependencies>
    <!-- Spring Boot Web -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <!-- Thymeleaf (vista web) -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-thymeleaf</artifactId>
    </dependency>

    <!-- WebClient -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-webflux</artifactId>
    </dependency>

    <!-- JPA (opcional Oracle) -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
</dependencies>

🧠 📄 MODELO
public class Noticia {

    private String titulo;
    private String descripcion;
    private String url;
    private String fuente;

    // getters y setters
}

🌍 🔌 CLIENTE API
@Service
public class NewsApiClient {

    private final String API_KEY = "TU_API_KEY";
    private final String URL = "https://newsapi.org/v2/top-headlines";

    public List<Noticia> obtenerNoticias(String pais) {

        WebClient webClient = WebClient.create();

        Map response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(URL)
                        .queryParam("country", pais)
                        .queryParam("apiKey", API_KEY)
                        .build())
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        List<Map<String, Object>> articles =
                (List<Map<String, Object>>) response.get("articles");

        return articles.stream().map(a -> {
            Noticia n = new Noticia();
            n.setTitulo((String) a.get("title"));
            n.setDescripcion((String) a.get("description"));
            n.setUrl((String) a.get("url"));
            n.setFuente(((Map) a.get("source")).get("name").toString());
            return n;
        }).toList();
    }
}

⚙️ 🧩 SERVICE
@Service
public class NoticiaService {

    @Autowired
    private NewsApiClient client;

    public List<Noticia> listarNoticias(String pais) {
        return client.obtenerNoticias(pais);
    }
}

🌐 🎮 CONTROLLER
@Controller
public class NoticiaController {

    @Autowired
    private NoticiaService service;

    @GetMapping("/")
    public String inicio(Model model) {
        model.addAttribute("noticias", service.listarNoticias("us"));
        return "index";
    }

    @GetMapping("/buscar")
    public String buscar(@RequestParam String pais, Model model) {
        model.addAttribute("noticias", service.listarNoticias(pais));
        return "index";
    }
}

🖥️ 🎨 VISTA (Thymeleaf - index.html)
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Noticias</title>
</head>
<body>

<h1>Noticias Internacionales</h1>

<form action="/buscar">
    <input type="text" name="pais" placeholder="Ej: us, co, mx">
    <button type="submit">Buscar</button>
</form>

<div th:each="n : ${noticias}">
    <h3 th:text="${n.titulo}"></h3>
    <p th:text="${n.descripcion}"></p>
    <a th:href="${n.url}" target="_blank">Leer más</a>
    <hr>
</div>

</body>
</html>

🗄️ 🧩 OPCIONAL: ORACLE 19c
Entidad
@Entity
public class NoticiaEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String titulo;
    private String descripcion;
    private String fuente;
}
Repositorio
public interface NoticiaRepository extends JpaRepository<NoticiaEntity, Long> {
}

🚀 FUNCIONALIDADES EXTRA (RECOMENDADO)
✔ Filtro por categoría (technology, sports, business)
✔ Paginación
✔ Cache de noticias
✔ Guardar favoritos ⭐
✔ Autenticación (Spring Security + JWT)
✔ Dashboard con estadísticas .

🧪 POSIBLE EXTENSIÓN PROFESIONAL
Microservicio de noticias
API propia (exponer tus noticias)
Integración con Kafka (streaming)
Frontend en React/.
