import java.util.*;

class User {
    String username;
    String password;

    User(String u, String p) {
        username = u;
        password = p;
    }
}

class Question {
    String question;
    String[] options;
    int correctAnswer;

    Question(String q, String[] opt, int ans) {
        question = q;
        options = opt;
        correctAnswer = ans;
    }
}

public class OnlineExamSystem {

    static Scanner sc = new Scanner(System.in);
    static User user = new User("student", "1234");
    static List<Question> questions = new ArrayList<>();
    static int score = 0;
    static boolean timeUp = false;

    public static void main(String[] args) {

        loadQuestions();

        System.out.println("===== ONLINE EXAM SYSTEM =====");

        if (!login()) {
            System.out.println("Too many failed attempts. Exiting...");
            return;
        }

        int choice;
        do {
            System.out.println("\n1. Update Profile");
            System.out.println("2. Start Exam");
            System.out.println("3. Logout");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    updateProfile();
                    break;
                case 2:
                    startExam();
                    break;
                case 3:
                    System.out.println("Logged out successfully.");
                    break;
                default:
                    System.out.println("Invalid choice");
            }

        } while (choice != 3);
    }

    // LOGIN
    static boolean login() {
        int attempts = 3;

        while (attempts > 0) {
            System.out.print("Enter username: ");
            String u = sc.next();

            System.out.print("Enter password: ");
            String p = sc.next();

            if (u.equals(user.username) && p.equals(user.password)) {
                System.out.println("Login successful!\n");
                return true;
            } else {
                attempts--;
                System.out.println("Wrong credentials. Attempts left: " + attempts);
            }
        }
        return false;
    }

    // UPDATE PROFILE
    static void updateProfile() {
        System.out.print("Enter new username: ");
        user.username = sc.next();

        System.out.print("Enter new password: ");
        user.password = sc.next();

        System.out.println("Profile updated successfully!");
    }

    // LOAD QUESTIONS
    static void loadQuestions() {
        questions.add(new Question(
                "What is Java?",
                new String[]{"Language", "OS", "DB", "Browser"},
                1
        ));

        questions.add(new Question(
                "Which keyword is used for inheritance?",
                new String[]{"this", "extends", "super", "import"},
                2
        ));

        questions.add(new Question(
                "Which method is entry point?",
                new String[]{"start()", "main()", "run()", "init()"},
                2
        ));
    }

    // START EXAM
    static void startExam() {
        score = 0;
        timeUp = false;

        System.out.println("\nExam Started! You have 30 seconds...\n");

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                timeUp = true;
                System.out.println("\n⏰ Time is up! Auto submitting exam...\n");
            }
        }, 30000); // 30 seconds

        for (Question q : questions) {

            if (timeUp) break;

            System.out.println(q.question);

            for (int i = 0; i < q.options.length; i++) {
                System.out.println((i + 1) + ". " + q.options[i]);
            }

            System.out.print("Your answer: ");
            int ans = sc.nextInt();

            if (ans == q.correctAnswer) {
                score++;
            }
        }

        timer.cancel();

        System.out.println("\n===== EXAM FINISHED =====");
        System.out.println("Your Score: " + score + "/" + questions.size());
    }
}