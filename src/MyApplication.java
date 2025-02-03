import controllers.AuthController;
import controllers.QuestionController;
import controllers.QuizController;
import controllers.AnswerController;

import models.*;

import java.util.*;
import java.util.function.Supplier;

public class MyApplication {
    private final AuthController authController;
    private final QuizController quizController;
    private final QuestionController questionController;
    private final AnswerController answerController;
    private final Scanner scanner;
    private User currentUser;

    public MyApplication(AuthController authController, QuizController quizController, QuestionController questionController, AnswerController answerController) {
        this.scanner = new Scanner(System.in);
        this.authController = authController;
        this.quizController = quizController;
        this.questionController = questionController;
        this.answerController = answerController;
    }

    private void mainMenu() {
        System.out.println();
        System.out.println("Welcome to Quiz Application");
        System.out.println("Select option:");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("0. Exit");
        System.out.println();
        System.out.print("Enter option (0-2): ");
    }

    private void quizMenu() {
        System.out.println();
        System.out.println("Quiz Menu");
        System.out.println("Select option:");
        System.out.println("1. Start Quiz");
        System.out.println("2. Create Quiz");
        System.out.println("3. Show Quizzes");
        System.out.println("4. Delete Quizzes");
        System.out.println("5. Manage Questions");
        if (currentUser.getRole() == User.Role.ADMIN) {
            System.out.println("6. Admin Panel");
        }
        if (currentUser.getRole() == User.Role.EDITOR) {
            System.out.println("7. Editor Panel");
        }
        System.out.println("0. Logout");
        System.out.println();
        System.out.print("Enter option (0-5): ");
    }

    private void questionMenu() {
        System.out.println("\n=== Question Menu ===");
        System.out.println("1. View Questions");
        System.out.println("2. Add Question");
        System.out.println("3. Delete Question");
        System.out.println("4. Manage Answers");
        System.out.println("0. Back");
        System.out.print("Enter your choice: ");
    }

    private void answerMenu() {
        System.out.println("\n=== Answer Menu ===");
        System.out.println("1. View Answers");
        System.out.println("2. Add Answer");
        System.out.println("3. Delete Answer");
        System.out.println("4. Update Answer");
        System.out.println("0. Back");
        System.out.print("Enter your choice: ");
    }


    public void start() {
        boolean running = true;

        while (running) {
            mainMenu();

            try {
                int option = scanner.nextInt();
                scanner.nextLine();

                switch (option) {
                    case 1 -> getRegistration();
                    case 2 -> {
                        getLogin();
                        if (currentUser != null) {
                            quizMenuHandler();
                        }
                    }
                    case 0 -> {
                        System.out.println("\n*************************");
                        System.out.println("Goodbye!");
                        System.out.println("*************************\n");
                        running = false;
                    }
                    default -> System.out.println("Invalid option. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Input must be an integer. " + e.getMessage());
                scanner.next();
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }

    private void quizMenuHandler() {
        boolean inQuizMenu = true;

        while (inQuizMenu) {
            quizMenu();

            try {
                int option = scanner.nextInt();
                scanner.nextLine();
                switch (option) {
                    case 1 -> startQuiz();
                    case 2 -> createQuiz();
                    case 3 -> showQuizzes();
                    case 4 -> deleteQuiz();
                    case 5 -> questionMenuHandler();
                    case 6 -> {
                        if (currentUser.getRole() == User.Role.ADMIN) {
                            adminPanel();
                        } else {
                            System.out.println("Invalid option.");
                        }
                    }
                    case 7 -> {
                        if (currentUser.getRole() == User.Role.EDITOR) {
                            editorPanel();
                        } else {
                            System.out.println("Invalid option.");
                        }
                    }
                    case 0 -> {
                        System.out.println("\n*************************");
                        System.out.println("Logging out...");
                        System.out.println("*************************\n");
                        currentUser = null;
                        inQuizMenu = false;
                    }
                    default -> System.out.println("Invalid option. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Input must be an integer: " + e.getMessage());
                scanner.next();
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }

    private void quizMenuHandlerForUser(User selectedUser) {
        boolean inQuizMenu = true;
        while (inQuizMenu) {

            quizMenu();
            try {
                int option = scanner.nextInt();
                scanner.nextLine();
                switch (option) {
                    case 1 -> startQuiz();
                    case 2 -> createQuiz();
                    case 3 -> showQuizzes();
                    case 4 -> deleteQuiz();
                    case 5 -> questionMenuHandler();
                    case 0 -> {
                        System.out.println("\n*************************");
                        System.out.println("Returning to Editor Panel...");
                        System.out.println("*************************\n");
                        inQuizMenu = false;
                    }
                    default -> System.out.println("Invalid option. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Input must be an integer: " + e.getMessage());
                scanner.next();
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }

    private void questionMenuHandler() {
        boolean inQuestionMenu = true;

        while (inQuestionMenu) {
            questionMenu();

            try {
                int option = scanner.nextInt();
                scanner.nextLine();
                switch (option) {
                    case 1 -> showQuestions();
                    case 2 -> addQuestion();
                    case 3 -> deleteQuestion();
                    case 4 -> handleAnswerMenu();
                    case 0 -> inQuestionMenu = false;
                    default -> System.out.println("Invalid option. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Input must be an integer: " + e.getMessage());
                scanner.next();
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }

    private void handleAnswerMenu() {
        while (true) {
            answerMenu();
            try {
                int option = scanner.nextInt();
                scanner.nextLine();

                switch (option) {
                    case 1 -> viewAnswers();
                    case 2 -> addAnswer();
                    case 3 -> deleteAnswer();
                    case 4 -> updateAnswer();
                    case 0 -> {
                        return;
                    }
                    default -> System.out.println("Invalid option. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Input must be an integer. " + e.getMessage());
                scanner.next();
            }
        }
    }

    public void getRegistration() {
        try {
            Supplier<String> usernameSupplier = () -> {
                System.out.print("Enter username: ");
                return scanner.nextLine();
            };
            Supplier<String> passwordSupplier = () -> {
                System.out.print("Enter password: ");
                return scanner.nextLine();
            };

            String username = usernameSupplier.get();
            String password = passwordSupplier.get();

            String response = authController.registerUser(username, password);
            System.out.println(response);

            if (response.equals("Registration successful!")) {
                currentUser = authController.loginUser(username, password);
                if (currentUser != null) {
                    System.out.println("Registration and login successful! Welcome, " + currentUser.getUserName());
                    quizMenuHandler();
                }
            }
        } catch (Exception e) {
            System.out.println("Error in registration: " + e.getMessage());
        }
    }
    public void getLogin() {
        try {
            Supplier<String> usernameSupplier = () -> {
                System.out.print("Enter username: ");
                return scanner.nextLine();
            };
            Supplier<String> passwordSupplier = () -> {
                System.out.print("Enter password: ");
                return scanner.nextLine();
            };

            String username = usernameSupplier.get();
            String password = passwordSupplier.get();

            User user = authController.loginUser(username, password);
            if (user != null) {
                currentUser = user;
                System.out.println("Login successful! Welcome, " + user.getUserName());
                quizMenuHandler();
            } else {
                System.out.println("Invalid username or password. Please try again.");
            }
        } catch (Exception e) {
            System.out.println("Error in login: " + e.getMessage());
        }
    }

    private void viewAllUsers() {
        var users = authController.getAllUsers();
        if (users.isEmpty()) {
            System.out.println("No users found.");
            return;
        }
        System.out.println("List of Users:");
        users.forEach(user ->
                System.out.println("ID: " + user.getUserId() +
                        " | Username: " + user.getUserName() +
                        " | Role: " + user.getRole())
        );
    }

    private void createQuiz() {
        createQuizForUser(currentUser);
    }

    private void createQuizForUser(User user) {
        if (user == null) {
            System.out.println("User is not specified.");
            return;
        }
        System.out.print("Enter quiz name: ");
        String quizName = scanner.nextLine();
        String response = quizController.createQuiz(quizName, user.getUserId());
        System.out.println(response);
    }

    private void adminPanel() {
        boolean inAdminPanel = true;
        while (inAdminPanel) {
            System.out.println("\n=== Admin Panel ===");
            System.out.println("1. View All Users");
            System.out.println("2. Change User Role");
            System.out.println("0. Back");
            System.out.print("Enter your choice: ");
            int option = scanner.nextInt();
            scanner.nextLine();
            switch (option) {
                case 1 -> viewAllUsers();
                case 2 -> changeUserRole();
                case 0 -> inAdminPanel = false;
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void changeUserRole() {
        System.out.print("Enter user ID to change role: ");
        int userId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter new role (ADMIN, EDITOR, USER): ");
        String newRole = scanner.nextLine().trim().toUpperCase();
        String result = authController.updateUserRole(currentUser.getUserId(), userId, newRole);
        System.out.println(result);
    }


    private void editorPanel() {
        boolean inEditorPanel = true;
        while (inEditorPanel) {
            System.out.println("\n=== Editor Panel ===");
            System.out.println("1. View All Users");
            System.out.println("2. Choose User for Editing");
            System.out.println("3. Edit Content (stub)");
            System.out.println("0. Back");
            System.out.print("Enter your choice: ");
            int option = scanner.nextInt();
            scanner.nextLine();
            switch (option) {
                case 1 -> viewAllUsers();
                case 2 -> {
                    User selectedUser = chooseUser();
                    if (selectedUser != null) {

                        quizMenuHandlerForUser(selectedUser);
                    } else {
                        System.out.println("No user selected.");
                    }
                }
                case 3 -> System.out.println("Editing content... (feature not implemented yet)");
                case 0 -> inEditorPanel = false;
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private User chooseUser() {
        System.out.println("Enter the ID of the user you want to manage:");

        var users = authController.getAllUsers();
        if (users.isEmpty()) {
            System.out.println("No users found.");
            return null;
        }

        for (var user : users) {
            System.out.println("ID: " + user.getUserId() +
                    " | Username: " + user.getUserName() +
                    " | Role: " + user.getRole());
        }
        System.out.print("Enter user ID: ");
        int selectedId = scanner.nextInt();
        scanner.nextLine();

        User selectedUser = authController.getUserById(selectedId);
        if (selectedUser == null) {
            System.out.println("User with ID " + selectedId + " not found.");
        }
        return selectedUser;
    }


    private void editQuizzes() {

        System.out.println("Editing quizzes... (feature not implemented yet)");
    }

    private void editQuestions() {

        System.out.println("Editing questions... (feature not implemented yet)");
    }

    private void editAnswers() {

        System.out.println("Editing answers... (feature not implemented yet)");
    }


    private void showQuizzes() {
        if (currentUser == null) {
            System.out.println("You must be logged in to view quizzes.");
            return;
        }

        List<Quiz> quizzes = quizController.showQuizzes(currentUser.getUserId());

        if (quizzes.isEmpty()) {
            System.out.println("No quizzes found.");
            return;
        }

        System.out.println("Your Quizzes with Question Count:");
        quizzes.forEach(quiz ->
                System.out.println(quiz.getQuizName() + " - " + quiz.getQuestionCount() + " questions")
        );
    }


    public void deleteQuiz() {
        System.out.println("Select quiz to delete:");

        List<Quiz> quizzes = quizController.showQuizzes(currentUser.getUserId());
        if (quizzes.isEmpty()) {
            System.out.println("No quizzes available to delete.");
            return;
        }


        for (int i = 0; i < quizzes.size(); i++) {
            System.out.println((i + 1) + ". " + quizzes.get(i).getQuizName());
        }

        try {
            System.out.print("Enter the number of the quiz to delete: ");
            int quizNumber = scanner.nextInt();
            scanner.nextLine();

            if (quizNumber > 0 && quizNumber <= quizzes.size()) {
                int quizId = quizzes.get(quizNumber - 1).getQuizId();
                String response = quizController.deleteQuiz(quizId);
                System.out.println(response);
            } else {
                System.out.println("Invalid quiz number. Please try again.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Input must be an integer: " + e.getMessage());
            scanner.next();
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }



    private void addQuestion() {
        System.out.println("Select quiz to add a question:");

        List<Quiz> quizzes = quizController.showQuizzes(currentUser.getUserId());
        if (quizzes.isEmpty()) {
            System.out.println("No quizzes available to add questions.");
            return;
        }

        for (int i = 0; i < quizzes.size(); i++) {
            System.out.println((i + 1) + ". " + quizzes.get(i).getQuizName());
        }

        try {
            System.out.print("Enter the number of the quiz: ");
            int quizNumber = scanner.nextInt();
            scanner.nextLine();

            if (quizNumber > 0 && quizNumber <= quizzes.size()) {
                int quizId = quizzes.get(quizNumber - 1).getQuizId();

                System.out.print("Enter question text: ");
                String questionText = scanner.nextLine();

                Question question = new Question(questionText, quizId);
                String response = questionController.addQuestion(question);
                System.out.println(response);
            } else {
                System.out.println("Invalid quiz number.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Input must be an integer.");
            scanner.next();
        }
    }

    private void showQuestions() {
        System.out.println("Select quiz to view questions:");

        List<Quiz> quizzes = quizController.showQuizzes(currentUser.getUserId());
        if (quizzes.isEmpty()) {
            System.out.println("No quizzes available.");
            return;
        }

        for (int i = 0; i < quizzes.size(); i++) {
            System.out.println((i + 1) + ". " + quizzes.get(i).getQuizName());
        }

        try {
            System.out.print("Enter the number of the quiz: ");
            int quizNumber = scanner.nextInt();
            scanner.nextLine();

            if (quizNumber > 0 && quizNumber <= quizzes.size()) {
                int quizId = quizzes.get(quizNumber - 1).getQuizId();

                List<Question> questions = questionController.getQuestionsByQuiz(quizId);
                if (questions.isEmpty()) {
                    System.out.println("No questions found for this quiz.");
                } else {
                    System.out.println("Questions:");
                    questions.forEach(q -> System.out.println("- " + q.getQuestionText()));
                }
            } else {
                System.out.println("Invalid quiz number.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Input must be an integer.");
            scanner.next();
        }
    }

    private void deleteQuestion() {
        System.out.println("Select quiz to delete a question:");

        List<Quiz> quizzes = quizController.showQuizzes(currentUser.getUserId());
        if (quizzes.isEmpty()) {
            System.out.println("No quizzes available.");
            return;
        }

        for (int i = 0; i < quizzes.size(); i++) {
            System.out.println((i + 1) + ". " + quizzes.get(i).getQuizName());
        }

        try {
            System.out.print("Enter the number of the quiz: ");
            int quizNumber = scanner.nextInt();
            scanner.nextLine();

            if (quizNumber > 0 && quizNumber <= quizzes.size()) {
                int quizId = quizzes.get(quizNumber - 1).getQuizId();

                List<Question> questions = questionController.getQuestionsByQuiz(quizId);
                if (questions.isEmpty()) {
                    System.out.println("No questions found for this quiz.");
                    return;
                }

                for (int i = 0; i < questions.size(); i++) {
                    System.out.println((i + 1) + ". " + questions.get(i).getQuestionText());
                }

                System.out.print("Enter the number of the question to delete: ");
                int questionNumber = scanner.nextInt();
                scanner.nextLine();

                if (questionNumber > 0 && questionNumber <= questions.size()) {
                    int questionId = questions.get(questionNumber - 1).getQuestionId();
                    String response = questionController.deleteQuestion(questionId);
                    System.out.println(response);
                } else {
                    System.out.println("Invalid question number.");
                }
            } else {
                System.out.println("Invalid quiz number.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Input must be an integer.");
            scanner.next();
        }
    }



    private void viewAnswers() {
        List<Quiz> quizzes = quizController.getQuizzesByUser(currentUser.getUserId());
        if (quizzes.isEmpty()) {
            System.out.println("No quizzes available.");
            return;
        }

        displayQuizzes(quizzes);

        System.out.print("Enter the number of the quiz to view questions: ");
        int quizIndex = scanner.nextInt();
        scanner.nextLine();

        if (quizIndex <= 0 || quizIndex > quizzes.size()) {
            System.out.println("Invalid quiz number.");
            return;
        }

        int selectedQuizId = quizzes.get(quizIndex - 1).getQuizId();

        List<Question> questions = questionController.getQuestionsByQuiz(selectedQuizId);
        if (questions.isEmpty()) {
            System.out.println("No questions available for this quiz.");
            return;
        }

        displayQuestions(questions);

        System.out.print("Enter the number of the question to view answers: ");
        int questionIndex = scanner.nextInt();
        scanner.nextLine();

        if (questionIndex <= 0 || questionIndex > questions.size()) {
            System.out.println("Invalid question number.");
            return;
        }

        int selectedQuestionId = questions.get(questionIndex - 1).getQuestionId();

        List<Answer> answers = answerController.getAnswersByQuestion(selectedQuestionId);
        if (answers.isEmpty()) {
            System.out.println("No answers found for this question.");
        } else {
            System.out.println("Answers:");
            answers.forEach(a -> System.out.println("- " + a.getAnswerText() + " (Correct: " + a.isCorrectAnswer() + ")"));
        }
    }

    private void addAnswer() {
        List<Quiz> quizzes = quizController.getQuizzesByUser(currentUser.getUserId());
        if (quizzes.isEmpty()) {
            System.out.println("No quizzes available.");
            return;
        }

        displayQuizzes(quizzes);

        System.out.print("Enter the number of the quiz to view questions: ");
        int quizIndex = scanner.nextInt();
        scanner.nextLine();

        if (quizIndex <= 0 || quizIndex > quizzes.size()) {
            System.out.println("Invalid quiz number.");
            return;
        }

        int selectedQuizId = quizzes.get(quizIndex - 1).getQuizId();

        List<Question> questions = questionController.getQuestionsByQuiz(selectedQuizId);
        if (questions.isEmpty()) {
            System.out.println("No questions available for this quiz.");
            return;
        }

        displayQuestions(questions);

        System.out.print("Enter the number of the question to view answers: ");
        int questionIndex = scanner.nextInt();
        scanner.nextLine();

        if (questionIndex <= 0 || questionIndex > questions.size()) {
            System.out.println("Invalid question number.");
            return;
        }

        int selectedQuestionId = questions.get(questionIndex - 1).getQuestionId();

        System.out.print("Enter answer text: ");
        String answerText = scanner.nextLine();
        System.out.print("Is this answer correct? (true/false): ");
        boolean isCorrect = scanner.nextBoolean();
        scanner.nextLine();

        Answer answer = new Answer(0, answerText, isCorrect, selectedQuestionId);
        if (answerController.addAnswer(answer)) {
            System.out.println("Answer added successfully.");
        } else {
            System.out.println("Failed to add answer.");
        }
    }


    private void deleteAnswer() {

        List<Quiz> quizzes = quizController.getQuizzesByUser(currentUser.getUserId());
        if (quizzes.isEmpty()) {
            System.out.println("No quizzes available.");
            return;
        }

        displayQuizzes(quizzes);

        System.out.print("Enter the number of the quiz to view questions: ");
        int quizIndex = scanner.nextInt();
        scanner.nextLine();

        if (quizIndex <= 0 || quizIndex > quizzes.size()) {
            System.out.println("Invalid quiz number.");
            return;
        }

        int selectedQuizId = quizzes.get(quizIndex - 1).getQuizId();

        List<Question> questions = questionController.getQuestionsByQuiz(selectedQuizId);
        if (questions.isEmpty()) {
            System.out.println("No questions available for this quiz.");
            return;
        }

        displayQuestions(questions);

        System.out.print("Enter the number of the question to view answers: ");
        int questionIndex = scanner.nextInt();
        scanner.nextLine();

        if (questionIndex <= 0 || questionIndex > questions.size()) {
            System.out.println("Invalid question number.");
            return;
        }

        int selectedQuestionId = questions.get(questionIndex - 1).getQuestionId();

        List<Answer> answers = answerController.getAnswersByQuestion(selectedQuestionId);
        if (answers.isEmpty()) {
            System.out.println("No answers found for this question.");
            return;
        }

        System.out.println("Answers:");
        for (int i = 0; i < answers.size(); i++) {
            Answer answer = answers.get(i);
            System.out.println((i + 1) + ". " + answer.getAnswerText() + " (Correct: " + answer.isCorrectAnswer() + ")");
        }

        System.out.print("Enter the number of the answer to delete: ");
        int answerIndex = scanner.nextInt();
        scanner.nextLine();

        if (answerIndex <= 0 || answerIndex > answers.size()) {
            System.out.println("Invalid answer number.");
            return;
        }

        int answerId = answers.get(answerIndex - 1).getAnswerId();

        if (answerController.deleteAnswer(answerId)) {
            System.out.println("Answer deleted successfully.");
        } else {
            System.out.println("Failed to delete answer.");
        }
    }


    private void updateAnswer() {

        List<Quiz> quizzes = quizController.getQuizzesByUser(currentUser.getUserId());
        if (quizzes.isEmpty()) {
            System.out.println("No quizzes available.");
            return;
        }

        displayQuizzes(quizzes);

        System.out.print("Enter the number of the quiz to view questions: ");
        int quizIndex = scanner.nextInt();
        scanner.nextLine();

        if (quizIndex <= 0 || quizIndex > quizzes.size()) {
            System.out.println("Invalid quiz number.");
            return;
        }

        int selectedQuizId = quizzes.get(quizIndex - 1).getQuizId();


        List<Question> questions = questionController.getQuestionsByQuiz(selectedQuizId);
        if (questions.isEmpty()) {
            System.out.println("No questions available for this quiz.");
            return;
        }

        displayQuestions(questions);

        System.out.print("Enter the number of the question to view answers: ");
        int questionIndex = scanner.nextInt();
        scanner.nextLine();

        if (questionIndex <= 0 || questionIndex > questions.size()) {
            System.out.println("Invalid question number.");
            return;
        }

        int selectedQuestionId = questions.get(questionIndex - 1).getQuestionId();

        List<Answer> answers = answerController.getAnswersByQuestion(selectedQuestionId);
        if (answers.isEmpty()) {
            System.out.println("No answers found for this question.");
            return;
        }


        System.out.println("Answers:");
        for (int i = 0; i < answers.size(); i++) {
            Answer answer = answers.get(i);
            System.out.println((i + 1) + ". " + answer.getAnswerText() + " (Correct: " + answer.isCorrectAnswer() + ")");
        }

        System.out.print("Enter the number of the answer to update: ");
        int answerIndex = scanner.nextInt();
        scanner.nextLine();

        if (answerIndex <= 0 || answerIndex > answers.size()) {
            System.out.println("Invalid answer number.");
            return;
        }

        int answerId = answers.get(answerIndex - 1).getAnswerId();

        System.out.print("Enter new answer text: ");
        String newAnswerText = scanner.nextLine();
        System.out.print("Is this answer correct? (true/false): ");
        boolean newIsCorrect = scanner.nextBoolean();
        scanner.nextLine();

        Answer updatedAnswer = new Answer(answerId, newAnswerText, newIsCorrect, selectedQuestionId);
        if (answerController.updateAnswer(updatedAnswer)) {
            System.out.println("Answer updated successfully.");
        } else {
            System.out.println("Failed to update answer.");
        }
    }

    private void displayQuizzes(List<Quiz> quizzes) {
        System.out.println("Available Quizzes:");
        for (int i = 0; i < quizzes.size(); i++) {
            System.out.println((i + 1) + ". " + quizzes.get(i).getQuizName());
        }
    }

    private void displayQuestions(List<Question> questions) {
        System.out.println("Questions:");
        for (int i = 0; i < questions.size(); i++) {
            System.out.println((i + 1) + ". " + questions.get(i).getQuestionText());
        }
    }

    private void startQuiz() {
        List<Quiz> quizzes = quizController.showQuizzes(currentUser.getUserId());
        if (quizzes.isEmpty()) {
            System.out.println("No quizzes available.");
            return;
        }

        System.out.println("Available Quizzes:");
        for (int i = 0; i < quizzes.size(); i++) {
            System.out.println((i + 1) + ". " + quizzes.get(i).getQuizName());
        }

        System.out.print("Select a quiz by number: ");
        int quizIndex = scanner.nextInt();
        scanner.nextLine();

        if (quizIndex <= 0 || quizIndex > quizzes.size()) {
            System.out.println("Invalid quiz number.");
            return;
        }

        int quizId = quizzes.get(quizIndex - 1).getQuizId();

        boolean restartQuiz;
        do {
            restartQuiz = false;

            List<Question> allQuestions = questionController.getQuestionsByQuiz(quizId);

            if (allQuestions.isEmpty()) {
                System.out.println("No questions available for this quiz.");
                return;
            }

            List<Question> questionsWithAnswers = new ArrayList<>();
            for (Question question : allQuestions) {
                List<Answer> answers = answerController.getAnswersByQuestion(question.getQuestionId());
                if (!answers.isEmpty()) {
                    questionsWithAnswers.add(question);
                }
            }

            if (questionsWithAnswers.isEmpty()) {
                System.out.println("No valid questions (with answers) available for this quiz.");
                return;
            }

            Collections.shuffle(questionsWithAnswers);

            int totalQuestions = questionsWithAnswers.size();
            int correctAnswers = 0;

            List<IncorrectAnswer> incorrectAnswers = new ArrayList<>();

            System.out.println("Starting the quiz...");

            for (int i = 0; i < totalQuestions; i++) {
                Question question = questionsWithAnswers.get(i);
                System.out.println("Question " + (i + 1) + ": " + question.getQuestionText());

                List<Answer> answers = answerController.getAnswersByQuestion(question.getQuestionId());

                Collections.shuffle(answers);

                for (int j = 0; j < answers.size(); j++) {
                    System.out.println((j + 1) + ". " + answers.get(j).getAnswerText());
                }

                System.out.print("Enter the number of your answer: ");
                int answerIndex = scanner.nextInt();
                scanner.nextLine();

                if (answerIndex > 0 && answerIndex <= answers.size()) {
                    Answer userAnswer = answers.get(answerIndex - 1);
                    if (userAnswer.isCorrectAnswer()) {
                        System.out.println("Correct!");
                        correctAnswers++;
                    } else {
                        System.out.println("Wrong.");

                        Answer correctAnswer = answers.stream().filter(Answer::isCorrectAnswer).findFirst().orElse(null);
                        incorrectAnswers.add(new IncorrectAnswer(question, userAnswer, correctAnswer));
                    }
                } else {
                    System.out.println("Invalid answer.");
                }

                System.out.println();
            }

            System.out.println("Quiz Finished!");
            System.out.println("You got " + correctAnswers + " out of " + totalQuestions + " questions correct.");

            if (!incorrectAnswers.isEmpty()) {
                System.out.print("Would you like to review the questions you answered incorrectly? (yes/no): ");
                String response = scanner.nextLine().trim().toLowerCase();
                if (response.equals("yes")) {
                    System.out.println("Incorrectly answered questions:");
                    for (IncorrectAnswer incorrect : incorrectAnswers) {
                        System.out.println("Question: " + incorrect.getQuestion().getQuestionText());
                        System.out.println("Your Answer: " + incorrect.getUserAnswer().getAnswerText());
                        System.out.println("Correct Answer: " + (incorrect.getCorrectAnswer() != null ? incorrect.getCorrectAnswer().getAnswerText() : "No correct answer found."));
                        System.out.println();
                    }
                }
            }

            System.out.print("Would you like to restart this quiz? (yes/no): ");
            String restartResponse = scanner.nextLine().trim().toLowerCase();
            if (restartResponse.equals("yes")) {
                restartQuiz = true;
            }

        } while (restartQuiz);

        System.out.println("Thank you for taking the quiz!");
    }

}