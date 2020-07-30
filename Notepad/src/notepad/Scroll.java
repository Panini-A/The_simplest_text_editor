
package notepad;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Scroll extends JScrollPane {
    
    private final String path;
    
    private final boolean isOpened;
    
    private final JTextArea text;
    
    public Scroll(JTextArea text, boolean isOpened, String path){ // Реализуем конструктор
        super(text);
        this.text = text;
        this.isOpened = isOpened;
        this.path = path;
    }
    
    public String getText(){ // Инициализируем метод
        return text.getText();
    } 
    
     public boolean isOpened(){ // Инициализируем метод
        return isOpened;
    } 
     
    public String getPath(){ // Инициализируем метод
        return path;
    }  
}
