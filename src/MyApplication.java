import controllers.AuthController;
import controllers.QuestionController;
import controllers.QuizController;
import controllers.AnswerController;

import models.*;

import java.util.*;

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
                            quizMenu();
                        }
                    }
                    case 0 -> {
                        System.out.println("\n*************************\n");
                        System.out.println("Goodbye!");
                        System.out.println("\n*************************\n");
                        running = false;
                    } // Завершение работы приложения
                    default -> System.out.println("Invalid option. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Input must be an integer. " + e.getMessage());
                scanner.next(); // Очистка неверного ввода
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }

            System.out.println("*************************");
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
                    case 0 -> {
                        System.out.println("\n*************************\n");
                        System.out.println("Logging out...");
                        System.out.println("\n*************************\n");
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
            System.out.println("*************************");
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
            System.out.println("*************************");
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
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            String response = authController.registerUser(username, password);
            System.out.println(response);

            if (response.equals("Registration successful!")) {
                currentUser = authController.loginUser(username, password);
                if (currentUser != null) {
                    System.out.println("Registration and login successful! Welcome, " + currentUser.getUser_name());
                    quizMenuHandler();
                }
            }
        } catch (Exception e) {
            System.out.println("Error in registration: " + e.getMessage());
        }
    }

    public void getLogin() {
        try {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            User user = authController.loginUser(username, password);
            if (user != null) {
                currentUser = user;
                System.out.println("Login successful! Welcome, " + user.getUser_name());
                quizMenuHandler();
            } else {
                System.out.println("Invalid username or password. Please try again.");
            }
        } catch (Exception e) {
            System.out.println("Error in login: " + e.getMessage());
        }
    }



    private void createQuiz() {
        if (currentUser == null) {
            System.out.println("You must be logged in to create a quiz.");
            return;
        }

        System.out.print("Enter quiz name: ");
        String quizName = scanner.nextLine();

        String response = quizController.createQuiz(quizName, currentUser.getUser_id());
        System.out.println(response);
    }

    private void showQuizzes() {
        if (currentUser == null) {
            System.out.println("You must be logged in to view quizzes.");
            return;
        }

        List<Quiz> quizzes = quizController.showQuizzes(currentUser.getUser_id());
        System.out.println("Your Quizzes:");
        quizzes.forEach(quiz -> System.out.println(quiz.getQuizName()));
    }

    public void deleteQuiz() {
        System.out.println("Select quiz to delete:");

        List<Quiz> quizzes = quizController.showQuizzes(currentUser.getUser_id());
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

        List<Quiz> quizzes = quizController.showQuizzes(currentUser.getUser_id());
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

        List<Quiz> quizzes = quizController.showQuizzes(currentUser.getUser_id());
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

        List<Quiz> quizzes = quizController.showQuizzes(currentUser.getUser_id());
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
        List<Question> questions = getQuestionsForCurrentUser();
        if (questions.isEmpty()) {
            System.out.println("No questions available.");
            return;
        }

        displayQuestions(questions);
        System.out.print("Enter the number of the question to view answers: ");
        int questionIndex = scanner.nextInt();
        scanner.nextLine();

        if (questionIndex > 0 && questionIndex <= questions.size()) {
            int questionId = questions.get(questionIndex - 1).getQuestionId();
            List<Answer> answers = answerController.getAnswersByQuestion(questionId);
            if (answers.isEmpty()) {
                System.out.println("No answers found for this question.");
            } else {
                System.out.println("Answers:");
                answers.forEach(a -> System.out.println("- " + a.getAnswerText() + " (Correct: " + a.isCorrectAnswer() + ")"));
            }
        } else {
            System.out.println("Invalid question number.");
        }
    }

    private void addAnswer() {
        List<Question> questions = getQuestionsForCurrentUser();
        if (questions.isEmpty()) {
            System.out.println("No questions available.");
            return;
        }

        displayQuestions(questions);
        System.out.print("Enter the number of the question to add an answer: ");
        int questionIndex = scanner.nextInt();
        scanner.nextLine();

        if (questionIndex > 0 && questionIndex <= questions.size()) {
            int questionId = questions.get(questionIndex - 1).getQuestionId();
            System.out.print("Enter answer text: ");
            String answerText = scanner.nextLine();
            System.out.print("Is this answer correct? (true/false): ");
            boolean isCorrect = scanner.nextBoolean();
            scanner.nextLine();

            Answer answer = new Answer(0, answerText, isCorrect, questionId);
            if (answerController.addAnswer(answer)) {
                System.out.println("Answer added successfully.");
            } else {
                System.out.println("Failed to add answer.");
            }
        } else {
            System.out.println("Invalid question number.");
        }
    }

    private void deleteAnswer() {
        List<Question> questions = getQuestionsForCurrentUser();
        if (questions.isEmpty()) {
            System.out.println("No questions available.");
            return;
        }

        displayQuestions(questions);
        System.out.print("Enter the number of the question to delete an answer: ");
        int questionIndex = scanner.nextInt();
        scanner.nextLine();

        if (questionIndex > 0 && questionIndex <= questions.size()) {
            int questionId = questions.get(questionIndex - 1).getQuestionId();
            List<Answer> answers = answerController.getAnswersByQuestion(questionId);

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

            if (answerIndex > 0 && answerIndex <= answers.size()) {
                int answerId = answers.get(answerIndex - 1).getAnswerId();
                if (answerController.deleteAnswer(answerId)) {
                    System.out.println("Answer deleted successfully.");
                } else {
                    System.out.println("Failed to delete answer.");
                }
            } else {
                System.out.println("Invalid answer number.");
            }
        } else {
            System.out.println("Invalid question number.");
        }
    }

    private List<Question> getQuestionsForCurrentUser() {
        List<Quiz> quizzes = quizController.showQuizzes(currentUser.getUser_id());
        List<Question> allQuestions = new ArrayList<>();

        for (Quiz quiz : quizzes) {
            allQuestions.addAll(questionController.getQuestionsByQuiz(quiz.getQuizId()));
        }

        return allQuestions;
    }

    private void displayQuestions(List<Question> questions) {
        System.out.println("Questions:");
        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            System.out.println((i + 1) + ". " + question.getQuestionText());
        }
    }

    private void startQuiz() {
        List<Quiz> quizzes = quizController.showQuizzes(currentUser.getUser_id());
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
