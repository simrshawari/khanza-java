/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fungsi;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Owner
 */
public class WarnaTableKalibrasi extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        
        // Set default color
        component.setBackground(Color.WHITE);
        component.setForeground(Color.BLACK);

        // Get the value from the table
        Object cellValue = table.getValueAt(row, 12);
        if (cellValue != null) {
            String cellValueStr = cellValue.toString();
            switch (cellValueStr) {
                case "Belum Kadaluarsa":
                    component.setBackground(Color.GREEN);
                    component.setForeground(Color.BLUE);
                    break;
                case "Belum di Kalibrasi":
                    component.setBackground(Color.YELLOW);
                    component.setForeground(Color.BLUE);
                    break;
                case "Kadaluarsa":
                    component.setBackground(Color.RED);
                    component.setForeground(Color.WHITE);
                    break;
                case "":
                    component.setBackground(Color.WHITE);
                    component.setForeground(Color.WHITE);
                    break;
                default:
                    // You can keep the default color or add additional cases here.
                    break;
            }
        }

        return component;
    }
}