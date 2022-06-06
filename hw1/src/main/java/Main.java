import org.springframework.context.support.ClassPathXmlApplicationContext;
import service.CheckService;
import service.CheckServiceImpl;
import service.Examiner;

public class Main {

    public static void main(String[] args) {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        var examiner = context.getBean(Examiner.class);
        examiner.showAllChecks();

    }
}
