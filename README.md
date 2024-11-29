1. Job.java (Model class for the Job)
java
Copy code
public class Job {
    private String title;
    private List<String> requiredSkills;
    private String location;
    private int minimumExperience;

    public Job(String title, List<String> requiredSkills, String location, int minimumExperience) {
        this.title = title;
        this.requiredSkills = requiredSkills;
        this.location = location;
        this.minimumExperience = minimumExperience;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getRequiredSkills() {
        return requiredSkills;
    }

    public String getLocation() {
        return location;
    }

    public int getMinimumExperience() {
        return minimumExperience;
    }
}

public class Job: Declares the class Job.
private String title;: Declares a private variable for the job title.
private List<String> requiredSkills;: Declares a private list to store the skills required for the job.
private String location;: Declares a private variable to store the job's location.
private int minimumExperience;: Declares a private variable to store the minimum experience required for the job.
Constructor public Job(...): Initializes a new job object using the title, skills, location, and experience provided.
Getter Methods: Each getter method retrieves the corresponding job attribute.

2. JobSeeker.java (Model class for the Job Seeker)
java
Copy code
import java.util.List;

public class JobSeeker {
    private String name;
    private String email;
    private int age;
    private int experience;
    private String location;
    private List<String> skills;

    public JobSeeker(String name, String email, int age, int experience, String location, List<String> skills) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.experience = experience;
        this.location = location;
        this.skills = skills;
    }

    public double calculateMatch(Job job) {
        double skillMatch = skills.stream().filter(job.getRequiredSkills()::contains).count() / (double) job.getRequiredSkills().size() * 60;
        double locationMatch = location.equalsIgnoreCase(job.getLocation()) ? 20 : 0;
        double experienceMatch = experience >= job.getMinimumExperience() ? 20 : 0;
        return skillMatch + locationMatch + experienceMatch;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return age;
    }

    public int getExperience() {
        return experience;
    }

    public String getLocation() {
        return location;
    }

    public List<String> getSkills() {
        return skills;
    }
}

public class JobSeeker: Declares the JobSeeker class.
private String name, email;: Declares private variables to store job seeker’s name and email.
private int age, experience;: Declares private variables for age and years of experience.
private String location;: Declares a private variable to store the job seeker's location.
private List<String> skills;: Declares a private list to store the skills of the job seeker.
Constructor public JobSeeker(...): Initializes a new job seeker object.
calculateMatch(Job job): This method compares the job seeker's details (skills, location, experience) with the job's requirements and calculates a match percentage:
skills.stream().filter(...): This filters the job seeker’s skills and matches them with the job’s required skills, calculating the match percentage (60% weight).
location.equalsIgnoreCase(...): Checks if the job seeker’s location matches the job’s location (20% weight).
experience >= job.getMinimumExperience(): Checks if the job seeker’s experience meets the minimum requirement for the job (20% weight).
The sum of these three percentages is returned as the match score.

3. InvalidInputException.java (Custom Exception class)
java
Copy code
public class InvalidInputException extends Exception {
    public InvalidInputException(String message) {
        super(message);
    }
}

public class InvalidInputException: Declares a custom exception class that extends the Exception class.
public InvalidInputException(String message): The constructor takes a message string and passes it to the superclass (Exception) constructor.

4. JobPortalMatchingSystemCSE215LabProject.java (Main class with GUI)
java
Copy code
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.List;

public class JobPortalMatchingSystemCSE215LabProject extends JFrame implements ActionListener {
    private JTextField tfName, tfEmail, tfAge, tfExperience, tfLocation, tfSkills;
    private JLabel lblResult;
    private JButton btnCalculate;
    private final String jobTitle = "Software Developer";
    private final List<String> jobSkills = Arrays.asList("Java", "SQL", "Spring", "Python");
    private final String jobLocation = "Dhaka";
    private final int jobExperience = 3;

    public JobPortalMatchingSystemCSE215LabProject() {
        setTitle("Job Portal Matching System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(0, 2));

        add(new JLabel("Enter Your Details:"));
        add(new JLabel());
        add(new JLabel("Name:"));
        tfName = new JTextField();
        add(tfName);
        add(new JLabel("Email:"));
        tfEmail = new JTextField();
        add(tfEmail);
        add(new JLabel("Age:"));
        tfAge = new JTextField();
        add(tfAge);
        add(new JLabel("Years of Experience:"));
        tfExperience = new JTextField();
        add(tfExperience);
        add(new JLabel("Location:"));
        tfLocation = new JTextField();
        add(tfLocation);
        add(new JLabel("Skills (comma-separated):"));
        tfSkills = new JTextField();
        add(tfSkills);

        btnCalculate = new JButton("Calculate Match");
        btnCalculate.addActionListener(this);
        add(btnCalculate);
        lblResult = new JLabel("Match Percentage: ");
        lblResult.setFont(new Font("Arial", Font.BOLD, 14));
        add(lblResult);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String name = tfName.getText().trim();
            String email = tfEmail.getText().trim();
            int age = Integer.parseInt(tfAge.getText().trim());
            int experience = Integer.parseInt(tfExperience.getText().trim());
            String location = tfLocation.getText().trim();
            List<String> skills = Arrays.asList(tfSkills.getText().trim().split("\\s*,\\s*"));

            JobSeeker seeker = new JobSeeker(name, email, age, experience, location, skills);
            Job job = new Job(jobTitle, jobSkills, jobLocation, jobExperience);

            double totalMatch = seeker.calculateMatch(job);
            lblResult.setText("<html>Match Percentage: <b>" + totalMatch + "%</b></html>");

            JOptionPane.showMessageDialog(this, String.format(
                    "Job Title: %s\nRequired Skills: %s\nLocation: %s\nMinimum Experience: %d years\n\nYour Match Percentage: %.2f%%",
                    jobTitle, String.join(", ", jobSkills), jobLocation, jobExperience, totalMatch),
                    "Match Results", JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numerical values for Age and Experience.",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (InvalidInputException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input: " + ex.getMessage(),
                    "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "An error occurred: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new JobPortalMatchingSystemCSE215LabProject();
    }
}

Imports: Java libraries for GUI (JFrame, JTextField, JLabel, etc.), event handling (ActionListener, ActionEvent), and utility classes (Arrays, List).
public class JobPortalMatchingSystemCSE215LabProject: Declares the main class for the Job Portal system.
Private fields (tfName, tfEmail, etc.): Declare Swing components (text fields, buttons, labels) for user input and result display.
public JobPortalMatchingSystemCSE215LabProject(): Constructor that sets up the window,
arranges the layout, and initializes the components (like text fields, labels, and buttons).
setTitle(...): Sets the title of the window.
setSize(600, 400): Specifies the window size.
setLayout(new GridLayout(0, 2)): Defines the layout of components in a grid.
Adding Components: It adds labels, text fields, and the "Calculate Match" button to the window.
btnCalculate.addActionListener(this): Adds an action listener to the button, so when it’s clicked, the actionPerformed method is called.
setVisible(true): Makes the window visible.
ActionPerformed Method:
actionPerformed(ActionEvent e): This method is triggered when the "Calculate Match" button is clicked. It:
Retrieves the input from the text fields.
Converts string inputs like age and experience into integers.
Creates a JobSeeker object and a Job object with predefined job requirements.
Calls the calculateMatch() method to calculate the match percentage based on skills, location, and experience.
Displays the match percentage in a label and shows the results in a message dialog.
Handles exceptions for invalid input (such as non-numeric values for age or experience).

Summary:
The system compares a job seeker's information with predefined job requirements (job title, skills, location, experience) to calculate a match percentage.
The code includes error handling for invalid input (e.g., invalid numbers) and uses a custom exception (InvalidInputException).
The GUI (Graphical User Interface) is built using Java Swing components like text fields, buttons, and labels.
The main logic of matching the job seeker’s attributes with the job's requirements is encapsulated in the JobSeeker class.
The result is shown both in a label on the window and in a popup message.


he structure of the code for the Job Portal Matching System is designed in a modular way using classes, packages, and encapsulation. Here's a detailed explanation of the structure:
1. Project Structure
The overall project structure in an Eclipse-based Java application might look like this:
scss
Copy code
JobPortalMatchingSystemCSE215LabProject/
│
├── src/ (Source folder where Java files are stored)
│   ├── job/ (Package to contain job-related classes)
│   │   └── Job.java
│   │   └── JobSeeker.java
│   │   └── InvalidInputException.java
│   └── JobPortalMatchingSystemCSE215LabProject.java
│
└── bin/ (Compiled .class files)

2. Class Structure
The project uses four main classes and is split into packages to reflect a clean architecture:
a. Job.java (in job package)
This class represents the job itself. It contains the attributes and methods for a job, like job title, required skills, location, and minimum experience.
Attributes:
String title: Job title (e.g., Software Developer).
List<String> requiredSkills: List of required skills for the job.
String location: The location where the job is based.
int minimumExperience: Minimum required years of experience for the job.
Constructor: Initializes a job with the specified attributes.
Getter methods: Provides access to job details like title, required skills, location, and minimum experience.
b. JobSeeker.java (in job package)
This class represents a job seeker. It contains the personal details and skills of the user.
Attributes:
String name: Job seeker's name.
String email: Job seeker's email.
int age: Age of the job seeker.
int experience: Years of experience the job seeker has.
String location: Job seeker's location.
List<String> skills: List of skills the job seeker has.
Constructor: Initializes a job seeker with personal details and skills.
Methods:
calculateMatch(Job job): Calculates the match percentage between the job seeker and the job based on skills, location, and experience.
c. InvalidInputException.java (in job package)
This is a custom exception class that extends Exception to handle invalid input scenarios (such as non-numeric values for age or experience).
Constructor: Takes a message as a parameter to explain the error when the exception is thrown.
d. JobPortalMatchingSystemCSE215LabProject.java (Main class)
This is the main class that runs the application with the graphical user interface (GUI).
Attributes:
Text fields to collect user inputs like tfName, tfEmail, tfAge, tfExperience, tfLocation, and tfSkills.
btnCalculate: A button to trigger the calculation of job matching.
lblResult: A label to display the match percentage.
Constructor: Sets up the window's layout, adds the input fields, labels, and buttons, and makes the window visible.
actionPerformed method: Handles the user interaction when the button is clicked. It:
Retrieves values from the text fields.
Converts text input for age and experience into integers.
Creates instances of JobSeeker and Job.
Calculates the match percentage by calling calculateMatch() on the JobSeeker object.
Displays the match percentage in a popup window and on the label.
main method: Initializes the application by creating an instance of the main class.
3. Packages and Class Responsibilities
The classes are structured as follows:
job Package: Contains all classes related to the data models (Job, JobSeeker, and the custom exception).
Job: Represents the job details.
JobSeeker: Represents the job seeker’s details.
InvalidInputException: Handles custom exceptions for invalid input.
JobPortalMatchingSystemCSE215LabProject.java: The Main GUI Class that connects everything together. This class runs the application and allows the user to interact with the system.
4. Flow of the Program
Initialization:
The program initializes a GUI window where users input their details (name, age, email, etc.) and required skills.
Predefined job details like job title, skills, location, and experience are set in the code.
User Input:
The user enters their details and clicks the "Calculate Match" button.
Job Matching Calculation:
The actionPerformed method retrieves user input, creates JobSeeker and Job objects, and calculates the match using the calculateMatch() method.
Result Display:
The match percentage is displayed on the screen (in the label) and a message popup with detailed information about the job and match percentage.
5. Overall Flow Summary:
User enters details (name, email, experience, skills, etc.) via GUI.
Match calculation is performed based on:
Matching skills.
Location comparison.
Experience check.
Results are displayed in a label and a pop-up message box.

Conclusion:
The structure follows modular programming principles, where each class has its distinct responsibility:
Job and JobSeeker store data and handle business logic like matching criteria.
InvalidInputException handles errors related to input validation.
JobPortalMatchingSystemCSE215LabProject provides the user interface and connects all components, allowing the user to interact with the system.
This modular design allows for easier maintenance and future extensions (e.g., adding more job criteria, storing data in a database, etc.).
If you need more clarification or further details, feel free to ask!

