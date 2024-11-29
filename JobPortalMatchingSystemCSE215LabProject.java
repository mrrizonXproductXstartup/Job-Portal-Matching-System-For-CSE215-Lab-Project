package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import exceptions.InvalidInputException;
import model.Job;
import model.JobSeeker;

public class JobPortalMatchingSystemCSE215LabProject extends JFrame implements ActionListener {
    private JTextField tfName, tfEmail, tfAge, tfExperience, tfLocation, tfSkills;
    private JLabel lblResult;
    private JButton btnCalculate;
    private Job job;

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

        job = new Job("Software Developer", Arrays.asList("Java", "SQL", "Spring", "Python"), "Dhaka", 3);

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

            if (name.isEmpty() || email.isEmpty() || location.isEmpty() || skills.isEmpty()) {
                throw new InvalidInputException("All fields must be filled.");
            }

            JobSeeker jobSeeker = new JobSeeker(name, email, age, skills, location, experience);

            double matchPercentage = jobSeeker.calculateMatch(job);

            lblResult.setText("<html>Match Percentage: <b>" + matchPercentage + "%</b></html>");

            String jobDetails = String.format(
                "Job Title: %s\nRequired Skills: %s\nLocation: %s\nMinimum Experience: %d years",
                job.getTitle(),
                String.join(", ", job.getRequiredSkills()),
                job.getLocation(),
                job.getMinimumExperience()
            );

            String userDetails = String.format(
                "Your Match Percentage: %.2f%%\n\nName: %s\nEmail: %s\nExperience: %d years\nLocation: %s\nSkills: %s",
                matchPercentage,
                jobSeeker.getName(),
                jobSeeker.getEmail(),
                jobSeeker.getExperience(),
                jobSeeker.getLocation(),
                String.join(", ", jobSeeker.getSkills())
            );

            JOptionPane.showMessageDialog(this, jobDetails + "\n\n" + userDetails, "Job Details and Match", JOptionPane.INFORMATION_MESSAGE);

            saveResults(jobSeeker, matchPercentage);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numerical values for Age and Experience.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (InvalidInputException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Validation Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error saving results: " + ex.getMessage(), "File Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveResults(JobSeeker jobSeeker, double matchPercentage) throws IOException {
        File file = new File("results.txt");
        try (FileWriter writer = new FileWriter(file, true)) {
            writer.write(String.format("Name: %s\nEmail: %s\nMatch Percentage: %.2f%%\n\n", jobSeeker.getName(), jobSeeker.getEmail(), matchPercentage));
        }
    }

    public static void main(String[] args) {
        new JobPortalMatchingSystemCSE215LabProject();
    }
}
