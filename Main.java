import java.io.*;
import java.util.*;

class Student {
    String name, gender, city, admissionStatus;
    int age;
    double admissionTestScore, highSchoolPercentage;

    public Student(String[] data) {
        this.name = data[0].trim();
        this.age = parseIntSafe(data[1]);
        this.gender = data[2].trim();
        this.admissionTestScore = parseDoubleSafe(data[3]);
        this.highSchoolPercentage = parseDoubleSafe(data[4]);
        this.city = data[5].trim();
        this.admissionStatus = data.length > 6 ? data[6].trim() : "Unknown";
    }

    private int parseIntSafe(String value) {
        try {
            return Integer.parseInt(value.trim());
        } catch (Exception e) {
            return -1;
        }
    }

    private double parseDoubleSafe(String value) {
        try {
            return Double.parseDouble(value.trim());
        } catch (Exception e) {
            return -1;
        }
    }

    public String toString() {
        return String.format("Name: %s, Age: %d, Test Score: %.2f, High School %%: %.2f, City: %s, Status: %s",
                name, age, admissionTestScore, highSchoolPercentage, city, admissionStatus);
    }
}

public class Main {
    public static void main(String[] args) {
        String csvFile = "C:/Users/NABILA/IdeaProjects/Tugas3kamil/src/student_admission_record_dirty.csv"; // Pastikan file ini ada di folder project
        List<Student> students = new ArrayList<>();

        // Baca file CSV
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            boolean isFirst = true;
            while ((line = br.readLine()) != null) {
                if (isFirst) { isFirst = false; continue; }
                String[] data = line.split(",", -1);
                if (data.length >= 6 && !data[0].trim().isEmpty()) {
                    students.add(new Student(data));
                }
            }
        } catch (IOException e) {
            System.out.println("Gagal membaca file: " + e.getMessage());
            return;
        }

        // Cetak data pertama
        System.out.println("Contoh data pertama:");
        if (!students.isEmpty()) System.out.println(students.get(0));
        else {
            System.out.println("Data kosong.");
            return;
        }

        // Input nama dari pengguna
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nMasukkan nama siswa yang ingin dicari: ");
        String targetName = scanner.nextLine().trim();

        // Pencarian linear berdasarkan nama (tidak case sensitive)
        boolean found = false;
        System.out.println("\nHasil pencarian untuk '" + targetName + "':");
        for (Student s : students) {
            if (s.name.equalsIgnoreCase(targetName)) {
                System.out.println(s);
                found = true;
            }
        }
        if (!found) System.out.println("Data tidak ditemukan.");

        // Bubble sort berdasarkan admissionTestScore
        for (int i = 0; i < students.size() - 1; i++) {
            for (int j = 0; j < students.size() - i - 1; j++) {
                if (students.get(j).admissionTestScore > students.get(j + 1).admissionTestScore) {
                    Collections.swap(students, j, j + 1);
                }
            }
        }

        // Tampilkan 3 siswa dengan nilai tes terendah
        System.out.println("\n3 Siswa dengan Admission Test Score terendah:");
        for (int i = 0; i < Math.min(3, students.size()); i++) {
            System.out.println(students.get(i));
        }

        scanner.close();
    }
}
