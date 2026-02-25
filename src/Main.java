import files.FileManager;
import manager.UniversityManager;
import ui.Menu;

public class Main {
    public static void main(String[] args) {
        UniversityManager manager     = new UniversityManager();
        FileManager       fileManager = new FileManager();

        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║         Loading saved data...            ║");
        System.out.println("╚══════════════════════════════════════════╝");

        fileManager.loadStudents(manager);
        fileManager.loadCourses(manager);

        Menu menu = new Menu(manager, fileManager);
        menu.start();
    }
}