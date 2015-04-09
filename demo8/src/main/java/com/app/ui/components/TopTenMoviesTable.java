/**
* TopTenMoviesTable.java
* appEducacionalVaadin
* 15/1/2015 21:15:11
* Copyright David
* com.app.ui.components
*/
package com.app.ui.components;

import java.text.DecimalFormat;

import com.vaadin.data.Property;
import com.vaadin.ui.Table;
import com.vaadin.ui.themes.ValoTheme;

/**
 * @author David
 *
 */
public class TopTenMoviesTable extends Table{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9171156784711547709L;

	@Override
    protected String formatPropertyValue(final Object rowId,
            final Object colId, final Property<?> property) {
        String result = super.formatPropertyValue(rowId, colId, property);
        if (colId.equals("revenue")) {
            if (property != null && property.getValue() != null) {
                Double r = (Double) property.getValue();
                String ret = new DecimalFormat("#.##").format(r);
                result = "$" + ret;
            } else {
                result = "";
            }
        }
        return result;
    }

    public TopTenMoviesTable() {
        setCaption("Top 10");

        addStyleName(ValoTheme.TABLE_BORDERLESS);
        addStyleName(ValoTheme.TABLE_NO_STRIPES);
        addStyleName(ValoTheme.TABLE_NO_VERTICAL_LINES);
        addStyleName(ValoTheme.TABLE_SMALL);
        setSortEnabled(false);
        setColumnAlignment("revenue", Align.RIGHT);
        setRowHeaderMode(RowHeaderMode.INDEX);
        setColumnHeaderMode(ColumnHeaderMode.HIDDEN);
        setSizeFull(); 	
        
        setSortAscending(false);
    }

	
}
