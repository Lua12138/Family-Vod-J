import org.junit.Before;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by forDream on 2015-12-27.
 */
public class SpringTest {
    @Before
    public void loadConfiguration() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
    }
}
