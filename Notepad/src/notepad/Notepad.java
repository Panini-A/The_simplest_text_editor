
package notepad;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.*;

public class Notepad {
    
    private final String NAME = "Новый файл"; // Название вкладки
    
    private JFileChooser f = new JFileChooser(); // Создание окна сохранения файла
    
    private JTabbedPane tabs = new JTabbedPane(); // Создание вкладки нового файла
  
    public static void main(String[] args) {
        
        SwingUtilities.invokeLater(new Runnable() {
            
            public void run() { // Метод, который вызывает конструтор класса Notepad
                new Notepad();
            }
        });
    }
    
    public Notepad(){ // Реализуем конструктор
        
        JMenuBar menu = new JMenuBar(); // Создаем меню
        
        JMenu file = new JMenu("Файл"); // Создаем вкладку Файл
        
        JMenuItem newFile = new JMenuItem("Создать файл"); // Создаем подвкладку
        JMenuItem openFile = new JMenuItem("Открыть файл"); // Создаем подвкладку
        JMenuItem saveFile = new JMenuItem("Сохранить файл"); // Создаем подвкладку
        
        // Добавление во вкладку Файл всех подвкладок
        file.add(newFile);
        file.add(openFile);
        file.add(saveFile);
        
        // Добавление в меню вкладки Файл
        menu.add(file);
        
        JFrame window = new JFrame("Текстовый редактор"); // Создаем главное окно программы 
        window.setSize(800, 600); // Задаем размер окна
        
        window.setJMenuBar(menu); // Добавление меню на главное окно программы
        
        window.add(tabs); // Добавление вкладки нового файла на главное окно программы
                
        window.setResizable(false); // Нельзя изменять размер окна
        window.setLocationRelativeTo(null); // Расположение программы в центре экрана
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Закрывает программу при нажатии на крестик
        window.setVisible(true); // Для отображения
        
        newFile.addActionListener(new ActionListener() { // При нажатии на подвкладку Создать файл происходит:
            
            public void actionPerformed(ActionEvent e) {
                
                JTextArea text = new JTextArea(); // Создание текстового поля на главном окне программы
                
                Scroll scroll = new Scroll(text, false, ""); // Создание прокрутки (с помощью класса Scroll)
                
                tabs.addTab(NAME, scroll); // Добавление вкладки нового файла на главное окно программы с возможностью прокрутки
            }
        });
        
        saveFile.addActionListener(new ActionListener() { // При нажатии на подвкладку Сохранить файл происходит:
            
            public void actionPerformed(ActionEvent e) {
                
                Scroll text = (Scroll)tabs.getSelectedComponent(); 

                String output = text.getText();
                
                if (tabs.countComponents() != 0){ // Если открыта хотя бы одна вкладка с файлом, то подвкладка Сохранить файл - активна,
                                                  // В противном случае - нет
                    if (text.isOpened()){ // Если файл был открыт, а не только что создан, то при нажатии на Сохранить файл
                                          // окно сохранения файла не будет выведено на экран, а данный файл перезапишется автоматически 
                        try{
                            FileOutputStream writer = new FileOutputStream(text.getPath()); // Создаем экземпляр класса
                            writer.write(output.getBytes());
                        } catch(IOException eq){eq.printStackTrace();} // Выводит трассировку стэка   
                    } 
                    
                    else{

                        f.showSaveDialog(null); // Вызывает окно сохранения файла(оно выводится в центре экрана)

                        File file = f.getSelectedFile(); // Получаем файл, который будем сохранять

                        try{
                            FileOutputStream writer = new FileOutputStream(file); // Создаем экземпляр класса
                            writer.write(output.getBytes());
                        } catch(IOException eq){eq.printStackTrace();} // Выводит трассировку стэка
                    }
                }  
            }
        });
        
        openFile.addActionListener(new ActionListener() { // При нажатии на подвкладку Открыть файл происходит:
            
            public void actionPerformed(ActionEvent e) {
                
                try{
                    f.showOpenDialog(null); // Вызывает окно выбора файла, который нужно открыть (оно выводится в центре экрана)

                    File file = f.getSelectedFile(); // Получаем файл, который будем открывать
                
                    String input = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
                    
                    JTextArea text = new JTextArea(input); // Запишет все что есть в файле
                    
                    Scroll scroll = new Scroll(text, true, file.getAbsolutePath());
                    
                    tabs.addTab(file.getName(), scroll); // Выводит на экран всю информацию из файла
                } catch(IOException el){el.printStackTrace();} // Выводит трассировку стэка
            }
        });
    }
}

                