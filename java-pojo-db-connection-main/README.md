# Pojo JAVA DB연동

## Code

User.java

```java
public class User {
    private String id;
    private String name;
    private String password;

    public User(String id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}
```

UserDao.java

```java
import java.sql.*;
import java.util.Map;

public class UserDao {
    public void add(User user) {
        Map<String, String> env = System.getenv();
        try {
            // DB접속 (ex sql workbeanch실행)
            Connection c = DriverManager.getConnection(env.get("DB_HOST"),
                    env.get("DB_USER"), env.get("DB_PASSWORD"));

            // Query문 작성
            PreparedStatement pstmt = c.prepareStatement("INSERT INTO users(id, name, password) VALUES(?,?,?);");
            pstmt.setString(1, user.getId());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getPassword());

            // Query문 실행
            pstmt.executeUpdate();

            pstmt.close();
            c.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public User findById(String id) {
        Map<String, String> env = System.getenv();
        Connection c;
        try {
            // DB접속 (ex sql workbeanch실행)
             c = DriverManager.getConnection(env.get("DB_HOST"),
                    env.get("DB_USER"), env.get("DB_PASSWORD"));

            // Query문 작성
            PreparedStatement pstmt = c.prepareStatement("SELECT * FROM users WHERE id = ?");
            pstmt.setString(1, id);

            // Query문 실행
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            User user = new User(rs.getString("id"), rs.getString("name"),
                    rs.getString("password"));

            rs.close();
            pstmt.close();
            c.close();

            return user;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        UserDao userDao = new UserDao();
//        userDao.add();
        User user = userDao.findById("6");
        System.out.println(user.getName());
    }
}
```

### Spring Boot dependency추가

spring-boot-starter-jdbc

spring-boot-starter-test

```java
dependencies {
    implementation 'mysql:mysql-connector-java:8.0.30'
    implementation 'org.springframework.boot:spring-boot-starter-jdbc:2.7.4'
    implementation 'org.springframework.boot:spring-boot-starter-test:2.7.4'
    testImplementation 'org.junit.jupiter:junit-jupiter-api:5.8.1'
    testRuntimeOnly 'org.junit.jupiter:junit-jupiter-engine:5.8.1'
}
```

## Factory를 Bean으로

```java
@Configuration
public class UserDaoFactory2 {

    @Bean
    public UserDao05Interface userDao05Interface() {
        return new UserDao05Interface(() -> {
            return null;
        });
    }

}

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx =
                new AnnotationConfigApplicationContext(UserDaoFactory2.class);
        UserDao05Interface userDao = ctx.getBean("userDao05Interface", UserDao05Interface.class);

    }
}
```

### Test에서 Factory의 Configuration사용
```java
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UserDaoFactory2.class)
public class UserDao05WithInterfaceTest {

    @Autowired
    ApplicationContext context;

    @Test
    public void testName() throws SQLException, ClassNotFoundException {
        UserDao05WithInterface userDao = context.getBean("userDao", UserDao05WithInterface.class);
        userDao.add();
    }
}
```

### StatementStrategy Interface

StatementStrategy Interface를 적용하면 아래와 같이 반복되는 부분을 한단계 추상화 할 수 있습니다.

```java
PreparedStatement pstmt = conn.prepareStatement("delete from users");
```

```java
public interface StatementStrategy {
    PreparedStatement makePreparedStatement(Connection c);
}
```


### 참고 
https://github.com/Kyeongrok/toby_spring

