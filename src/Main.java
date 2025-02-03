import controllers.AnswerController;
import controllers.AuthController;
import controllers.QuestionController;
import controllers.QuizController;
import database.PostgresDB;
import repositories.AnswerRepository;
import repositories.QuestionRepository;
import repositories.QuizRepository;
import repositories.UserRepository;
import services.AnswerService;
import services.QuestionService;
import services.QuizService;
import services.UserService;

public class Main {
    public static void main(String[] args) {

        String host = "jdbc:postgresql://localhost:5432";
        String username = "postgres";
        String password = "0000";
        String dbName = "quiz_app";

        PostgresDB db = PostgresDB.getInstance(host, username, password, dbName);

        UserRepository userRepo = new UserRepository(db);
        QuizRepository quizRepo = new QuizRepository(db);
        QuestionRepository questionRepo = new QuestionRepository(db);
        AnswerRepository answerRepo = new AnswerRepository(db);

        UserService userService = new UserService(userRepo);
        QuizService quizService = new QuizService(quizRepo);
        QuestionService questionService = new QuestionService(questionRepo);
        AnswerService answerService = new AnswerService(answerRepo);

        AuthController authController = new AuthController(userService);
        QuizController quizController = new QuizController(quizService);
        QuestionController questionController = new QuestionController(questionService);
        AnswerController answerController = new AnswerController(answerService);

        MyApplication app = new MyApplication(authController, quizController, questionController, answerController);
        app.start();
    }
}
