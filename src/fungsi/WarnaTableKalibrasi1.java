/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fungsi;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Owner
 */
public class WarnaTableKalibrasi1 extends DefaultTableCellRenderer {
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (row % 2 == 1) {
            component.setBackground(new Color(255, 246, 244));
            component.setForeground(new Color(50, 50, 50));
        } else {
            component.setBackground(new Color(255, 255, 255));
            component.setForeground(new Color(50, 50, 50));
        } 

         if (column == 13) {
            if (table.getValueAt(row, 12).toString().equals("Belum Kadaluarsa")) {
                component.setBackground(Color.GREEN);
                component.setForeground(Color.BLUE);
            } else if (table.getValueAt(row, 12).toString().equals("Belum di Kalibrasi")) {
                component.setBackground(Color.YELLOW);
                component.setForeground(Color.BLUE);
            } else if (table.getValueAt(row, 12).toString().equals("Kadaluarsa")) {
                component.setBackground(Color.RED);
                component.setForeground(Color.WHITE);
            }
      }

        return component;
    }

}
