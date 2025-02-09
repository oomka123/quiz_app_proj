import application.MyApplication;
import controllers.AnswerController;
import controllers.AuthController;
import controllers.Icontollers.IAnswerController;
import controllers.Icontollers.IAuthController;
import controllers.Icontollers.IQuestionController;
import controllers.Icontollers.IQuizController;
import controllers.QuestionController;
import controllers.QuizController;
import database.PostgresDB;
import repositories.Irepositories.IAnswerRepository;
import repositories.Irepositories.IQuestionRepository;
import repositories.Irepositories.IQuizRepository;
import repositories.Irepositories.IUserRepository;
import repositories.AnswerRepository;
import repositories.QuestionRepository;
import repositories.QuizRepository;
import repositories.UserRepository;
import services.*;
import services.Iservices.*;

public class Main {
    public static void main(String[] args) {

        String host = "jdbc:postgresql://localhost:5432";
        String username = "postgres";
        String password = "0000";
        String dbName = "quiz_app";


        PostgresDB db = PostgresDB.getInstance(host, username, password, dbName);


        IUserRepository userRepo = new UserRepository(db);
        IQuizRepository quizRepo = new QuizRepository(db);
        IQuestionRepository questionRepo = new QuestionRepository(db);
        IAnswerRepository answerRepo = new AnswerRepository(db);


        IUserAuthService authService = new UserService(userRepo);
        IUserManagementService managementService = (IUserManagementService) authService;
        IQuizService quizService = new QuizService(quizRepo);
        IQuestionService questionService = new QuestionService(questionRepo);
        IAnswerService answerService = new AnswerService(answerRepo);


        IAuthController authController = new AuthController(authService, managementService);
        IQuizController quizController = new QuizController(quizService);
        IQuestionController questionController = new QuestionController(questionService);
        IAnswerController answerController = new AnswerController(answerService);


        MyApplication app = new MyApplication(authController, quizController, questionController, answerController);
        app.start();
    }
}
